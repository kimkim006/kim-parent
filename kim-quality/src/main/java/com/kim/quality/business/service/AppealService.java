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

import com.kim.base.service.BaseCacheUtil;
import com.kim.base.service.BaseUserOrgService;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.quality.business.dao.AppealDao;
import com.kim.quality.business.entity.AppealEntity;
import com.kim.quality.business.entity.ApprovalEntity;
import com.kim.quality.business.entity.AttachmentEntity;
import com.kim.quality.business.enums.MainTask;
import com.kim.quality.business.service.cache.AppealCacheService;
import com.kim.quality.business.vo.AppealVo;
import com.kim.quality.common.CommonConstant;

/**
 * 申诉记录表服务实现类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
@Service
public class AppealService extends BaseService {
	
	@Autowired
	private AppealDao appealDao;
	@Autowired
	private MainTaskService mainTaskService;
	@Autowired
	private BaseUserOrgService baseUserOrgService;
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private MainTaskCacheService mainTaskCacheService;
	@Autowired
	private AppealCacheService appealCacheService;
	@Autowired
	private ApprovalCacheService approvalCacheService;
	@Autowired
	private AttachmentService attachmentService;
	
	private static final String ATTA_NAME = "申诉附件-图片";
	
	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public AppealEntity findById(String id) {
	
		return appealDao.find(new AppealVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public AppealEntity find(AppealVo vo) {
	
		return appealCacheService.findWithCache(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public PageRes<AppealEntity> listByPage(AppealVo vo) {
		
		return appealDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	public List<AppealEntity> list(AppealVo vo) {
		
		return appealDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public AppealEntity insert(AppealEntity entity) {
		//检查任务状态
		MainTaskEntity task = checkData(entity);
		insertAndUpdate(entity, task);
			
		String leader = baseUserOrgService.getOrgByUser(entity.getOperUser(), entity.getTenantId());
		if(StringUtil.isBlank(leader)){
			logger.error("提交申诉单时检查到该用户未配置业务组长, username:{}, tenantId:{}", entity.getOperUser(), entity.getTenantId());
			throw new BusinessException("您的工号未配置业务组长, 无法提交申诉, 请联系管理员!");
		}
		updateTask(entity, task, leader);
		ApprovalEntity approval = insertApproval(entity, task, leader);
		
//		throw new BusinessException("测试");
		updateRedis(entity, approval);
		return entity;
	}

	private void insertAndUpdate(AppealEntity entity, MainTaskEntity task) {
		entity.setAppealer(entity.getOperUser());
		entity.setInspector(task.getInspector());
		entity.setStatus(AppealEntity.STATUS_TO_APPROVAL);
		
		//先清除一下
		AppealVo vo = new AppealVo().tenantId(entity.getTenantId());
		vo.setTaskId(entity.getTaskId());
		appealDao.clearPre(vo);
		entity.setIsLast(MainTask.ITEM_IS_LAST_Y);
		appealDao.insert(entity);
		if(StringUtil.isNotBlank(entity.getAttachmentId())){
			String[] attaIdArr = entity.getAttachmentId().split(",");
			
			AttachmentEntity attaEntity = new AttachmentEntity().tenantId(entity.getTenantId());
			attaEntity.setTaskId(entity.getTaskId());
			attaEntity.setItemId(entity.getId());
			attaEntity.setItemType(AttachmentEntity.ITEM_TYPE_APPEAL);
			attaEntity.setName(ATTA_NAME);
			for (String id : attaIdArr) {
				attaEntity.setId(id);
				attachmentService.update(attaEntity);
			}
		}
	}
	
	/**
	 * 刷新redis缓存
	 * @param entity
	 * @param approval
	 */
	private void updateRedis(AppealEntity entity, ApprovalEntity approval) {
		//插入申诉记录,追加
		mainTaskCacheService.mainTaskDigestAdd(entity.getTenantId(), entity.getTaskId(), entity.getKey());
		//插入申诉待审核记录,追加
		mainTaskCacheService.mainTaskDigestAdd(entity.getTenantId(), entity.getTaskId(), approval.getKey());
		//刷新申诉记录缓存
		appealCacheService.reloadAppeal(entity.getTenantId(), entity.getId());
		//刷新任务缓存
		mainTaskCacheService.reloadMainTask(entity.getTenantId(), entity.getTaskId());
		//刷新审核记录缓存
		approvalCacheService.reloadApproval(entity.getTenantId(), approval.getId());
	}

	/**
	 * 更新任务状态
	 * @param entity
	 * @param task
	 * @param leader
	 * @return
	 */
	private MainTaskEntity updateTask(AppealEntity entity, MainTaskEntity task, String leader) {
		MainTaskEntity taskEntity = new MainTaskEntity();
		taskEntity.copyBaseField(entity);
		taskEntity.setId(entity.getTaskId());
		taskEntity.setStatus(MainTask.STATUS_TO_APPROVAL_APPEAL);
		taskEntity.setAppealCount(1);//填非空，sql中会+1
		taskEntity.setCurProcesser(leader);
		taskEntity.setOldStatus(task.getStatus());
		int n = mainTaskService.updateStatus(taskEntity);
		if(n < 1){
			logger.error("提交申诉单修改任务状态时检查到该任务状态已发生变化, taskId:{}, 条件status:{}, tenantId:{}", 
					entity.getTaskId(), task.getStatus(), entity.getTenantId());
			throw new BusinessException("该任务状态已发生变化, 请刷新列表重试!");
		}
		return taskEntity;
	}
	
	/**
	 * 插入一条待审核记录
	 * @param entity
	 * @param task
	 * @param leader
	 * @param key 
	 * @return
	 */
	private ApprovalEntity insertApproval(AppealEntity entity, MainTaskEntity task, String leader){
		ApprovalEntity approval = new ApprovalEntity();
		approval.copyBaseField(entity);
		approval.setTaskId(entity.getTaskId());
		approval.setUpstreamId(entity.getId());
		approval.setAgentId(task.getAgentId());
		approval.setInspector(task.getInspector());
		approval.setType(ApprovalEntity.TYPE_APPEAL);
		approval.setStatus(ApprovalEntity.STATUS_TO_APPROVAL);
		approval.setSubmitter(entity.getAppealer());
		approval.setSubmitTime(entity.getOperTime());
		approval.setAuditor(leader);
		
		//先清除一下
		ApprovalVo vo = new ApprovalVo().tenantId(approval.getTenantId());
		vo.setTaskId(approval.getTaskId());
		approvalDao.clearPre(vo);
		approval.setIsLast(MainTask.ITEM_IS_LAST_Y);
		approvalDao.insert(approval);
		return approval;
	}

	private MainTaskEntity checkData(AppealEntity entity) {
		MainTaskVo vo = new MainTaskVo().tenantId(entity.getTenantId());
		vo.setId(entity.getTaskId());
		MainTaskEntity task = mainTaskCacheService.findWithCache(vo );
		if(task == null){
			logger.error("提交申诉单时检查到该任务不存在, taskId:{}", vo.getId());
			throw new BusinessException("该任务已不存在, 请刷新列表后重试!");
		}
		if(!NumberUtil.equals(MainTask.STATUS_RESULT_FEEDBACK, task.getStatus()) 
				&& !NumberUtil.equals(MainTask.STATUS_APPEAL_PASS, task.getStatus())
				&& !NumberUtil.equals(MainTask.STATUS_APPEAL_REJECTED, task.getStatus())){
			logger.error("提交申诉单时检查到该任务状态不允许提交申诉单, taskId:{}, status:{}", vo.getId(), task.getStatus());
			throw new BusinessException("该任务状态不允许提交申诉!");
		}
		String appealCountStr = BaseCacheUtil.getParam(CommonConstant.PARAM_MAX_APPEAL_COUNT_KEY, entity.getTenantId());
		int appealCount = CommonConstant.MAX_APPEAL_COUNT;
		if(StringUtil.isNotBlank(appealCountStr) && NumberUtil.isNumber(appealCountStr)){
			appealCount = Integer.parseInt(appealCountStr);
		}
		if(NumberUtil.compare(task.getAppealCount(), appealCount) >= 0){
			logger.error("提交申诉单时检查到该任务提交申诉单的次数已达上限, taskId:{}, maxAppealCount:{}, appealCount:{}",
					vo.getId(), appealCount, task.getAppealCount());
			throw new BusinessException("该任务提交申诉单的次数已达上限("+appealCount+"次)!");
		}
		return task;
	}

	/**
	 * 修改记录
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(AppealEntity entity) {

		return appealDao.update(entity);
	}

	@Transactional(readOnly=false)
	public int calcLast(AppealVo vo) {
		
		List<String> idList = appealDao.listLast(vo);
		logger.info("共查询到已申诉任务{}条...", idList.size());		
		
		Map<String, Object> param = new HashMap<>();
		param.put("idList", idList);
		param.put("tenantId", vo.getTenantId());
		
		param.put("isLast", MainTask.ITEM_IS_LAST_N);
		param.put("isIn", false);
		int n = appealDao.updateLastById(param);
		logger.info("共修改为非最后一条的申诉记录{}条!", n);
		
		param.put("isLast", MainTask.ITEM_IS_LAST_Y);
		param.put("isIn", true);
		int m = appealDao.updateLastById(param);
		logger.info("共修改为最后一条的申诉记录{}条!", m);
		
		return n+m;
	}

}
