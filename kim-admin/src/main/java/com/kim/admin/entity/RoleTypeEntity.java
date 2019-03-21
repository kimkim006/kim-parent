package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 角色类型实体类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class RoleTypeEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String name;//名称
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
}