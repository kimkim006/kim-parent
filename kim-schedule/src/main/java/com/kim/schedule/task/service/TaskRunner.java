package com.kim.schedule.task.service;

import java.util.Map;

public interface TaskRunner {
	
	public int run(Map<String, String> param, String tenantId);
}
