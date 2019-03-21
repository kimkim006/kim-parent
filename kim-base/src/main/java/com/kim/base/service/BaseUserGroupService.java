package com.kim.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.vo.GroupUserVo;
import com.kim.base.common.BaseRedisConstant;
import com.kim.base.dao.BaseUserGroupDao;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

@Service
public class BaseUserGroupService extends BaseService{
	
	@Autowired
	private BaseUserGroupDao baseUserGroupDao;
	
	/**
	 * 根据用户工号清除用户小组缓存
	 * @param username 
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearGroupCache(String username, String tenantId) {
		RedisUtil.opsForHash().delete(BaseRedisConstant.getUserGroupKey(tenantId), username);
		return true;
	}
	
	/**
	 * 清除全部的用户小组缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearGroupCache(String tenantId) {
		RedisUtil.delete(BaseRedisConstant.getUserGroupKey(tenantId));
		return true;
	}
	
	/**
	 * 重新加载全部的用户小组缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public void reloadGroupCache(String tenantId) {
		String key = BaseRedisConstant.getUserGroupKey(tenantId);
		RedisUtil.delete(key);
		List<GroupUserEntity> list = baseUserGroupDao.listGroupByUser(new GroupUserVo().tenantId(tenantId));
		if(CollectionUtil.isEmpty(list)){
			return ;
		}
		
		for (GroupUserEntity entity : list) {
			RedisUtil.opsForHash().put(key, entity.getUsername(), JSONObject.toJSONString(entity));
		}
	}
	
	/**
	 * 根据用户工号获取小组
	 * @param username
	 * @param tenantId
	 * @return
	 */
	public GroupUserEntity getGroup(String username, String tenantId) {
		if(StringUtil.isBlank(username) || StringUtil.isBlank(tenantId)){
			return null;
		}
		
		//先从redis缓存中获取
		String key = BaseRedisConstant.getUserGroupKey(tenantId);
		Object object = RedisUtil.opsForHash().get(key, username);
		String value;
		if(object != null){
			value = String.valueOf(object);
			if(StringUtil.isNotBlank(value)){
				return JSONObject.parseObject(value, GroupUserEntity.class);
			}
		}
		GroupUserVo vo = new GroupUserVo().tenantId(tenantId);
		vo.setUsername(username);
		List<GroupUserEntity> list = baseUserGroupDao.listGroupByUser(vo);
		if(CollectionUtil.isEmpty(list)){
			logger.error("该用户没有配置小组, username:{}", username);
			return null;
		}
		if(list.size() > 1){
			logger.warn("该用户数据有小组{}条, 将默认取第一条, username:{}", list.size(), username);
		}
		GroupUserEntity entity = list.get(0);
		RedisUtil.opsForHash().put(key, username, JSONObject.toJSONString(entity));
		return entity;
	}
	

}
