package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 话务工号与功能码表关联表参数封装类
 * @date 2019-3-7 16:14:23
 * @author liubo
 */
public class AgentFuncRlVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String agentId;  //话务工号
	private String funcId;  //功能id

	public AgentFuncRlVo id(String id) {
		setId(id);
		return this;
	}

	public AgentFuncRlVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
    
	
	public void setFuncId(String funcId){  
        this.funcId = funcId;  
    }  
      
    public String getFuncId(){  
        return this.funcId;  
    }
    
	
}