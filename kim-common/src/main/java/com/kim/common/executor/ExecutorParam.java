package com.kim.common.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorParam {
	
	private Integer corePoolSize;
	private Integer maximumPoolSize;
	private Integer keepAliveTime;
	private TimeUnit unit;
	private Integer workQueueSize;
	private BlockingQueue<Runnable> workQueue;
	private ThreadFactory threadFactory;
	private RejectedExecutionHandler handler;
	
	public Integer getCorePoolSize() {
		if(corePoolSize == null){
			corePoolSize = 5;
		}
		return corePoolSize;
	}
	public void setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public Integer getMaximumPoolSize() {
		if(maximumPoolSize == null){
			maximumPoolSize = 10;
		}
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public Integer getKeepAliveTime() {
		if(keepAliveTime == null){
			keepAliveTime = 180;
		}
		return keepAliveTime;
	}
	public void setKeepAliveTime(Integer keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
	public TimeUnit getUnit() {
		if(unit == null){
			unit = TimeUnit.SECONDS;
		}
		return unit;
	}
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
	public BlockingQueue<Runnable> getWorkQueue() {
		if(workQueue == null){
			workQueue = new LinkedBlockingDeque<>(getWorkQueueSize());
		}
		return workQueue;
	}
	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}
	public RejectedExecutionHandler getHandler() {
		if(handler == null){
			handler = new ThreadPoolExecutor.CallerRunsPolicy();
		}
		return handler;
	}
	public void setHandler(RejectedExecutionHandler handler) {
		this.handler = handler;
	}
	public Integer getWorkQueueSize() {
		if(workQueueSize == null){
			workQueueSize = 100;
		}
		return workQueueSize;
	}
	public void setWorkQueueSize(Integer workQueueSize) {
		this.workQueueSize = workQueueSize;
	}
	
	public static ThreadFactory defaultIcmThreadFactory() {
        return new DefaultIcmThreadFactory();
    }
	
	public ThreadFactory getThreadFactory() {
		if(threadFactory == null){
			threadFactory = defaultIcmThreadFactory();
		}
		return threadFactory;
	}
	public void setThreadFactory(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
	}
	
	

}
