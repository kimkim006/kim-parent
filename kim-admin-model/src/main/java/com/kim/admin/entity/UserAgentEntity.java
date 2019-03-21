package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 坐席工号表实体类
 * @date 2018-9-7 15:33:14
 * @author yonghui.wu
 */
public class UserAgentEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String username;//用户名/工号
	private String agentNo;//话务工号
	private String platform;//话务平台
	private String agentPwd;//话务工号密码
	/**额外字段**/
	private String name;  //姓名
	public UserAgentEntity id(String id) {
		setId(id);
		return this;
	}

	public UserAgentEntity tenantId(String tenantId) {
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
	
	public void setAgentNo(String agentNo){  
        this.agentNo = agentNo;  
    }  
    
	@FieldDisplay("话务工号")
    public String getAgentNo(){
        return this.agentNo;  
    }
	
	public void setPlatform(String platform){  
        this.platform = platform;  
    }  
    
	@FieldDisplay("话务平台")
    public String getPlatform(){
        return this.platform;  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgentPwd() {
		return agentPwd;
	}

	public void setAgentPwd(String agentPwd) {
		this.agentPwd = agentPwd;
	}
}