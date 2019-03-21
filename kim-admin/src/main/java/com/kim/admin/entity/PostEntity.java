package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 职位表实体类
 * @date 2017-11-16 14:44:05
 * @author bo.liu01
 */
public class PostEntity extends LoggedEntity{
	
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