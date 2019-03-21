package com.kim.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.base.common.PlatformEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.entity.UserAgentEntity;
import com.kim.common.base.BaseService;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 数据字典和参数缓存工具
 * @author bo.liu01
 *
 */
public class BaseCacheUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseCacheUtil.class);
	
	private static Object lock = new Object();
	private static Map<Class<? extends BaseService>, BaseService> services = new HashMap<>();
	
	private BaseCacheUtil(){
		throw new IllegalStateException("Utility class");
	}
	
	private static <T extends BaseService> T getBaseService(Class<T> clazz){
		@SuppressWarnings("unchecked")
		T service = (T) services.get(clazz);
		if(service == null){
			synchronized (lock) {
				service = HttpServletUtil.getBean(clazz);
				if(service == null){
					throw new RuntimeException("未找到Bean组件："+clazz.getName());
				}else{
					services.put(clazz, service);
				}
			}
		}
		return service;
	}
	
	public static BaseCacheService getBaseCacheService(){
		return getBaseService(BaseCacheService.class);
	}
	
	public static BaseUserGroupService getBaseUserGroupService(){
		return getBaseService(BaseUserGroupService.class);
	}
	
	public static BaseUserAgentService getBaseUserAgentService(){
		return getBaseService(BaseUserAgentService.class);
	}
	
	public static BaseUserDepartService getBaseUserDepartService(){
		return getBaseService(BaseUserDepartService.class);
	}
	
	/**
	 * 根据编码清除参数缓存
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public static boolean clearParamCache(String code, String tenantId) {
		return getBaseCacheService().clearParamCache(code, tenantId);
	}
	
	/**
	 * 根据参数编码获取参数值
	 * @param code
	 * @param tenantId
	 * @param defaultValue 默认值
	 * @return
	 */
	public static int getParam(String code, String tenantId, int defaultValue){
		String value = getParam(code, tenantId);
		if(StringUtil.isNotBlank(value) && NumberUtil.isNumber(value)){
			return Integer.parseInt(value);
		}
		return defaultValue;
	}
	
	/**
	 * 根据参数编码获取参数值
	 * @param code
	 * @param tenantId
	 * @param defaultValue 默认值
	 * @return
	 */
	public static double getParam(String code, String tenantId, double defaultValue){
		String value = getParam(code, tenantId);
		if(StringUtil.isNotBlank(value) && NumberUtil.isNumber(value)){
			return Double.parseDouble(value);
		}
		return defaultValue;
	}
	
	/**
	 * 根据参数编码获取参数值
	 * @param code
	 * @param tenantId
	 * @param defaultValue 默认值
	 * @return
	 */
	public static long getParam(String code, String tenantId, long defaultValue){
		String value = getParam(code, tenantId);
		if(StringUtil.isNotBlank(value) && NumberUtil.isNumber(value)){
			return Long.parseLong(value);
		}
		return defaultValue;
	}
	
	/**
	 * 根据参数编码获取参数值
	 * @param code
	 * @param tenantId
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getParam(String code, String tenantId, Object defaultValue){
		String value = getParam(code, tenantId);
		if(StringUtil.isNotBlank(value)){
			return value;
		}
		return String.valueOf(defaultValue);
	}
	
	/**
	 * 根据参数编码获取参数值
	 * @param code
	 * @param tenantId 
	 * @param key
	 * @return
	 */
	public static String getParam(String code, String tenantId){
		return getBaseCacheService().getParam(code, tenantId);
	}
	
	/**
	 * 根据字典编码清除缓存
	 * @param id
	 * @param tenantId 
	 * @return
	 */
	public static boolean clearDictCache(String id, String tenantId) {
		return getBaseCacheService().clearDictCache(id, tenantId);
	}
	
	/**
	 * 获取所有的租户信息, 包含租户id和名字
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public static List<Map<String, String>> listAllTenant(){
		return getBaseCacheService().listAllTenant();
	}
	
	/**
	 * 根据字典编码获取键值对
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public static JSONObject getDict(String code, String tenantId){
		return getBaseCacheService().getDict(code, tenantId);
	}
	
	public static JSONArray getDictList(String code, String tenantId){
		return getBaseCacheService().getDictList(code, tenantId);
	}
	
	/**
	 * 根据字典编码和键获取名称
	 * @param code
	 * @param key
	 * @param tenantId 
	 * @return
	 */
	public static String getDictValue(String code, String key, String tenantId){
		return getBaseCacheService().getDictValue(code, key, tenantId);
	}
	
	/**
	 * 获取当前用户的小组
	 * @return
	 */
	public static GroupUserEntity getCurGroup(){
		return getBaseUserGroupService().getGroup(TokenUtil.getUsername(), TokenUtil.getTenantId());
	}
	
	/**
	 * 获取当前用户的话务工号
	 * @return
	 */
	public static List<UserAgentEntity> getCurAgent(){
		return getBaseUserAgentService().getUserAgent(TokenUtil.getUsername(), TokenUtil.getTenantId());
	}
	
	/**
	 * 获取当前用户的话务工号信息
	 * @return
	 */
	public static UserAgentEntity getCurAgent(PlatformEnum platform){
		return getBaseUserAgentService().getUserAgent(TokenUtil.getUsername(), platform, TokenUtil.getTenantId());
	}
	
	/**
	 * 获取当前用户的话务工号
	 * @return
	 */
	public static String getCurAgentId(PlatformEnum platform){
		UserAgentEntity agent = getCurAgent(platform);
		if(agent == null){
			logger.error("获取当前用户话务工号时检查到该坐席未配置话务工号：{}, tenantId:{}", TokenUtil.getUsername(), TokenUtil.getTenantId());
			return null;
		}else{
			return agent.getAgentNo();
		}
	}
	
	/**
	 * 获取当前用户的机构信息
	 * @return
	 */
	public static DepartmentUserEntity getCurDepart(){
		return getBaseUserDepartService().getDepart(TokenUtil.getUsername(), TokenUtil.getTenantId());
	}
	
	/**
	 * 获取当前用户的机构编码
	 * @return
	 */
	public static String getCurDepartCode(){
		DepartmentUserEntity depart = getCurDepart();
		if(depart == null){
			logger.error("获取当前用户机构时检查到该坐席未配置机构：{}, tenantId:{}", TokenUtil.getUsername(), TokenUtil.getTenantId());
			return null;
		}else{
			return depart.getDepartmentCode();
		}
	}
	
}
