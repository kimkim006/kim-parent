package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 角色类型参数封装类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class RoleTypeVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String name;  //名称
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
}