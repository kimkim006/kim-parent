package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 任务分配明细表参数封装类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class AllocateDetailVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String actBatchNo;  //分配批次号
	private String batchNo;  //抽检批次号
	private String mainTaskId;  //任务id
	private String inspector;  //质检员

	public AllocateDetailVo id(String id) {
		setId(id);
		return this;
	}

	public AllocateDetailVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setActBatchNo(String actBatchNo){  
        this.actBatchNo = actBatchNo;  
    }  
      
    public String getActBatchNo(){  
        return this.actBatchNo;  
    }
	
	public void setBatchNo(String batchNo){  
        this.batchNo = batchNo;  
    }  
      
    public String getBatchNo(){  
        return this.batchNo;  
    }
	
	public void setMainTaskId(String mainTaskId){  
        this.mainTaskId = mainTaskId;  
    }  
      
    public String getMainTaskId(){  
        return this.mainTaskId;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
      
    public String getInspector(){  
        return this.inspector;  
    }
	
}