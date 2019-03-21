package com.kim.admin.service;

import java.util.Hashtable;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.kim.admin.common.BeanConfig;
import com.kim.admin.dao.LoginUserDao;
import com.kim.admin.entity.LoginResult;
import com.kim.admin.entity.LoginUserEntity;
import com.kim.admin.entity.UserEntity;
import com.kim.admin.entity.UserRoleEntity;
import com.kim.admin.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.common.base.BaseService;
import com.kim.common.util.DateUtil;
import com.kim.common.util.Md5Algorithm;
import com.kim.common.util.StringUtil;

@Service
public class LdabLoginService extends BaseService {
	
	@Autowired
	private LoginUserDao loginUserDao;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserService userService;
	@Autowired
	private BeanConfig beanConfig;
	
	/** 域账号登录url */
    private static final String LDAP_URL_FORMAT = "ldap://%s:%s";
    /**
     * ldap登录
     */
    public LoginResult ldabLoginAuth(LoginUserEntity loginUser) {
    	LoginResult loginResult = checkLdapConfig();
        if(loginResult != null){
        	return loginResult;
        }
        loginResult = new LoginResult(loginUser.getUsername());
    	
    	String username = loginUser.getUsername();
    	String principalName = getPrincipalName(loginUser.getUsername());
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, principalName);
        env.put(Context.SECURITY_CREDENTIALS, loginUser.getPassword());
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, String.format(LDAP_URL_FORMAT, beanConfig.getLdapHost(), beanConfig.getLdapPort()));
        
        DirContext ctx = null;
        try {
            /**ldap登录，如果有异常说明登录失败*/
            ctx = new InitialDirContext(env);
            
            /**ldap获取用户信息*/
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = beanConfig.getLdapSearchFilter() + "=" + getSAMAccountName(username);
            //搜索域节点
            String searchBase = beanConfig.getLdapSearchBase();
            // 定制返回属性
            String[] returnedAtts = {"mail", "pager", "company", "name", "displayName", "sAMAccountName", "telephoneNumber"};
            searchCtls.setReturningAttributes(returnedAtts);
            // 不定制属性，将返回所有的属性集
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
            
            UserEntity userEntity = new UserEntity();
            int count = 0;
            while (answer.hasMoreElements()) {
                if (count > 1) {
                    logger.error("该用户名有多个相同的域账号, userName:{}", username);
                    return loginResult.setSuccess(false).setMsg("该用户名有多个相同的域账号");
                }
                Attributes attrs = answer.next().getAttributes();
                if (attrs != null) {
                    userEntity.setUsername(getAttr(attrs, "pager"));
                    userEntity.setName(getAttr(attrs, "displayName"));
                    userEntity.setPhone(getAttr(attrs, "telephoneNumber"));
                    userEntity.setEmail(getAttr(attrs, "mail"));
                }
                count++;
            }
            if (StringUtil.isBlank(userEntity.getUsername())) {
            	logger.error("域登录操作未获取到工号, userName:{}", username);
                return loginResult.setSuccess(false).setMsg("域登录操作未获取到工号");
            }
            /**
             * 有则update
             * 无则insert
             */
            userEntity.setUsername(username);
            userEntity.setPassword(Md5Algorithm.getInstance().md5Encode(loginUser.getPassword()));
            userEntity.setOrigins(UserEntity.ORIGINS_LDAP);
            userEntity.setOperUser("ldap");
            userEntity.setOperTime(DateUtil.getCurrentTime());
            
            LoginUserEntity tmpUser = loginUserDao.find(new LoginUserVo().setUsername(username));
            if (tmpUser == null) {
                logger.info("ldap登录通过，新增工号username:{}, name:{}", username, userEntity.getName());
                userService.insert(userEntity);
                
                UserRoleEntity userRole = new UserRoleEntity();
                userRole.setUsername(username);
                userRole.setRoleCode(UserRoleEntity.DEFAULT_USER_ROLE_CODE);
                userRole.setOperUser("ldap");
                userRole.setOperTime(DateUtil.getCurrentTime());
                logger.info("ldap登录通过, 新增用户:{} 增加用户默认权限:{}", username, UserRoleEntity.DEFAULT_USER_ROLE_CODE);
                userRoleService.insert(userRole);
                
            } else {
            	logger.info("ldap登录通过，更新工号username:{}, name:{}", username, userEntity.getName());
            	userService.update(userEntity);
            }
            
            logger.info("域账号登录成功! username:{}", username);
            return loginResult.setSuccess(true).setMsg("登录成功");
        } catch (AuthenticationException e) {
            logger.error("域账号用户名或密码不正确, username:{}", username);
            return loginResult.setSuccess(false).setMsg("域账号用户名或密码不正确");
        } catch (CommunicationException e) {
        	logger.error("域账号连接失败, username:"+ username, e);
        	return loginResult.setSuccess(false).setMsg("域账号登录连接失败, 请尝试通过工号方式登录!");
        } catch (Exception e) {
        	logger.error("域账号验证失败, 异常信息未知, username:"+username, e);
        	return loginResult.setSuccess(false).setMsg("域登录验证失败, 请尝试通过工号方式登录!");
        } finally {
            close(ctx);
        }
    }

	private LoginResult checkLdapConfig() {
		if(StringUtil.isBlank(beanConfig.getLdapHost()) || StringUtil.isBlank(beanConfig.getLdapPort())
				|| StringUtil.isBlank(beanConfig.getLdapSearchBase()) || StringUtil.isBlank(beanConfig.getLdapSearchFilter())
				|| StringUtil.isBlank(beanConfig.getLdapPrefix()) || StringUtil.isBlank(beanConfig.getLdapSuffix())){
        	logger.error("初始化加载ldap配置出错, ldap配置均不能为空, "
        		+ "ldapHost:{}, ldapPort:{}, ldapSearchBase:{}, ldapSearchFilter:{}, LdapPrefix:{}, LdapSuffix:{}", 
        			beanConfig.getLdapHost(), beanConfig.getLdapPort(), beanConfig.getLdapSearchBase(),
        			beanConfig.getLdapSearchFilter(), beanConfig.getLdapPrefix(), beanConfig.getLdapSuffix());
        	return new LoginResult(false, "域登录连接失败，请尝试工号登录!");
        }
		return null;
	}
    
    private String getPrincipalName(String userName) {
		
		String ldapPrefix = beanConfig.getLdapPrefix();
        if (userName.startsWith(ldapPrefix)) {
            userName = userName.substring(userName.indexOf(ldapPrefix) + 3, userName.length());
        }
        String ldapSuffix = beanConfig.getLdapSuffix();
        if (!userName.endsWith(ldapSuffix)) {
        	userName = userName + ldapSuffix;
        }
		return userName;
	}
    
    private String getSAMAccountName(String userName) {
    	String ldapPrefix = beanConfig.getLdapPrefix();
		if (userName.startsWith(ldapPrefix)) {
			userName = userName.substring(userName.indexOf(ldapPrefix) + 3, userName.length());
		}
		String ldapSuffix = beanConfig.getLdapSuffix();
		if (userName.endsWith(ldapSuffix)) {
			userName = userName.substring(0, userName.indexOf(ldapSuffix));
		}
		return userName;
	}

	private String getAttr(Attributes attrs, String name) throws NamingException {
		Attribute value = attrs.get(name);
		if (value == null) {
			return null;
		}
		NamingEnumeration<?> all = value.getAll();
		if (all == null) {
			return null;
		}
		Object obj = all.next();
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}

	private void close(DirContext ctx) {
		if (null != ctx) {
		    try {
		        ctx.close();
		    } catch (Exception e) {
		        logger.error("InitialDirContext关闭异常", e);
		    }
		}
	}

}
