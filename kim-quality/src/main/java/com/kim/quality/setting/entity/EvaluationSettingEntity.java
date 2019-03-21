package com.kim.quality.setting.entity;

import com.kim.common.util.NumberUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 质检评分选项表实体类
 * @date 2018-9-10 14:40:52
 * @author jianming.chen
 */
public class EvaluationSettingEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;

    /** 类型, 1目录 */
    public static final int TYPE_EVASET_DIR = 1;
    /** 类型, 2选项 */
    public static final int TYPE_EVASET = 2;
    
    /**评分类型, 1加分 **/
	public static final int EVAL_TYPE_ADD = 1;
	/**评分类型, 2减分 **/
	public static final int EVAL_TYPE_SUB = 2;

	private String parentId;//父id
	private String name;//名称
	private Integer type;//选项类型, 1目录, 2选项
	private Integer evalType;//评分类型, 1加分, 2减分
	private Double score;//分值

    private List<EvaluationSettingEntity> dirChildren;

    /**全路径名称**/
	private String evalItemName;
	
	public EvaluationSettingEntity id(String id) {
		setId(id);
		return this;
	}

	public EvaluationSettingEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setParentId(String parentId){
        this.parentId = parentId;  
    }  
    
	@FieldDisplay("父id")
    public String getParentId(){
        return this.parentId;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
    
	@FieldDisplay("选项类型, 1目录, 2选项")
    public Integer getType(){
        return this.type;  
    }
	
	public void setEvalType(Integer evalType){  
        this.evalType = evalType;  
    }  
    
	@FieldDisplay("评分类型, 1加分, 2减分")
    public Integer getEvalType(){
        return this.evalType;  
    }
	
	public void setScore(Double score){  
        this.score = score;  
    }  
    
	@FieldDisplay("分值")
    public Double getScore(){
        return this.score;  
    }
	
	public String getScoreStr(){
		if(getScore() != null){
			if(getEvalType() == null){
				return String.valueOf(getScore());
			}else if(EvaluationSettingEntity.EVAL_TYPE_ADD == getEvalType()){
				return "+" + NumberUtil.format(getScore());
			}else{
				return "-" + NumberUtil.format(getScore());
			}
		}
		return null;
	}

    public List<EvaluationSettingEntity> getDirChildren() {

        return dirChildren;
    }

    public void setDirChildren(List<EvaluationSettingEntity> dirChildren) {
        this.dirChildren = dirChildren;
    }

    public void addChildren(EvaluationSettingEntity e) {
        if(this.dirChildren == null) {
            this.dirChildren = new ArrayList<>();
        }
        this.dirChildren.add(e);
    }

	public String getEvalItemName() {
		return evalItemName;
	}

	public void setEvalItemName(String evalItemName) {
		this.evalItemName = evalItemName;
	}

}