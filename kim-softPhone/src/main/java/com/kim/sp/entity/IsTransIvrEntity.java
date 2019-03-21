package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 转IVR信息表实体类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class IsTransIvrEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 是否转ivr  1:转ivr */
	public static final int IS_TRANS_YES = 1;
	/** 是否转ivr  0:不转ivr */
	public static final int IS_TRANS_NO = 1;
	
	/** 是否转成功  Y发送成功  */
	public static final String IS_SEND_SUCCESS_YES = "Y";
	/** 是否转成功 N发送失败 */
	public static final String IS_SEND_SUCCESS_NO = "N";
	/** 是否转成功  K联系号码不匹配不能发送短信(为空或不是手机号) */
	public static final String IS_SEND_SUCCESS_K = "K";
	
	/** 思科通话唯一标识 */
	private String customerCallId;
	/** 用户姓名 */
	private String name;
	/** 用户工号 */
	private String username;
	/** 话务工号 */
	private String agentId;
	/** 是否转ivr  0:不转ivr,1:转ivr */
	private Integer isTrans;
	/** 结束时间 */
	private String endTime;
	/** 电话号码 */
	private String telephone;
	/** 是否转成功 Y 发送成功，N发送失败，K联系号码不能发送短信(为空或不是手机号)*/
	private String isSendSuccess;
	/** 热线号码 */
	private String serviceNo;
	/** 创建时间 */
	private String createTime;
	/** 创建人code */
	private String createName;
	/** 创建人名称 */
	private String createUser;
	
	public IsTransIvrEntity id(String id) {
		setId(id);
		return this;
	}

	public IsTransIvrEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setCustomerCallId(String customerCallId){  
        this.customerCallId = customerCallId;  
    }  
    
	@FieldDisplay("思科通话唯一标识")
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
	
	public void setIsTrans(Integer isTrans){  
        this.isTrans = isTrans;  
    }  
    
	@FieldDisplay("是否转ivr  0:不转ivr,1:转ivr")
    public Integer getIsTrans(){
        return this.isTrans;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
    
	@FieldDisplay("结束时间")
    public String getEndTime(){
        return this.endTime;  
    }
	
	public void setTelephone(String telephone){  
        this.telephone = telephone;  
    }  
    
	@FieldDisplay("电话号码")
    public String getTelephone(){
        return this.telephone;  
    }
	
	public void setIsSendSuccess(String isSendSuccess){  
        this.isSendSuccess = isSendSuccess;  
    }  
    
	@FieldDisplay("是否转成功")
    public String getIsSendSuccess(){
        return this.isSendSuccess;  
    }
	
	public void setServiceNo(String serviceNo){  
        this.serviceNo = serviceNo;  
    }  
    
	@FieldDisplay("热线号码")
    public String getServiceNo(){
        return this.serviceNo;  
    }
	
	public void setCreateTime(String createTime){  
        this.createTime = createTime;  
    }  
    
	@FieldDisplay("创建时间")
    public String getCreateTime(){
        return this.createTime;  
    }
	
	public void setCreateName(String createName){  
        this.createName = createName;  
    }  
    
	@FieldDisplay("创建人code")
    public String getCreateName(){
        return this.createName;  
    }
	
	public void setCreateUser(String createUser){  
        this.createUser = createUser;  
    }  
    
	@FieldDisplay("创建人名称")
    public String getCreateUser(){
        return this.createUser;  
    }
	
}