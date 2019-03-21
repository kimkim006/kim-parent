package com.kim.schedule.job.vo;

import com.kim.common.base.BaseVo;

/**
 * 定时调度任务表参数封装类
 * @date 2018-8-21 10:39:53
 * @author bo.liu01
 */
public class TaskJobVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String jobName;  //任务名称
	private String jobGroup;  //任务分组
	private String jobDesc;  //任务描述
	private Integer active;  //是否启用, 0不启用, 1启用
	private String cronExpression;  //con表达式
	private String cronExpDesc;  //表达式描述
	private String beanClass;  //执行类
	private String beanId;  //组件id
	private String methodName;  //组件方法
	private Integer type;  //任务类型, 1有状态 0无状态

	public TaskJobVo id(String id) {
		setId(id);
		return this;
	}

	public TaskJobVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setJobName(String jobName){  
        this.jobName = jobName;  
    }  
      
    public String getJobName(){  
        return this.jobName;  
    }
	
	public void setJobGroup(String jobGroup){  
        this.jobGroup = jobGroup;  
    }  
      
    public String getJobGroup(){  
        return this.jobGroup;  
    }
	
	public void setJobDesc(String jobDesc){  
        this.jobDesc = jobDesc;  
    }  
      
    public String getJobDesc(){  
        return this.jobDesc;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
      
    public Integer getActive(){  
        return this.active;  
    }
	
	public void setCronExpression(String cronExpression){  
        this.cronExpression = cronExpression;  
    }  
      
    public String getCronExpression(){  
        return this.cronExpression;  
    }
	
	public void setCronExpDesc(String cronExpDesc){  
        this.cronExpDesc = cronExpDesc;  
    }  
      
    public String getCronExpDesc(){  
        return this.cronExpDesc;  
    }
	
	public void setBeanClass(String beanClass){  
        this.beanClass = beanClass;  
    }  
      
    public String getBeanClass(){  
        return this.beanClass;  
    }
	
	public void setBeanId(String beanId){  
        this.beanId = beanId;  
    }  
      
    public String getBeanId(){  
        return this.beanId;  
    }
	
	public void setMethodName(String methodName){  
        this.methodName = methodName;  
    }  
      
    public String getMethodName(){  
        return this.methodName;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
      
    public Integer getType(){  
        return this.type;  
    }
	
}