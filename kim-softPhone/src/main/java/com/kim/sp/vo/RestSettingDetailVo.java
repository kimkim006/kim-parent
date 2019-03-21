package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 小休配置明细表参数封装类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class RestSettingDetailVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String restId;  //小休配置主记录
	private String startTime;  //开始时间
	private String endTime;  //结束时间
	private Integer limitNum;  //允许人数
	private Integer isActive;  //是否可用 1是 0否

	public RestSettingDetailVo id(String id) {
		setId(id);
		return this;
	}

	public RestSettingDetailVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRestId(String restId){  
        this.restId = restId;  
    }  
      
    public String getRestId(){  
        return this.restId;  
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
    
	
	public void setLimitNum(Integer limitNum){  
        this.limitNum = limitNum;  
    }  
      
    public Integer getLimitNum(){  
        return this.limitNum;  
    }
    
	
	public void setIsActive(Integer isActive){  
        this.isActive = isActive;  
    }  
      
    public Integer getIsActive(){  
        return this.isActive;  
    }
    
	
}