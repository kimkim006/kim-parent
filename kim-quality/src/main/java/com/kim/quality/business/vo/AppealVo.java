package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 申诉记录表参数封装类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
public class AppealVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String taskId;  //任务id
	private String inspector;  //质检员
	private String appealer;  //申诉人
	private String content;  //申诉原因
	private Integer status;  //状态, 0待审批, 1处理中, 2通过, 3驳回
	private Integer isLast;//是否最近一次, 1是, 0否

	public AppealVo id(String id) {
		setId(id);
		return this;
	}

	public AppealVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
      
    public String getTaskId(){  
        return this.taskId;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
      
    public String getInspector(){  
        return this.inspector;  
    }
	
	public void setAppealer(String appealer){  
        this.appealer = appealer;  
    }  
      
    public String getAppealer(){  
        return this.appealer;  
    }
	
	public void setContent(String content){  
        this.content = content;  
    }  
      
    public String getContent(){  
        return this.content;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }

	public Integer getIsLast() {
		return isLast;
	}

	public void setIsLast(Integer isLast) {
		this.isLast = isLast;
	}
	
}