package com.kim.quality.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.setting.service.SampleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.executor.TaskExecutor;
import com.kim.common.lock.RedisLockOnce;
import com.kim.common.page.PageRes;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.dao.MainTaskDao;
import com.kim.quality.business.dao.SampleDao;
import com.kim.quality.business.dao.SampleTapeDao;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.business.entity.SampleResult;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.vo.MainTaskVo;
import com.kim.quality.business.vo.SampleTapeVo;
import com.kim.quality.business.vo.SampleVo;
import com.kim.quality.common.CommonConstant;
import com.kim.quality.common.QualityRedisConstant;
import com.kim.quality.setting.entity.SampleRuleEntity;
import com.kim.quality.setting.vo.SampleRuleVo;

/**
 * 抽检批次记录表服务实现类
 * @date 2018-8-28 18:24:20
 * @author bo.liu01
 */
@Service
public class SampleService extends BaseService {
	
	@Autowired
	private SampleDao sampleDao;
	@Autowired
	private MainTaskDao mainTaskDao;
	@Autowired
	private SampleTapeDao sampleTapeDao;
	@Autowired
	private SampleRuleService sampleRuleService;
	
	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public SampleEntity findById(String id) {
	
		return sampleDao.find(new SampleVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public SampleEntity find(SampleVo vo) {
	
		return sampleDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public PageRes<SampleEntity> listByPage(SampleVo vo) {
		
		return sampleDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	public List<SampleEntity> list(SampleVo vo) {
		
		return sampleDao.list(vo);
	}
	
	private List<SampleRuleEntity> getSystemSampleRule(String tenantId) {
		SampleRuleVo vo = new SampleRuleVo().tenantId(tenantId);
		vo.setSampleType(SampleRuleEntity.SAMPLE_TYPE_SYSTEM);
		vo.setActive(SampleRuleEntity.ACTIVE_YES);
		List<SampleRuleEntity> list = sampleRuleService.list(vo);
		if(CollectionUtil.isEmpty(list)){
			logger.error("该租户未配置系统抽检规则，将停止系统抽检，tenantId:{}", tenantId);
			throw new BusinessException("该租户未配置系统抽检规则");
		}
		return list;
	}
	
	@Transactional(readOnly=false)
	public List<SampleResult> executeSystem(SampleEntity sample) {
		List<SampleRuleEntity> list = getSystemSampleRule(sample.getTenantId());
		List<SampleResult> resultList = new ArrayList<>();
		for (SampleRuleEntity sampleRule : list) {
			sample.setRuleId(sampleRule.getId());
			resultList.addAll(execute(sample));
		}
		return resultList;
	}
	
	@Transactional(readOnly=false)
	public List<SampleResult> execute(SampleEntity sample) {
		RedisLockOnce lockOnce = new RedisLockOnce(QualityRedisConstant.getSampleOrigLockKey(sample.getRuleId()), 
				CommonConstant.SAMPLE_ORIG_REDIS_LOCK_EXPIRE_TIME);
		boolean res = lockOnce.lock();
		if(!res){
			logger.error("当前已有线程正在执行抽检任务! lockKey:{}", lockOnce.getLockKey());
			throw new BusinessException("当前已有线程正在执行抽检任务，请稍后再试!");
		}
		try {
			return executeSample(sample);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally{
			lockOnce.unLock();
		}
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	private List<SampleResult> executeSample(SampleEntity sample) {
		
		//查询抽检规则
		SampleRuleEntity rule = getSampleRule(sample);
		List<SampleResult> resultList = new ArrayList<>();
		if(StringUtil.isNotBlank(sample.getExtractDate())){//指定了某一天的数据
			operSample(sample, rule, 0);
			resultList.add(new SampleResult(sample.getExtractDate(), sample.getBusinessCode(), true));
		} else if(StringUtil.isBlank(sample.getExtractEndDate())){//没有指定结束日期，则只抽取开始日期这一条的数据
			sample.setExtractDate(sample.getExtractStartDate());
			operSample(sample, rule, 0);
			resultList.add(new SampleResult(sample.getExtractStartDate(), sample.getBusinessCode(), true));
		} else{
			List<String> dateList = DateUtil.getDateDuration(sample.getExtractStartDate(), sample.getExtractEndDate());
			if(CollectionUtil.isEmpty(dateList)){
				return resultList;
			}
			int limit = getSampleLimit(sample.getTenantId());
			if(dateList.size() > limit){
				logger.error("批量抽检一次抽检天数超过了最大的限制, max-limit:{}, 实际提交量：{}", limit, dateList.size());
				throw new BusinessException("批量抽检一次提交的天数不能超过"+limit+"天");
			}
			int i=1;
			for (String date : dateList) {
				try {
					sample.setExtractDate(date);
					operSample(sample, rule, (++i)*10L);
					resultList.add(new SampleResult(date, sample.getBusinessCode(), true));
				} catch (BusinessException e) {
					logger.error(e.getMessage());
					resultList.add(new SampleResult(date, sample.getBusinessCode(), false, e.getMessage()));
				}
			}
		}
		return resultList;
	}
	
	private int getSampleLimit(String tenantId){
		return BaseCacheUtil.getParam(CommonConstant.SAMPLE_LIMIT_MAX_KEY, tenantId, CommonConstant.SAMPLE_MAX_LIMIT_DEFAULT);
	}

	private int operSample(SampleEntity sample, SampleRuleEntity rule, long delay) {
		//检查抽检批次是否存在
		checkSample(sample, rule);

		//检查该日期现在是否有线程已经在执行抽检任务
		RedisLockOnce lockOnce = new RedisLockOnce(QualityRedisConstant.getSampleOrigLockKey(sample.getExtractDate()), 
				CommonConstant.SAMPLE_ORIG_REDIS_LOCK_EXPIRE_TIME);
		boolean res = lockOnce.lock();
		if(!res){
			logger.error("当前已有线程正在执行该日期的抽检任务, date:{}! lockKey:{}", sample.getExtractDate(), lockOnce.getLockKey());
			throw new BusinessException("当前已有线程正在执行该日期的抽检任务，请稍后再试!");
		}
		
		//初始化抽检批次记录
		SampleEntity sampleEntity = new SampleEntity();
		sampleEntity.copyBaseField(sample);
		sampleEntity.setExtractDate(sample.getExtractDate());
		sampleEntity.setRuleId(sample.getRuleId());
		sampleEntity.setRuleName(sample.getRuleName());
		sampleEntity.setBatchNo(DateUtil.formatDate(new Date(System.currentTimeMillis() + delay), DateUtil.PATTERN_YYYYMMDDHHMMSS_SSS));
		sampleEntity.setModeCode(rule.getModeCode());
		sampleEntity.setBusinessCode(rule.getBusinessCode());
		sampleEntity.setSampleType(rule.getSampleType());
		sampleEntity.setStatus(SampleEntity.STATUS_DOING);
		sampleDao.insert(sampleEntity);
		
		TaskExecutor.submit(() -> extract(sampleEntity, rule, lockOnce));
		return 1;
	}

	@Transactional(readOnly=false)
	public int extract(SampleEntity sample, SampleRuleEntity rule, RedisLockOnce lockOnce) {
		try {
			//抽取质检数据
			List<SampleTapeEntity> tapeList = ExtractUtil.extract(sample, rule);
			
			//转换生成质检任务
			List<MainTaskEntity> mainTaskList = transfer(tapeList, sample);
			sample.setExtractNum(mainTaskList.size());
			sample.setAssignableNum(mainTaskList.size());
			//保存数据
			BatchUtil.batchInsert(mainTaskDao, mainTaskList);
			BatchUtil.batchInsert(sampleTapeDao, tapeList);
			sample.setStatus(SampleEntity.STATUS_SUCCESS);
			return mainTaskList.size();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			sample.setStatus(SampleEntity.STATUS_FAIL);
			sample.addRemark(e.getMessage());
			return 0;
			//throw e
		} catch (Exception e) {
			logger.error("抽检时出现异常, sample:{}", JSONObject.toJSONString(sample));
			logger.error("抽检时出现异常", e);
			sample.setStatus(SampleEntity.STATUS_FAIL);
			sample.addRemark("抽检时出现异常，"+ StringUtil.substring(e.getMessage(), 0, 400));
			//throw new BusinessException("抽检时出现异常")
			return 0;
		} finally {
			sampleDao.update(sample);
			lockOnce.unLock();
		}
	}
	
	private SampleRuleEntity getSampleRule(SampleEntity sample) {
		SampleRuleVo vo = new SampleRuleVo().tenantId(sample.getTenantId());
		vo.setId(sample.getRuleId());
		SampleRuleEntity rule = sampleRuleService.find(vo);
		if(rule == null){
			logger.error("该抽检规则已不存在，ruleId:{}", sample.getRuleId());
			throw new BusinessException("该抽检规则已不存在，请刷新页面后重试!");
		}
		return rule;
	}

	private void checkSample(SampleEntity entity, SampleRuleEntity rule) {
		SampleVo vo = new SampleVo().tenantId(entity.getTenantId());
		vo.setExtractDate(entity.getExtractDate());
		vo.setRuleId(entity.getRuleId());
		vo.setSampleType(rule.getSampleType());
		List<SampleEntity> list = sampleDao.list(vo);
		if(CollectionUtil.isEmpty(list)){
			return ;
		}
		for (SampleEntity sample : list) {
			if(sample.getStatus() != null 
					&&(sample.getStatus() == SampleEntity.STATUS_DOING
					|| sample.getStatus() == SampleEntity.STATUS_ALLOCATED
					|| sample.getStatus() == SampleEntity.STATUS_SUCCESS)){
				logger.error("已存在抽检批次, date:{}, ruleId:{}, sampleType:{}, BatchNo:{}", 
						entity.getExtractDate(), entity.getRuleId(), rule.getSampleType(), sample.getBatchNo());
				throw new BusinessException("【"+entity.getExtractDate()+"】已存在抽检批次【"+sample.getBatchNo()+"】");
			}
		}
	}
	
	private List<MainTaskEntity> transfer(List<SampleTapeEntity> tapeList, SampleEntity sample) {
		
		List<MainTaskEntity> mainTaskList = new ArrayList<>(tapeList.size());
//		private String batchNo;//抽检批次号
//		private String modeCode;//质检模式, tape录音质检, text文本, mixed混合
//		private String businessCode;//业务线
//		private Integer ruleId;//抽检规则id
//		private String resolver;//解析器标识
//		private String sampleType;//规则类型,  system系统抽检, manual人工抽检
//		private Integer itemType;//抽检数据类型,  1录音, 2文本
//		private String itemId;//记录id
//		private String busiGroupCode;//业务小组
//		private String agentId;//坐席工号
		MainTaskEntity mainTask ;
		for (SampleTapeEntity tape : tapeList) {
			mainTask = new MainTaskEntity().createId();
			mainTask.setBatchNo(sample.getBatchNo());
			mainTask.setModeCode(sample.getModeCode());
			mainTask.setBusinessCode(sample.getBusinessCode());
			mainTask.setRuleId(sample.getRuleId());
			mainTask.setSampleType(sample.getSampleType());
			mainTask.setResolver(tape.getResolver());
			mainTask.setItemType(MainTask.ITEM_TYPE_TAPE);
			mainTask.setBusiGroupCode(tape.getBusiGroupCode());
			mainTask.setAgentId(tape.getAgentId());
			
			mainTask.setAllocateCount(0);
			mainTask.setRecycleCount(0);
			mainTask.setAppealCount(0);
			mainTask.setApprovalCount(0);
			mainTask.setEvaluationCount(0);
			mainTask.setAdjustScoreCount(0);
			mainTask.setScore(0D);
			mainTask.setStatus(MainTask.STATUS_TO_ALLOCATE);
			
			mainTask.copyBaseField(sample);
			mainTaskList.add(mainTask);
			
			tape.setMainTaskId(mainTask.getId());
			tape.copyBaseField(sample);
		}
		return mainTaskList;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(SampleVo vo) {
		
		SampleEntity sample = sampleDao.find(vo);
		if(sample == null){
			logger.error("该抽检批次不存在， id:{}", vo.getId());
			return 0;
		}
		if(sample.getStatus() != null && sample.getStatus() == SampleEntity.STATUS_ALLOCATED){
			logger.error("该抽检批次数据已分配，不可删除， batchNo:{}", sample.getBatchNo());
			throw new BusinessException("该抽检批次数据已分配，不可删除!");
		}
		
		int n = sampleDao.deleteLogic(vo);
		if(n > 0){
			MainTaskVo v = new MainTaskVo().tenantId(vo.getTenantId());
			v.setBatchNo(sample.getBatchNo());
			mainTaskDao.deleteLogic(v);
			
			SampleTapeVo v1 = new SampleTapeVo().tenantId(vo.getTenantId());
			v1.setBatchNo(sample.getBatchNo());
			sampleTapeDao.deleteLogic(v1);
		}

		return n;
	}
	
	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int deleteDetail(SampleVo vo) {
		
		//检查数据
		String[] taskIdArr = vo.getTaskId().split(",");
		SampleEntity sample = sampleDao.find(vo);
		if(sample == null){
			logger.error("该抽检批次不存在， batchNo:{}", vo.getBatchNo());
			throw new BusinessException("该抽检批次不存在!");
		}
		if(sample.getStatus() != null && sample.getStatus() != SampleEntity.STATUS_SUCCESS
				&& sample.getStatus() != SampleEntity.STATUS_ALLOCATED){
			logger.error("该抽检批次数据未抽检成功，不可删除数据， batchNo:{}", sample.getBatchNo());
			throw new BusinessException("该抽检批次数据未抽检成功，不可删除数据!");
		}
		
		if(NumberUtil.equals(sample.getExtractNum(), taskIdArr.length)){
			logger.error("若要删除该批次的全部数据请直接删除该批次， batchNo:{}", sample.getBatchNo());
			throw new BusinessException("若要删除该批次的全部数据请直接删除该批次!");
		}
		
		if(sample.getAssignableNum() < taskIdArr.length){
			logger.error("该抽检批次数据状态已发生变化， batchNo:{}", sample.getBatchNo());
			throw new BusinessException("该抽检批次数据状态已发生变化，请刷新列表重试!");
		}
		
		List<String> taskIdList = Arrays.asList(taskIdArr);
		
		//开始删除任务数据
		MainTaskVo v = new MainTaskVo().tenantId(vo.getTenantId());
		v.setBatchNo(sample.getBatchNo());
		v.setIds(taskIdList);
		v.statusAdd(MainTask.STATUS_TO_ALLOCATE, MainTask.STATUS_RECYCLED);
		int n = mainTaskDao.deleteLogic(v);
		if(n != taskIdArr.length){
			logger.error("该抽检批次任务数据状态已发生变化， batchNo:{}, actualNum:{}, taskIdNum:{}", 
					sample.getBatchNo(), n, taskIdArr.length);
			throw new BusinessException("该抽检批次任务数据状态已发生变化，请刷新列表重试!");
		}
		//开始删除抽检的录音数据
		SampleTapeVo v1 = new SampleTapeVo().tenantId(vo.getTenantId());
		v1.setBatchNo(vo.getBatchNo());
		v1.setMainTaskIdList(taskIdList);
		n = sampleTapeDao.deleteLogic(v1);
		if(n != taskIdArr.length){
			logger.error("该抽检批次录音数据状态已发生变化， batchNo:{}, actualNum:{}, taskIdNum:{}", 
					sample.getBatchNo(), n, taskIdArr.length);
			throw new BusinessException("该抽检批次录音数据状态已发生变化，请刷新列表重试!");
		}
		//修改抽检批次数据
		SampleEntity entity = new SampleEntity();
		entity.copyBaseField(vo);
		entity.setBatchNo(vo.getBatchNo());
		entity.setDeleteNum(taskIdArr.length);
		sampleDao.updateStatus(entity );
		
		return n;
	}
	
	/**
	 * 分配数据后修改批次状态
	 * @param batchMap
	 * @return
	 */
	@Transactional(readOnly=false)
	public int updateForAllocate(Map<String, Integer> batchMap) {

		SampleEntity entity = new SampleEntity();
		entity.setStatus(SampleEntity.STATUS_ALLOCATED);
		entity.initBaseField();
		int n = 0;
		for(Entry<String, Integer> entry : batchMap.entrySet()){
			entity.setBatchNo(entry.getKey());
			entity.setAllcateNum(entry.getValue());
			n += sampleDao.updateStatus(entity );
		}
		return n;
	}
	
	/**
	 * 回收数据后修改批次状态
	 * @param batchMap
	 * @return
	 */
	@Transactional(readOnly=false)
	public int updateForRecycle(Map<String, Integer> batchMap) {

		SampleEntity entity = new SampleEntity();
		entity.initBaseField();
		int n = 0;
		for(Entry<String, Integer> entry : batchMap.entrySet()){
			entity.setBatchNo(entry.getKey());
			entity.setRecycleNum(entry.getValue());
			n += sampleDao.updateStatus(entity);
			sampleDao.correctStatus(entity);
		}
		return n;
	}

}
