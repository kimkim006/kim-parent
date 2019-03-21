package com.kim.schedule.job.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 定时调度任务表实体类
 * @date 2018-8-21 10:39:53
 * @author bo.liu01
 */
public class TaskJobEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 默认的任务分组 */
	public static final String DEFAULT_GROUP = "dedault_group";
	
	/** 是否启动任务 1启用 */
	public static final Integer ACTIVE_YES = 1;
	/** 是否启动任务 0不启用 */
	public static final Integer ACTIVE_NO = 0;
	
	/** 任务是否有状态 1有 */
	public static final Integer TYPE_YES = 1;
	/** 任务是否有状态 0无 */
	public static final Integer TYPE_NO = 0;
	
	private String jobName;//任务名称
	private String jobGroup;//任务分组
	private String jobDesc;//任务描述
	private Integer active;//是否启用, 0不启用, 1启用
	private String cronExpression;//con表达式
	private String cronExpDesc;//表达式描述
	private String beanClass;//执行类
	private String beanId;//组件id
	private String methodName;//组件方法
	private String exeParam;//执行参数
	private Integer type;//任务类型, 1有状态，0无状态
	
	/**
	 * NONE：Trigger已经完成，且不会在执行，或者找不到该触发器，或者Trigger已经被删除 
	 * NORMAL:正常
	 * PAUSED：暂停 
	 * COMPLETE：触发完成，但是任务可能还正在执行中
	 * BLOCKED：线程阻塞 
	 * ERROR：出现错误
	 * RUNNING:正在执行
	 */
	private String status;//任务在定时器中的状态,
	
	public TaskJobEntity id(String id) {
		setId(id);
		return this;
	}

	public TaskJobEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setJobName(String jobName){  
        this.jobName = jobName;  
    }  
    
	@FieldDisplay("任务名称")
    public String getJobName(){
        return this.jobName;  
    }
	
	public void setJobGroup(String jobGroup){  
        this.jobGroup = jobGroup;  
    }  
    
	@FieldDisplay("任务分组")
    public String getJobGroup(){
        return this.jobGroup;  
    }
	
	public void setJobDesc(String jobDesc){  
        this.jobDesc = jobDesc;  
    }  
    
	@FieldDisplay("任务描述")
    public String getJobDesc(){
        return this.jobDesc;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
    
	@FieldDisplay("是否启用, 0不启用, 1启用")
    public Integer getActive(){
        return this.active;  
    }
	
	public void setCronExpression(String cronExpression){  
        this.cronExpression = cronExpression;  
    }  
    
	@FieldDisplay("con表达式")
    public String getCronExpression(){
        return this.cronExpression;  
    }
	
	public void setCronExpDesc(String cronExpDesc){  
        this.cronExpDesc = cronExpDesc;  
    }  
    
	@FieldDisplay("表达式描述")
    public String getCronExpDesc(){
        return this.cronExpDesc;  
    }
	
	public void setBeanClass(String beanClass){  
        this.beanClass = beanClass;  
    }  
    
	@FieldDisplay("执行类")
    public String getBeanClass(){
        return this.beanClass;  
    }
	
	public void setBeanId(String beanId){  
        this.beanId = beanId;  
    }  
    
	@FieldDisplay("组件id")
    public String getBeanId(){
        return this.beanId;  
    }
	
	public void setMethodName(String methodName){  
        this.methodName = methodName;  
    }  
    
	@FieldDisplay("组件方法")
    public String getMethodName(){
        return this.methodName;  
    }
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@FieldDisplay("任务类型")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getExeParam() {
		return exeParam;
	}

	public void setExeParam(String exeParam) {
		this.exeParam = exeParam;
	}
	
}