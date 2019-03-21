package com.kim.quality.business.entity;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.business.enums.MainTask;

import java.util.Date;

/**
 * 审批记录明细表实体类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
public class ApprovalEntity extends LoggedEntity implements RelateTaskItem{

	private static final long serialVersionUID = 1L;
	
	/**审核类型，1评分审核  */
	public final static int TYPE_EVAL = 1;
	/**审核类型，2申诉审核  */
	public final static int TYPE_APPEAL = 2;
	
	/**状态, 0待审批 */
	public final static int STATUS_TO_APPROVAL = 0;
	/**状态, 1通过 */
	public final static int STATUS_PASSED = 1;
	/**状态, 2驳回 */
	public final static int STATUS_REJECTED = 2;

	private String taskId;//任务id
	private String agentId;//坐席工号
	private String inspector;//质检员
	private Integer type;//审批类型, 1评分审批, 2申诉审批
	private String upstreamId;//评分/申诉记录
	private String submitter;//提交人
	private String submitTime;//提交时间
	private String auditor;//审核人
	private String approvalTime;//审批时间
	private String content;//审批意见
	private String mistake;//是否质检差错
	private Integer status;//状态, 0待审批, 1通过, 2驳回
	private Integer isLast;//是否最后一次记录, 1是, 0否
	
	@Override
	public String getKeyType(){
		return MainTask.KEY_TYPE_APPROVAL;
	}

	@Override
	public Long getSortTime() {
		if(StringUtil.isBlank(getSubmitTime())){
			return null;
		}
		Date date = DateUtil.parseDate(getSubmitTime());
		if(date == null){
			return null;
		}
		return date.getTime();
	}

	public ApprovalEntity id(String id) {
		setId(id);
		return this;
	}

	public ApprovalEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTaskId(String taskId){
        this.taskId = taskId;
    }

	@FieldDisplay("任务id")
    public String getTaskId(){
        return this.taskId;
    }

	public void setAgentId(String agentId){
        this.agentId = agentId;
    }

	@FieldDisplay("坐席工号")
    public String getAgentId(){
        return this.agentId;
    }

	public void setInspector(String inspector){
        this.inspector = inspector;
    }

	@FieldDisplay("质检员")
    public String getInspector(){
        return this.inspector;
    }

	public void setType(Integer type){
        this.type = type;
    }

	@FieldDisplay("审批类型, 1评分审批, 2申诉审批")
    public Integer getType(){
        return this.type;
    }

	public void setUpstreamId(String upstreamId){
        this.upstreamId = upstreamId;
    }

	@FieldDisplay("评分/申诉记录")
    public String getUpstreamId(){
        return this.upstreamId;
    }

	public void setSubmitter(String submitter){
        this.submitter = submitter;
    }

	@FieldDisplay("提交人")
    public String getSubmitter(){
        return this.submitter;
    }

	public void setSubmitTime(String submitTime){
        this.submitTime = submitTime;
    }

	@FieldDisplay("提交时间")
    public String getSubmitTime(){
        return this.submitTime;
    }

	public void setAuditor(String auditor){
        this.auditor = auditor;
    }

	@FieldDisplay("审核人")
    public String getAuditor(){
        return this.auditor;
    }

	public void setApprovalTime(String approvalTime){
        this.approvalTime = approvalTime;
    }

	@FieldDisplay("审批时间")
    public String getApprovalTime(){
        return this.approvalTime;
    }

	public void setContent(String content){
        this.content = content;
    }

	@FieldDisplay("审批意见")
    public String getContent(){
        return this.content;
    }

	public void setMistake(String mistake){
        this.mistake = mistake;
    }

	@FieldDisplay("是否质检差错")
    public String getMistake(){
        return this.mistake;
    }

	public void setStatus(Integer status){
        this.status = status;
    }

	@FieldDisplay("状态, 0待审批, 1通过, 2驳回")
    public Integer getStatus(){
        return this.status;
    }

	public Integer getIsLast() {
		return isLast;
	}

	public void setIsLast(Integer isLast) {
		this.isLast = isLast;
	}

}