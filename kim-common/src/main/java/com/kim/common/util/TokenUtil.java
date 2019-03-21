package com.kim.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kim.common.config.CommonBeanConfig;
import com.kim.common.config.CommonBeanConfigUtil;
import com.kim.common.constant.CommonConstants;
import com.kim.common.constant.RedisConstant;
import com.kim.common.exception.BusinessException;
import com.kim.common.redis.RedisUtil;

/**
 * Created by bo.liu01 on 2017/11/17.
 */
public class TokenUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
	
	private static final String[] USER_INFO_FIELDS = new String[]{
			"username", "loadSoftPhone", "name", "phone", "email", "joinDate", 
			"tenantId=>actualTenantId", "rightTenantId=>tenantId",
			"origins", "portrait", "sign", "path", "loginType", "loginTime"};
	
	private static String getSessionPattern(String token){
		return RedisConstant.getLoginSessionPattern(token);
	}
	private static String getInfoKey(String token){
		return RedisConstant.getLoginSessionInfoKey(token);
	}
	private static String getAuthKey(String token){
		return RedisConstant.getLoginSessionAuthKey(token);
	}
	/*
	private static String getResKey(String token){
		return RedisConstant.getLoginSessionResKey(token);
	}*/
	private static String getRouteKey(String token){
		return RedisConstant.getLoginSessionRouteKey(token);
	}
	
    /**
     * 获取当前登录用户的token
     * @return
     */
    public static String getToken() {
    	HttpServletRequest request = HttpServletUtil.getOriginalRequest();
		if(request == null){
			return null;
		}
		Object token = request.getAttribute(CommonConstants.ACCESS_TOKEN_NAME);
		if(token == null){
			logger.warn("token值为空!");
			return null;
		}
		return token.toString();
	}
    
    public static String getTokenWithCheck() {
    	String token = getToken();
    	if (StringUtil.isBlank(token)) {
			logger.error("token信息不存在, 请重新登录!");
			throw new BusinessException("登录信息超时, 请重新登录");
		}
		return token;
	}
    
    public static String removeLoginSession() {
    	String token = getToken();
        if (StringUtil.isBlank(token)) {
        	logger.error("用户退出失败，没有token!");
        	return null;
        } 
        
        String username = getUsername();
        Set<String> keys = RedisUtil.keys(getSessionPattern(token));
        if(CollectionUtil.isEmpty(keys)){
        	logger.warn("用户已退出系统， token:{}", token);
        }else{
        	for (String key : keys) {
            	RedisUtil.delete(key);
    		}
            logger.info("用户退出成功， token:{}, userInfo：{}",  token, username);
        }
        return token;
	}
    
    public static void setCurInfo(List<String> permissions, List<?> routes){
    	String token = getTokenWithCheck();
    	if(CollectionUtil.isNotEmpty(permissions)){
    		setCurAuth(token, permissions);
    	}
    	if(CollectionUtil.isNotEmpty(routes)){
    		setCurRoute(token, routes);
    	}
	}
    
    public static void setLoginInfo(String accessToken, Object user){
    	
    	Map<String, Object> infoMap = CollectionUtil.java2Map(user, USER_INFO_FIELDS);
    	//redis缓存，会话有效时间
    	String key = getInfoKey(accessToken);
    	RedisUtil.opsForHash().putAll(key, JSON.parseObject(JSON.toJSONString(infoMap)));
    	RedisUtil.expire(key, CommonBeanConfigUtil.getConfig().getAccessTokenKeepTime(), TimeUnit.SECONDS);
    }
    
    public static void setCurAuth(String accessToken, List<String> permissions){
    	
    	//redis缓存权限
    	String key = getAuthKey(accessToken);
    	RedisUtil.opsForList().leftPushAll(key, permissions);
    	RedisUtil.expire(key, CommonBeanConfigUtil.getConfig().getAccessTokenKeepTime(), TimeUnit.SECONDS);
    }
    public static void setCurRoute(String accessToken, List<?> routes){
    	
    	//redis缓存菜单
    	String key = getRouteKey(accessToken);
    	RedisUtil.opsForValue().set(key, JSON.toJSONString(routes), 
    			CommonBeanConfigUtil.getConfig().getAccessTokenKeepTime(), TimeUnit.SECONDS);
    }
    
	public static JSONObject getCurInfo(){
		String token = getTokenWithCheck();
		String key = getInfoKey(token);
		if(!RedisUtil.hasKey(key)){
			logger.error("登录信息不存在, 请重新登录! accessToken:{}", token);
			throw new BusinessException("登录信息超时, 请重新登录");
		}
		Map<Object, Object> infoMap = RedisUtil.opsForHash().entries(key);
		return JSONObject.parseObject(JSON.toJSONString(infoMap));
	}
	
	public static List<String> getCurAuth(){
		String token = getTokenWithCheck();
		String key = getAuthKey(token);
		return RedisUtil.opsForList().range(key, 0, RedisUtil.opsForList().size(key));
	}
	
	public static String getCurRoute(){
		
		return RedisUtil.opsForValue().get(getRouteKey(getTokenWithCheck()));
	}

	/**
	 * 获取当前用户能操作的租户编号
	 * （一般用户的tenantId和actualTenantId相同，只有管理员不同）
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	public static String getTenantId() {
		
		String token = getToken();
		if(token == null){
			return null;
		}
		Object obj = RedisUtil.opsForHash().get(getInfoKey(token), "tenantId");
		if(obj != null){
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * 获取当前用户的租户编号
	 * 
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	public static String getActualTenantId() {
		
		String token = getToken();
		if(token == null){
			return null;
		}
		Object obj = RedisUtil.opsForHash().get(getInfoKey(token), "actualTenantId");
		if(obj != null){
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * 获取当前用户名字
	 * 
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	public static String getCurrentName() {
		
		String token = getToken();
		if(token == null){
			return null;
		}
		Object obj = RedisUtil.opsForHash().get(getInfoKey(token), "name");
		if(obj != null){
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * 获取当前用户账号
	 * 
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	public static String getUsername() {
		String token = getToken();
		if(token == null){
			return null;
		}
		Object obj = RedisUtil.opsForHash().get(getInfoKey(token), "username");
		if(obj != null){
			return obj.toString();
		}
		return null;
	}
	
	/**
     * 判断是否登录用户
     * @param accessToken
     * @return true当前已登录, false当前未登录
     */
    public static boolean isLogin(String accessToken){
		Boolean s = RedisUtil.hasKey(getInfoKey(accessToken));
		if(s == null || !s){
			logger.debug("登录信息为空, 没有登录, accessToken:{}", accessToken);
			return false;
		}
		return true;
	}
    
    /**
     * 更新session有效时间
     * @param accessToken
     * @return
     */
    public static boolean sessionExpire(String accessToken){
    	return sessionExpire(accessToken, 0L);
    }
    
    /**
     * 更新session有效时间
     * @param accessToken
     * @param accessTokenKeepTime
     * @return
     */
    public static boolean sessionExpire(String accessToken, long accessTokenKeepTime){
    	String key = getInfoKey(accessToken);
    	Boolean s = RedisUtil.hasKey(key);
		if(s == null || !s){
			logger.debug("该token没有登录, accessToken:{}", accessToken);
			return false;
		}
		if(accessTokenKeepTime < CommonBeanConfig.ACCESS_TOKEN_KEEP_TIME_MIN){
			accessTokenKeepTime = CommonBeanConfigUtil.getConfig().getAccessTokenKeepTime();
		}
    	//更新session有效时间
		List<String> list = getSessionKeys(accessToken);
		for (String keytmp : list) {
			RedisUtil.expire(keytmp, accessTokenKeepTime, TimeUnit.SECONDS);
		}
    	return RedisUtil.expire(key, accessTokenKeepTime, TimeUnit.SECONDS);
    }
    
    private static List<String> getSessionKeys(String accessToken){
    	List<String> list = new ArrayList<>();
    	list.add(getAuthKey(accessToken));
    	list.add(getRouteKey(accessToken));
    	return list;
    }
	
	public static String parseToken(HttpServletRequest request) {
		if(request == null){
			request = HttpServletUtil.getOriginalRequest();
		}
		if(request == null){
			return null;
		}
		String token = request.getHeader(CommonConstants.AUTH_HEADER_NAME);
		return StringUtil.isBlank(token) ? request.getParameter(CommonConstants.AUTH_PARAMETER_NAME)
				:token;
	}
	
	public static String getPath(HttpServletRequest request){
		if(request == null){
			request = HttpServletUtil.getOriginalRequest();
		}
		if(request == null){
			return null;
		}
    	String contextPath = CommonBeanConfigUtil.getConfig().getContextPath();
    	if(StringUtil.isNotBlank(contextPath)){
    		String ctx = contextPath.startsWith("/") ? contextPath : "/"+contextPath;
    		return request.getRequestURI().replace(ctx, "");
    	}
    	return request.getRequestURI();
    }
	
}
