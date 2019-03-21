package com.kim.quality.business.entity;

public class SampleResult {
	
	private String date;
	private String businessCode;
	private boolean rel;
	private String msg;
	
	public SampleResult(String date, String businessCode, boolean rel) {
		super();
		this.date = date;
		this.businessCode = businessCode;
		this.rel = rel;
		this.msg = rel ? "提交成功!": "提交失败!";
	}
	
	public SampleResult(String date, String businessCode, boolean rel, String msg) {
		super();
		this.date = date;
		this.businessCode = businessCode;
		this.rel = rel;
		this.msg = msg;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public boolean isRel() {
		return rel;
	}
	public void setRel(boolean rel) {
		this.rel = rel;
	}

}
