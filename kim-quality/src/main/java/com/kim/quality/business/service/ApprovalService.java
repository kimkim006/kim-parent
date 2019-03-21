package com.kim.quality.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.quality.business.dao.ApprovalDao;
import com.kim.quality.business.entity.MainTaskEntity;
import com.kim.quality.business.service.cache.ApprovalCacheService;
import com.kim.quality.business.service.cache.MainTaskCacheService;
import com.kim.quality.business.vo.ApprovalVo;
import com.kim.quality.business.vo.MainTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.service.BaseUserOrgService;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.dao.AppealDao;
import com.kim.quality.business.dao.EvaluationDao;
import com.kim.quality.business.entity.AppealEntity;
import com.kim.quality.business.entity.ApprovalEntity;
import com.kim.quality.business.entity.EvaluationEntity;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.service.cache.AppealCacheService;
import com.kim.quality.business.service.cache.EvaluationCacheService;

/**
 * 审批记录明细表服务实现类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
@Service
public class ApprovalService extends BaseService {
	
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private EvaluationDao evaluationDao;
	@Autowired
	private AppealDao appealDao;
	@Autowired
	private BaseUserOrgService baseUserOrgService;
	@Autowired
	private MainTaskService mainTaskService;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;
	@Autowired
	private ApprovalCacheService approvalCacheService;
	@Autowired
	private AppealCacheService appealCacheService;
	@Autowired
	private EvaluationCacheService evaluationCacheService;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public ApprovalEntity findById(String id) {

		return approvalDao.find(new ApprovalVo().id(id).tenantId(TokenUtil.getTenantId()));
	}

	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public ApprovalEntity find(ApprovalVo vo) {

		return approvalCacheService.findWithCache(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public PageRes<ApprovalEntity> listByPage(ApprovalVo vo) {

		return approvalDao.listByPage(vo, vo.getPage());
	}

	/**
	 * 新增记录
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public ApprovalEntity insert(ApprovalEntity entity) {
		
		int n = approvalDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(ApprovalEntity entity) {

		return approvalDao.update(entity);
	}

	private MainTaskEntity checkTaskData(ApprovalEntity entity) {
		MainTaskVo vo = new MainTaskVo().tenantId(entity.getTenantId());
		vo.setId(entity.getTaskId());
		MainTaskEntity task = mainTaskCacheService.findWithCache(vo );
		if(task == null){
			logger.error("提交审核时检查到任务不存在, taskId:{}", vo.getId());
			throw new BusinessException("任务已不存在, 请刷新列表后重试!");
		}
		if(!NumberUtil.equals(MainTask.STATUS_TO_APPROVAL, task.getStatus()) 
				&& !NumberUtil.equals(MainTask.STATUS_TO_APPROVAL_APPEAL, task.getStatus())){
			logger.error("提交审核时检查到任务状态不允许提交审核, taskId:{}, status:{}", vo.getId(), task.getStatus());
			throw new BusinessException("该任务状态不允许提交审核!");
		}
		
		if(NumberUtil.equals(ApprovalEntity.TYPE_EVAL, entity.getType()) 
				&& !NumberUtil.equals(MainTask.STATUS_TO_APPROVAL, task.getStatus())){
			logger.error("提交审核时检查到在评分审核阶段任务状态不允许提交审核, taskId:{}, status:{}", vo.getId(), task.getStatus());
			throw new BusinessException("评分审核阶段任务状态不允许提交审核!");
		}
		if(NumberUtil.equals(ApprovalEntity.TYPE_APPEAL, entity.getType()) 
				&& !NumberUtil.equals(MainTask.STATUS_TO_APPROVAL_APPEAL, task.getStatus())){
			logger.error("提交审核时检查到在申诉审核阶段任务状态不允许提交审核, taskId:{}, status:{}", vo.getId(), task.getStatus());
			throw new BusinessException("申诉审核阶段任务状态不允许提交审核!");
		}
		return task;
	}
	
	private ApprovalEntity checkApprovalData(ApprovalEntity entity){
		ApprovalVo appvo = new ApprovalVo().tenantId(entity.getTenantId());
		appvo.setId(entity.getId());
		appvo.setType(entity.getType());
		ApprovalEntity ent = approvalCacheService.findWithCache(appvo);
		if(ent == null){
			logger.error("提交审核时检查到审核记录不存在, taskId:{}, id:{}", entity.getId(), entity.getId());
			throw new BusinessException("该审核记录不存在!");
		}
		if(!NumberUtil.equals(entity.getType(), ent.getType())){
			logger.error("提交审核时检查到审核类型不一致, taskId:{}, id:{}, 前端type:{}, 数据库type:{}", 
					entity.getId(), entity.getId(), entity.getType(), ent.getType());
			throw new BusinessException("该审核类型不一致, 请检查审核类型和数据类型!");
		}
		return ent;
	}

    /**
     * 质检审核
     * @param approvalVo
     * @return
     */
    @Transactional(readOnly = false)
    public int audit(ApprovalEntity entity) {
    	
		MainTaskEntity task = checkTaskData(entity);
		ApprovalEntity ent = checkApprovalData(entity);
		entity.setUpstreamId(ent.getUpstreamId());
		entity.setInspector(ent.getInspector());
		entity.setAgentId(ent.getAgentId());
		entity.setAuditor(entity.getOperUser());
		entity.setApprovalTime(entity.getOperTime());
		int n = approvalDao.update(entity);
		if(n < 1){
			logger.error("审批记录修改失败，taskId:{}, id:{}, tenantId:{}", entity.getTaskId(), entity.getId(), entity.getTenantId());
			throw new BusinessException("审批失败，该任务已审批过或状态已发生变化");
		}
		
		if(StringUtil.isBlank(ent.getUpstreamId())){
			logger.error("审核记录中上游id为空，taskId:{}, id:{}, tenantId:{}", entity.getTaskId(), entity.getId(), entity.getTenantId());
			throw new BusinessException("检查到审核数据有异常，请联系管理员!");
		}
		
		if(NumberUtil.equals(ApprovalEntity.TYPE_EVAL, entity.getType())){
			updateEvalAndTask(entity, task);
		}else{
			updateAppealAndTask(entity, task);
		}
		
		//刷新redis缓存
		approvalCacheService.reloadApproval(entity.getTenantId(), entity.getId());
		mainTaskCacheService.reloadMainTask(entity.getTenantId(), entity.getTaskId());
		if(NumberUtil.equals(ApprovalEntity.TYPE_EVAL, entity.getType())){
			evaluationCacheService.reloadEval(entity.getTenantId(), ent.getUpstreamId());
		}else{
			appealCacheService.reloadAppeal(entity.getTenantId(), ent.getUpstreamId());
		}
        return n;
    }
    
	private void updateEvalAndTask(ApprovalEntity entity, MainTaskEntity task) {
		EvaluationEntity eval = new EvaluationEntity();
		eval.copyBaseField(entity);
		eval.setId(entity.getUpstreamId());
		eval.setTaskId(entity.getTaskId());
		
		int status = 0;
		String curProcesser = null;
		//判断是审核通过还是驳回
		if(NumberUtil.equals(ApprovalEntity.STATUS_PASSED, entity.getStatus())){
			eval.setStatus(EvaluationEntity.STATUS_PASSED);
			
			status = MainTask.STATUS_RESULT_FEEDBACK;
			curProcesser = entity.getAgentId();
		}else{
			eval.setStatus(EvaluationEntity.STATUS_REJECTED);
			
			status = MainTask.STATUS_APPROVAL_REJECTED;
			curProcesser = entity.getInspector();
		}
		//修改任务状态
		updateTask(entity, task, curProcesser, status);
		//修改评分状态
		int n = evaluationDao.update(eval);
		if(n < 1){
			logger.error("提交审核时检查到审核关联的评分记录更新状态失败，taskId:{}, approvalId:{}, evalId:{}", entity.getTaskId(), entity.getId(), entity.getUpstreamId());
			throw new BusinessException("检查到审核评分数据有异常，请联系管理员!");
		}
	}
	
	private void updateAppealAndTask(ApprovalEntity entity, MainTaskEntity task) {
		AppealEntity appeal = new AppealEntity();
		appeal.copyBaseField(entity);
		appeal.setId(entity.getUpstreamId());
		appeal.setTaskId(entity.getTaskId());
		
		int status = 0;
		String curProcesser = null;
		//判断是审核通过还是驳回
		if(NumberUtil.equals(ApprovalEntity.STATUS_PASSED, entity.getStatus())){
			appeal.setStatus(AppealEntity.STATUS_IN_PROCESS);
			status = MainTask.STATUS_TO_APPEAL_DEAL;
			curProcesser = baseUserOrgService.getOrgByUser(entity.getInspector(), entity.getTenantId());
			if(StringUtil.isBlank(curProcesser)){
				logger.error("提交审核通过时检查到该用户未配置质检组长, approvalType:{}, username:{}, tenantId:{}", 
						entity.getType(), entity.getInspector(), entity.getTenantId());
				throw new BusinessException("质检员未配置质检组长, 无法提交审核, 请联系管理员!");
			}
		}else{
			appeal.setStatus(AppealEntity.STATUS_REJECTED);
			status = MainTask.STATUS_APPEAL_REJECTED;
			curProcesser = entity.getAgentId();
		}
		//修改任务状态
		updateTask(entity, task, curProcesser, status);
		//修改申诉单状态
		int n = appealDao.update(appeal);
		if(n < 1){
			logger.error("提交审核时检查到审核关联的申诉记录更新状态失败，taskId:{}, approvalId:{}, evalId:{}", entity.getTaskId(), entity.getId(), entity.getUpstreamId());
			throw new BusinessException("检查到审核申诉数据有异常，请联系管理员!");
		}
	}
	
	/**
	 * 更新任务状态
	 * @param entity
	 * @param task
	 * @param leader
	 * @return
	 */
	private MainTaskEntity updateTask(ApprovalEntity entity, MainTaskEntity task, String curProcesser, int status) {
		MainTaskEntity taskEntity = new MainTaskEntity();
		taskEntity.copyBaseField(entity);
		taskEntity.setId(entity.getTaskId());
		taskEntity.setStatus(status);
		taskEntity.setApprovalCount(1);//填非空，sql中会+1
		taskEntity.setCurProcesser(curProcesser);
		taskEntity.setOldStatus(task.getStatus());
		int n = mainTaskService.updateStatus(taskEntity);
		if(n < 1){
			logger.error("提交审核修改任务状态时检查到该任务状态已发生变化, taskId:{}, 条件status:{}, tenantId:{}", entity.getTaskId(), task.getStatus(), entity.getTenantId());
			throw new BusinessException("该任务状态已发生变化, 请刷新列表重试!");
		}
		return taskEntity;
	}

    /**
     * 批量质检审核
     *
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public int auditBatch(ApprovalEntity entity) {
        String idStr = entity.getId();
        String taskIdStr = entity.getTaskId();
        String[] ids = idStr.split(",");
        String[] taskIds = taskIdStr.split(",");
        if(ids.length != taskIds.length){
        	logger.error("批量审核时检查到id个数和任务个数不相等， id length:{}, task size:{}", ids.length, taskIds.length);
        	throw new BusinessException("参数错误，请刷新页面重试!");
        }
        int n=0;
        for (int i = 0; i < ids.length; i++) {
        	entity.setId(ids[i]);
        	entity.setTaskId(taskIds[i]);
        	n +=audit(entity);
        }
        return n;
    }
    
    @Transactional(readOnly=false)
	public int calcLast(ApprovalVo vo) {
		
		List<String> idList = approvalDao.listLast(vo);
		logger.info("共查询到已审核任务{}条...", idList.size());		
		
		Map<String, Object> param = new HashMap<>();
		param.put("idList", idList);
		param.put("tenantId", vo.getTenantId());
		
		param.put("isLast", MainTask.ITEM_IS_LAST_N);
		param.put("isIn", false);
		int n = approvalDao.updateLastById(param);
		logger.info("共修改为非最后一条的审核记录{}条!", n);
		
		param.put("isLast", MainTask.ITEM_IS_LAST_Y);
		param.put("isIn", true);
		int m = approvalDao.updateLastById(param);
		logger.info("共修改为最后一条的审核记录{}条!", m);
		
		return n+m;
	}

}
