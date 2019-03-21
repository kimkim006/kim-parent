package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 坐席工号表参数封装类
 * @date 2018-9-7 15:33:14
 * @author yonghui.wu
 */
public class UserAgentVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String username;  //用户名/工号
	private String agentNo;  //话务工号
	private String platform;  //话务平台
	/**额外字段**/
	private String name;
	private String status;
	public UserAgentVo id(String id) {
		setId(id);
		return this;
	}

	public UserAgentVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
	
	public void setAgentNo(String agentNo){  
        this.agentNo = agentNo;  
    }  
      
    public String getAgentNo(){  
        return this.agentNo;  
    }
	
	public void setPlatform(String platform){  
        this.platform = platform;  
    }  
      
    public String getPlatform(){  
        return this.platform;  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}