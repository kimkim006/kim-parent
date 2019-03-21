package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 职位表参数封装类
 * @date 2017-11-16 14:44:05
 * @author bo.liu01
 */
public class PostVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String name;  //名称
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	
}