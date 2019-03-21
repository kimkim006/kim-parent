package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;
import com.kim.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 质检任务表参数封装类
 * @date 2018-8-30 11:15:47
 * @author bo.liu01
 */
public class MainTaskVo extends BaseVo{ 

	private static final long serialVersionUID = 1L;
	
	private String startTime;  //抽检批次号
	private String endTime;  //抽检批次号
	private String batchNo;  //抽检批次号
	private List<String> batchNos;  //抽检批次号,
	private String modeCode;  //质检模式, tape录音质检, text文本, mixed混合
	private String businessCode;  //业务线
	private Integer ruleId;  //抽检规则id
    private String resolver;  //解析器标识
	private String sampleType;  //规则类型,  system系统抽检, manual人工抽检
	private Integer itemType;  //抽检数据类型,  1录音, 2文本
	private String busiGroupCode;  //业务小组
	private String agentId;  //坐席工号
	private String qualityGroupCode;  //质检小组
	private String inspector;  //质检员
	private String curProcesser;  //当前处理人
	private String lastProcesser;  //上次处理人
    private Integer status;  //状态, 0待分配, 1已回收, 2待质检, 3待审批, 4反馈结果, 5审批驳回, 6申诉待审批, 7申诉待处理, 8申诉通过, 9申诉驳回, 10坐席已确认
    private String allocateTime;//分配时间
    private Double score;//得分
    
    private String custPhone;//客户手机号
    private String operTimeStart;//操作时间开始
    private String operTimeEnd;//操作时间结束
    
    private String auditor;//审批人
    private String approvalStatus;//审批意见
    private String mistake;//是否质检差错（0：否，1：是）
    private Integer damaged;//是否录音损坏（0：否，1：是）
    private Integer approvalType;  //审核记录类型
	private List<Integer> statusArr;  //状态
    private List<String> ids;//主键IDs
    private String dataType;//抽检数据详情类型

	public MainTaskVo id(String id) {
		setId(id);
		return this;
	}

	public MainTaskVo tenantId(String tenantId) {
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
	
	public void setRuleId(Integer ruleId){  
        this.ruleId = ruleId;  
    }  
      
    public Integer getRuleId(){  
        return this.ruleId;  
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public void setSampleType(String sampleType){
        this.sampleType = sampleType;  
    }  
      
    public String getSampleType(){  
        return this.sampleType;  
    }
	
	public void setItemType(Integer itemType){  
        this.itemType = itemType;  
    }  
      
    public Integer getItemType(){  
        return this.itemType;  
    }
	
	public void setBusiGroupCode(String busiGroupCode){  
        this.busiGroupCode = busiGroupCode;  
    }  
      
    public String getBusiGroupCode(){  
        return this.busiGroupCode;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
	
	public void setQualityGroupCode(String qualityGroupCode){  
        this.qualityGroupCode = qualityGroupCode;  
    }  
      
    public String getQualityGroupCode(){  
        return this.qualityGroupCode;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
      
    public String getInspector(){  
        return this.inspector;  
    }

    public void setCurProcesser(String curProcesser){
        this.curProcesser = curProcesser;  
    }  
      
    public String getCurProcesser(){  
        return this.curProcesser;  
    }
	
	public void setLastProcesser(String lastProcesser){  
        this.lastProcesser = lastProcesser;  
    }  
      
    public String getLastProcesser(){  
        return this.lastProcesser;  
    }

    public void setStatus(Integer status){
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getMistake() {
        return mistake;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
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
	
	public void statusAdd(Integer status) {
		if(CollectionUtil.isEmpty(statusArr)){
			statusArr = new ArrayList<>();
		}
		this.statusArr.add(status);
	}
	
	public void statusAdd(Integer ... status) {
		if(CollectionUtil.isEmpty(statusArr)){
			statusArr = new ArrayList<>();
		}
		this.statusArr.addAll(Arrays.asList(status));
	}

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

	public String getAllocateTime() {
		return allocateTime;
	}

	public void setAllocateTime(String allocateTime) {
		this.allocateTime = allocateTime;
	}

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

	public Integer getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(Integer approvalType) {
		this.approvalType = approvalType;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getDamaged() {
		return damaged;
	}

	public void setDamaged(Integer damaged) {
		this.damaged = damaged;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
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
}