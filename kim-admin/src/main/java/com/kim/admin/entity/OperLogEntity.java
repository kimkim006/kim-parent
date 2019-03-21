package com.kim.admin.entity;

import com.kim.log.entity.OperateLogsEntity;

public class OperLogEntity extends OperateLogsEntity {
	private static final long serialVersionUID = 1L;
	
	private String tenantName;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

}
