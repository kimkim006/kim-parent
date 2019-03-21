package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 录音转储记录表参数封装类
 * @date 2018-8-21 14:48:55
 * @author bo.liu01
 */
public class TapeSyncVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String timeDate;  //日期
	private Integer type;  //呼叫类型
	private String startTime;  //开始时间
	private String endTime;  //结束时间
	private Integer totalCount;  //数据量
	private Integer status;  //状态,  0成功, 1正在同步, 2未获取到数据, 3异常中断

	public TapeSyncVo id(String id) {
		setId(id);
		return this;
	}

	public TapeSyncVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTimeDate(String timeDate){  
        this.timeDate = timeDate;  
    }  
      
    public String getTimeDate(){  
        return this.timeDate;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
      
    public String getStartTime(){  
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
      
    public String getEndTime(){  
        return this.endTime;  
    }
	
	public void setTotalCount(Integer totalCount){  
        this.totalCount = totalCount;  
    }  
      
    public Integer getTotalCount(){  
        return this.totalCount;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}