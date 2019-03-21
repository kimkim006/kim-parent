package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 任务回收明细表参数封装类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class RecycleDetailVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String rcyBatchNo;  //回收批次号
	private String batchNo;  //抽检批次号
	private String mainTaskId;  //任务id
	private String inspector;  //质检员
	private Integer status;  //回收前状态, 同任务状态

	public RecycleDetailVo id(String id) {
		setId(id);
		return this;
	}

	public RecycleDetailVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRcyBatchNo(String rcyBatchNo){  
        this.rcyBatchNo = rcyBatchNo;  
    }  
      
    public String getRcyBatchNo(){  
        return this.rcyBatchNo;  
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
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }
	
}