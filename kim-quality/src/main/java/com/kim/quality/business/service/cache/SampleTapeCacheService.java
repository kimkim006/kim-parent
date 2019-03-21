package com.kim.quality.business.service.cache;

import java.util.concurrent.TimeUnit;

import com.kim.quality.business.entity.SampleTapeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.SampleTapeDao;
import com.kim.quality.business.vo.SampleTapeVo;
import com.kim.quality.common.QualityRedisConstant;

@Service
public class SampleTapeCacheService extends BaseService{
	
	@Autowired
	private SampleTapeDao sampleTapeDao;
	@Autowired
	private CacheKeyService cacheKeyService;
	
	public SampleTapeEntity findWithCache(String tenantId, String taskId) {

		SampleTapeVo v = new SampleTapeVo().tenantId(tenantId);
		v.setMainTaskId(taskId);
		return findWithCache(v);
	}
	
	public SampleTapeEntity findWithCache(SampleTapeVo vo) {
		String key = QualityRedisConstant.getMainTaskTapeKey(vo.getTenantId(), vo.getMainTaskId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseObject(value, SampleTapeEntity.class);
		}
		return loadSampleTape(vo, key);
	}
	
	public void reloadSampleTape(String tenantId, String id){
		String key = QualityRedisConstant.getMainTaskTapeKey(tenantId, id);
		RedisUtil.delete(key);
		loadSampleTape(new SampleTapeVo().tenantId(tenantId).id(id) , key);
	}

	private SampleTapeEntity loadSampleTape(SampleTapeVo vo, String key) {
		SampleTapeEntity entity = sampleTapeDao.find(vo);
		if(entity == null){
			logger.error("该抽检录音记录不存在, tenantId:{}, mainTaskId:{}, id:{}", vo.getTenantId(), vo.getMainTaskId(), vo.getId());
			return null;
		}
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(entity), 
				cacheKeyService.getTaskExpire(entity.getTenantId()), TimeUnit.SECONDS);
		return entity;
	}

}
