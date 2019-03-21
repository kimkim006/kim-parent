package com.kim.schedule.job.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.kim.schedule.job.entity.TaskJobEntity;

/**
 * 若上一次执行未执行完，则下次轮询的时候会等待上一次执行完成后再执行下一次的操作
 * 
 * @date 2017年1月4日
 * @author liubo04
 */
@DisallowConcurrentExecution
public class QuartzJobDisallowConcurrentExecution implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskJobEntity taskJob = (TaskJobEntity) context.getMergedJobDataMap().get("taskJob");
		TaskUtils.invokMethod(taskJob);

	}
}
