package com.kim.quality.business.entity;

import com.kim.common.util.StringUtil;
import com.kim.common.util.UUIDUtils;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.business.enums.MainTask;

/**
 * 质检任务表实体类
 * @date 2018-8-30 11:15:47
 * @author bo.liu01
 */
public class MainTaskEntity extends LoggedEntity  implements RelateTaskItem{
	
	protected static final long serialVersionUID = 1L; 
	
	//数据库字段 ---start----
	protected String batchNo;//抽检批次号
	protected String modeCode;//质检模式, tape录音质检, text文本, mixed混合
	protected String businessCode;//业务线
	protected String ruleId;//抽检规则id
	protected String resolver;//解析器标识
	protected String sampleType;//规则类型,  system系统抽检, manual人工抽检
	protected Integer itemType;//抽检数据类型,  1录音, 2文本
	protected String busiGroupCode;//业务小组
	protected String agentId;//坐席工号
	protected String qualityGroupCode;//质检小组
	protected String inspector;//质检员
	protected String extractTime;//抽取时间
	protected String allocateTime;//分配时间
	protected String curProcesser;//当前处理人
	protected String lastProcesser;//上次处理人
	protected String lastProcessTime;//上次处理时间
	protected Integer allocateCount;//分配次数
	protected Integer recycleCount;//回收次数
	protected Integer approvalCount;//审核次数
	protected Integer appealCount;//申诉次数
	protected Integer evaluationCount;//评分次数
	protected Integer adjustScoreCount;//调整分数次数
	protected Integer status;//状态, 参考 MainTask.STATUS_XXX
	protected Double score;//得分
	//数据库字段 ---end----
	
	//非数据库字段 --- start ---
	private SampleTapeEntity tape = new SampleTapeEntity();//录音对象
//	private EvaluationEntity eval = new EvaluationEntity();//评分对象
//	private EvaluationDetailEntity evalDetail = new EvaluationDetailEntity();//评分明细对象
	protected Object[] evalDetailArr; 
	
	private Integer damaged;//录音是否损坏
	private String damagedName;//录音是否损坏
	private String approvalId;//审批id
	private Integer approvalType;//审批类型, 1评分审批, 2申诉审批
	private String submitter;//审核提交人
	private String submitterName;//审核提交人
	private String submitTime;//审核提交时间
	private String groupName;//小组名称
	private String agentName;//坐席姓名
	private String inspectorName;//质检员姓名
	private String busiGroupName;//业务小组名称
	private String curProcesserName;//当前处理人
	private String qualityGroupName;//质检小组名称
	private String ruleName;//抽检规则名称
	protected String statusName;//状态中文名称
	protected String scoreDetail;//得分明细
	protected Integer oldStatus;//修改前的旧状态， 参考 MainTask.STATUS_XXX
	//非数据库字段--- end ---
	
	@Override
	public Long getSortTime() {
		return 1L;
	}

	@Override
	public String getKeyType() {
		return MainTask.KEY_TYPE_INFO;
	}
	
	/**
	 * 产生唯一主键值
	 */
	public MainTaskEntity createId(){
		if(StringUtil.isBlank(id)){
			this.id = UUIDUtils.getUuid();
		}
		return this;
	}
	
	public MainTaskEntity id(String id) {
		setId(id);
		return this;
	}

	public MainTaskEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setBatchNo(String batchNo){  
        this.batchNo = batchNo;  
    }  
    
	@FieldDisplay("抽检批次号, 规则id+抽取数据日期")
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
	
	public void setItemType(Integer itemType){  
        this.itemType = itemType;  
    }  
    
	@FieldDisplay("抽检数据类型,  1录音, 2文本")
    public Integer getItemType(){
        return this.itemType;  
    }
	
	public void setBusiGroupCode(String busiGroupCode){  
        this.busiGroupCode = busiGroupCode;  
    }  
    
	@FieldDisplay("业务小组")
    public String getBusiGroupCode(){
        return this.busiGroupCode;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("坐席工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
	public void setQualityGroupCode(String qualityGroupCode){  
        this.qualityGroupCode = qualityGroupCode;  
    }  
    
	@FieldDisplay("质检小组")
    public String getQualityGroupCode(){
        return this.qualityGroupCode;  
    }
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
    
	public Integer getRecycleCount() {
		return recycleCount;
	}

	public void setRecycleCount(Integer recycleCount) {
		this.recycleCount = recycleCount;
	}

	@FieldDisplay("质检员")
    public String getInspector(){
        return this.inspector;  
    }
	
	public void setAllocateTime(String allocateTime){  
        this.allocateTime = allocateTime;  
    }  
    
	public String getScoreDetail() {
		return scoreDetail;
	}

	public void setScoreDetail(String scoreDetail) {
		this.scoreDetail = scoreDetail;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	@FieldDisplay("分配时间")
    public String getAllocateTime(){
        return this.allocateTime;  
    }
	
	public void setCurProcesser(String curProcesser){  
        this.curProcesser = curProcesser;  
    }  
    
	@FieldDisplay("当前处理人")
    public String getCurProcesser(){
        return this.curProcesser;  
    }
	
	public void setLastProcesser(String lastProcesser){  
        this.lastProcesser = lastProcesser;  
    }  
    
	@FieldDisplay("上次处理人")
    public String getLastProcesser(){
        return this.lastProcesser;  
    }
	
	public void setLastProcessTime(String lastProcessTime){  
        this.lastProcessTime = lastProcessTime;  
    }  
    
	@FieldDisplay("上次处理时间")
    public String getLastProcessTime(){
        return this.lastProcessTime;  
    }
	
	public void setAllocateCount(Integer allocateCount){  
        this.allocateCount = allocateCount;  
    }  
    
	@FieldDisplay("分配次数")
    public Integer getAllocateCount(){
        return this.allocateCount;  
    }
	
	public void setApprovalCount(Integer approvalCount){  
        this.approvalCount = approvalCount;  
    }  
    
	@FieldDisplay("审核次数")
    public Integer getApprovalCount(){
        return this.approvalCount;  
    }
	
	public void setAppealCount(Integer appealCount){  
        this.appealCount = appealCount;  
    }  
    
	@FieldDisplay("申诉次数")
    public Integer getAppealCount(){
        return this.appealCount;  
    }
	
	public void setEvaluationCount(Integer evaluationCount){  
        this.evaluationCount = evaluationCount;  
    }  
    
	@FieldDisplay("评分次数")
    public Integer getEvaluationCount(){
        return this.evaluationCount;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("状态")
    public Integer getStatus(){
        return this.status;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
    
	@FieldDisplay("得分")
    public Double getScore(){
        return this.score;  
    }

	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBusiGroupName() {
		return busiGroupName;
	}

	public void setBusiGroupName(String busiGroupName) {
		this.busiGroupName = busiGroupName;
	}

	public String getCurProcesserName() {
		return curProcesserName;
	}

	public void setCurProcesserName(String curProcesserName) {
		this.curProcesserName = curProcesserName;
	}

	public String getQualityGroupName() {
		return qualityGroupName;
	}

	public void setQualityGroupName(String qualityGroupName) {
		this.qualityGroupName = qualityGroupName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public SampleTapeEntity getTape() {
		return tape;
	}

	public void setTape(SampleTapeEntity tape) {
		this.tape = tape;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public Integer getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(Integer approvalType) {
		this.approvalType = approvalType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSubmitterName() {
		return submitterName;
	}

	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getInspectorName() {
		return inspectorName;
	}

	public void setInspectorName(String inspectorName) {
		this.inspectorName = inspectorName;
	}

	public Object[] getEvalDetailArr() {
		return evalDetailArr;
	}

	public void setEvalDetailArr(Object[] evalDetailArr) {
		this.evalDetailArr = evalDetailArr;
	}

	public Integer getDamaged() {
		return damaged;
	}

	public void setDamaged(Integer damaged) {
		this.damaged = damaged;
	}

	public String getDamagedName() {
		return damagedName;
	}

	public void setDamagedName(String damagedName) {
		this.damagedName = damagedName;
	}

	public Integer getAdjustScoreCount() {
		return adjustScoreCount;
	}

	public void setAdjustScoreCount(Integer adjustScoreCount) {
		this.adjustScoreCount = adjustScoreCount;
	}

}