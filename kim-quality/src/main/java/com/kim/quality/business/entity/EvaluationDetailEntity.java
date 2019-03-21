package com.kim.quality.business.entity;

import com.kim.common.util.NumberUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.setting.entity.EvaluationSettingEntity;

/**
 * 质检评分明细表实体类
 * @date 2018-9-25 14:28:11
 * @author yonghui.wu
 */
public class EvaluationDetailEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String taskId;//任务id
	private String evaluationId;//评分id
	private String agentId;//坐席工号
	private String inspector;//质检员
	private String evalItemId;//id
	private String evalItemName;//
	private Integer evalType;//评分类型, 1加分, 2减分
	private Double score;//分值
	
	public EvaluationDetailEntity id(String id) {
		setId(id);
		return this;
	}

	public EvaluationDetailEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public String getScoreStr(){
		if(getScore() != null){
			if(getEvalType() == null){
				return String.valueOf(getScore());
			}else if(EvaluationSettingEntity.EVAL_TYPE_ADD == getEvalType()){
				return "+" + NumberUtil.format(getScore());
			}else{
				return "-" + NumberUtil.format(getScore());
			}
		}
		return null;
	}
	
	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
    
	@FieldDisplay("任务id")
    public String getTaskId(){
        return this.taskId;  
    }
	
	public void setEvaluationId(String evaluationId){  
        this.evaluationId = evaluationId;  
    }  
    
	@FieldDisplay("评分id")
    public String getEvaluationId(){
        return this.evaluationId;  
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
	
	public void setEvalItemId(String evalItemId){  
        this.evalItemId = evalItemId;  
    }  
    
	@FieldDisplay("id")
    public String getEvalItemId(){
        return this.evalItemId;  
    }
	
	public void setEvalItemName(String evalItemName){  
        this.evalItemName = evalItemName;  
    }  
    
	@FieldDisplay("")
    public String getEvalItemName(){
        return this.evalItemName;  
    }
	
	public void setEvalType(Integer evalType){  
        this.evalType = evalType;  
    }  
    
	@FieldDisplay("评分类型, 1加分, 2减分")
    public Integer getEvalType(){
        return this.evalType;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
    
	@FieldDisplay("分值")
    public Double getScore(){
        return this.score;  
    }
	
}