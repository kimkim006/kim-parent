package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 挂机不就绪记录表实体类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class AgentNotReadyEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 类型  1挂机后不就绪 */
	public static final int TYPE_NOT_READY = 1;
	
	/** 用户姓名 */
	private String name;
	/** 用户工号 */
	private String username;
	/** 话务工号 */
	private String agentId;
	/** 开始时间 */
	private String startTime;
	/** 类型  1挂机后不就绪 */
	private Integer types;
	
	public AgentNotReadyEntity id(String id) {
		setId(id);
		return this;
	}

	public AgentNotReadyEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("用户姓名")
    public String getName(){
        return this.name;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("话务工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
    
	@FieldDisplay("开始时间")
    public String getStartTime(){
        return this.startTime;  
    }
	
	public void setTypes(Integer types){  
        this.types = types;  
    }  
    
	@FieldDisplay("类型  1挂机后不就绪")
    public Integer getTypes(){
        return this.types;  
    }
	
}