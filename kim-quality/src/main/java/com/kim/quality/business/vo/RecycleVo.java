package com.kim.quality.business.vo;

import java.util.List;

import com.kim.common.base.BaseVo;

/**
 * 任务回收记录表参数封装类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
public class RecycleVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private List<String> batchNos;  //抽检批次号
	private String batchNo;  //抽检批次号
	private String rcyBatchNo;  //回收批次号
	private String detail;  //分配详情
	private Integer userCount;  //人员数量
	private Integer totalCount;  //分配总数量
	
	private Integer taskNum;  //实际回收总数量
	private Integer available;  //可回收总数量
	private List<Object> users;
	
	/**查询条件**/
	private String operTimeStart;  //操作开始时间
	private String operTimeEnd;  //操作结束时间

	public RecycleVo id(String id) {
		setId(id);
		return this;
	}

	public RecycleVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRcyBatchNo(String rcyBatchNo){  
        this.rcyBatchNo = rcyBatchNo;  
    }  
      
    public String getRcyBatchNo(){  
        return this.rcyBatchNo;  
    }
	
	public void setDetail(String detail){  
        this.detail = detail;  
    }  
      
    public String getDetail(){  
        return this.detail;  
    }
	
	public void setUserCount(Integer userCount){  
        this.userCount = userCount;  
    }  
      
    public Integer getUserCount(){  
        return this.userCount;  
    }
	
	public void setTotalCount(Integer totalCount){  
        this.totalCount = totalCount;  
    }  
      
    public Integer getTotalCount(){  
        return this.totalCount;  
    }

	public String getOperTimeStart() {
		return operTimeStart;
	}

	public void setOperTimeStart(String operTimeStart) {
		this.operTimeStart = operTimeStart;
	}

	public String getOperTimeEnd() {
		return operTimeEnd;
	}

	public void setOperTimeEnd(String operTimeEnd) {
		this.operTimeEnd = operTimeEnd;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<String> getBatchNos() {
		return batchNos;
	}

	public void setBatchNos(List<String> batchNos) {
		this.batchNos = batchNos;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public List<Object> getUsers() {
		return users;
	}

	public void setUsers(List<Object> users) {
		this.users = users;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}
}