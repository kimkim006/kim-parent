package com.kim.quality.business.service.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseService;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.dao.AdjustScoreDao;
import com.kim.quality.business.dao.AppealDao;
import com.kim.quality.business.dao.ApprovalDao;
import com.kim.quality.business.dao.EvaluationDao;
import com.kim.quality.business.dao.MainTaskDao;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.entity.RelateTaskItem;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.vo.AdjustScoreVo;
import com.kim.quality.business.vo.AppealVo;
import com.kim.quality.business.vo.ApprovalVo;
import com.kim.quality.business.vo.EvaluationVo;
import com.kim.quality.business.vo.MainTaskVo;
import com.kim.quality.common.QualityRedisConstant;

@Service
public class MainTaskCacheService extends BaseService{
	
	@Autowired
	private MainTaskDao mainTaskDao;
	@Autowired
	private EvaluationDao evaluationDao;
	@Autowired
	private AppealDao appealDao;
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private AdjustScoreDao adjustScoreDao;
	@Autowired
	private CacheKeyService cacheKeyService;
	
	public void clearCacheById(String tenantId, List<String> taskIdList){
		
		for (String taskId : taskIdList) {
			clearCacheById(tenantId, taskId);
		}
	}
	
	public void clearCacheById(String tenantId, String taskId){
		RedisUtil.delete(QualityRedisConstant.getMainTaskKey(tenantId, taskId));
	}
	
	public MainTaskEntity findWithCache(String tenantId, String taskId){
		
		return findWithCache(new MainTaskVo().tenantId(tenantId).id(taskId));
	}
	/**
	 * 根据id
	 * @param vo
	 * @return
	 */
	public MainTaskEntity findWithCache(MainTaskVo vo) {
		String key = QualityRedisConstant.getMainTaskKey(vo.getTenantId(), vo.getId());
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseObject(value, MainTaskEntity.class);
		}
		return loadMainTask(vo, key);
	}
	
	public void reloadMainTask(String tenantId, String taskId){
		String key = QualityRedisConstant.getMainTaskKey(tenantId, taskId);
		RedisUtil.delete(key);
		loadMainTask(new MainTaskVo().tenantId(tenantId).id(taskId) , key);
	}
	
	private MainTaskEntity loadMainTask(MainTaskVo vo, String key) {
		MainTaskEntity entity = mainTaskDao.find(vo);
		if(entity == null){
			logger.error("该任务录不存在, tenantId:{}, id:{}", vo.getTenantId(), vo.getId());
			return null;
		}
		RedisUtil.opsForValue().set(key, JSONObject.toJSONString(entity), 
				cacheKeyService.getTaskExpire(entity.getTenantId()), TimeUnit.SECONDS);
		return entity;
	}
	
	public List<String> reloadMainTaskDigest(String tenantId, String taskId){
		String key = QualityRedisConstant.getMainTaskDigestKey(TokenUtil.getTenantId(), taskId);
		RedisUtil.delete(key);
		List<String> tmpKeyList = findKeys(tenantId, taskId);
		if (CollectionUtil.isNotEmpty(tmpKeyList)) {
			RedisUtil.opsForList().rightPushAll(key, tmpKeyList);
			RedisUtil.getRedisTemplate().expire(key, 
					cacheKeyService.getTaskExpire(TokenUtil.getTenantId()), TimeUnit.SECONDS);
		}
		return tmpKeyList;
	}
	
	public void mainTaskDigestAdd(String tenantId, String taskId, String ... ids){
		
		if(StringUtil.isBlank(taskId) || CollectionUtil.isEmpty(ids)){
			return;
		}
		String key = QualityRedisConstant.getMainTaskDigestKey(tenantId, taskId);
		for (String id : ids) {
			RedisUtil.opsForList().rightPush(key, id);
		}
		RedisUtil.getRedisTemplate().expire(key, 
				cacheKeyService.getTaskExpire(tenantId), TimeUnit.SECONDS);
	}
	
	/**
	 * 根据任务id查询评分，审核，申诉记录key值，key值格式：type-id, 其中type参考MainTask.KEY_TYPE_XXX
	 *
	 * @param taskId
	 * @return
	 */
	private List<String> findKeys(String tenantId, String taskId) {

		List<RelateTaskItem> itemList = new ArrayList<>();

		//获取评分记录
		EvaluationVo evaluationVo = new EvaluationVo().tenantId(tenantId);
		evaluationVo.setTaskId(taskId);
		itemList.addAll(evaluationDao.listKey(evaluationVo));

		//获取审核记录
		ApprovalVo approvalVo = new ApprovalVo().tenantId(tenantId);
		approvalVo.setTaskId(taskId);
		itemList.addAll(approvalDao.listKey(approvalVo));

		//获取申诉记录
		AppealVo appealVo = new AppealVo().tenantId(tenantId);
		appealVo.setTaskId(taskId);
		itemList.addAll(appealDao.listKey(appealVo));
		
		//获取调整分数记录
		AdjustScoreVo adVo = new AdjustScoreVo().tenantId(tenantId);
		adVo.setTaskId(taskId);
		itemList.addAll(adjustScoreDao.listKey(adVo));

		Collections.sort(itemList, (RelateTaskItem o1, RelateTaskItem o2) ->
				NumberUtil.compare(o1.getSortTime(), o2.getSortTime())
		);
		List<String> keyList = new ArrayList<>();
		keyList.add(String.format("%s-%s", MainTask.KEY_TYPE_INFO, taskId));
		for (RelateTaskItem item : itemList) {
			keyList.add(item.getKey());
		}
		return keyList;
	}
	
	public List<String> getTaskRelateKey(String tenantId, String taskId){
		String key = QualityRedisConstant.getMainTaskDigestKey(TokenUtil.getTenantId(), taskId);
		Long size = RedisUtil.opsForList().size(key);

		if (size != null && size > 0) {
			return RedisUtil.opsForList().range(key, 0, size);
		}else{
			return reloadMainTaskDigest(tenantId, taskId);
		}
	}

}
