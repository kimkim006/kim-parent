package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 话务工号与功能码表关联表实体类
 * @date 2019-3-7 16:14:23
 * @author liubo
 */
public class AgentFuncRlEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 话务工号 */
	private String agentId;
	/** 功能id */
	private String funcId;
	
	public AgentFuncRlEntity id(String id) {
		setId(id);
		return this;
	}

	public AgentFuncRlEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("话务工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
	public void setFuncId(String funcId){  
        this.funcId = funcId;  
    }  
    
	@FieldDisplay("功能id")
    public String getFuncId(){
        return this.funcId;  
    }
	
}