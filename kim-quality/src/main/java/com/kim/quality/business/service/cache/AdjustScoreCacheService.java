package com.kim.quality.business.service.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.AdjustScoreDao;
import com.kim.quality.business.entity.AdjustScoreEntity;
import com.kim.quality.business.vo.AdjustScoreVo;
import com.kim.quality.common.QualityRedisConstant;

@Service
public class AdjustScoreCacheService extends BaseService{
	
	@Autowired
	private AdjustScoreDao adjustScoreDao;
	@Autowired
	private CacheKeyService cacheKeyService;
	
	public AdjustScoreEntity findWithCache(String tenantId, String id) {
		
		return findWithCache(new AdjustScoreVo().tenantId(tenantId).id(id));
	}
	
	public AdjustScoreEntity findWithCache(AdjustScoreVo vo) {
		
		String key = QualityRedisConstant.getAdjustScorekey(vo.getTenantId(), vo.getId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseObject(value, AdjustScoreEntity.class);
		}
		return loadAdjustScore(vo, key);
	}

	private AdjustScoreEntity loadAdjustScore(AdjustScoreVo vo, String key) {
		AdjustScoreEntity entity = adjustScoreDao.find(vo);
		if(entity == null){
			logger.error("该任务录不存在, tenantId:{}, id:{}", vo.getTenantId(), vo.getId());
			return null;
		}
		
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(entity), 
				cacheKeyService.getTaskExpire(entity.getTenantId()), TimeUnit.SECONDS);
		return entity;
	}
	
	public void reloadAdjustScore(String tenantId, String id){
		String key = QualityRedisConstant.getAppealKey(tenantId, id);
		RedisUtil.delete(key);
		loadAdjustScore(new AdjustScoreVo().tenantId(tenantId).id(id) , key);
	}

}
