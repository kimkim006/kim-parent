package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 租户策略表参数封装类
 * @date 2018-8-1 14:10:22
 * @author bo.liu01
 */
public class TenantPolicyVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String username;  //用户名/工号
	private String isCurrent;  //是否当前租户， 1是, 0否
	private String actualTenantId;  //所属租户

	public TenantPolicyVo id(String id) {
		setId(id);
		return this;
	}

	public TenantPolicyVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
	public void setIsCurrent(String isCurrent){  
        this.isCurrent = isCurrent;  
    }  
      
    public String getIsCurrent(){  
        return this.isCurrent;  
    }
	
	public void setActualTenantId(String actualTenantId){  
        this.actualTenantId = actualTenantId;  
    }  
      
    public String getActualTenantId(){  
        return this.actualTenantId;  
    }
	
}