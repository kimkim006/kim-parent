package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 参数配置表实体类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
public class ParamEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String code;//编码
	private String name;//名称
	private String value;//值
	
	private String tenantName;//值
	
	public ParamEntity id(String id) {
		setId(id);
		return this;
	}

	public ParamEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setValue(String value){  
        this.value = value;  
    }  
    
	@FieldDisplay("值")
    public String getValue(){
        return this.value;  
    }

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
}