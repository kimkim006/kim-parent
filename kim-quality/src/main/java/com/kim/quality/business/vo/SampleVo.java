package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 抽检批次记录表参数封装类
 * @date 2018-8-30 11:15:47
 * @author bo.liu01
 */
public class SampleVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String batchNo;  //抽检批次号,
	private List<String> batchNos;  //抽检批次号,
	private String modeCode;  //质检模式, tape录音质检, text文本, mixed混合
	private String businessCode;  //业务线
	private String ruleId;  //抽检规则id
	private String sampleType;  //规则类型,  system系统抽检, manual人工抽检
	private String extractDate;  //抽取数据时间
	private String extractDateStart;  //抽取数据时间
	private String extractDateEnd;  //抽取数据时间
	private Integer extractNum;  //抽取数量
	private Integer status;  //状态, 0正在抽取, 1抽取成功, 2抽检失败, 3已分配
	
	private List<Integer> statusArr;  //状态, 0正在抽取, 1抽取成功, 2抽检失败, 3已分配
	
	private String taskId;

	public SampleVo id(String id) {
		setId(id);
		return this;
	}

	public SampleVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setBatchNo(String batchNo){  
        this.batchNo = batchNo;  
    }  
      
    public String getBatchNo(){  
        return this.batchNo;  
    }
	
	public void setModeCode(String modeCode){  
        this.modeCode = modeCode;  
    }  
      
    public String getModeCode(){  
        return this.modeCode;  
    }
	
	public void setBusinessCode(String businessCode){  
        this.businessCode = businessCode;  
    }  
      
    public String getBusinessCode(){  
        return this.businessCode;  
    }
	
	public void setRuleId(String ruleId){  
        this.ruleId = ruleId;  
    }  
      
    public String getRuleId(){  
        return this.ruleId;  
    }
	
	public void setSampleType(String sampleType){  
        this.sampleType = sampleType;  
    }  
      
    public String getSampleType(){  
        return this.sampleType;  
    }
	
	public void setExtractDate(String extractDate){  
        this.extractDate = extractDate;  
    }  
      
    public String getExtractDate(){  
        return this.extractDate;  
    }
	
	public void setExtractNum(Integer extractNum){  
        this.extractNum = extractNum;  
    }  
      
    public Integer getExtractNum(){  
        return this.extractNum;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }

	public String getExtractDateEnd() {
		return extractDateEnd;
	}

	public void setExtractDateEnd(String extractDateEnd) {
		this.extractDateEnd = extractDateEnd;
	}

	public String getExtractDateStart() {
		return extractDateStart;
	}

	public void setExtractDateStart(String extractDateStart) {
		this.extractDateStart = extractDateStart;
	}

	public List<String> getBatchNos() {
		return batchNos;
	}

	public void setBatchNos(List<String> batchNos) {
		this.batchNos = batchNos;
	}

	public List<Integer> getStatusArr() {
		return statusArr;
	}

	public void setStatusArr(List<Integer> statusArr) {
		this.statusArr = statusArr;
	}
	
	public void addStatusArr(Integer ... statusArr) {
		if(this.statusArr == null){
			this.statusArr = new ArrayList<>();
		}
		this.statusArr.addAll(Arrays.asList(statusArr));
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}