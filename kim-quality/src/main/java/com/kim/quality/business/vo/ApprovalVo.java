package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

import java.util.List;

/**
 * 审批记录明细表参数封装类
 *
 * @author jianming.chen
 * @date 2018-9-18 18:56:25
 */
public class ApprovalVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    private String taskId;  //任务id
    private String agentId;  //坐席工号
    private String inspector;  //质检员
    private Integer type;  //审批类型, 1评分审批, 2申诉审批
    private String upstreamId;  //评分/申诉记录
    private String submitter;  //提交人
    private String submitTime;  //提交时间
    private String auditor;  //审核人
    private String approvalTime;  //审批时间
    private String content;  //审批意见
    private String mistake;  //是否质检差错
    private Integer status;  //状态, 0待审批, 1通过, 2驳回

    List<String> taskIds;//任务ids

    public ApprovalVo id(String id) {
        setId(id);
        return this;
    }

    public ApprovalVo tenantId(String tenantId) {
        setTenantId(tenantId);
        return this;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspector() {
        return this.inspector;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    public void setUpstreamId(String upstreamId) {
        this.upstreamId = upstreamId;
    }

    public String getUpstreamId() {
        return this.upstreamId;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getSubmitter() {
        return this.submitter;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitTime() {
        return this.submitTime;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditor() {
        return this.auditor;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalTime() {
        return this.approvalTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
    }

    public String getMistake() {
        return this.mistake;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }
}