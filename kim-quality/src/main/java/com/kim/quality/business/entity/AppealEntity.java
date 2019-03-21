package com.kim.quality.business.entity;

import java.util.Date;
import java.util.List;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.business.enums.MainTask;

/**
 * 申诉记录表实体类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
public class AppealEntity extends LoggedEntity implements RelateTaskItem{
	
	private static final long serialVersionUID = 1L;
	
	/** 状态, 0待审批 */
	public static final int STATUS_TO_APPROVAL = 0;
	/** 状态, 1处理中 */
	public static final int STATUS_IN_PROCESS = 1;
	/** 状态, 2成功 */
	public static final int STATUS_PASSED = 2;
	/** 状态, 3驳回 */
	public static final int STATUS_REJECTED = 3;
	
	private String taskId;//任务id
	private String inspector;//质检员
	private String appealer;//申诉人
	private String appealTime;//申诉时间
	private String content;//申诉原因
	private Integer isLast;//是否最近一次, 1是, 0否
	private Integer status;//状态, 0待审批, 1处理中, 2通过, 3驳回
	
	private Integer oldStatus;//状态
	private String attachmentId;//附件id
	
	private List<AttachmentEntity> attaList;
	
	@Override
	public String getKeyType(){
		return MainTask.KEY_TYPE_APPEAL;
	}
	
	@Override
	public Long getSortTime() {
		if(StringUtil.isBlank(getAppealTime())){
			return null;
		}
		Date date = DateUtil.parseDate(getAppealTime());
		if(date == null){
			return null;
		}
		return date.getTime();
	}
	
	public AppealEntity id(String id) {
		setId(id);
		return this;
	}

	public AppealEntity tenantId(String tenantId) {
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
	
	public void setInspector(String inspector){  
        this.inspector = inspector;  
    }  
    
	@FieldDisplay("质检员")
    public String getInspector(){
        return this.inspector;  
    }
	
	public void setAppealer(String appealer){  
        this.appealer = appealer;  
    }  
    
	@FieldDisplay("申诉人")
    public String getAppealer(){
        return this.appealer;  
    }
	
	public void setContent(String content){  
        this.content = content;  
    }  
    
	@FieldDisplay("申诉原因")
    public String getContent(){
        return this.content;  
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

	public String getAppealTime() {
		return appealTime;
	}

	public void setAppealTime(String appealTime) {
		this.appealTime = appealTime;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public List<AttachmentEntity> getAttaList() {
		return attaList;
	}

	public void setAttaList(List<AttachmentEntity> attaList) {
		this.attaList = attaList;
	}
	
}