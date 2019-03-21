package com.kim.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseBeanConfig{
	
	/** 服务id */
	@Value("${spring.application.name}")
	protected String serviceId;

	public String getServiceId() {
		return serviceId;
	}

	
}
