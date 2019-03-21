package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 用户职位表实体类
 * @date 2018-7-13 15:07:28
 * @author bo.liu01
 */
public class UserPostEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String username;//用户名/工号
	private String postId;//职务id
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户名/工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setPostId(String postId){  
        this.postId = postId;  
    }  
    
	@FieldDisplay("职务id")
    public String getPostId(){
        return this.postId;  
    }
	
	public UserPostEntity setTenantId(String tenantId){  
        this.tenantId = tenantId;  
        return this;
    }  
    
}