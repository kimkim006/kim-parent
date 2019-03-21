package com.kim.schedule.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kim.schedule.job.scheduler.service.ScheduleManage;

@Component
@Order(2)
public class InitService implements ApplicationListener<ContextRefreshedEvent> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//加载定时任务到定时器上面
		ScheduleManage scheduleManage = event.getApplicationContext().getBean(ScheduleManage.class);
		scheduleManage.init();
	}

}
