package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音损坏明细表实体类
 * @date 2018-9-26 15:01:14
 * @author yonghui.wu
 */
public class TapeDamagedEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/**录音状态, 1有效 **/
	public final static int STATUS_VALID=1;
	/**录音状态, 2已废弃**/
	public final static int STATUS_INVALID=2;
	
	private String taskId;//任务id
	private String evaluationId;//评分id
	private String tapeId;//录音id
	private String agentId;//坐席工号
	private String inspector;//质检员
	private Integer status;//状态, 1有效, 2已废弃
	

	public TapeDamagedEntity id(String id) {
		setId(id);
		return this;
	}

	public TapeDamagedEntity tenantId(String tenantId) {
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
	
	public void setEvaluationId(String evaluationId){  
        this.evaluationId = evaluationId;  
    }  
    
	@FieldDisplay("评分id")
    public String getEvaluationId(){
        return this.evaluationId;  
    }
	
	public void setTapeId(String tapeId){  
        this.tapeId = tapeId;  
    }  
    
	@FieldDisplay("录音id")
    public String getTapeId(){
        return this.tapeId;  
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
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("状态, 1有效, 2已废弃")
    public Integer getStatus(){
        return this.status;  
    }
	
}