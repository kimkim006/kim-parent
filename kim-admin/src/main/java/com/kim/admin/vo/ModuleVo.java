package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 模块表参数封装类
 * @date 2017-11-10 17:57:36
 * @author bo.liu01
 */
public class ModuleVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String name;  //名称
	private String code;  //编码
	private String className;  //类名
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }
	
	public void setClassName(String className){  
        this.className = className;  
    }  
      
    public String getClassName(){  
        return this.className;  
    }
	
}