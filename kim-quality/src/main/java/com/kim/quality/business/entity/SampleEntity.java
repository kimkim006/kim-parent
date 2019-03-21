package com.kim.quality.business.entity;

import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 抽检批次记录表实体类
 * @date 2018-8-30 11:15:47
 * @author bo.liu01
 */
public class SampleEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 状态, 0正在抽取  */
	public static final int STATUS_DOING = 0;
	/** 状态, 1抽取成功  */
	public static final int STATUS_SUCCESS = 1;
	/** 状态, 2抽检失败  */
	public static final int STATUS_FAIL = 2;
	/** 状态, 3已分配  */
	public static final int STATUS_ALLOCATED = 3;
	
	private String batchNo;//抽检批次号,
	private String modeCode;//质检模式, tape录音质检, text文本, mixed混合
	private String businessCode;//业务线
	private String ruleId;//抽检规则id
	private String ruleName;//抽检规则id
	private String sampleType;//规则类型,  system系统抽检, manual人工抽检
	private String extractDate;//抽取数据时间
	private Integer extractNum;//抽取数量
	private Integer assignableNum;//可分配量
	private Integer deleteNum;//已删除量
	private Integer status;//状态, 0正在抽取, 1抽取成功, 2抽检失败, 3已分配
	
	private String extractStartDate;//抽取数据时间
	private String extractEndDate;//抽取数据时间
	private Integer allcateNum;//已分配量
	private Integer recycleNum;//回收配量
	
	public SampleEntity id(String id) {
		setId(id);
		return this;
	}

	public SampleEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setBatchNo(String batchNo){  
        this.batchNo = batchNo;  
    }  
    
	@FieldDisplay("抽检批次号,")
    public String getBatchNo(){
        return this.batchNo;  
    }
	
	public void setModeCode(String modeCode){  
        this.modeCode = modeCode;  
    }  
    
	@FieldDisplay("质检模式, tape录音质检, text文本, mixed混合")
    public String getModeCode(){
        return this.modeCode;  
    }
	
	public void setBusinessCode(String businessCode){  
        this.businessCode = businessCode;  
    }  
    
	@FieldDisplay("业务线")
    public String getBusinessCode(){
        return this.businessCode;  
    }
	
	public void setRuleId(String ruleId){  
        this.ruleId = ruleId;  
    }  
    
	@FieldDisplay("抽检规则id")
    public String getRuleId(){
        return this.ruleId;  
    }
	
	public void setSampleType(String sampleType){  
        this.sampleType = sampleType;  
    }  
    
	@FieldDisplay("规则类型,  system系统抽检, manual人工抽检")
    public String getSampleType(){
        return this.sampleType;  
    }
	
	public void setExtractDate(String extractDate){  
        this.extractDate = extractDate;  
    }  
    
	@FieldDisplay("抽取数据时间")
    public String getExtractDate(){
        return this.extractDate;  
    }
	
	public void setExtractNum(Integer etractNum){  
        this.extractNum = etractNum;  
    }  
    
	@FieldDisplay("抽取数量")
    public Integer getExtractNum(){
        return this.extractNum;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("状态, 0正在抽取, 1抽取成功, 2抽检失败, 3已分配")
    public Integer getStatus(){
        return this.status;  
    }

	public void addRemark(String message) {
		if(StringUtil.isBlank(remark)){
			remark = message;
		}else{
			remark += ";" + message;
		}
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getAssignableNum() {
		return assignableNum;
	}

	public void setAssignableNum(Integer assignableNum) {
		this.assignableNum = assignableNum;
	}

	public Integer getAllcateNum() {
		return allcateNum;
	}

	public void setAllcateNum(Integer allcateNum) {
		this.allcateNum = allcateNum;
	}

	public Integer getRecycleNum() {
		return recycleNum;
	}

	public void setRecycleNum(Integer recycleNum) {
		this.recycleNum = recycleNum;
	}

	public String getExtractStartDate() {
		return extractStartDate;
	}

	public void setExtractStartDate(String extractStartDate) {
		this.extractStartDate = extractStartDate;
	}

	public String getExtractEndDate() {
		return extractEndDate;
	}

	public void setExtractEndDate(String extractEndDate) {
		this.extractEndDate = extractEndDate;
	}

	public Integer getDeleteNum() {
		return deleteNum;
	}

	public void setDeleteNum(Integer deleteNum) {
		this.deleteNum = deleteNum;
	}

}