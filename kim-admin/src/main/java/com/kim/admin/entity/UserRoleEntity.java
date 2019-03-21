package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 用户角色表实体类
 * @date 2017-11-10 11:27:35
 * @author bo.liu01
 */
public class UserRoleEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_USER_ROLE_CODE = ""; 
	
	private String username;//用户名
	private String roleCode;//角色，admin_role.code
	
	private String usernames;//用户名
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户名")
    public String getUsername(){
        return this.username;  
    }
	
	public void setRoleCode(String roleCode){  
        this.roleCode = roleCode;  
    }  
    
	@FieldDisplay("角色，admin_role.code")
    public String getRoleCode(){
        return this.roleCode;  
    }

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}
	
}