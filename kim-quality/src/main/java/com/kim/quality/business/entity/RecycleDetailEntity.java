package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.business.enums.MainTaskStatusEnum;

/**
 * 任务回收明细表实体类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class RecycleDetailEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String rcyBatchNo;//回收批次号
	private String batchNo;//抽检批次号
	private String mainTaskId;//任务id
	private String inspector;//质检员
	private Integer status;//回收前状态, 同任务状态
//	private String statusDesc;
	public RecycleDetailEntity id(String id) {
		setId(id);
		return this;
	}

	public RecycleDetailEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setRcyBatchNo(String rcyBatchNo){  
        this.rcyBatchNo = rcyBatchNo;  
    }  
    
	@FieldDisplay("回收批次号")
    public String getRcyBatchNo(){
        return this.rcyBatchNo;  
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
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("回收前状态, 同任务状态")
    public Integer getStatus(){
        return this.status;
    }
	
	public String getStatusDesc() {
		return MainTaskStatusEnum.getValueByKey(this.status);
	}

}