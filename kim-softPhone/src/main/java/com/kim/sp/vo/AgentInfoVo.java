package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 工号信息表参数封装类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class AgentInfoVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String agentId;  //话务工号
	private String agentPwd;  //工号密码
	private String platformType;  //平台类型
	private String platformId;  //话务平台id
	private String recordPlatformId;  //录音平台id
	
	private String extension;  //是否加载软电话

	public AgentInfoVo id(String id) {
		setId(id);
		return this;
	}

	public AgentInfoVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
    
	
	public void setAgentPwd(String agentPwd){  
        this.agentPwd = agentPwd;  
    }  
      
    public String getAgentPwd(){  
        return this.agentPwd;  
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
    
	
	public void setRecordPlatformId(String recordPlatformId){  
        this.recordPlatformId = recordPlatformId;  
    }  
      
    public String getRecordPlatformId(){  
        return this.recordPlatformId;  
    }

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	
}