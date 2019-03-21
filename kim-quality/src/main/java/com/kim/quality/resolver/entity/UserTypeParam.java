package com.kim.quality.resolver.entity;

import com.kim.common.util.StringUtil;

public class UserTypeParam implements ResolverParam {
	
	private String userType;

	@Override
	public String checkParam() {
		if(StringUtil.isBlank(userType)){
			return "员工类型抽取器参数为空";
		}
		return null;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
