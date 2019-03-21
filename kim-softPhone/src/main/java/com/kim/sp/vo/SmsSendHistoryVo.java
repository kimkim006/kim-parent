package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 上下行短信发送记录参数封装类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class SmsSendHistoryVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String appCode;  //业务系统代码
	private String businessNo;  //业务系统请求批次号
	private String businessType;  //发送类型
	private String tempCode;  //模板编号
	private String phone;  //电话号码
	private String customerCallId;  //通话唯一标识
	private String name;  //用户姓名
	private String username;  //用户工号
	private String agentId;  //话务工号
	private String subCode;  //短信类型标志
	private String smsRespNo;  //短信平台处理流水

	public SmsSendHistoryVo id(String id) {
		setId(id);
		return this;
	}

	public SmsSendHistoryVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setAppCode(String appCode){  
        this.appCode = appCode;  
    }  
      
    public String getAppCode(){  
        return this.appCode;  
    }
    
	
	public void setBusinessNo(String businessNo){  
        this.businessNo = businessNo;  
    }  
      
    public String getBusinessNo(){  
        return this.businessNo;  
    }
    
	
	public void setBusinessType(String businessType){  
        this.businessType = businessType;  
    }  
      
    public String getBusinessType(){  
        return this.businessType;  
    }
    
	
	public void setTempCode(String tempCode){  
        this.tempCode = tempCode;  
    }  
      
    public String getTempCode(){  
        return this.tempCode;  
    }
    
	
	public void setPhone(String phone){  
        this.phone = phone;  
    }  
      
    public String getPhone(){  
        return this.phone;  
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
    
	
	public void setSubCode(String subCode){  
        this.subCode = subCode;  
    }  
      
    public String getSubCode(){  
        return this.subCode;  
    }
    
	
	public void setSmsRespNo(String smsRespNo){  
        this.smsRespNo = smsRespNo;  
    }  
      
    public String getSmsRespNo(){  
        return this.smsRespNo;  
    }
    
	
}