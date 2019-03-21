package com.kim.schedule.mq.entity;

import com.kim.common.base.BaseObject;

public class InputData extends BaseObject{

	private static final long serialVersionUID = 1L;
	
	private String data;
	private String source;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
