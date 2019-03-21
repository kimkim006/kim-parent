package com.kim.schedule.common;

import com.kim.common.constant.CommonConstants;

public class CommonConstant extends CommonConstants {

	/** 坐席成绩自动确认天数 */
	public static final String CONFIRM_DAYS = "confirmDays";
	
	/** 录音文件临时存放的天数 */
	public static final String TAPE_FILE_TMP_CACHE_DAYS = "tapeFileTmpCacheDays";
	
	/** 录音地址服务器  数据字典key */
	public static final String TAPE_BASE_ADDRESS_DICT_CODE = "tapeBaseAddress";
	/** 录音地址服务器  默认其他数据字典内容编码 */
	public static final String TAPE_BASE_ADDRESS_OTHER = "other";
	/** 录音地址过滤处理项  数据字典key */
	public static final String TAPE_FILTER_DEAL_DICT_CODE = "tapeFilterDeal";
	
	
	/** MQ接口名称, 接收小结数据 */
	public static final String MQ_INTERFACE_SUMMARY = "summary";
	/** MQ接口名称, 接收录音小结关联数据 */
	public static final String MQ_INTERFACE_TAPE_SUMMARY = "tapeSummary";
	
}
