package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 坐席小休记录表实体类
 * @date 2019-3-13 10:14:40
 * @author liubo
 */
public class AgentRestEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 小休结果1.成功 */
	public static final int RESULT_SUCCESS = 1;
	/** 小休结果  0.失败 */
	public static final int RESULT_FAIL = 0;
	
	/** 小休日期 */
	private String restDate;
	/** 开始排队时间 */
	private String bqTime;
	/** 小休开始时间 */
	private String startTime;
	/** 小休结束时间 */
	private String endTime;
	/** 小休结果1.成功 0.失败 */
	private Integer result;
	/** 本次小休时长 */
	private Long timeLong;
	/** 用户姓名 */
	private String name;
	/** 用户工号 */
	private String username;
	/** 话务工号 */
	private String agentId;
	/** 组织机构编码 */
	private String departmentCode;
	/** 组织机构名称 */
	private String departmentName;
	
	public AgentRestEntity id(String id) {
		setId(id);
		return this;
	}

	public AgentRestEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setRestDate(String restDate){  
        this.restDate = restDate;  
    }  
    
	@FieldDisplay("小休日期")
    public String getRestDate(){
        return this.restDate;  
    }
	
	public void setBqTime(String bqTime){  
        this.bqTime = bqTime;  
    }  
    
	@FieldDisplay("开始排队时间")
    public String getBqTime(){
        return this.bqTime;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
    
	@FieldDisplay("小休开始时间")
    public String getStartTime(){
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
    
	@FieldDisplay("小休结束时间")
    public String getEndTime(){
        return this.endTime;  
    }
	
	public void setResult(Integer result){  
        this.result = result;  
    }  
    
	@FieldDisplay("小休结果1.成功 0.失败")
    public Integer getResult(){
        return this.result;  
    }
	
	public void setTimeLong(Long timeLong){  
        this.timeLong = timeLong;  
    }  
    
	@FieldDisplay("本次小休时长")
    public Long getTimeLong(){
        return this.timeLong;  
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
	
	public void setDepartmentCode(String departmentCode){  
        this.departmentCode = departmentCode;  
    }  
    
	@FieldDisplay("组织机构编码")
    public String getDepartmentCode(){
        return this.departmentCode;  
    }
	
	public void setDepartmentName(String departmentName){  
        this.departmentName = departmentName;  
    }  
    
	@FieldDisplay("组织机构名称")
    public String getDepartmentName(){
        return this.departmentName;  
    }

}