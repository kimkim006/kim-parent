package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 小休配置明细表实体类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class RestSettingDetailEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 小休配置主记录 */
	private String restId;
	/** 开始时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 允许人数 */
	private Integer limitNum;
	/** 是否可用 1是 0否 */
	private Integer isActive;
	
	public RestSettingDetailEntity id(String id) {
		setId(id);
		return this;
	}

	public RestSettingDetailEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setRestId(String restId){  
        this.restId = restId;  
    }  
    
	@FieldDisplay("小休配置主记录")
    public String getRestId(){
        return this.restId;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
    
	@FieldDisplay("开始时间")
    public String getStartTime(){
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
    
	@FieldDisplay("结束时间")
    public String getEndTime(){
        return this.endTime;  
    }
	
	public void setLimitNum(Integer limitNum){  
        this.limitNum = limitNum;  
    }  
    
	@FieldDisplay("允许人数")
    public Integer getLimitNum(){
        return this.limitNum;  
    }
	
	public void setIsActive(Integer isActive){  
        this.isActive = isActive;  
    }  
    
	@FieldDisplay("是否可用 1是 0否")
    public Integer getIsActive(){
        return this.isActive;  
    }
	
}