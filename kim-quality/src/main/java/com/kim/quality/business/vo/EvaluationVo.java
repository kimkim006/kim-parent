package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 质检评分表参数封装类
 * @date 2018-9-17 15:43:36
 * @author jianming.chen
 */
public class EvaluationVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String taskId;  //任务id
	private String agentId;  //坐席工号
	private String inspector;  //质检员
	private Integer damaged;  //录音是否损坏, 0否, 1是
	private Double origScore;  //初始分数
	private Double score;  //得分, 根据计算得分，若计算得分超过120，则为120，若计算得分小于0则为0
	private Double calScore;  //计算得分
	private Integer status;  //状态, 0待审核, 1审核通过, 2审核驳回

	public EvaluationVo id(String id) {
		setId(id);
		return this;
	}

	public EvaluationVo tenantId(String tenantId) {
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
	
	public void setDamaged(Integer damaged){  
        this.damaged = damaged;  
    }  
      
    public Integer getDamaged(){  
        return this.damaged;  
    }
	
	public void setOrigScore(Double origScore){  
        this.origScore = origScore;  
    }  
      
    public Double getOrigScore(){  
        return this.origScore;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
      
    public Double getScore(){  
        return this.score;  
    }
	
	public void setCalScore(Double calScore){  
        this.calScore = calScore;  
    }  
      
    public Double getCalScore(){  
        return this.calScore;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }

}