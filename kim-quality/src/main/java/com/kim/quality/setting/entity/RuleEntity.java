package com.kim.quality.setting.entity;

import java.util.HashMap;
import java.util.Map;

public interface RuleEntity {
	
	/** 类型, 1规则类型,系统/手工抽检  */
	public static final int TYPE_RULE_TYPE = 1;
	/** 类型, 2质检模式 */
	public static final int TYPE_MODE = 2;
	/** 类型, 3热线 */
	public static final int TYPE_HOST_LINE = 3;
	/** 类型, 4具体的质检规则 */
	public static final int TYPE_RULES = 4;
	
	/** 抽检规则类型, system系统抽检规则 */
	public static final String SAMPLE_TYPE_SYSTEM = "system";
	/** 抽检规则类型, manual手工抽检规则 */
	public static final String SAMPLE_TYPE_MANUAL = "manual";
	
	public static Map<String, String> getSampleTypeName(){
		Map<String, String> map = new HashMap<>();
		map.put(SAMPLE_TYPE_SYSTEM, "系统抽检规则");
		map.put(SAMPLE_TYPE_MANUAL, "人工抽检规则");
		return map;
    }
	
	public void setParentCode(String parentCode); 
    
    public String getParentCode();
	
	public void setCode(String code);
    
    public String getCode();
	
	public void setName(String name); 
    
    public String getName();
	
	public void setType(Integer type); 
    
    /**
     * 类型, 1规则类型,系统/手工抽检, 2质检模式, 3热线, 4具体的质检规则
     * @return
     */
    public Integer getType();
    
    /**
     * 抽检规则类型, system系统抽检规则, manual手工抽检规则
     * @return
     */
    public String getSampleType();
    
    /**
     * 唯一的摘要信息
     * @return
     */
    public String getDigest();
    
    /**
     * 是否可用
     * @return
     */
    public boolean getDisabled();
    
}
