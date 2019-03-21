package com.kim.quality.setting.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 抽检规则
 * @author bo.liu01
 *
 */
public class SampleRuleEntity extends LoggedEntity implements RuleEntity{
	
	private static final long serialVersionUID = 1L;
	
	/** 质检模式, tape录音质检 */
	public static final String MODE_CODE_TAPE = "tape";
	/** 质检模式, text文本 */
	public static final String MODE_CODE_TEXT = "text";
	/** 质检模式, mixed混合 */
	public static final String MODE_CODE_MIXED = "mixed";
	
	/** 质检规则是否可用, 1可用 */
	public static final int ACTIVE_YES = 1;
	/** 质检规则是否可用, 0不可用 */
	public static final int ACTIVE_NO = 0;
	
	private String modeCode;//质检模式, tape录音质检, text文本, mixed混合
	private String businessCode;//业务线
	private String name;//名称
	private Integer active;//是否可用, 1可用, 0不可用
	
	private String sampleType;//抽检规则类型
	
	private Integer personAvg;//人均数量， 系统抽检时才使用该字段
	
	private List<SampleRuleDetailEntity> ruleDetail;
	
	public static Map<String, String> getModeName(){
		Map<String, String> map = new HashMap<>();
		map.put(MODE_CODE_TAPE, "录音质检");
		map.put(MODE_CODE_TEXT, "文本质检");
		map.put(MODE_CODE_MIXED, "混合质检");
		return map;
	}
	
	public SampleRuleEntity id(String id) {
		setId(id);
		return this;
	}

	public SampleRuleEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setModeCode(String modeCode){  
        this.modeCode = modeCode;  
    }  
    
	@FieldDisplay("质检模式, tape录音质检, text文本, mixed混合")
    public String getModeCode(){
        return this.modeCode;  
    }
	
	public void setBusinessCode(String businessCode){  
        this.businessCode = businessCode;  
    }  
    
	@FieldDisplay("业务线")
    public String getBusinessCode(){
        return this.businessCode;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
    
	@FieldDisplay("是否可用, 1可用, 0不可用")
    public Integer getActive(){
        return this.active;  
    }

	@Override
	public void setParentCode(String parentCode) {
		this.businessCode = parentCode;
		
	}

	@Override
	public String getParentCode() {
		return businessCode;
	}

	@Override
	public void setCode(String code) {
		this.id = code;
		
	}

	@Override
	public String getCode() {
		return id;
	}

	@Override
	public void setType(Integer type) {
	}

	@Override
	public Integer getType() {
		return TYPE_RULES;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public List<SampleRuleDetailEntity> getRuleDetail() {
		return ruleDetail;
	}

	public void setRuleDetail(List<SampleRuleDetailEntity> ruleDetail) {
		this.ruleDetail = ruleDetail;
	}
	
	@Override
	public boolean getDisabled() {
		return false;
	}
	
	@Override
	public String getDigest() {
		
		return getId();
	}

	public Integer getPersonAvg() {
		return personAvg;
	}

	public void setPersonAvg(Integer personAvg) {
		this.personAvg = personAvg;
	}

}
