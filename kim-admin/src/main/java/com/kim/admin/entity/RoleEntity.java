package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 角色表实体类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class RoleEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String code;//编码
	private String name;//名称
	private Integer roleTypeId;//角色类型,admin_role_type.id
	private String roleTypeName;//角色类型,admin_role_type.id

    public String getRoleTypeName() {
        return roleTypeName;
    }

    public void setRoleTypeName(String roleTypeName) {
        this.roleTypeName = roleTypeName;
    }

    public void setCode(String code){
        this.code = code;  
    }  
    
	@FieldDisplay("编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setRoleTypeId(Integer roleTypeId){  
        this.roleTypeId = roleTypeId;  
    }  
    
	@FieldDisplay("角色类型,admin_role_type.id")
    public Integer getRoleTypeId(){
        return this.roleTypeId;  
    }
	
}