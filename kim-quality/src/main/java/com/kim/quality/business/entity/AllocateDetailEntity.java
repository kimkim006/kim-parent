package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 任务分配明细表实体类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class AllocateDetailEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String actBatchNo;//分配批次号
	private String batchNo;//抽检批次号
	private String mainTaskId;//任务id
	private String inspector;//质检员
	
	public AllocateDetailEntity id(String id) {
		setId(id);
		return this;
	}

	public AllocateDetailEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setActBatchNo(String actBatchNo){  
        this.actBatchNo = actBatchNo;  
    }  
    
	@FieldDisplay("分配批次号")
    public String getActBatchNo(){
        return this.actBatchNo;  
    }
	
	public void setBatchNo(String batchNo){  
        this.batchNo = batchNo;  
    }  
    
	@FieldDisplay("抽检批次号")
    public String getBatchNo(){
        return this.batchNo;  
    }
	
	public void setMainTaskId(String mainTaskId){  
        this.mainTaskId = mainTaskId;  
    }  
    
	@FieldDisplay("任务id")
    public String getMainTaskId(){
        return this.mainTaskId;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
    
	@FieldDisplay("质检员")
    public String getInspector(){
        return this.inspector;  
    }
	
}