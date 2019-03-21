package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 任务处理记录表参数封装类
 * @date 2018-12-12 14:44:33
 * @author liubo
 */
public class AdjustScoreVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String taskId;  //任务id
	private String agentId;  //坐席工号
	private String inspector;  //质检员
	private String submitter;  //提交人
	private String submitTime;  //提交时间
	private Double score;  //得分
	private String content;  //处理说明
	private Integer isLast;  //是否最后一次, 1是, 0否

	public AdjustScoreVo id(String id) {
		setId(id);
		return this;
	}

	public AdjustScoreVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
      
    public String getTaskId(){  
        return this.taskId;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
      
    public String getInspector(){  
        return this.inspector;  
    }
	
	public void setSubmitter(String submitter){  
        this.submitter = submitter;  
    }  
      
    public String getSubmitter(){  
        return this.submitter;  
    }
	
	public void setSubmitTime(String submitTime){  
        this.submitTime = submitTime;  
    }  
      
    public String getSubmitTime(){  
        return this.submitTime;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
      
    public Double getScore(){  
        return this.score;  
    }
	
	public void setContent(String content){  
        this.content = content;  
    }  
      
    public String getContent(){  
        return this.content;  
    }
	
	public void setIsLast(Integer isLast){  
        this.isLast = isLast;  
    }  
      
    public Integer getIsLast(){  
        return this.isLast;  
    }
	
}