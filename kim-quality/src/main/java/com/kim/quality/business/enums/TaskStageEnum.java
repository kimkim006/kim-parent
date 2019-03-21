package com.kim.quality.business.enums;

import com.kim.common.util.StringUtil;

public enum TaskStageEnum {
	
	APPEAL("APPEAL", "申诉阶段", new int[]{
			MainTask.STATUS_RESULT_FEEDBACK, 
			MainTask.STATUS_TO_APPROVAL_APPEAL, 
			MainTask.STATUS_TO_APPEAL_DEAL,
			MainTask.STATUS_APPEAL_PASS,
			MainTask.STATUS_APPEAL_REJECTED,
			MainTask.STATUS_RESULT_CONFIRM
	}),
	
	APPROVAL("APPROVAL", "审核阶段", new int[]{
			MainTask.STATUS_TO_APPROVAL, 
			MainTask.STATUS_RESULT_FEEDBACK,
			MainTask.STATUS_APPROVAL_REJECTED,
			MainTask.STATUS_TO_APPROVAL_APPEAL,
			MainTask.STATUS_APPEAL_REJECTED,
			MainTask.STATUS_TO_APPEAL_DEAL
	}),
	
	EVALUATION("EVAL", "评分阶段", new int[]{
			MainTask.STATUS_TO_EVALUATION, 
			MainTask.STATUS_APPROVAL_REJECTED, 
			MainTask.STATUS_TO_APPROVAL, 
			MainTask.STATUS_TO_APPEAL_DEAL,
			MainTask.STATUS_APPEAL_PASS			
	}),
	
	APPEAL_DEAL("APPEAL_DEAL", "申诉处理阶段", new int[]{
			MainTask.STATUS_APPEAL_PASS,
			MainTask.STATUS_RESULT_FEEDBACK
	}),
	
	ALL("ALL", "全部", new int[]{});
	
	private String stage;
	private String name;
	private int[] status;
	
	private TaskStageEnum(String stage, String name, int[] status) {
		this.stage = stage;
		this.name = name;
		this.status = status;
	}
	
	public static TaskStageEnum getStageEnum(String stage){
		if(stage == null){
			return null;
		}
		for (TaskStageEnum e : TaskStageEnum.values()) {
			if(StringUtil.equalsIgnoreCase(e.getStage(), stage) ){
				return e;
			}
		}
		return null;
	}

	public String getStage() {
		return stage;
	}

	public String getName() {
		return name;
	}

	public int[] getStatus() {
		return status;
	}
	

}
