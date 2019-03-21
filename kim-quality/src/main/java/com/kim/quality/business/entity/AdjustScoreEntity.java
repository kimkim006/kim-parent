package com.kim.quality.business.entity;

import java.util.Date;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.business.enums.MainTask;

/**
 * 任务处理记录表实体类
 * @date 2018-12-12 14:44:33
 * @author liubo
 */
public class AdjustScoreEntity extends LoggedEntity implements RelateTaskItem{
	
	private static final long serialVersionUID = 1L; 
	
	private String taskId;//任务id
	private String agentId;//坐席工号
	private String inspector;//质检员
	private String submitter;//提交人
	private String submitTime;//提交时间
	private Double adScore;//调整分值
	private Double calScore;//计算得分
	private Double score;//得分
	private String content;//处理说明
	private Integer isLast;//是否最后一次, 1是, 0否
	
	@Override
	public String getKeyType(){
		return MainTask.KEY_TYPE_ADJUST_SCORE;
	}
	
	@Override
	public Long getSortTime() {
		if(StringUtil.isBlank(getSubmitTime())){
			return null;
		}
		Date date = DateUtil.parseDate(getSubmitTime());
		if(date == null){
			return null;
		}
		return date.getTime();
	}
	
	public AdjustScoreEntity id(String id) {
		setId(id);
		return this;
	}

	public AdjustScoreEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
    
	@FieldDisplay("任务id")
    public String getTaskId(){
        return this.taskId;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("坐席工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
    
	@FieldDisplay("质检员")
    public String getInspector(){
        return this.inspector;  
    }
	
	public void setSubmitter(String submitter){  
        this.submitter = submitter;  
    }  
    
	@FieldDisplay("提交人")
    public String getSubmitter(){
        return this.submitter;  
    }
	
	public void setSubmitTime(String submitTime){  
        this.submitTime = submitTime;  
    }  
    
	@FieldDisplay("提交时间")
    public String getSubmitTime(){
        return this.submitTime;  
    }
	
	public void setAdScore(Double adScore){  
        this.adScore = adScore;  
    }  
    
	@FieldDisplay("调整分值")
    public Double getAdScore(){
        return this.adScore;  
    }
	
	public void setCalScore(Double calScore){  
        this.calScore = calScore;  
    }  
    
	@FieldDisplay("计算得分")
    public Double getCalScore(){
        return this.calScore;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
    
	@FieldDisplay("得分")
    public Double getScore(){
        return this.score;  
    }
	
	public void setContent(String content){  
        this.content = content;  
    }  
    
	@FieldDisplay("处理说明")
    public String getContent(){
        return this.content;  
    }
	
	public void setIsLast(Integer isLast){  
        this.isLast = isLast;  
    }  
    
	@FieldDisplay("是否最后一次, 1是, 0否")
    public Integer getIsLast(){
        return this.isLast;  
    }
	
}