package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 部门用户表参数封装类
 * @date 2017-11-16 14:44:05
 * @author bo.liu01
 */
public class DepartmentUserVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String departmentCode;  //部门编码
	private String username;  //工号
	
	public DepartmentUserVo id(String id) {
		setId(id);
		return this;
	}

	public DepartmentUserVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setDepartmentCode(String departmentCode){  
        this.departmentCode = departmentCode;  
    }  
      
    public String getDepartmentCode(){  
        return this.departmentCode;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
}