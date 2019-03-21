package com.kim.admin.entity;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseObject;

public class LoginResult extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	
	private String identity;
	private boolean isSuccess = false;
	private String msg;
	private JSONObject data;
	
	public LoginResult(String identity){
		super();
		this.identity =identity;
	}
	
	public LoginResult(boolean isSuccess, String msg) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
	}
	
	public LoginResult(String identity, boolean isSuccess, String msg) {
		super();
		this.identity = identity;
		this.isSuccess = isSuccess;
		this.msg = msg;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public LoginResult setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public LoginResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public String getIdentity() {
		return identity;
	}

	public LoginResult setIdentity(String identity) {
		this.identity = identity;
		return this;
	}

}
