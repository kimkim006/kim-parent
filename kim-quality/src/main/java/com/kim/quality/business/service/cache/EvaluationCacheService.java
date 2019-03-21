package com.kim.quality.business.service.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.kim.quality.business.dao.EvaluationDetailDao;
import com.kim.quality.business.entity.EvaluationEntity;
import com.kim.quality.business.vo.EvaluationVo;
import com.kim.quality.common.QualityRedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.EvaluationDao;
import com.kim.quality.business.entity.EvaluationDetailEntity;
import com.kim.quality.business.vo.EvaluationDetailVo;

@Service
public class EvaluationCacheService extends BaseService{
	
	@Autowired
	private EvaluationDao evaluationDao;
	@Autowired
	private EvaluationDetailDao evaluationDetailDao;
	@Autowired
	private CacheKeyService cacheKeyService;
	
	public EvaluationEntity findWithCache(String tenantId, String id) {
		
		return findWithCache(new EvaluationVo().tenantId(tenantId).id(id));
	}
	
	public EvaluationEntity findWithCache(EvaluationVo vo) {
		
		//先从redis缓存中取数据
		String key = QualityRedisConstant.getEvalKey(vo.getTenantId(), vo.getId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseObject(value, EvaluationEntity.class);
		}
		return loadEval(vo, key);
	}

	private EvaluationEntity loadEval(EvaluationVo vo, String key) {
		EvaluationEntity evaluation = evaluationDao.find(vo);
		if(evaluation == null){
			logger.error("该评分记录不存在, id:{}", vo.getId());
			return null;
		}
		if(!NumberUtil.equals(EvaluationEntity.DAMAGED_YES, evaluation.getDamaged())){
			EvaluationDetailVo evaluationDetailVo = new EvaluationDetailVo().tenantId(vo.getTenantId());
			evaluationDetailVo.setEvaluationId(vo.getId());
			List<EvaluationDetailEntity> evaluationDetailList = evaluationDetailDao.list(evaluationDetailVo);
			evaluation.setEvaluationDetailList(evaluationDetailList);
		}
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(evaluation), 
				cacheKeyService.getTaskExpire(evaluation.getTenantId()), TimeUnit.SECONDS);
		return evaluation;
	}
	
	public void reloadEval(String tenantId, String id){
		String key = QualityRedisConstant.getEvalKey(tenantId, id);
		RedisUtil.delete(key);
		loadEval(new EvaluationVo().tenantId(tenantId).id(id) , key);
	}

}
