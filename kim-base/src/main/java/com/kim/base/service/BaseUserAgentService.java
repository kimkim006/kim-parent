package com.kim.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kim.base.dao.BaseUserAgentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.admin.entity.UserAgentEntity;
import com.kim.admin.vo.UserAgentVo;
import com.kim.base.common.BaseRedisConstant;
import com.kim.base.common.PlatformEnum;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

/**
 * 坐席工号表服务实现类
 * @date 2018-9-7 15:33:14
 * @author yonghui.wu
 */
@Service
public class BaseUserAgentService extends BaseService {
	
	@Autowired
	private BaseUserAgentDao baseUserAgentDao;

	private String getHashKey(String username, String platform){
		return String.format("%s:%s", username, platform);
	}
	
	/**
	 * 根据用户工号清除话务工号缓存
	 * @param username
	 * @param platform
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearUserAgentCache(String username, PlatformEnum platform, String tenantId) {
		RedisUtil.opsForHash().delete(BaseRedisConstant.getUserAgentKey(tenantId), getHashKey(username, platform.getKey()));
		return true;
	}

	/**
	 * 根据用户工号清除话务工号缓存
	 * @param username
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearUserAgentCache(String username, String tenantId) {
		
		for (PlatformEnum platform : PlatformEnum.values()) {
			clearUserAgentCache(username, platform, tenantId);
		}
		return true;
	}
	
	/**
	 * 清除全部的话务工号缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearUserAgentCache(String tenantId) {
		RedisUtil.delete(BaseRedisConstant.getUserAgentKey(tenantId));
		return true;
	}
	
	/**
	 * 重新加载全部的话务工号缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public void reloadUserAgentCache(String tenantId) {
		String key = BaseRedisConstant.getUserAgentKey(tenantId);
		RedisUtil.delete(key);
		List<UserAgentEntity> list = baseUserAgentDao.listUserAgent(new UserAgentVo().tenantId(tenantId));
		if(CollectionUtil.isEmpty(list)){
			return ;
		}
		
		for (UserAgentEntity entity : list) {
			RedisUtil.opsForHash().put(key, getHashKey(entity.getUsername(), entity.getPlatform()), JSONObject.toJSONString(entity));
		}
	}
	
	/**
	 * 根据用户工号获取话务工号
	 * @param username
	 * @param platform
	 * @param tenantId
	 * @return
	 */
	public UserAgentEntity getUserAgent(String username, PlatformEnum platform, String tenantId) {
		if(StringUtil.isBlank(username) || platform == null || StringUtil.isBlank(tenantId)){
			return null;
		}
		
		//先从redis缓存中获取
		String key = BaseRedisConstant.getUserAgentKey(tenantId);
		String hashKey = getHashKey(username, platform.getKey());
		Object object = RedisUtil.opsForHash().get(key, hashKey);
		String value;
		if(object != null){
			value = String.valueOf(object);
			if(StringUtil.isNotBlank(value)){
				return JSONObject.parseObject(value, UserAgentEntity.class);
			}
		}
		UserAgentVo vo = new UserAgentVo().tenantId(tenantId);
		vo.setUsername(username);
		vo.setPlatform(platform.getKey());
		List<UserAgentEntity> list = baseUserAgentDao.listUserAgent(vo);
		if(CollectionUtil.isEmpty(list)){
			logger.error("该用户没有配置话务工号, username:{}, paltform:{}", username, platform.getKey());
			return null;
		}
		if(list.size() > 1){
			logger.warn("该用户数据有话务工号{}条, 将默认取第一条, username:{}, paltform:{}", list.size(), username, platform.getKey());
		}
		UserAgentEntity entity = list.get(0);
		RedisUtil.opsForHash().put(key, hashKey, JSONObject.toJSONString(entity));
		return entity;
	}
	
	/**
	 * 根据用户工号获取话务工号
	 * @param username
	 * @param tenantId
	 * @return
	 */
	public List<UserAgentEntity> getUserAgent(String username, String tenantId) {
		if(StringUtil.isBlank(username) || StringUtil.isBlank(tenantId)){
			return Collections.emptyList();
		}
		
		List<UserAgentEntity> list = new ArrayList<>();
		UserAgentEntity entity;
		for (PlatformEnum platform : PlatformEnum.values()) {
			entity = getUserAgent(username, platform, tenantId);
			if(entity != null){
				list.add(entity);
			}
		}
		return list;
	}


}
