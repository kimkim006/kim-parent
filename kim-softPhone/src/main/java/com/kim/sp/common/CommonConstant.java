package com.kim.sp.common;

import com.kim.common.constant.CommonConstants;

public class CommonConstant extends CommonConstants {
	
	/** 参数配置  短信平台APP编码 */
	public static final String PARAM_SMS_APP_CODE = "sms_app_code";
	/** 参数配置  上下行短信模板-业务编码  */
	public static final String PARAM_SMS_UP_DOWN_BUSINESS_CODE = "sms_up_down_business_code";
	/** 参数配置  发送短信URL */
	public static final String PARAM_SMS_URL = "sms_url";
	
	/** 参数配置 就绪时长  */
	public static final String PARAM_AGENT_READY_TIME = "agentReadyTime";
	/** 默认的就绪时长配置  */
	public static final long DEFAULT_AGENT_READY_TIME = 10L;
	/** 数据字典 xtoolbox配置  */
	public static final String DICT_XTOOLBOX_CONFIG = "xtoolboxConfig";
	
	/** 坐席小休是否需要申请 */
	public static final String AGENT_FOR_REST = "agentForRest";
	/** 坐席话务工号名称 */
	public static final String AGENT_ID = "agentId";
	
	/** 身份证号码的*号 */
	public static final String ID_CORD_XH = "*";
	/** 身份证号码的最后一位X */
	public static final String ID_CORD_SUFFIX_X = "X";
	
	
	/** 参数配置  买买提URL */
	public static final String PARAM_MMT_URL = "mmtIvrUrl";
	
	/** 合同列表查询接口名称  */
	public static final String SERVICEID_CONTRACT_LIST_QUERY = "ContractListQuery";
	
	
	


}
