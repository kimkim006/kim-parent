package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 话务工号功能码表参数封装类
 * @date 2019-3-7 16:35:07
 * @author liubo
 */
public class AgentFunctionVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String funcCode;  //功能编码
	private String funcName;  //功能名称
	private String funcValue;  //功能值
	private String platformType;  //平台类型
	private String platformId;  //话务平台id
	
	private String agentId;  //话务工号

	public AgentFunctionVo id(String id) {
		setId(id);
		return this;
	}

	public AgentFunctionVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setFuncCode(String funcCode){  
        this.funcCode = funcCode;  
    }  
      
    public String getFuncCode(){  
        return this.funcCode;  
    }
    
	
	public void setFuncName(String funcName){  
        this.funcName = funcName;  
    }  
      
    public String getFuncName(){  
        return this.funcName;  
    }
    
	
	public void setFuncValue(String funcValue){  
        this.funcValue = funcValue;  
    }  
      
    public String getFuncValue(){  
        return this.funcValue;  
    }
    
	
	public void setPlatformType(String platformType){  
        this.platformType = platformType;  
    }  
      
    public String getPlatformType(){  
        return this.platformType;  
    }
    
	
	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
      
    public String getPlatformId(){  
        return this.platformId;  
    }

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
    
	
}