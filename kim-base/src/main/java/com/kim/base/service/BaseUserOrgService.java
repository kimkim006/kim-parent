package com.kim.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.admin.entity.GroupEntity;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.entity.UserOrgEntity;
import com.kim.admin.vo.UserOrgVo;
import com.kim.base.common.BaseRedisConstant;
import com.kim.base.dao.BaseUserOrgDao;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;

@Service
public class BaseUserOrgService extends BaseService {
	
	@Autowired
	private BaseUserOrgDao baseUserOrgDao;
	@Autowired
	private BaseUserGroupService baseUserGroupService;
	
	/**
	 * 根据用户工号清除用户组织缓存
	 * @param username 
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearOrgUserCache(String username, String tenantId) {
		RedisUtil.opsForHash().delete(BaseRedisConstant.getOrgUserKey(tenantId), username);
		return true;
	}
	
	/**
	 * 根据小组编码清除用户组织缓存
	 * @param groupCode 小组编码 
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearOrgGroupCache(String groupCode, String tenantId) {
		RedisUtil.opsForHash().delete(BaseRedisConstant.getOrgGroupKey(tenantId), groupCode);
		return true;
	}
	
	/**
	 * 清除全部的用户组织缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearOrgAllUserCache(String tenantId) {
		RedisUtil.delete(BaseRedisConstant.getOrgUserKey(tenantId));
		return true;
	}
	
	/**
	 * 清除全部的小组组织缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public boolean clearOrgAllGroupCache(String tenantId) {
		RedisUtil.delete(BaseRedisConstant.getOrgGroupKey(tenantId));
		return true;
	}
	
	/**
	 * 重新加载全部的用户组织缓存
	 * @param tenantId 租户id
	 * @return
	 */
	public void reloadOrgUserCache(String tenantId, int codeType) {
		String key;
		if(codeType == UserOrgEntity.CODE_TYPE_GROUP){
			key = BaseRedisConstant.getOrgGroupKey(tenantId);
		}else{
			key = BaseRedisConstant.getOrgUserKey(tenantId);
		}
		RedisUtil.delete(key);
		UserOrgVo vo = new UserOrgVo().tenantId(tenantId);
		vo.setCodeType(codeType);
		List<UserOrgEntity> list = baseUserOrgDao.listUserOrg(vo );
		if(CollectionUtil.isEmpty(list)){
			return ;
		}
		for (UserOrgEntity entity : list) {
			RedisUtil.opsForHash().put(key, entity.getItemCode(), entity.getUpperSuperior());
		}
	}
	
	/**
	 * 根据用户工号获取用户组织关系
	 * @param username 用户工号
	 * @param tenantId 租户id
	 * @return 一般为组长工号
	 */
	public String getOrgByUser(String username, String tenantId){
		if(StringUtil.isBlank(username) || StringUtil.isBlank(tenantId)){
			return null;
		}
		
		//先从redis缓存中获取
		String key = BaseRedisConstant.getOrgUserKey(tenantId);
		Object object = RedisUtil.opsForHash().get(key, username);
		String value;
		if(object != null){
			value = String.valueOf(object);
			if(StringUtil.isNotBlank(value)){
				return value;
			}
		}
		UserOrgVo vo = new UserOrgVo().tenantId(tenantId);
		vo.setCodeType(UserOrgEntity.CODE_TYPE_USER);
		vo.setItemCode(username);
		List<UserOrgEntity> list = baseUserOrgDao.listUserOrg(vo );
		if(CollectionUtil.isEmpty(list)){
			logger.error("该用户没有配置组织关系, username:{}", username);
			return null;
		}
		if(list.size() > 1){
			logger.warn("该用户组织关系有{}条, 将默认取第一条, username:{}", list.size(), username);
		}
		value = list.get(0).getUpperSuperior();
		if(StringUtil.isBlank(value)){
			return null;
		}
		RedisUtil.opsForHash().put(key, username, value);
		return value;
	}
	
	/**
	 * 根据小组编码获取小组组织关系
	 * @param groupCode 小组编码
	 * @param tenantId 租户id
	 * @return 一般为经理工号
	 */
	public String getOrgByGroup(String groupCode, String tenantId){
		if(StringUtil.isBlank(groupCode) || StringUtil.isBlank(tenantId)){
			return null;
		}
		
		//先从redis缓存中获取
		String key = BaseRedisConstant.getOrgGroupKey(tenantId);
		Object object = RedisUtil.opsForHash().get(key, groupCode);
		String value;
		if(object != null){
			value = String.valueOf(object);
			if(StringUtil.isNotBlank(value)){
				return value;
			}
		}
		UserOrgVo vo = new UserOrgVo().tenantId(tenantId);
		vo.setCodeType(UserOrgEntity.CODE_TYPE_GROUP);
		vo.setItemCode(groupCode);
		List<UserOrgEntity> list = baseUserOrgDao.listUserOrg(vo);
		if(CollectionUtil.isEmpty(list)){
			logger.error("该小组没有配置组织关系, groupCode:{}", groupCode);
			return null;
		}
		if(list.size() > 1){
			logger.warn("该小组组织关系有{}条, 将默认取第一条, groupCode:{}", list.size(), groupCode);
		}
		value = list.get(0).getUpperSuperior();
		if(StringUtil.isBlank(value)){
			return null;
		}
		RedisUtil.opsForHash().put(key, groupCode, value);
		return value;
	}
	
	/**
	 * 根据用户工号(一般为组长) 获取小组组织关系
	 * @param username 用户工号
	 * @param tenantId 租户id
	 * @return 一般为经理工号
	 */
	public String getOrgGroupByUser(String username, String tenantId){
		if(StringUtil.isBlank(username) || StringUtil.isBlank(tenantId)){
			logger.error("根据用户工号获取小组组织关系时，参数为空");
			throw new BusinessException("根据用户工号获取小组组织关系异常, 请联系管理员!");
		}
		
		GroupUserEntity group = baseUserGroupService.getGroup(username, tenantId);
		if(group == null || StringUtil.isBlank(group.getGroupCode())){
			logger.error("该用户未配置小组, username:{}, tenantId:{}", username, tenantId);
			throw new BusinessException("用户【"+username+"】未配置小组");
		}
		if(!NumberUtil.equals(GroupEntity.USER_TYPE_LEADER, group.getType())){
			logger.error("该用户不是组长, username:{}, tenantId:{}", username, tenantId);
			throw new BusinessException("用户【"+username+"】不是组长");
		}
		String value = getOrgByGroup(group.getGroupCode(), tenantId);
		if(StringUtil.isBlank(value)){
			logger.error("该用户的小组未配置经理或领导, username:{}, groupCode：{}, tenantId:{}", username, group.getGroupCode(), tenantId);
			throw new BusinessException("小组【"+group.getGroupCode()+"】未配置经理或领导");
		}
		return value;
	}

}
