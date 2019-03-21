package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 部门用户表实体类
 * @date 2017-11-16 14:44:05
 * @author bo.liu01
 */
public class DepartmentUserEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String parentCode;//父部门编码
	private String departmentId;//部门id
	private String departmentName;//部门名称
	
	private String departmentCode;//部门编码
	private String username;//用户名
	
	private String usernames;//用户名

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setDepartmentCode(String departmentCode){
        this.departmentCode = departmentCode;  
    }  
    
	@FieldDisplay("部门编码")
    public String getDepartmentCode(){
        return this.departmentCode;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户名")
    public String getUsername(){
        return this.username;  
    }

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
}