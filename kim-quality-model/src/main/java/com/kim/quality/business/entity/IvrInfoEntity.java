package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音IVR信息实体类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
public class IvrInfoEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String date;//数据日期
	private String serialNumber;//录音流水号
	private String custIdCard;//客户身份证
	private String ivrVerify;//ivr验证
	private Integer ivrVerifyCode;//ivr验证
	
	public IvrInfoEntity id(String id) {
		setId(id);
		return this;
	}

	public IvrInfoEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
    
	@FieldDisplay("录音流水号")
    public String getSerialNumber(){
        return this.serialNumber;  
    }
	
	public void setCustIdCard(String custIdCard){  
        this.custIdCard = custIdCard;  
    }  
    
	@FieldDisplay("客户身份证")
    public String getCustIdCard(){
        return this.custIdCard;  
    }
	
	public void setIvrVerify(String ivrVerify){  
        this.ivrVerify = ivrVerify;  
    }  
    
	@FieldDisplay("ivr验证")
    public String getIvrVerify(){
        return this.ivrVerify;  
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getIvrVerifyCode() {
		return ivrVerifyCode;
	}

	public void setIvrVerifyCode(Integer ivrVerifyCode) {
		this.ivrVerifyCode = ivrVerifyCode;
	}
	
}