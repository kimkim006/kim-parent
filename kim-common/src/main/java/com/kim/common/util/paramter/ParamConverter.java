package com.kim.common.util.paramter;

public enum ParamConverter {
	
	/** 时间字符串转时间戳转换器 */
	DATE_STR2TIMESTAMP(new DateStr2TimestampConvert()),
	/** 时间戳转时间字符串转换器 */
	TIMESTAMP2DATE_STR(new Timestamp2DateStrConvert());
	
	private ParameterConvert paramConvert;
	
	private ParamConverter(ParameterConvert paramConvert) {
		this.paramConvert = paramConvert;
	}
	public ParameterConvert getParamConvert() {
		return paramConvert;
	}
	public void setParamConvert(ParameterConvert paramConvert) {
		this.paramConvert = paramConvert;
	}

}
