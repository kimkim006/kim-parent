package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 上下行短信发送记录实体类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class SmsSendHistoryEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	/** 短信类型标志 MS短信满意度   */
	public static final String SUB_CODE_MS = "MS";
	/** 短信类型标志 IVR ivr满意度 */
	public static final String SUB_CODE_IVR = "IVR";
	
	/** 发送结果 success成功 */
	public static final String SMS_RESP_RES_SUCCESS = "success";
	/** 发送结果 fail失败 */
	public static final String SMS_RESP_RES_FAIL = "fail";
	/** 发送结果 unknown未知 */
	public static final String SMS_RESP_RES_UNKNOWN = "unknown";
	/** 发送结果 exception异常 */
	public static final String SMS_RESP_RES_EXCEPTION = "exception";
	
	/** 业务系统代码 */
	private String appCode;
	/** 业务系统请求批次号 */
	private String businessNo;
	/** 发送类型 */
	private String businessType;
	/** 模板编号 */
	private String tempCode;
	/** 电话号码 */
	private String phone;
	/** 通话唯一标识 */
	private String customerCallId;
	/** 用户姓名 */
	private String name;
	/** 用户工号 */
	private String username;
	/** 话务工号 */
	private String agentId;
	/** 短信类型标志 MS短信满意度  IVR ivr满意度  */
	private String subCode;
	/** 发送结果 */
	private String smsRespRes;
	/** 短信平台处理流水 */
	private String smsRespNo;
	
	public SmsSendHistoryEntity id(String id) {
		setId(id);
		return this;
	}

	public SmsSendHistoryEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setAppCode(String appCode){  
        this.appCode = appCode;  
    }  
    
	@FieldDisplay("业务系统代码")
    public String getAppCode(){
        return this.appCode;  
    }
	
	public void setBusinessNo(String businessNo){  
        this.businessNo = businessNo;  
    }  
    
	@FieldDisplay("业务系统请求批次号")
    public String getBusinessNo(){
        return this.businessNo;  
    }
	
	public void setBusinessType(String businessType){  
        this.businessType = businessType;  
    }  
    
	@FieldDisplay("发送类型")
    public String getBusinessType(){
        return this.businessType;  
    }
	
	public void setTempCode(String tempCode){  
        this.tempCode = tempCode;  
    }  
    
	@FieldDisplay("模板编号")
    public String getTempCode(){
        return this.tempCode;  
    }
	
	public void setPhone(String phone){  
        this.phone = phone;  
    }  
    
	@FieldDisplay("电话号码")
    public String getPhone(){
        return this.phone;  
    }
	
	public void setCustomerCallId(String customerCallId){  
        this.customerCallId = customerCallId;  
    }  
    
	@FieldDisplay("通话唯一标识")
    public String getCustomerCallId(){
        return this.customerCallId;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("用户姓名")
    public String getName(){
        return this.name;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("话务工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
	public void setSubCode(String subCode){  
        this.subCode = subCode;  
    }  
    
	@FieldDisplay("短信类型标志")
    public String getSubCode(){
        return this.subCode;  
    }
	
	public void setSmsRespNo(String smsRespNo){  
        this.smsRespNo = smsRespNo;  
    }  
    
	@FieldDisplay("短信平台处理流水")
    public String getSmsRespNo(){
        return this.smsRespNo;  
    }

	public String getSmsRespRes() {
		return smsRespRes;
	}

	public void setSmsRespRes(String smsRespRes) {
		this.smsRespRes = smsRespRes;
	}
	
}