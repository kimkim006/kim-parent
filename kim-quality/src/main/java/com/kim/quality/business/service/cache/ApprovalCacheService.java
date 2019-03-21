package com.kim.quality.business.service.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.ApprovalDao;
import com.kim.quality.business.entity.ApprovalEntity;
import com.kim.quality.business.vo.ApprovalVo;
import com.kim.quality.common.QualityRedisConstant;

@Service
public class ApprovalCacheService extends BaseService{
	
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private CacheKeyService cacheKeyService;
	
	public ApprovalEntity findWithCache(String tenantId, String id) {
		
		return findWithCache(new ApprovalVo().tenantId(tenantId).id(id));
	}
	
	public ApprovalEntity findWithCache(ApprovalVo vo) {
		String key = QualityRedisConstant.getApprovalKey(vo.getTenantId(), vo.getId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseObject(value, ApprovalEntity.class);
		}
		return loadApproval(vo, key);
	}

	
	private ApprovalEntity loadApproval(ApprovalVo vo, String key) {
		ApprovalEntity entity = approvalDao.find(vo);
		if(entity == null){
			logger.error("该审核记录不存在, tenantId:{}, id:{}", vo.getTenantId(), vo.getId());
			return null;
		}
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(entity), 
				cacheKeyService.getTaskExpire(entity.getTenantId()), TimeUnit.SECONDS);
		return entity;
	}
	
	public void reloadApproval(String tenantId, String id){
		String key = QualityRedisConstant.getApprovalKey(tenantId, id);
		RedisUtil.delete(key);
		loadApproval(new ApprovalVo().tenantId(tenantId).id(id) , key);
	}

}
