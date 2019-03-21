package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 坐席小休记录表参数封装类
 * @date 2019-3-13 11:22:51
 * @author liubo
 */
public class AgentRestVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String restDate;  //小休日期
	private String restDateStart;  //查询小休日期开始时间
	private String restDateEnd;  //查询小休日期结束时间
	private String bqTime;  //开始排队时间
	private String bqTimeStart;  //查询开始排队时间开始时间
	private String bqTimeEnd;  //查询开始排队时间结束时间
	private String startTime;  //小休开始时间
	private String startTimeStart;  //查询小休开始时间开始时间
	private String startTimeEnd;  //查询小休开始时间结束时间
	private String endTime;  //小休结束时间
	private String endTimeStart;  //查询小休结束时间开始时间
	private String endTimeEnd;  //查询小休结束时间结束时间
	private Integer result;  //小休结果1.成功 0.失败
	private Long timeLong;  //本次小休时长
	private String name;  //用户姓名
	private String username;  //用户工号
	private String agentId;  //话务工号
	private String departmentCode;  //组织机构编码
	private String departmentName;  //组织机构名称

	public AgentRestVo id(String id) {
		setId(id);
		return this;
	}

	public AgentRestVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRestDate(String restDate){  
        this.restDate = restDate;  
    }  
      
    public String getRestDate(){  
        return this.restDate;  
    }
    
	public void setRestDateStart(String restDateStart){  
        this.restDateStart = restDateStart;  
    }
	
	public String getRestDateStart(){  
        return this.restDateStart;  
    }
	
	public void setRestDateEnd(String restDateEnd){  
        this.restDateEnd = restDateEnd;  
    }
	
	public String getRestDateEnd(){  
        return this.restDateEnd;  
    }
	
	public void setBqTime(String bqTime){  
        this.bqTime = bqTime;  
    }  
      
    public String getBqTime(){  
        return this.bqTime;  
    }
    
	public void setBqTimeStart(String bqTimeStart){  
        this.bqTimeStart = bqTimeStart;  
    }
	
	public String getBqTimeStart(){  
        return this.bqTimeStart;  
    }
	
	public void setBqTimeEnd(String bqTimeEnd){  
        this.bqTimeEnd = bqTimeEnd;  
    }
	
	public String getBqTimeEnd(){  
        return this.bqTimeEnd;  
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
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
      
    public String getEndTime(){  
        return this.endTime;  
    }
    
	public void setEndTimeStart(String endTimeStart){  
        this.endTimeStart = endTimeStart;  
    }
	
	public String getEndTimeStart(){  
        return this.endTimeStart;  
    }
	
	public void setEndTimeEnd(String endTimeEnd){  
        this.endTimeEnd = endTimeEnd;  
    }
	
	public String getEndTimeEnd(){  
        return this.endTimeEnd;  
    }
	
	public void setResult(Integer result){  
        this.result = result;  
    }  
      
    public Integer getResult(){  
        return this.result;  
    }
    
	
	public void setTimeLong(Long timeLong){  
        this.timeLong = timeLong;  
    }  
      
    public Long getTimeLong(){  
        return this.timeLong;  
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
    
	
	public void setDepartmentCode(String departmentCode){  
        this.departmentCode = departmentCode;  
    }  
      
    public String getDepartmentCode(){  
        return this.departmentCode;  
    }
    
	
	public void setDepartmentName(String departmentName){  
        this.departmentName = departmentName;  
    }  
      
    public String getDepartmentName(){  
        return this.departmentName;  
    }
	
}