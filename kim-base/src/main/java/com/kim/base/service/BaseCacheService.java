package com.kim.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kim.base.dao.BaseOperDao;
import com.kim.common.base.BaseService;
import com.kim.common.constant.RedisConstant;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

/**
 * 参数配置表服务实现类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@Service
public class BaseCacheService extends BaseService {
	
	@Autowired
	private BaseOperDao baseOperDao;
	
	public List<Map<String, String>> listAllTenant(){
		return baseOperDao.listAllTenant();
	}
	
	/**
	 * 根据编码清除参数缓存
	 * @param code
	 * @return
	 */
	public boolean clearParamCache(String code, String tenantId) {
		RedisUtil.delete(RedisConstant.getParamKey(code, tenantId));
		return true;
	}
	
	/**
	 * 根据参数编码获取值
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public String getParam(String code, String tenantId){
		if(StringUtil.isBlank(code)){
			return null;
		}
		//先从redis缓存中获取
		String key = RedisConstant.getParamKey(code, tenantId);
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return value;
		}
		value = baseOperDao.findParam(code, tenantId);
		if(StringUtil.isBlank(value)){
			//如果租户不为空，则再尝试获取全局的配置
			if(StringUtil.isNotBlank(tenantId)){
				return getParam(code, null);
			}
			return null;
		}
		RedisUtil.opsForValue().set(key, value);
		return value;
	}
	
	/**
	 * 根据字典编码清除缓存
	 * @param id
	 * @param tenantId 
	 * @return
	 */
	public boolean clearDictCache(String id, String tenantId) {
		String code = baseOperDao.findFirstDict(id, tenantId);
		if(StringUtil.isBlank(code)){
			return false;
		}
		RedisUtil.delete(RedisConstant.getDictKey(code, tenantId));
		return true;
	}
	
	/**
	 * 根据字典编码获取键值对
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public JSONArray getDictItemList(String code, String tenantId){
		if(StringUtil.isBlank(code)){
			return new JSONArray();
		}
		//先从redis缓存中获取
		String key = RedisConstant.getDictKey(code, tenantId);
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseArray(value);
		}
		List<Map<String, String>> list = baseOperDao.listDictByCode(code, tenantId);
		if(CollectionUtil.isEmpty(list)){
			if(StringUtil.isNotBlank(tenantId)){//如果租户不为空，则再尝试获取全局的配置
				return getDictItemList(code, null);
			}
			return new JSONArray();
		}
		
		value = JSON.toJSONString(list);
		RedisUtil.opsForValue().set(key, value);
		return JSONObject.parseArray(value);
	}
	
	/**
	 * 根据字典编码获取键值对
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public JSONObject getDictItem(String code, String tenantId){
		
		JSONArray list = getDictItemList(code, tenantId);
		JSONObject map = new JSONObject();
		JSONObject entity;
		for (Object object : list) {
			entity = (JSONObject)JSONObject.toJSON(object);
			map.put(entity.getString("code"), entity.getString("name"));
		}
		return map;
	}
	
	/**
	 * 根据字典编码获取键值对
	 * @param code
	 * @param tenantId 
	 * @return
	 */
	public JSONObject getDict(String code, String tenantId){
		return getDictItem(code, tenantId);
	}
	
	public JSONArray getDictList(String code, String tenantId){
		return getDictItemList(code, tenantId);
	}
	
	/**
	 * 根据字典编码和键获取名称
	 * @param code
	 * @param key
	 * @param tenantId 
	 * @return
	 */
	public String getDictValue(String code, String key, String tenantId){
		return getDictItem(code, tenantId).getString(key);
	}
	
}