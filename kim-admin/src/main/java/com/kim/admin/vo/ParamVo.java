package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 参数配置表参数封装类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
public class ParamVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String code;  //编码
	private String name;  //名称
	private String value;  //值

	public ParamVo id(String id) {
		setId(id);
		return this;
	}

	public ParamVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setValue(String value){  
        this.value = value;  
    }  
      
    public String getValue(){  
        return this.value;  
    }
	
}