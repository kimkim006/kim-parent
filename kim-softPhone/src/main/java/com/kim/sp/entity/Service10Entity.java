package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 服务记录信息表实体类
 * @date 2019-3-12 19:16:50
 * @author liubo
 */
public class Service10Entity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 服务ID */
	private String serviceId;
	/** 服务类型 1电话 2短信 3APP */
	private String serviceType;
	/** 服务联系电话 */
	private String servicePhone;
	/** 热线号码 */
	private String serviceNo;
	/** 主叫号码 */
	private String ani;
	/** 呼叫方向 */
	private String callType;
	/** 服务开始时间 */
	private String startTime;
	/** 服务结束时间 */
	private String endTime;
	/** 用户姓名 */
	private String name;
	/** 用户工号 */
	private String username;
	/** 话务工号 */
	private String agentId;
	/** 组织机构编码 */
	private String departmentCode;
	/** 组织机构名称 */
	private String departmentName;
	/** 业务类型  1客服  2电销 3催收 */
	private String bussinessType;
	/** 是否预约回复 */
	private String isBooking;
	/** 预约时间 */
	private String bookingTime;
	/** 是否需要回复 */
	private String isRemind;
	/** 接触记录 */
	private String serviceLog;
	/** 被叫号码 */
	private String dnis;
	/** 来电区号 */
	private String areaCode;
	/** 来电名称 */
	private String areaName;
	/** 一来电类型 */
	private String dirTypeName;
	/** 二来电类型 */
	private String subDirTypeName;
	/** 投诉回复状态 0暂存 1完成 */
	private String replyStatus;
	/** 回复记录 */
	private String replyLog;
	/** 客户编号 */
	private String customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 1.移动 2.核心 3.潜在 */
	private String customerType;
	/** 录音Id */
	private String recordId;
	/** 挂断时间 */
	private String disconnectTime;
	/** 接通时间 */
	private String connectTime;
	/** 是否绑定微信 1是, 0否 */
	private Integer isBindWx;
	/** 预期天数 */
	private Integer pushDate;
	/** 思科通话唯一标识 */
	private String customerCallId;
	
	public Service10Entity id(String id) {
		setId(id);
		return this;
	}

	public Service10Entity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
    
	@FieldDisplay("服务ID")
    public String getServiceId(){
        return this.serviceId;  
    }
	
	public void setServiceType(String serviceType){  
        this.serviceType = serviceType;  
    }  
    
	@FieldDisplay("服务类型 1电话 2短信 3APP")
    public String getServiceType(){
        return this.serviceType;  
    }
	
	public void setServicePhone(String servicePhone){  
        this.servicePhone = servicePhone;  
    }  
    
	@FieldDisplay("服务联系电话")
    public String getServicePhone(){
        return this.servicePhone;  
    }
	
	public void setServiceNo(String serviceNo){  
        this.serviceNo = serviceNo;  
    }  
    
	@FieldDisplay("热线号码")
    public String getServiceNo(){
        return this.serviceNo;  
    }
	
	public void setAni(String ani){  
        this.ani = ani;  
    }  
    
	@FieldDisplay("主叫号码")
    public String getAni(){
        return this.ani;  
    }
	
	public void setCallType(String callType){  
        this.callType = callType;  
    }  
    
	@FieldDisplay("呼叫方向")
    public String getCallType(){
        return this.callType;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
    
	@FieldDisplay("服务开始时间")
    public String getStartTime(){
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
    
	@FieldDisplay("服务结束时间")
    public String getEndTime(){
        return this.endTime;  
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
	
	public void setDepartmentCode(String departmentCode){  
        this.departmentCode = departmentCode;  
    }  
    
	@FieldDisplay("组织机构编码")
    public String getDepartmentCode(){
        return this.departmentCode;  
    }
	
	public void setDepartmentName(String departmentName){  
        this.departmentName = departmentName;  
    }  
    
	@FieldDisplay("组织机构名称")
    public String getDepartmentName(){
        return this.departmentName;  
    }
	
	public void setBussinessType(String bussinessType){  
        this.bussinessType = bussinessType;  
    }  
    
	@FieldDisplay("业务类型  1客服  2电销 3催收")
    public String getBussinessType(){
        return this.bussinessType;  
    }
	
	public void setIsBooking(String isBooking){  
        this.isBooking = isBooking;  
    }  
    
	@FieldDisplay("是否预约回复")
    public String getIsBooking(){
        return this.isBooking;  
    }
	
	public void setBookingTime(String bookingTime){  
        this.bookingTime = bookingTime;  
    }  
    
	@FieldDisplay("预约时间")
    public String getBookingTime(){
        return this.bookingTime;  
    }
	
	public void setIsRemind(String isRemind){  
        this.isRemind = isRemind;  
    }  
    
	@FieldDisplay("是否需要回复")
    public String getIsRemind(){
        return this.isRemind;  
    }
	
	public void setServiceLog(String serviceLog){  
        this.serviceLog = serviceLog;  
    }  
    
	@FieldDisplay("接触记录")
    public String getServiceLog(){
        return this.serviceLog;  
    }
	
	public void setDnis(String dnis){  
        this.dnis = dnis;  
    }  
    
	@FieldDisplay("被叫号码")
    public String getDnis(){
        return this.dnis;  
    }
	
	public void setAreaCode(String areaCode){  
        this.areaCode = areaCode;  
    }  
    
	@FieldDisplay("来电区号")
    public String getAreaCode(){
        return this.areaCode;  
    }
	
	public void setAreaName(String areaName){  
        this.areaName = areaName;  
    }  
    
	@FieldDisplay("来电名称")
    public String getAreaName(){
        return this.areaName;  
    }
	
	public void setDirTypeName(String dirTypeName){  
        this.dirTypeName = dirTypeName;  
    }  
    
	@FieldDisplay("一来电类型")
    public String getDirTypeName(){
        return this.dirTypeName;  
    }
	
	public void setSubDirTypeName(String subDirTypeName){  
        this.subDirTypeName = subDirTypeName;  
    }  
    
	@FieldDisplay("二来电类型")
    public String getSubDirTypeName(){
        return this.subDirTypeName;  
    }
	
	public void setReplyStatus(String replyStatus){  
        this.replyStatus = replyStatus;  
    }  
    
	@FieldDisplay("投诉回复状态 0暂存 1完成")
    public String getReplyStatus(){
        return this.replyStatus;  
    }
	
	public void setReplyLog(String replyLog){  
        this.replyLog = replyLog;  
    }  
    
	@FieldDisplay("回复记录")
    public String getReplyLog(){
        return this.replyLog;  
    }
	
	public void setCustomerId(String customerId){  
        this.customerId = customerId;  
    }  
    
	@FieldDisplay("客户编号")
    public String getCustomerId(){
        return this.customerId;  
    }
	
	public void setCustomerName(String customerName){  
        this.customerName = customerName;  
    }  
    
	@FieldDisplay("客户名称")
    public String getCustomerName(){
        return this.customerName;  
    }
	
	public void setCustomerType(String customerType){  
        this.customerType = customerType;  
    }  
    
	@FieldDisplay("客户类型 1.移动 2.核心 3.潜在")
    public String getCustomerType(){
        return this.customerType;  
    }
	
	public void setRecordId(String recordId){  
        this.recordId = recordId;  
    }  
    
	@FieldDisplay("录音Id")
    public String getRecordId(){
        return this.recordId;  
    }
	
	public void setDisconnectTime(String disconnectTime){  
        this.disconnectTime = disconnectTime;  
    }  
    
	@FieldDisplay("挂断时间")
    public String getDisconnectTime(){
        return this.disconnectTime;  
    }
	
	public void setConnectTime(String connectTime){  
        this.connectTime = connectTime;  
    }  
    
	@FieldDisplay("接通时间")
    public String getConnectTime(){
        return this.connectTime;  
    }
	
	public void setIsBindWx(Integer isBindWx){  
        this.isBindWx = isBindWx;  
    }  
    
	@FieldDisplay("是否绑定微信 1是, 0否")
    public Integer getIsBindWx(){
        return this.isBindWx;  
    }
	
	public void setPushDate(Integer pushDate){  
        this.pushDate = pushDate;  
    }  
    
	@FieldDisplay("预期天数")
    public Integer getPushDate(){
        return this.pushDate;  
    }
	
	public void setCustomerCallId(String customerCallId){  
        this.customerCallId = customerCallId;  
    }  
    
	@FieldDisplay("思科通话唯一标识")
    public String getCustomerCallId(){
        return this.customerCallId;  
    }
	
}