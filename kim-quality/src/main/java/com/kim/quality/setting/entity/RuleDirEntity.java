package com.kim.quality.setting.entity;

import java.util.ArrayList;
import java.util.List;

import com.kim.common.util.CollectionUtil;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 质检规则目录表实体类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
public class RuleDirEntity extends LoggedEntity implements RuleEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String parentCode;//父编码
	private String code;//编码
	private String name;//名称
	private Integer type;//类型, 1热线, 0其他
	
	private String sampleType;//抽检规则类型
	private List<RuleDirEntity> dirChildren;
	private List<SampleRuleEntity> ruleChildren;
	
	private static RuleDirEntity createTop(){
		RuleDirEntity entity = new RuleDirEntity();
		entity.setType(TYPE_RULE_TYPE);
		return entity;
	}
	
	public static RuleDirEntity createSystemTop(){
		RuleDirEntity entity = createTop();
		entity.setCode(SAMPLE_TYPE_SYSTEM);
		entity.setSampleType(SAMPLE_TYPE_SYSTEM);
		entity.setName("系统抽检规则");
		return entity;
	}
	
	public static RuleDirEntity createManualTop(){
		RuleDirEntity entity = createTop();
		entity.setCode(SAMPLE_TYPE_MANUAL);
		entity.setSampleType(SAMPLE_TYPE_MANUAL);
		entity.setName("手工抽检规则");
		return entity;
	}
	
	public RuleDirEntity id(String id) {
		setId(id);
		return this;
	}

	public RuleDirEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setParentCode(String parentCode){  
        this.parentCode = parentCode;  
    }  
    
	@FieldDisplay("父编码")
    public String getParentCode(){
        return this.parentCode;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("编码")
    public String getCode(){
        return this.code;  
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
    
	@FieldDisplay("类型, 1热线, 0其他")
    public Integer getType(){
        return this.type;  
    }

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public List<SampleRuleEntity> getRuleChildren() {
		return ruleChildren;
	}
	
	public void setDirChildren(List<RuleDirEntity> dirChildren) {
		this.dirChildren = dirChildren;
	}

	public void setRuleChildren(List<SampleRuleEntity> ruleChildren) {
		this.ruleChildren = ruleChildren;
	}
	
	public void addChildren(RuleDirEntity e) {
		if(this.dirChildren == null){
			this.dirChildren = new ArrayList<>();
		}
		this.dirChildren.add(e);
	}
	
	public void addChildren(SampleRuleEntity e) {
		if(this.ruleChildren == null){
			this.ruleChildren = new ArrayList<>();
		}
		this.ruleChildren.add(e);
	}
	
	public List<? extends RuleEntity> getChildren() {
		if(CollectionUtil.isNotEmpty(dirChildren)){
			return dirChildren;
		}
		return ruleChildren;
	}

	@Override
	public boolean getDisabled() {
		return true;
	}

	@Override
	public String getDigest() {
		
		return code;
	}
	
}