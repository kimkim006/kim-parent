package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 挂机不就绪记录表参数封装类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class AgentNotReadyVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String name;  //用户姓名
	private String username;  //用户工号
	private String agentId;  //话务工号
	private String startTime;  //开始时间
	private String startTimeStart;  //查询开始时间开始时间
	private String startTimeEnd;  //查询开始时间结束时间
	private String types;  //类型  1挂机后不就绪

	public AgentNotReadyVo id(String id) {
		setId(id);
		return this;
	}

	public AgentNotReadyVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
    
	
	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
    
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
    
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
      
    public String getStartTime(){  
        return this.startTime;  
    }
    
	public void setStartTimeStart(String startTimeStart){  
        this.startTimeStart = startTimeStart;  
    }
	
	public String getStartTimeStart(){  
        return this.startTimeStart;  
    }
	
	public void setStartTimeEnd(String startTimeEnd){  
        this.startTimeEnd = startTimeEnd;  
    }
	
	public String getStartTimeEnd(){  
        return this.startTimeEnd;  
    }
	
	public void setTypes(String types){  
        this.types = types;  
    }  
      
    public String getTypes(){  
        return this.types;  
    }
    
	
}