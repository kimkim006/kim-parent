package com.kim.admin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.kim.admin.entity.LoginUserEntity;
import com.kim.admin.entity.TenantPolicyEntity;
import com.kim.admin.entity.UserAgentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kim.admin.common.BeanConfig;
import com.kim.admin.common.CommonConstant;
import com.kim.admin.dao.LoginUserDao;
import com.kim.admin.entity.AuthorityEntity;
import com.kim.admin.entity.FrontUser;
import com.kim.admin.entity.LoginResult;
import com.kim.admin.entity.MenuEntity;
import com.kim.admin.entity.RouteConfig;
import com.kim.admin.entity.RouteConfigMeta;
import com.kim.admin.entity.UserEntity;
import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.LoginException;
import com.kim.common.response.MsgCode;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.IdGeneratorUtils;
import com.kim.common.util.Md5Algorithm;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.util.DownloadUtil;

/**
 * @author bo.liu01
 *
 */
@Service
public class LoginService extends BaseService {
    
	@Autowired
	private LoginUserDao loginUserDao;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserService userService;
    @Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BeanConfig beanConfig;
    @Resource
    private TenantPolicyService tenantPolicyService;
    
    public LoginResult authentication(LoginUserEntity loginUser) {
    	//数据库登录
    	LoginResult loginResult = dbLoginAuth(loginUser);
    	
    	return dealLoginResult(loginResult, loginUser);
    }

    /**
     * 登录结果处理
     * @param loginResult
     * @param loginUser 
     * @return 
     */
    public LoginResult dealLoginResult(LoginResult loginResult, LoginUserEntity loginUser) {
		if(loginResult.isSuccess()){
    		loginResult.setData(initLoginUserInfo(loginResult.getIdentity(), loginUser));
    	}
		return loginResult;
	}
    
    public LoginResult dbLoginAuth(LoginUserEntity loginUser) {
    	LoginUserEntity user = loginUserDao.findForLogin(loginUser.getUsername());
        LoginResult result = new LoginResult(loginUser.getUsername());
        if (user == null) {
        	return result.setSuccess(false).setMsg("工号不存在");
        } else {
        	String password = Md5Algorithm.getInstance().md5Encode(loginUser.getPassword());
        	if(password.equals(user.getPassword())){
        		if(user.getStatus() == null){
        			return result.setSuccess(false).setMsg("账号不可用");
        		}
        		if(user.getStatus() == LoginUserEntity.STATUS_NORMAL){
        			return result.setSuccess(true).setMsg("登录成功!");
        		}else if(user.getStatus() == LoginUserEntity.STATUS_LOGOFF){
        			return result.setSuccess(false).setMsg("账号已注销");
    			}else{
    				return result.setSuccess(false).setMsg("账号不可用");
    			}
        	}else{
        		return result.setSuccess(false).setMsg("密码错误");
        	}
        }
    }
    
    /**
     * 初始化登录用户信息
     * @param username
     * @param loginUser 
     * @return
     */
    public JSONObject initLoginUserInfo(String username, LoginUserEntity loginUser) {
    	UserEntity userObj = loginUserDao.findByUsername(username);
    	if(userObj == null){
    		logger.error("用户不存在, 请先添加用户或同步用户信息! username:{}", username);
    		throw new LoginException(MsgCode.UNKNOWN_USER);
    	}
    	//查询管辖的租户
    	TenantPolicyEntity tpl = tenantPolicyService.findByUsername(username);
    	String tenantId = userObj.getTenantId();
    	if(tpl != null && StringUtil.isNotBlank(tpl.getTenantId())){
    		tenantId = tpl.getTenantId();
    	}
    	userObj.setRightTenantId(tenantId);
    	userObj.setLoginType(LoginUserEntity.LOGIN_TYPE_SIMPLE);
    	userObj.setLoginTime(DateUtil.getCurrentTime());
    	
    	//是否加载软电话
    	if(StringUtil.equals(LoginUserEntity.LOAD_SOFT_PHONE_YES, loginUser.getLoadSoftPhone())){
    		UserAgentEntity agent = BaseCacheUtil.getBaseUserAgentService().getUserAgent(username, PlatformEnum.CISCO, tenantId);
			userObj.setLoadSoftPhone(agent != null && StringUtil.isNotBlank(agent.getAgentNo())?
					LoginUserEntity.LOAD_SOFT_PHONE_YES : LoginUserEntity.LOAD_SOFT_PHONE_NO);
    	}else{
    		userObj.setLoadSoftPhone(LoginUserEntity.LOAD_SOFT_PHONE_NO);
    	}
    	
    	//头像store存储
	    if(StringUtil.isNotBlank(userObj.getPortrait())){
		    userObj.setSign(DownloadUtil.sign(userObj.getPortrait()));
		    userObj.setPath(DownloadUtil.encode(userObj.getPortrait()));
	    }
    	
    	String accessToken = IdGeneratorUtils.getSerialNo();
        TokenUtil.setLoginInfo(accessToken, userObj);
        logger.info("用户登录成功, username:{}, token:{}", userObj.getUsername(), accessToken);
        
		JSONObject json = new JSONObject();
		json.put(CommonConstant.ACCESS_TOKEN_NAME, accessToken);
		json.put("username", userObj.getUsername());
		json.put("loadSoftPhone", userObj.getLoadSoftPhone());
		return json;
	}
    
    public FrontUser getUserInfo(String username) {

    	UserEntity userInfo = JSONObject.toJavaObject(TokenUtil.getCurInfo(), UserEntity.class);
		userInfo.setRightTenantId(userInfo.getTenantId());
		userInfo.setTenantId(TokenUtil.getActualTenantId());
		FrontUser frontUser = new FrontUser();
	    frontUser.setInfo(userInfo);
	    
	    List<String> roles = roleService.listByUsername(username);
		frontUser.setRoles(roles);
	    
		List<String> permissions = TokenUtil.getCurAuth();
    	String routeValue = TokenUtil.getCurRoute();
    	if(CollectionUtil.isNotEmpty(permissions) && StringUtil.isNotBlank(routeValue)){
    		frontUser.setPermissions(permissions);
    		frontUser.setRouteConfigs(JSONArray.parseArray(routeValue, RouteConfig.class));
    		return frontUser;
    	}
	    
    	//如果缓存中没有数据，则从数据库中查询
		List<MenuEntity> list = menuService.listBtnByOwner(username, AuthorityEntity.OWNER_TYPE_USER);
		list.addAll(menuService.listRoleBtnByUser(username));
		
		List<MenuEntity> menuList = new ArrayList<>();
		Set<String> btnCodeSet = new HashSet<>();
		if(CollectionUtil.isNotEmpty(list)){
			Map<String, MenuEntity> allMenuMap = menuService.getAllMenuMap();
			Set<String> menuCodeSet = new HashSet<>();
			for(MenuEntity entity : list){
				//过滤重复的菜单和按钮项
				if(btnCodeSet.contains(entity.getCode())){
					continue;
				}
				btnCodeSet.add(entity.getCode());
				//获取所有有权限的菜单
				MenuTreeUtil.getMenus(entity.getParentCode(), menuList, allMenuMap, menuCodeSet);
			}
		}
		frontUser.setPermissions(new ArrayList<>(btnCodeSet));
		frontUser.setRouteConfigs(transfer(menuList));
		//设置当前session
		TokenUtil.setCurInfo(frontUser.getPermissions(), frontUser.getRouteConfigs());
		return frontUser;
	}

	

	private List<RouteConfig> transfer(List<MenuEntity> menuList){

		List<RouteConfig> list = new LinkedList<>();

		//转成路由配置对象
		RouteConfig routeConfig;
		for(MenuEntity entity : menuList){
			routeConfig = new RouteConfig();
			routeConfig.setParentCode(entity.getParentCode());
			if(StringUtil.isBlank(entity.getParentCode())){
				routeConfig.setPath("/" + entity.getCode());
			}else{
				routeConfig.setPath(entity.getCode());
			}
			routeConfig.setCode(entity.getCode());
			routeConfig.setAbsolutePath("/"+entity.getFullPath().replace(".", "/"));
			routeConfig.setName(entity.getName());
			while(StringUtil.isNotBlank(entity.getUrl()) && entity.getUrl().startsWith("/")){
				entity.setUrl(entity.getUrl().substring(1));
			}
			routeConfig.setComponent(entity.getUrl());
			routeConfig.setIcon(StringUtil.isBlank(entity.getIcon()) ? 
					beanConfig.getMenuIconDefault() :entity.getIcon());
			routeConfig.setType(entity.getType());
			routeConfig.setSortNum(entity.getSortNum());
			if(NumberUtil.equals(entity.getType(), MenuEntity.TYPE_MENU)){
				routeConfig.setMeta(RouteConfigMeta.defaultMeta());
			}
			list.add(routeConfig);
		}

		//父子关联
		RouteConfig pconfig;
		Map<String, RouteConfig> routeConfigMap = CollectionUtil.getMapByProperty(list, "code");
		for(RouteConfig config : list){
			pconfig = routeConfigMap.get(config.getParentCode());
			if(pconfig == null){
				continue;
			}
			pconfig.add(config);
		}

		//过滤掉子菜单，只保留顶层菜单
		Iterator<RouteConfig> it = list.iterator();
		while(it.hasNext()){
			if(StringUtil.isNotBlank(it.next().getParentCode())){
				it.remove();
			}
		}
		
		//菜单排序
		MenuTreeUtil.sortByAsc(list);
		return list;
	}
    
    /**
     * 退出操作
     * @return
     */
    public boolean loginOut(){
    	String token = TokenUtil.removeLoginSession();
        if (StringUtil.isBlank(token)) {
        	logger.error("用户登出操作失败，没有token!");
        	return false;
        } 
        return true;
    }

	/**
	 * 修改密码
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int update(LoginUserEntity loginUserEntity) {
		
		return loginUserDao.update(loginUserEntity);
	}

}
