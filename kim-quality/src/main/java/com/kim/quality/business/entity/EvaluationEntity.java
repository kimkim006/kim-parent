package com.kim.quality.business.entity;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;
import com.kim.quality.business.enums.MainTask;

import java.util.Date;
import java.util.List;

/**
 * 质检评分表实体类
 * @date 2018-9-17 15:43:36
 * @author jianming.chen
 */
public class EvaluationEntity extends LoggedEntity implements RelateTaskItem{
	
	private static final long serialVersionUID = 1L; 
	
	/** 损坏与否状态, 0：正常  **/
	public final static int DAMAGED_NO = 0;
	/** 损坏与否状态, 1：损坏 **/
	public final static int DAMAGED_YES = 1;
	
	/** 损坏与否状态, 0：正常  **/
	public final static String DAMAGED_NO_NAME = "否";
	/** 损坏与否状态, 1：损坏 **/
	public final static String DAMAGED_YES_NAME = "是";
	
	/**审核状态 , 0待审核 **/
	public final static int STATUS_PENDING = 0;
	/**审核状态, 1审核驳回 **/
	public final static int STATUS_REJECTED = 1;
	/**审核状态, 2审核通过 **/
	public final static int STATUS_PASSED = 2;
	
	private String taskId;//任务id
	private String agentId;//坐席工号
	private String inspector;//质检员
	private String evalTime;//评分时间
	private Integer damaged;//录音是否损坏, 0否, 1是
	private Double origScore;//初始分数
	private Double score;//得分, 根据计算得分，若计算得分超过120，则为120，若计算得分小于0则为0
	private Double calScore;//计算得分
	private Integer status;//状态, 0待审核, 1审核驳回, 2审核通过
	private Integer isLast;//是否最后一次记录, 1是, 0否

	private String evaluationSettings; //批量新增时实体JSON字符串对象,前端传递
	private List<EvaluationDetailEntity> evaluationDetailList; //redis缓存时存储对象
	
	/***录音ID*/
	private String tapeId;
	

	public EvaluationEntity id(String id) {
		setId(id);
		return this;
	}

	public EvaluationEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	@Override
	public String getKeyType(){
		return MainTask.KEY_TYPE_EVALUATION;
	}
	
	@Override
	public Long getSortTime() {
		if(StringUtil.isBlank(getEvalTime())){
			return null;
		}
		Date date = DateUtil.parseDate(getEvalTime());
		if(date == null){
			return null;
		}
		return date.getTime();
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
	
	public void setDamaged(Integer damaged){  
        this.damaged = damaged;  
    }  
    
	@FieldDisplay("录音是否损坏, 0否, 1是")
    public Integer getDamaged(){
        return this.damaged;  
    }
	
	public void setOrigScore(Double origScore){  
        this.origScore = origScore;  
    }  
    
	@FieldDisplay("初始分数")
    public Double getOrigScore(){
        return this.origScore;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
    
	@FieldDisplay("得分")
    public Double getScore(){
        return this.score;  
    }
	
	public void setCalScore(Double calScore){  
        this.calScore = calScore;  
    }  
    
	@FieldDisplay("计算得分")
    public Double getCalScore(){
        return this.calScore;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("状态")
    public Integer getStatus(){
        return this.status;  
    }

	public String getEvaluationSettings() {
		return evaluationSettings;
	}

	public void setEvaluationSettings(String evaluationSettings) {
		this.evaluationSettings = evaluationSettings;
	}

	public List<EvaluationDetailEntity> getEvaluationDetailList() {
		return evaluationDetailList;
	}

	public void setEvaluationDetailList(List<EvaluationDetailEntity> evaluationDetailList) {
		this.evaluationDetailList = evaluationDetailList;
	}

	public String getTapeId() {
		return tapeId;
	}

	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}

	public String getEvalTime() {
		return evalTime;
	}

	public void setEvalTime(String evalTime) {
		this.evalTime = evalTime;
	}

	public Integer getIsLast() {
		return isLast;
	}

	public void setIsLast(Integer isLast) {
		this.isLast = isLast;
	}
}