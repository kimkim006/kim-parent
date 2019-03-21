package com.kim.schedule.task.entity;

import com.kim.admin.entity.GroupUserEntity;

public class UserAgentMapping extends GroupUserEntity {

	private static final long serialVersionUID = 1L;
	
	private String agentNo;//坐席号

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

}
