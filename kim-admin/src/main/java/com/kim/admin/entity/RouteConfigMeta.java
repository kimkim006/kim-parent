package com.kim.admin.entity;

import com.kim.common.base.BaseObject;

public class RouteConfigMeta extends BaseObject{

	private static final long serialVersionUID = 1L;
	
	private static RouteConfigMeta DEFAULT_INSTANCE = new RouteConfigMeta();
	
	/** 是否缓存页面 */
	private boolean keepAlive = true;
	
	public static RouteConfigMeta defaultMeta(){
		return DEFAULT_INSTANCE;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

}
