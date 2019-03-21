package com.kim.quality.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.service.cache.ApprovalCacheService;
import com.kim.quality.business.service.cache.EvaluationCacheService;
import com.kim.quality.business.service.cache.MainTaskCacheService;
import com.kim.quality.business.vo.ApprovalVo;
import com.kim.quality.business.vo.EvaluationVo;
import com.kim.quality.business.vo.MainTaskVo;
import com.kim.quality.business.vo.TapeDamagedVo;
import com.kim.quality.common.CommonConstant;
import com.kim.quality.setting.entity.EvaluationSettingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.base.service.BaseUserOrgService;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.dao.AppealDao;
import com.kim.quality.business.dao.ApprovalDao;
import com.kim.quality.business.dao.EvaluationDao;
import com.kim.quality.business.dao.EvaluationDetailDao;
import com.kim.quality.business.dao.MainTaskDao;
import com.kim.quality.business.dao.TapeDamagedDao;
import com.kim.quality.business.entity.AppealEntity;
import com.kim.quality.business.entity.ApprovalEntity;
import com.kim.quality.business.entity.EvaluationDetailEntity;
import com.kim.quality.business.entity.EvaluationEntity;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.entity.TapeDamagedEntity;
import com.kim.quality.business.service.cache.AppealCacheService;

/**
 * 质检评分表服务实现类
 *
 * @author jianming.chen
 * @date 2018-9-17 15:43:36
 */
@Service
public class EvaluationService extends BaseService {

	@Autowired
	private EvaluationDao evaluationDao;
	@Autowired
	private EvaluationDetailDao evaluationDetailDao;
	@Autowired
	private TapeDamagedDao tapeDamagedDao;
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private BaseUserOrgService baseUserOrgService;
	@Autowired
	private MainTaskDao mainTaskDao;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;
	@Autowired
	private EvaluationCacheService evaluationCacheService;
	@Autowired
	private ApprovalCacheService approvalCacheService;
	@Autowired
	private AppealCacheService appealCacheService;
	@Autowired
	private AppealDao appealDao;

	/**
	 * @desc: 单条评分汇总数据查询
	 * 1、先从redis缓存中取数据
	 * 2、没有则从数据库中取数据
	 * @param: [vo]
	 * @return: EvaluationEntity
	 * @auther: yonghui.wu
	 * @date: 2018/9/28 11:20
	 */
	public EvaluationEntity find(EvaluationVo vo) {
		
		EvaluationEntity entity = evaluationCacheService.findWithCache(vo);
		if(entity == null){
			logger.error("该评分记录不存在, id:{}", vo.getId());
			throw new BusinessException("评分记录不存在, 请刷新列表重试!");
		}
		return entity;
	}
	
	/**
	 * @desc: 评分操作
	 * 1、查询质检任务(mainTask)
	 * 2、更新历史录音损坏数据（存在则更新不存在则不处理tapeDamaged）
	 * 3、插入评分数据
	 *    3.1、汇总数据evaluation;
	 *    3.2、评分明细数据evalationDetail
	 *    3.3、损坏数据tapeDamaged
	 * 4、插入评分缓存(
	 *    4.1、插入Redis评分汇总MainTask数据,追加、
	 *    4、2、插入Redis评分明细EVAL数据,新增，清空前端字符串数据
	 * 5、插入审核数据
	 *    5.1、插入审核数据；
	 *    5.2、插入审核redis汇总缓存
	 * 6、更新质检任务
	 * @param: [entity]
	 * @return: int
	 * @auther: yonghui.wu
	 * @date: 2018/9/21 18:10
	 */
	@Transactional(readOnly = false)
	public EvaluationEntity insert(EvaluationEntity evaluation) {
		//查询质检任务
		MainTaskEntity mainTask = checkData(evaluation);
		
		//插入评分数据（汇总数据、评分明细数据、损坏数据）
		insertEvaluation(evaluation, mainTask);
		
		String nextProcesser = null;
		ApprovalEntity approval = null;
		AppealEntity appeal = null;
		if (isSampleAudit(mainTask)) {
			//查询质检组长
			String leader = baseUserOrgService.getOrgByUser(evaluation.getOperUser(), evaluation.getTenantId());
			if(StringUtil.isBlank(leader)){
				logger.error("提交评分时检查到该用户未配置质检组长, username:{}, tenantId:{}", evaluation.getOperUser(), evaluation.getTenantId());
				throw new BusinessException("您的工号未配置质检组长, 无法提交评分, 请联系管理员!");
			}
			//插入审核数据
			approval = insertApproval(evaluation, leader);
			nextProcesser = leader;
		}else{
			//申诉后的评分不需要再审核，直接到坐席结果反馈
			nextProcesser = evaluation.getAgentId();
			approval = null;
			appeal = updateAppeal(evaluation);
		}
		
		//更新质检任务
		updateMainTask(evaluation, mainTask, nextProcesser);
		//设置评分与审核redis缓存
		updateRedis(evaluation, approval,  appeal);
		return evaluation;
	}
	
	private AppealEntity updateAppeal(EvaluationEntity evaluation) {
		AppealEntity appeal = new AppealEntity();
		appeal.copyBaseField(evaluation);
		appeal.setTaskId(evaluation.getTaskId());
		appeal.setStatus(AppealEntity.STATUS_PASSED);
		appeal.setOldStatus(AppealEntity.STATUS_IN_PROCESS);
		//修改申诉单状态
		int n = appealDao.update(appeal);
		if(n < 1){
			logger.error("提交评分修改申诉时检查到申诉记录更新状态失败，taskId:{}, evalId:{}", evaluation.getTaskId(), evaluation.getId());
			throw new BusinessException("检查到申诉数据有异常，请联系管理员!");
		}
		return appeal;
	}
	
	private MainTaskEntity checkData(EvaluationEntity evaluation) {
		MainTaskVo vo = new MainTaskVo().tenantId(evaluation.getTenantId());
		vo.setId(evaluation.getTaskId());
		MainTaskEntity task = mainTaskCacheService.findWithCache(vo );
		if(task == null){
			logger.error("提交评分时检查到该任务不存在, taskId:{}", vo.getId());
			throw new BusinessException("该任务已不存在, 请刷新列表后重试!");
		}
		if(!NumberUtil.equals(MainTask.STATUS_TO_EVALUATION, task.getStatus())
				&& !NumberUtil.equals(MainTask.STATUS_APPROVAL_REJECTED, task.getStatus())
				&& !NumberUtil.equals(MainTask.STATUS_TO_APPEAL_DEAL, task.getStatus())){
			logger.error("提交评分时检查到该任务状态不允许提交评分, taskId:{}, status:{}", vo.getId(), task.getStatus());
			throw new BusinessException("该任务状态不允许提交申诉!");
		}
		
		String origScoreStr = BaseCacheUtil.getParam(CommonConstant.DICT_ORIG_SCORE_KEY, evaluation.getTenantId());
		if(StringUtil.isBlank(origScoreStr)){
			logger.error("提交评分时检查到初始默认分数未配置，dict key:{}", CommonConstant.DICT_ORIG_SCORE_KEY);
			throw new BusinessException("初始的默认分数未配置，请联系管理员!");
		}
		if(!NumberUtil.isNumber(origScoreStr)){
			logger.error("提交评分时检查到初始默认分数配置不是数字，dict key:{}", CommonConstant.DICT_ORIG_SCORE_KEY);
			throw new BusinessException("初始的默认分数配置错误，请联系管理员!");
		}
		evaluation.setOrigScore(Double.valueOf(origScoreStr));
		return task;
	}

	/**
	 * 是否质检员的评分审核
	 * @param mainTask
	 * @return
	 */
	private boolean isSampleAudit(MainTaskEntity mainTask) {
		return NumberUtil.equals(MainTask.STATUS_TO_EVALUATION, mainTask.getStatus()) 
				|| NumberUtil.equals(MainTask.STATUS_APPROVAL_REJECTED, mainTask.getStatus());
	}

	/**
	 * @desc: 更新
	 * @param: [evaluation]
	 * @return: void
	 * @auther: yonghui.wu
	 * @date: 2018/9/27 13:46
	 */
	private void updateHistoryTapeDamaged(EvaluationEntity evaluation) {
		TapeDamagedVo vo = new TapeDamagedVo();
		vo.setTaskId(evaluation.getTapeId());
		vo.setTenantId(evaluation.getTenantId());
		TapeDamagedEntity historyTapeDamaged = tapeDamagedDao.find(vo);
		if (historyTapeDamaged != null) {
			historyTapeDamaged.setStatus(TapeDamagedEntity.STATUS_INVALID);
			historyTapeDamaged.copyOperField(evaluation);
			tapeDamagedDao.update(historyTapeDamaged);
		}
	}

	/**
	 * @desc: 插入录音评分明细
	 * @param: [evaluation, settingList, evaluationDetailList]
	 * @return: void
	 * @auther: yonghui.wu
	 * @date: 2018/9/27 14:05
	 */
	private void insertEvaluationDetailList(EvaluationEntity evaluation, List<EvaluationSettingEntity> settingList) {
		List<EvaluationDetailEntity> evaluationDetailList = new ArrayList<>();
		for (EvaluationSettingEntity setting : settingList) {
			EvaluationDetailEntity evaluationDetail = new EvaluationDetailEntity();
			evaluationDetail.copyBaseField(evaluation);
			evaluationDetail.setTaskId(evaluation.getTaskId());
			evaluationDetail.setEvaluationId(evaluation.getId());
			evaluationDetail.setAgentId(evaluation.getAgentId());//坐席工号
			evaluationDetail.setInspector(evaluation.getInspector());
			evaluationDetail.setEvalItemId(setting.getId());//评分id
			evaluationDetail.setEvalItemName(setting.getEvalItemName());
			evaluationDetail.setEvalType(setting.getEvalType());
			evaluationDetail.setScore(setting.getScore());
			evaluationDetail.setRemark(setting.getRemark());
			evaluationDetailDao.insert(evaluationDetail);
			evaluationDetailList.add(evaluationDetail);
		}
		evaluation.setEvaluationDetailList(evaluationDetailList);
	}

	/**
	 * @param task 
	 * @desc: 插入损毁明细
	 * @param: [evaluation]
	 * @return: void
	 * @auther: yonghui.wu
	 * @date: 2018/9/27 14:08
	 */
	private void insertTapeDamaged(EvaluationEntity evaluation) {
		TapeDamagedEntity tapeDamaged = new TapeDamagedEntity();
		tapeDamaged.setTaskId(evaluation.getTaskId());
		tapeDamaged.setEvaluationId(evaluation.getId());
		tapeDamaged.setTapeId(evaluation.getTapeId());
		tapeDamaged.setAgentId(evaluation.getAgentId());
		tapeDamaged.setInspector(evaluation.getInspector());
		tapeDamaged.setStatus(TapeDamagedEntity.STATUS_VALID);
		tapeDamaged.copyBaseField(evaluation);
		tapeDamagedDao.insert(tapeDamaged);
	}

	/**
	 * @param leader 
	 * @desc: 1、插入审核数据
	 * @param: [evaluation, agentId]
	 * @return 
	 * @return: void
	 * @auther: yonghui.wu
	 * @date: 2018/9/27 14:11
	 */
	private ApprovalEntity insertApproval(EvaluationEntity evaluation, String leader) {
		
		ApprovalEntity approval = new ApprovalEntity();
		//1、插入审核数据
		approval.setTaskId(evaluation.getTaskId());
		approval.setAgentId(evaluation.getAgentId());
		approval.setInspector(evaluation.getInspector());
		approval.setType(ApprovalEntity.TYPE_EVAL);
		approval.setUpstreamId(evaluation.getId());
		approval.setSubmitter(evaluation.getOperUser());
		approval.setSubmitTime(DateUtil.getCurrentTime());
		approval.setAuditor(leader);
		approval.setStatus(ApprovalEntity.STATUS_TO_APPROVAL);
		approval.copyBaseField(evaluation);
		
		//先清除一下
		ApprovalVo vo = new ApprovalVo().tenantId(approval.getTenantId());
		vo.setTaskId(approval.getTaskId());
		approvalDao.clearPre(vo);
		approval.setIsLast(MainTask.ITEM_IS_LAST_Y);
		approvalDao.insert(approval);
		return approval;
	}
	
	/**
	 * @desc:
	 * @param: [evaluation, calScore, mainTask]
	 * @return: void
	 * @auther: yonghui.wu
	 * @date: 2018/9/27 14:19
	 */
	private int updateMainTask(EvaluationEntity evaluation, MainTaskEntity mainTask, String nextProcesser) {
		
		MainTaskEntity taskEntity = new MainTaskEntity().tenantId(mainTask.getTenantId());
		taskEntity.setId(mainTask.getId());
		taskEntity.setOldStatus(mainTask.getStatus());
		taskEntity.setCurProcesser(nextProcesser);//下次处理人
		if (isSampleAudit(mainTask)) {
			taskEntity.setStatus(MainTask.STATUS_TO_APPROVAL);
		} else {
			taskEntity.setStatus(MainTask.STATUS_APPEAL_PASS);
		}
		taskEntity.setEvaluationCount(1);
		taskEntity.setScore(evaluation.getScore());
		taskEntity.copyBaseField(evaluation);
		return mainTaskDao.updateStatus(taskEntity);
	}

	/**
	 * @desc: 插入评分evaluation数据
	 * @param: [evaluation, agentId]
	 * @return: void
	 * @auther: yonghui.wu
	 * @date: 2018/9/27 14:25
	 */
	private void insertEvaluation(EvaluationEntity evaluation, MainTaskEntity task) {
		
		//更新历史录音损坏数据（存在则更新不存在则不处理）
		updateHistoryTapeDamaged(evaluation);
				
		evaluation.setAgentId(task.getAgentId());
		evaluation.setInspector(evaluation.getOperUser());
		evaluation.setStatus(EvaluationEntity.STATUS_PENDING);
		
		//3、判断录音文件是否损坏，插入明细
		if(NumberUtil.equals(EvaluationEntity.DAMAGED_YES, evaluation.getDamaged())) {
			evaluation.setScore(evaluation.getOrigScore());
			evaluation.setCalScore(evaluation.getOrigScore());
			insertEval(evaluation);
			//evaluationDao.insert(evaluation)
			//3.2、插入损坏明细数据
			insertTapeDamaged(evaluation);
		}else{
			List<EvaluationSettingEntity> settingList = new ArrayList<>();
			if(StringUtil.isNotBlank(evaluation.getEvaluationSettings())){
				settingList = JSONObject.parseArray(evaluation.getEvaluationSettings(), EvaluationSettingEntity.class);
			}
			//计算得分
			calc(evaluation, settingList);
			insertEval(evaluation);
//			evaluationDao.insert(evaluation)
			if(CollectionUtil.isNotEmpty(settingList)){
				//插入评分明细
				insertEvaluationDetailList(evaluation, settingList);
			}
		}
	}
	
	private void insertEval(EvaluationEntity eval){
		EvaluationVo vo = new EvaluationVo().tenantId(eval.getTenantId());
		vo.setTaskId(eval.getTaskId());
		evaluationDao.clearPre(vo);
		eval.setIsLast(MainTask.ITEM_IS_LAST_Y);
		evaluationDao.insert(eval);
	}

	/**
	 * 计算得分
	 * @param evaluation
	 * @param settingList
	 */
	private void calc(EvaluationEntity evaluation, List<EvaluationSettingEntity> settingList) {
		Double calScore = evaluation.getOrigScore();
		if(CollectionUtil.isNotEmpty(settingList)){
			for (EvaluationSettingEntity setting : settingList) {
				if (NumberUtil.equals(EvaluationSettingEntity.EVAL_TYPE_ADD, setting.getEvalType())) {
					calScore = NumberUtil.sum(calScore, setting.getScore());
				} else if (NumberUtil.equals(EvaluationSettingEntity.EVAL_TYPE_SUB, setting.getEvalType())) {
					calScore = NumberUtil.sub(calScore, setting.getScore());
				} else{
					logger.error("提交评分时检查到评分选项的评分类型有误, evalType:{}, settingId:{}", setting.getEvalType(), setting.getId());
					throw new BusinessException("检查到评分选项的评分类型有误, 请联系管理员!");
				}
			}
		}
		evaluation.setScore(MainTaskUtil.getActualScore(calScore, evaluation.getTenantId()));
		evaluation.setCalScore(calScore);
	}

	/**
	 * 刷新缓存数据
	 * @param evaluation
	 * @param approval
	 * @param appeal 
	 * @param mainTask
	 */
	private void updateRedis(EvaluationEntity evaluation, ApprovalEntity approval,
			AppealEntity appeal) {
		//插入评分数据,追加
		mainTaskCacheService.mainTaskDigestAdd(evaluation.getTenantId(), evaluation.getTaskId(), evaluation.getKey());
		//刷新评分数据缓存
		evaluationCacheService.reloadEval(evaluation.getTenantId(), evaluation.getId());
		//刷新任务记录缓存
		mainTaskCacheService.reloadMainTask(evaluation.getTenantId(), evaluation.getTaskId());
		
		if(approval != null){
			//插入评分待审核记录，追加
			mainTaskCacheService.mainTaskDigestAdd(evaluation.getTenantId(), evaluation.getTaskId(), approval.getKey());
			//刷新审核记录缓存
			approvalCacheService.reloadApproval(evaluation.getTenantId(), approval.getId());
		}
		
		if(appeal != null){
			//刷新申诉记录缓存
			appealCacheService.reloadAppeal(evaluation.getTenantId(), appeal.getId());
		}
	}

	@Transactional(readOnly=false)
	public int calcLast(EvaluationVo vo) {
		
		List<String> idList = evaluationDao.listLast(vo);
		logger.info("共查询到已评分任务{}条...", idList.size());
		
		Map<String, Object> param = new HashMap<>();
		param.put("idList", idList);
		param.put("tenantId", vo.getTenantId());
		
		param.put("isLast", MainTask.ITEM_IS_LAST_N);
		param.put("isIn", false);
		int n = evaluationDao.updateLastById(param);
		logger.info("共修改为非最后一条的评分记录{}条!", n);
		
		param.put("isLast", MainTask.ITEM_IS_LAST_Y);
		param.put("isIn", true);
		int m = evaluationDao.updateLastById(param);
		logger.info("共修改为最后一条的评分记录{}条!", m);
		
		return n+m;
	}
}
