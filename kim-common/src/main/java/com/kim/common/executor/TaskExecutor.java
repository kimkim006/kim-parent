package com.kim.common.executor;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutor {
	
	private static ThreadPoolExecutor threadPoolExecutor;
	
	public static ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}
	
	@Autowired(required = true)
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		TaskExecutor.threadPoolExecutor = threadPoolExecutor;
	}
	
	public static void submit(Runnable command){
		threadPoolExecutor.execute(command);
	}

}
