package com.kim.schedule.job.scheduler;

import com.kim.schedule.job.entity.TaskJobEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @Description: 计划任务执行处 无状态
 */
public class QuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskJobEntity taskJob = (TaskJobEntity) context.getMergedJobDataMap().get("taskJob");
		TaskUtils.invokMethod(taskJob);
	}
}
