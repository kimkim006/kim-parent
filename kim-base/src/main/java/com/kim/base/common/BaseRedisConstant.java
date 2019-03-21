package com.kim.base.common;

import com.kim.common.constant.RedisConstant;

public class BaseRedisConstant extends RedisConstant {
	
	/** 用户的组织关系, tenantId, 缓存数据为:username-上级领导username */
	protected static final String ORG_USER_KEY_FORMAT = "ORG:USER:%s";
	/** 小组的组织关系, tenantId, 缓存数据为:groupCode-上级领导username*/
	protected static final String ORG_GROUP_KEY_FORMAT = "ORG:GROUP:%s";
	
	/** 用户的小组, tenantId, 缓存数据为:username-{对象}*/
	protected static final String USER_GROUP_KEY_FORMAT = "USER_GROUP:%s";
	
	/** 用户的小组, tenantId, 缓存数据为:username-{对象}*/
	protected static final String USER_DEPART_KEY_FORMAT = "USER_DEPART:%s";
	
	/** 用户的工号, tenantId, 缓存数据为:username:platform-{对象} */
	protected static final String USER_AGENT_KEY_FORMAT = "USER_AGENT:%s";
	
	/**
	 * @return 用户工号的key
	 */
	public static String getUserAgentKey(String key){
		return String.format(USER_AGENT_KEY_FORMAT, key);
	}
	
	/**
	 * @return 用户小组的key
	 */
	public static String getUserGroupKey(String key){
		return String.format(USER_GROUP_KEY_FORMAT, key);
	}
	
	/**
	 * @return 用户机构的key
	 */
	public static String getUserDepartKey(String key){
		return String.format(USER_DEPART_KEY_FORMAT, key);
	}
	
	/**
	 * @return 用户的组织关系的key
	 */
	public static String getOrgUserKey(String key){
		return String.format(ORG_USER_KEY_FORMAT, key);
	}
	
	/**
	 * @return 小组的组织关系的key
	 */
	public static String getOrgGroupKey(String key){
		return String.format(ORG_GROUP_KEY_FORMAT, key);
	}

}
