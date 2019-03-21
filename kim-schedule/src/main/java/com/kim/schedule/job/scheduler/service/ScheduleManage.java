package com.kim.schedule.job.scheduler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.kim.schedule.job.dao.TaskJobDao;
import com.kim.schedule.job.entity.TaskJobEntity;
import com.kim.schedule.job.scheduler.QuartzJob;
import com.kim.schedule.job.scheduler.QuartzJobDisallowConcurrentExecution;
import com.kim.schedule.job.scheduler.TaskUtils;
import com.kim.schedule.job.vo.TaskJobVo;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class ScheduleManage {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private TaskJobDao taskJobDao;

	public void init(){
		logger.info("初始化定时任务列表准备...");
		//从数据库加载定时任务
		List<TaskJobEntity> jobList = taskJobDao.list(new TaskJobVo());
		logger.info("初始化定时任务列表，共{}个任务!", jobList.size());
		int n = 0;
		String res = null;
		for (TaskJobEntity job : jobList) {
			res = TaskUtils.checkJob(job);
			if(res != null){
				logger.error("添加定时任务失败，提示:{}, job:{}", res, job);
				continue;
			}
			try {
				addJob(job);
				n++;
			} catch (SchedulerException e) {
				logger.error("任务添加到执行列表失败, jobName:[{}]", job.getJobName());
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("初始化定时任务列表，共{}个任务初始化成功!", n);
	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(TaskJobEntity job) throws SchedulerException {
		if (job == null || !TaskJobEntity.ACTIVE_YES.equals(job.getActive())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (trigger == null) {
			logger.info("add job, job:{}", job);
			Class<? extends Job> clazz = TaskJobEntity.TYPE_YES.equals(job.getType()) ? QuartzJob.class
					: QuartzJobDisallowConcurrentExecution.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("taskJob", job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			logger.info("update job, job:{}", job);
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<TaskJobEntity> getAllJob() throws SchedulerException  {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<TaskJobEntity> jobList = new ArrayList<>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				TaskJobEntity job = new TaskJobEntity();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<TaskJobEntity> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<TaskJobEntity> jobList = new ArrayList<>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			TaskJobEntity job = new TaskJobEntity();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param taskJob
	 * @throws SchedulerException
	 */
	public void pauseJob(TaskJobEntity taskJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param taskJob
	 * @throws SchedulerException
	 */
	public void resumeJob(TaskJobEntity taskJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param taskJob
	 * @throws SchedulerException
	 */
	public void deleteJob(TaskJobEntity taskJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * 立即执行job
	 * 
	 * @param taskJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(TaskJobEntity taskJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param taskJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(TaskJobEntity taskJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(taskJob.getJobName(), taskJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}


}
