package com.kim.quality.business.entity;

import java.util.Date;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 质检任务分配记录表实体类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class AllocateEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String actBatchNo;//分配批次号
	private String detail;//分配详情
	private Integer userCount;//人员数量
	private Integer totalCount;//分配总数量
	
	public void createActBatchNo() {
		if(StringUtil.isBlank(actBatchNo)){
			actBatchNo = "act" + DateUtil.formatDate(new Date(), DateUtil.PATTERN_YYYYMMDDHHMMSS);
		}
	}
	
	public AllocateEntity id(String id) {
		setId(id);
		return this;
	}

	public AllocateEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setActBatchNo(String actBatchNo){  
        this.actBatchNo = actBatchNo;  
    }  
    
	@FieldDisplay("分配批次号")
    public String getActBatchNo(){
        return this.actBatchNo;  
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