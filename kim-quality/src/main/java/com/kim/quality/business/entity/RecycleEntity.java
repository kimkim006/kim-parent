package com.kim.quality.business.entity;

import java.util.Date;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 任务回收记录表实体类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class RecycleEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String rcyBatchNo;//回收批次号
	private String detail;//分配详情
	private Integer userCount;//人员数量
	private Integer totalCount;//分配总数量
	
	public RecycleEntity id(String id) {
		setId(id);
		return this;
	}

	public RecycleEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void createRcyBatchNo(){
		if(StringUtil.isBlank(rcyBatchNo)){
			rcyBatchNo = "rcy" + DateUtil.formatDate(new Date(), DateUtil.PATTERN_YYYYMMDDHHMMSS);
		}
	}
	
	public void setRcyBatchNo(String rcyBatchNo){  
        this.rcyBatchNo = rcyBatchNo;  
    }  
    
	@FieldDisplay("回收批次号")
    public String getRcyBatchNo(){
        return this.rcyBatchNo;  
    }
	
	public void setDetail(String detail){  
        this.detail = detail;  
    }  
    
	@FieldDisplay("分配详情")
    public String getDetail(){
        return this.detail;  
    }
	
	public void setUserCount(Integer userCount){  
        this.userCount = userCount;  
    }  
    
	@FieldDisplay("人员数量")
    public Integer getUserCount(){
        return this.userCount;  
    }
	
	public void setTotalCount(Integer totalCount){  
        this.totalCount = totalCount;  
    }  
    
	@FieldDisplay("分配总数量")
    public Integer getTotalCount(){
        return this.totalCount;  
    }
	
}