package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 质检评分明细表参数封装类
 * @date 2018-9-25 14:28:11
 * @author yonghui.wu
 */
public class EvaluationDetailVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String taskId;  //任务id
	private String evaluationId;  //评分id
	private String agentId;  //坐席工号
	private String inspector;  //质检员
	private Integer evalItemId;  //id
	private String evalItemName;  //
	private Integer evalType;  //评分类型, 1加分, 2减分
	private Double score;  //分值

	public EvaluationDetailVo id(String id) {
		setId(id);
		return this;
	}

	public EvaluationDetailVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
      
    public String getTaskId(){  
        return this.taskId;  
    }
	
	public void setEvaluationId(String evaluationId){  
        this.evaluationId = evaluationId;  
    }  
      
    public String getEvaluationId(){  
        return this.evaluationId;  
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
	
	public void setEvalItemId(Integer evalItemId){  
        this.evalItemId = evalItemId;  
    }  
      
    public Integer getEvalItemId(){  
        return this.evalItemId;  
    }
	
	public void setEvalItemName(String evalItemName){  
        this.evalItemName = evalItemName;  
    }  
      
    public String getEvalItemName(){  
        return this.evalItemName;  
    }
	
	public void setEvalType(Integer evalType){  
        this.evalType = evalType;  
    }  
      
    public Integer getEvalType(){  
        return this.evalType;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
      
    public Double getScore(){  
        return this.score;  
    }
	
}