package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 部门表参数封装类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class DepartmentVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String name;  //名称
	private String code;  //编码
	private String fullCode;  //编码
	private String parentCode;  //父部门
	
	@Override
	public void setId(String id){
		super.setId(id);
	}
	@Override
	public DepartmentVo setTenantId(String id){
		super.setTenantId(id);
		return this;
	}
	
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
	
	public void setParentCode(String parentCode){  
        this.parentCode = parentCode;  
    }  
      
    public String getParentCode(){  
        return this.parentCode;  
    }
	
	public DepartmentVo id(String id) {
		setId(id);
		return this;
	}
	public String getFullCode() {
		return fullCode;
	}
	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}
	public DepartmentVo fullCode(String fullCode) {
		this.fullCode = fullCode;
		return this;
	}
	
}