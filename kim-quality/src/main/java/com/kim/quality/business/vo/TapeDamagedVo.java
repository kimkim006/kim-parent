package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 录音损坏明细表参数封装类
 * @date 2018-9-26 15:01:14
 * @author yonghui.wu
 */
public class TapeDamagedVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String taskId;  //任务id
	private Long evaluationId;  //评分id
	private String tapeId;  //录音id
	private String agentId;  //坐席工号
	private String inspector;  //质检员
	private Integer status;  //状态, 1有效, 2已废弃

	public TapeDamagedVo id(String id) {
		setId(id);
		return this;
	}

	public TapeDamagedVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
      
    public String getTaskId(){  
        return this.taskId;  
    }
	
	public void setEvaluationId(Long evaluationId){  
        this.evaluationId = evaluationId;  
    }  
      
    public Long getEvaluationId(){  
        return this.evaluationId;  
    }
	
	public void setTapeId(String tapeId){  
        this.tapeId = tapeId;  
    }  
      
    public String getTapeId(){  
        return this.tapeId;  
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
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }
	
}