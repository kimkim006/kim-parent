package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 转IVR信息表参数封装类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class IsTransIvrVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String customerCallId;  //思科通话唯一标识
	private String name;  //用户姓名
	private String username;  //用户工号
	private String agentId;  //话务工号
	private String isTrans;  //是否转ivr  0:不转ivr,1:转ivr
	private String endTime;  //结束时间
	private String endTimeStart;  //查询结束时间开始时间
	private String endTimeEnd;  //查询结束时间结束时间
	private String telephone;  //电话号码
	private String isSendSuccess;  //是否转成功
	private String serviceNo;  //热线号码
	private String createTime;  //创建时间
	private String createTimeStart;  //查询创建时间开始时间
	private String createTimeEnd;  //查询创建时间结束时间
	private String createName;  //创建人code
	private String createUser;  //创建人名称

	public IsTransIvrVo id(String id) {
		setId(id);
		return this;
	}

	public IsTransIvrVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setCustomerCallId(String customerCallId){  
        this.customerCallId = customerCallId;  
    }  
      
    public String getCustomerCallId(){  
        return this.customerCallId;  
    }
    
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
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
    
	
	public void setIsTrans(String isTrans){  
        this.isTrans = isTrans;  
    }  
      
    public String getIsTrans(){  
        return this.isTrans;  
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
	
	public void setTelephone(String telephone){  
        this.telephone = telephone;  
    }  
      
    public String getTelephone(){  
        return this.telephone;  
    }
    
	
	public void setIsSendSuccess(String isSendSuccess){  
        this.isSendSuccess = isSendSuccess;  
    }  
      
    public String getIsSendSuccess(){  
        return this.isSendSuccess;  
    }
    
	
	public void setServiceNo(String serviceNo){  
        this.serviceNo = serviceNo;  
    }  
      
    public String getServiceNo(){  
        return this.serviceNo;  
    }
    
	
	public void setCreateTime(String createTime){  
        this.createTime = createTime;  
    }  
      
    public String getCreateTime(){  
        return this.createTime;  
    }
    
	public void setCreateTimeStart(String createTimeStart){  
        this.createTimeStart = createTimeStart;  
    }
	
	public String getCreateTimeStart(){  
        return this.createTimeStart;  
    }
	
	public void setCreateTimeEnd(String createTimeEnd){  
        this.createTimeEnd = createTimeEnd;  
    }
	
	public String getCreateTimeEnd(){  
        return this.createTimeEnd;  
    }
	
	public void setCreateName(String createName){  
        this.createName = createName;  
    }  
      
    public String getCreateName(){  
        return this.createName;  
    }
    
	
	public void setCreateUser(String createUser){  
        this.createUser = createUser;  
    }  
      
    public String getCreateUser(){  
        return this.createUser;  
    }
    
	
}