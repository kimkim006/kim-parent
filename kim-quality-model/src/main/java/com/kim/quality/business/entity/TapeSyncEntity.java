package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音转储记录表实体类
 * @date 2018-8-21 14:48:54
 * @author bo.liu01
 */
public class TapeSyncEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 状态,  0成功 */
	public static final int STATUS_SUCCESS = 0;
	/** 状态,  1正在同步 */
	public static final int STATUS_GOING = 1;
	/** 状态,  2失败 */
	public static final int STATUS_FAIL = 2;
	/** 状态,  3异常中断 */
	public static final int STATUS_INTERRUPT = 3;
	
	private String timeDate;//日期
	private Integer type;//呼叫类型
	private String startTime;//开始时间
	private String endTime;//结束时间
	private Integer totalCount;//数据量
	private Integer status;//状态,  0成功, 1正在同步, 2失败, 3异常中断
	
	public TapeSyncEntity id(String id) {
		setId(id);
		return this;
	}

	public TapeSyncEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setTimeDate(String timeDate){  
        this.timeDate = timeDate;  
    }  
    
	@FieldDisplay("日期")
    public String getTimeDate(){
        return this.timeDate;  
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
	
	public void setTotalCount(Integer totalCount){  
        this.totalCount = totalCount;  
    }  
    
	@FieldDisplay("数据量")
    public Integer getTotalCount(){
        return this.totalCount;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("状态,  0成功, 1正在同步, 2未获取到数据, 3异常中断")
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