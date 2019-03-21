package com.kim.quality.resolver.entity;

import com.kim.common.util.StringUtil;

public class SummaryParam implements ResolverParam {
	
	private String typeCode;//类型编码
	private String firstCode;//一级编码
	private String secondCode;//二级编码
	private String thirdCode;//三级编码
	private String forthCode;//四级编码

	@Override
	public String checkParam() {
		if(StringUtil.isBlank(typeCode)){
			return "弹屏小结抽取器类型参数为空";
		}
		return null;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}

	public String getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}

	public String getForthCode() {
		return forthCode;
	}

	public void setForthCode(String forthCode) {
		this.forthCode = forthCode;
	}

}
