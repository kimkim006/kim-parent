package com.kim.schedule.job.service;

import java.util.List;
import java.util.Map;

import com.kim.schedule.job.dao.TaskJobDao;
import com.kim.schedule.job.scheduler.TaskUtils;
import com.kim.schedule.job.vo.TaskJobVo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.common.util.collectioninter.CollectionProperty;
import com.kim.schedule.job.entity.TaskJobEntity;
import com.kim.schedule.job.scheduler.service.ScheduleManage;

/**
 * 定时调度任务表服务实现类
 * @date 2018-8-21 10:39:53
 * @author bo.liu01
 */
@Service
public class TaskJobService extends BaseService {
	
	@Autowired
	private TaskJobDao taskJobDao;
	@Autowired
	private ScheduleManage scheduleManage;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	public TaskJobEntity findById(String id) {
	
		return taskJobDao.find(new TaskJobVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	public TaskJobEntity find(TaskJobVo vo) {
	
		return taskJobDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	public PageRes<TaskJobEntity> listByPage(TaskJobVo vo) {
		
		PageRes<TaskJobEntity> res = taskJobDao.listByPage(vo, vo.getPage());
		dealStatus(res.getList());
		return res;
	}
	
	private void dealStatus(List<TaskJobEntity> list){
		Map<String, TaskJobEntity> map = CollectionUtil.getMap(list, new CollectionProperty<TaskJobEntity, String>() {
			@Override
			public String getProperty(TaskJobEntity t) {
				return t.getJobGroup() + "-" + t.getJobName();
			}
		});
		try {
			List<TaskJobEntity> qlist = scheduleManage.getAllJob();
			merge(map, qlist, false);
			List<TaskJobEntity> runningList = scheduleManage.getRunningJob();
			merge(map, runningList, true);
		} catch (SchedulerException e) {
			logger.error("处理定时任务状态异常", e);
		}
		for (TaskJobEntity job : list) {
			if(StringUtil.isBlank(job.getStatus())){
				job.setStatus("PAUSED");
			}
		}
	}
	
	private void merge(Map<String, TaskJobEntity> map, List<TaskJobEntity> list, boolean isRunning){
		for (TaskJobEntity entity : list) {
			TaskJobEntity job = map.get(entity.getJobGroup() + "-" + entity.getJobName());
			if(job != null){
				if(isRunning){
					job.setStatus("RUNNING");
				}else{
					job.setStatus(entity.getStatus());
				}
				job.setJobName(entity.getJobName());
				job.setJobGroup(entity.getJobGroup());
				job.setCronExpression(entity.getCronExpression());
			}
		}
	}
	private void setDefaultVal(TaskJobEntity scheduleJobEntity){
		if(scheduleJobEntity.getType() == null){
			scheduleJobEntity.setType(TaskJobEntity.TYPE_NO);
		}
		if(scheduleJobEntity.getActive() == null){
			scheduleJobEntity.setActive(TaskJobEntity.ACTIVE_YES);
		}
		if(StringUtil.isBlank(scheduleJobEntity.getJobGroup())){
			scheduleJobEntity.setJobGroup(TaskJobEntity.DEFAULT_GROUP);
		}
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	public List<TaskJobEntity> list(TaskJobVo vo) {
		
		return taskJobDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public TaskJobEntity insert(TaskJobEntity entity) {
		
		setDefaultVal(entity);
		int n = taskJobDao.insert(entity);
		if(n > 0){
			try {
				scheduleManage.addJob(entity);
			} catch (Exception e) {
				logger.error("添加定时任务到定时任务列表失败", e);
				throw new BusinessException("任务添加成功，但添加到定时器失败");
			}
		}
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(TaskJobEntity entity) {

		if(StringUtil.isBlank(entity.getJobGroup())){
			entity.setJobGroup(TaskJobEntity.DEFAULT_GROUP);
		}
		int n = taskJobDao.update(entity);
		if(n > 0){
			try {
				scheduleManage.addJob(entity);
			} catch (Exception e) {
				logger.error("任务记录更新成功，但更新到定时器失败", e);
				throw new BusinessException("任务记录更新成功，但更新到定时器失败");
			}
		}
		return n;
	}

	private TaskJobEntity getJob(TaskJobVo vo) {
		TaskJobEntity job = findById(vo.getId());
		if(job == null){
			logger.error("不存在的定时任务，id:{}", vo.getId());
			throw new BusinessException("该任务不存在");
		}
		return job;
	}
	
	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(TaskJobVo vo) {

		TaskJobEntity job = getJob(vo);
		if(job.getActive() == TaskJobEntity.ACTIVE_YES){
			logger.error("任务正在被定时器计划，请先暂停任务再删除，id:{}", vo.getId());
			throw new BusinessException("任务正在被定时器计划，请先暂停任务再删除");
		}
		try {
			scheduleManage.deleteJob(job);
		} catch (SchedulerException e) {
			logger.error("删除定时任务到定时任务列表失败", e);
			throw new BusinessException("从定时器上删除任务失败");
		}
		return taskJobDao.deleteLogic(vo);
	}
	
	@Transactional(readOnly=false)
	public int active(TaskJobVo scheduleJobVo) {
		
		TaskJobEntity job = getJob(scheduleJobVo);
		TaskJobEntity jobEntity = new TaskJobEntity();
		jobEntity.setId(scheduleJobVo.getId());
		jobEntity.setActive(scheduleJobVo.getActive());
		int n = taskJobDao.update(jobEntity);
		job.setActive(scheduleJobVo.getActive());
		try {
			if(job.getActive() == TaskJobEntity.ACTIVE_YES){
				scheduleManage.addJob(job);
			}else{
				scheduleManage.deleteJob(job);
			}
		} catch (SchedulerException e) {
			logger.error("更新定时任务到定时任务列表失败", e);
			throw new BusinessException("任务记录更新成功，但更新到定时器失败");
		}
		return n;
	}
	
	@Transactional(readOnly=false)
	public int execute(TaskJobVo vo) {
		
		TaskJobEntity job = getJob(vo);
		try {
			TaskUtils.invokMethod(job);
		} catch (BusinessException e) {
			logger.error("执行定时任务失败", e);
			throw e;
		} catch (Exception e) {
			logger.error("执行定时任务异常", e);
			throw new BusinessException("执行定时任务异常");
		}
		return 1;
	}

}
