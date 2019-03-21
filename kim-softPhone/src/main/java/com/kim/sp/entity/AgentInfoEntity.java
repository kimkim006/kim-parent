package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 工号信息表实体类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class AgentInfoEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 话务工号 */
	private String agentId;
	/** 工号密码 */
	private String agentPwd;
	/** 平台类型 */
	private String platformType;
	/** 话务平台id */
	private String platformId;
	/** 录音平台id */
	private String recordPlatformId;
	
	public AgentInfoEntity id(String id) {
		setId(id);
		return this;
	}

	public AgentInfoEntity tenantId(String tenantId) {
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
	
	public void setAgentPwd(String agentPwd){  
        this.agentPwd = agentPwd;  
    }  
    
	@FieldDisplay("工号密码")
    public String getAgentPwd(){
        return this.agentPwd;  
    }
	
	public void setPlatformType(String platformType){  
        this.platformType = platformType;  
    }  
    
	@FieldDisplay("平台类型")
    public String getPlatformType(){
        return this.platformType;  
    }
	
	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
    
	@FieldDisplay("话务平台id")
    public String getPlatformId(){
        return this.platformId;  
    }
	
	public void setRecordPlatformId(String recordPlatformId){  
        this.recordPlatformId = recordPlatformId;  
    }  
    
	@FieldDisplay("录音平台id")
    public String getRecordPlatformId(){
        return this.recordPlatformId;  
    }
	
}