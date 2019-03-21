package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 角色表参数封装类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class RoleVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String code;  //编码
	private String name;  //名称
	private String roleTypeId;  //角色类型,admin_role_type.id
	
	private String username;  //用户名
	
	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setRoleTypeId(String roleTypeId){  
        this.roleTypeId = roleTypeId;  
    }  
      
    public String getRoleTypeId(){  
        return this.roleTypeId;  
    }

	public String getUsername() {
		return username;
	}

	public RoleVo setUsername(String username) {
		this.username = username;
		return this;
	}
	
}