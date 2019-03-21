package com.kim.quality.setting.vo;

import com.kim.common.base.BaseVo;

/**
 * 质检评分选项表参数封装类
 * @date 2018-9-10 14:40:52
 * @author jianming.chen
 */
public class EvaluationSettingVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String parentId;  //父id
	private String name;  //名称
	private Integer type;  //选项类型, 1目录, 2选项
	private Integer evalType;  //评分类型, 1加分, 2减分
	private Double score;  //分值

	public EvaluationSettingVo id(String id) {
		setId(id);
		return this;
	}

	public EvaluationSettingVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setParentId(String parentId){
        this.parentId = parentId;  
    }  
      
    public String getParentId(){
        return this.parentId;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
      
    public Integer getType(){  
        return this.type;  
    }
	
	public void setEvalType(Integer evalType){  
        this.evalType = evalType;  
    }  
      
    public Integer getEvalType(){  
        return this.evalType;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
      
    public Double getScore(){  
        return this.score;  
    }

}