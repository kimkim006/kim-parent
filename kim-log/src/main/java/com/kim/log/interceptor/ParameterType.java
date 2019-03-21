package com.kim.log.interceptor;

/**
 * 参数类型
 * @date 2017年3月24日
 * @author liubo04
 */
public enum ParameterType{
	
	/** 适用于get,post等的url参数列表中的参数 */
	QUERY,
	/** 适用于post请求的body参数 */
	BODY;
	
}