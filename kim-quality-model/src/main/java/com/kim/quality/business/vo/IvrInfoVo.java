package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 录音IVR信息参数封装类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
public class IvrInfoVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String serialNumber;  //录音流水号
	private String custIdCard;  //客户身份证
	private String ivrVerify;  //ivr验证
	private String ivrVerifyCode;  //ivr验证
	
	private String startTime;  //开始时间
	private String endTime;  //结束时间

	public IvrInfoVo id(String id) {
		setId(id);
		return this;
	}

	public IvrInfoVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
      
    public String getSerialNumber(){  
        return this.serialNumber;  
    }
	
	public void setCustIdCard(String custIdCard){  
        this.custIdCard = custIdCard;  
    }  
      
    public String getCustIdCard(){  
        return this.custIdCard;  
    }
	
	public void setIvrVerify(String ivrVerify){  
        this.ivrVerify = ivrVerify;  
    }  
      
    public String getIvrVerify(){  
        return this.ivrVerify;  
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIvrVerifyCode() {
		return ivrVerifyCode;
	}

	public void setIvrVerifyCode(String ivrVerifyCode) {
		this.ivrVerifyCode = ivrVerifyCode;
	}
	
}