package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 租户策略表实体类
 * @date 2018-8-1 14:10:22
 * @author bo.liu01
 */
public class TenantPolicyEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 是否当前租户， 1是 */
	public static final int IS_CURRENT_YES = 1;
	/** 是否当前租户， 0否 */
	public static final int IS_CURRENT_NO = 0;
	
	private String username;//用户名/工号
	private int isCurrent;//是否当前租户， 1是, 0否
	private String actualTenantId;//所属租户
	
	public TenantPolicyEntity id(String id) {
		setId(id);
		return this;
	}

	public TenantPolicyEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户名/工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setIsCurrent(int isCurrent){  
        this.isCurrent = isCurrent;  
    }  
    
	@FieldDisplay("是否当前租户， 1是, 0否")
    public int getIsCurrent(){
        return this.isCurrent;  
    }
	
	public void setActualTenantId(String actualTenantId){  
        this.actualTenantId = actualTenantId;  
    }  
    
	@FieldDisplay("所属租户")
    public String getActualTenantId(){
        return this.actualTenantId;  
    }
	
}