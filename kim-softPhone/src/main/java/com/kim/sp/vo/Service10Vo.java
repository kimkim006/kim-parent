package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 服务记录信息表参数封装类
 * @date 2019-3-6 19:35:36
 * @author liubo
 */
public class Service10Vo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String serviceId;  //服务ID
	private String ani;  //主叫号码
	private String callType;  //呼叫方向
	private String startTime;  //服务开始时间
	private String startTimeStart;  //查询服务开始时间开始时间
	private String startTimeEnd;  //查询服务开始时间结束时间
	private String endTime;  //服务结束时间
	private String endTimeStart;  //查询服务结束时间开始时间
	private String endTimeEnd;  //查询服务结束时间结束时间
	private String username;  //用户工号
	private String agentId;  //座席工号
	private String orgCode;  //组织机构编码
	private String bussinessType;  //业务类型  1客服  2电销 3催收
	private String isBooking;  //是否预约回复
	private String bookingTime;  //预约时间
	private String bookingTimeStart;  //查询预约时间开始时间
	private String bookingTimeEnd;  //查询预约时间结束时间
	private String areaName;  //来电名称
	private String areaCode;  //来电区号
	private String serviceType;  //服务类型 1电话 2短信 3APP
	private String isRemind;  //是否需要回复
	private String serviceLog;  //接触记录
	private String dnis;  //被叫号码
	private String servicePhone;  //服务联系电话
	private String subDirTypeName;  //二来电类型
	private String replyStatus;  //投诉回复状态 0暂存 1完成
	private String replyLog;  //回复记录
	private String dirTypeName;  //一来电类型
	private String customerId;  //客户编号
	private String customerName;  //客户名称
	private String customerType;  //客户类型 1.移动 2.核心 3.潜在
	private String recordid;  //呼入弹屏传过来的录音Id
	private String serviceno;  //热线号码
	private String disconnectTime;  //挂断时间
	private String disconnectTimeStart;  //查询挂断时间开始时间
	private String disconnectTimeEnd;  //查询挂断时间结束时间
	private String connectTime;  //接通时间
	private String connectTimeStart;  //查询接通时间开始时间
	private String connectTimeEnd;  //查询接通时间结束时间
	private String reserve;  //客户记录是否绑定微信号，汽车金融记录是否风险合同
	private Integer pushDate;  //预期天数
	private String customerCallId;  //
	
	private String phone;  //

	public Service10Vo id(String id) {
		setId(id);
		return this;
	}

	public Service10Vo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
      
    public String getServiceId(){  
        return this.serviceId;  
    }
    
	
	public void setAni(String ani){  
        this.ani = ani;  
    }  
      
    public String getAni(){  
        return this.ani;  
    }
    
	
	public void setCallType(String callType){  
        this.callType = callType;  
    }  
      
    public String getCallType(){  
        return this.callType;  
    }
    
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
      
    public String getStartTime(){  
        return this.startTime;  
    }
    
	public void setStartTimeStart(String startTimeStart){  
        this.startTimeStart = startTimeStart;  
    }
	
	public String getStartTimeStart(){  
        return this.startTimeStart;  
    }
	
	public void setStartTimeEnd(String startTimeEnd){  
        this.startTimeEnd = startTimeEnd;  
    }
	
	public String getStartTimeEnd(){  
        return this.startTimeEnd;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
      
    public String getEndTime(){  
        return this.endTime;  
    }
    
	public void setEndTimeStart(String endTimeStart){  
        this.endTimeStart = endTimeStart;  
    }
	
	public String getEndTimeStart(){  
        return this.endTimeStart;  
    }
	
	public void setEndTimeEnd(String endTimeEnd){  
        this.endTimeEnd = endTimeEnd;  
    }
	
	public String getEndTimeEnd(){  
        return this.endTimeEnd;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
    
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
    
	
	public void setOrgCode(String orgCode){  
        this.orgCode = orgCode;  
    }  
      
    public String getOrgCode(){  
        return this.orgCode;  
    }
    
	
	public void setBussinessType(String bussinessType){  
        this.bussinessType = bussinessType;  
    }  
      
    public String getBussinessType(){  
        return this.bussinessType;  
    }
    
	
	public void setIsBooking(String isBooking){  
        this.isBooking = isBooking;  
    }  
      
    public String getIsBooking(){  
        return this.isBooking;  
    }
    
	
	public void setBookingTime(String bookingTime){  
        this.bookingTime = bookingTime;  
    }  
      
    public String getBookingTime(){  
        return this.bookingTime;  
    }
    
	public void setBookingTimeStart(String bookingTimeStart){  
        this.bookingTimeStart = bookingTimeStart;  
    }
	
	public String getBookingTimeStart(){  
        return this.bookingTimeStart;  
    }
	
	public void setBookingTimeEnd(String bookingTimeEnd){  
        this.bookingTimeEnd = bookingTimeEnd;  
    }
	
	public String getBookingTimeEnd(){  
        return this.bookingTimeEnd;  
    }
	
	public void setAreaName(String areaName){  
        this.areaName = areaName;  
    }  
      
    public String getAreaName(){  
        return this.areaName;  
    }
    
	
	public void setAreaCode(String areaCode){  
        this.areaCode = areaCode;  
    }  
      
    public String getAreaCode(){  
        return this.areaCode;  
    }
    
	
	public void setServiceType(String serviceType){  
        this.serviceType = serviceType;  
    }  
      
    public String getServiceType(){  
        return this.serviceType;  
    }
    
	
	public void setIsRemind(String isRemind){  
        this.isRemind = isRemind;  
    }  
      
    public String getIsRemind(){  
        return this.isRemind;  
    }
    
	
	public void setServiceLog(String serviceLog){  
        this.serviceLog = serviceLog;  
    }  
      
    public String getServiceLog(){  
        return this.serviceLog;  
    }
    
	
	public void setDnis(String dnis){  
        this.dnis = dnis;  
    }  
      
    public String getDnis(){  
        return this.dnis;  
    }
    
	
	public void setServicePhone(String servicePhone){  
        this.servicePhone = servicePhone;  
    }  
      
    public String getServicePhone(){  
        return this.servicePhone;  
    }
    
	
	public void setSubDirTypeName(String subDirTypeName){  
        this.subDirTypeName = subDirTypeName;  
    }  
      
    public String getSubDirTypeName(){  
        return this.subDirTypeName;  
    }
    
	
	public void setReplyStatus(String replyStatus){  
        this.replyStatus = replyStatus;  
    }  
      
    public String getReplyStatus(){  
        return this.replyStatus;  
    }
    
	
	public void setReplyLog(String replyLog){  
        this.replyLog = replyLog;  
    }  
      
    public String getReplyLog(){  
        return this.replyLog;  
    }
    
	
	public void setDirTypeName(String dirTypeName){  
        this.dirTypeName = dirTypeName;  
    }  
      
    public String getDirTypeName(){  
        return this.dirTypeName;  
    }
    
	
	public void setCustomerId(String customerId){  
        this.customerId = customerId;  
    }  
      
    public String getCustomerId(){  
        return this.customerId;  
    }
    
	
	public void setCustomerName(String customerName){  
        this.customerName = customerName;  
    }  
      
    public String getCustomerName(){  
        return this.customerName;  
    }
    
	
	public void setCustomerType(String customerType){  
        this.customerType = customerType;  
    }  
      
    public String getCustomerType(){  
        return this.customerType;  
    }
    
	
	public void setRecordid(String recordid){  
        this.recordid = recordid;  
    }  
      
    public String getRecordid(){  
        return this.recordid;  
    }
    
	
	public void setServiceno(String serviceno){  
        this.serviceno = serviceno;  
    }  
      
    public String getServiceno(){  
        return this.serviceno;  
    }
    
	
	public void setDisconnectTime(String disconnectTime){  
        this.disconnectTime = disconnectTime;  
    }  
      
    public String getDisconnectTime(){  
        return this.disconnectTime;  
    }
    
	public void setDisconnectTimeStart(String disconnectTimeStart){  
        this.disconnectTimeStart = disconnectTimeStart;  
    }
	
	public String getDisconnectTimeStart(){  
        return this.disconnectTimeStart;  
    }
	
	public void setDisconnectTimeEnd(String disconnectTimeEnd){  
        this.disconnectTimeEnd = disconnectTimeEnd;  
    }
	
	public String getDisconnectTimeEnd(){  
        return this.disconnectTimeEnd;  
    }
	
	public void setConnectTime(String connectTime){  
        this.connectTime = connectTime;  
    }  
      
    public String getConnectTime(){  
        return this.connectTime;  
    }
    
	public void setConnectTimeStart(String connectTimeStart){  
        this.connectTimeStart = connectTimeStart;  
    }
	
	public String getConnectTimeStart(){  
        return this.connectTimeStart;  
    }
	
	public void setConnectTimeEnd(String connectTimeEnd){  
        this.connectTimeEnd = connectTimeEnd;  
    }
	
	public String getConnectTimeEnd(){  
        return this.connectTimeEnd;  
    }
	
	public void setReserve(String reserve){  
        this.reserve = reserve;  
    }  
      
    public String getReserve(){  
        return this.reserve;  
    }
    
	
	public void setPushDate(Integer pushDate){  
        this.pushDate = pushDate;  
    }  
      
    public Integer getPushDate(){  
        return this.pushDate;  
    }
    
	
	public void setCustomerCallId(String customerCallId){  
        this.customerCallId = customerCallId;  
    }  
      
    public String getCustomerCallId(){  
        return this.customerCallId;  
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    
	
}