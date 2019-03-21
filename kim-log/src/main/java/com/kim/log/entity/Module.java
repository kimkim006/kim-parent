package com.kim.log.entity;

import com.kim.log.annotation.FieldDisplay;

/**
 * 模块表实体类
 * @date 2017-11-10 17:57:36
 * @author bo.liu01
 */
public class Module extends BaseRes {
	
	private static final long serialVersionUID = 1L; 
	
	protected String className;//类名

	public void setClassName(String className){
        this.className = className;
    }  
    
	@FieldDisplay("类名")
    public String getClassName(){
        return this.className;  
    }
	
}