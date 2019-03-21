package com.kim.quality.setting.vo;

import com.kim.common.base.BaseVo;

/**
 * 质检规则目录表参数封装类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
public class RuleDirVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String parentCode;  //父编码
	private String code;  //编码
	private String sampleType;//抽检规则类型

	public RuleDirVo id(String id) {
		setId(id);
		return this;
	}

	public RuleDirVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setParentCode(String parentCode){  
        this.parentCode = parentCode;  
    }  
      
    public String getParentCode(){  
        return this.parentCode;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	
	
}