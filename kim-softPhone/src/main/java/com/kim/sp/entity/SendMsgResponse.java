package com.kim.sp.entity;

import com.kim.common.base.BaseObject;

/**
 * 短信发送信息Response
 *
 */
public class SendMsgResponse extends BaseObject{

	private static final long serialVersionUID = 8185342792703745162L;
	
	/** 成功的code码 */
	public static final String RESP_CODE_SUCCESS = "000";

	/** 业务系统代码 */
	private String appCode;

	/** 业务类型 */
	private String businessType;

	/** 模板编号 */
	private String templCode;

	/** 业务系统请求批次号 */
	private String businessNo;

	/** 定时日期 */
	private String atTime;

	/** 短信平台处理唯一流水 */
	private String smsRespNo;

	/** 返回码 */
	private String respCode;

	/** 返回码对应的中文解释 */
	private String respMessage;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTemplCode() {
		return templCode;
	}

	public void setTemplCode(String templCode) {
		this.templCode = templCode;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getAtTime() {
		return atTime;
	}

	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	public String getSmsRespNo() {
		return smsRespNo;
	}

	public void setSmsRespNo(String smsRespNo) {
		this.smsRespNo = smsRespNo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

}
