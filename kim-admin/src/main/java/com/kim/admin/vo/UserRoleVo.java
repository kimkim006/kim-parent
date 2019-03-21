package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

import java.util.List;

/**
 * 用户角色表参数封装类
 * @date 2017-11-10 11:27:35
 * @author bo.liu01
 */
public class UserRoleVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String username;  //用户名
	private String roleCode;  //角色，admin_role.code
	
	private String usernames;  //用户名
	private String addRole;  //要添加的角色
	private String delRole;  //要移除的角色
	private List<String> delRoleList;  //要移除的角色

    public List<String> getDelRoleList() {
        return delRoleList;
    }

    public void setDelRoleList(List<String> delRoleList) {
        this.delRoleList = delRoleList;
    }

    public String getAddRole() {
        return addRole;
    }

    public void setAddRole(String addRole) {
        this.addRole = addRole;
    }

    public String getDelRole() {
        return delRole;
    }

    public void setDelRole(String delRole) {
        this.delRole = delRole;
    }

    public void setUsername(String username){
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
	public void setRoleCode(String roleCode){  
        this.roleCode = roleCode;  
    }  
      
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