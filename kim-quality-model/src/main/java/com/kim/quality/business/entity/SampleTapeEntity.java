package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;

/**
 * 抽检录音表实体类
 * @date 2018-9-28 9:22:10
 * @author bo.liu01
 */
public class SampleTapeEntity extends TapeEntity{
	
	private static final long serialVersionUID = 1L; 
	
	//抽检任务中需要此字段
	private String resolver;//解析器标识
	
	private String tapeId;//录音id
	private String batchNo;//抽检批次号
	private String mainTaskId;//任务id
	
	public SampleTapeEntity id(String id) {
		setId(id);
		return this;
	}

	public SampleTapeEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setTapeId(String tapeId){  
        this.tapeId = tapeId;  
    }  
    
	@FieldDisplay("录音id")
    public String getTapeId(){
        return this.tapeId;  
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
	
	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}
	
}