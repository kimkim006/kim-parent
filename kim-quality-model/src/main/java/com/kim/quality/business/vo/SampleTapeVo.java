package com.kim.quality.business.vo;

/**
 * 抽检录音表参数封装类
 * @date 2018-9-28 9:22:10
 * @author bo.liu01
 */
public class SampleTapeVo extends TapeVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String batchNo;  //抽检批次号
	private String tapeId;  //录音id
	private String busiGroupCode;  //业务小组
	
	public SampleTapeVo id(String id) {
		setId(id);
		return this;
	}

	public SampleTapeVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTapeId(String tapeId){  
        this.tapeId = tapeId;  
    }  
      
    public String getTapeId(){  
        return this.tapeId;  
    }

	public String getBusiGroupCode() {
		return busiGroupCode;
	}

	public void setBusiGroupCode(String busiGroupCode) {
		this.busiGroupCode = busiGroupCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
}