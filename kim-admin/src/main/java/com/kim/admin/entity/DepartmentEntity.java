package com.kim.admin.entity;

import java.util.ArrayList;
import java.util.List;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 部门表实体类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class DepartmentEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;

	private String name;//名称
	private String code;//编码
	private String fullCode;//完整编码
	private String parentCode;//父部门
	private String parentName;//父部门
	
	private String fullName;//部门全称，包含父部门
	private boolean disabled = false;

    private List<DepartmentEntity> children = new ArrayList<>();

    public List<DepartmentEntity> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentEntity> children) {
        this.children = children;
    }

    public void addChild(DepartmentEntity depart) {
        this.children.add(depart);
    }

    public void setName(String name){
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setParentCode(String parentCode){
        this.parentCode = parentCode;  
    }  
    
	@FieldDisplay("父部门")
    public String getParentCode(){
        return this.parentCode;  
    }
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}
	
}