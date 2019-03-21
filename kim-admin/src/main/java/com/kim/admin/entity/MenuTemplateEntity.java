package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 租户菜单模板表实体类
 * @date 2018-8-2 15:08:10
 * @author bo.liu01
 */
public class MenuTemplateEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String code;//模板编码
	private String name;//模板名称
	
	public MenuTemplateEntity id(String id) {
		setId(id);
		return this;
	}

	public MenuTemplateEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("模板编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("模板名称")
    public String getName(){
        return this.name;  
    }

}