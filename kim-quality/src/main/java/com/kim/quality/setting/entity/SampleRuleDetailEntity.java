package com.kim.quality.setting.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 抽检规则明细表实体类
 * @date 2018-8-23 15:10:03
 * @author bo.liu01
 */
public class SampleRuleDetailEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String ruleId;//规则id
	private String name;//名称
	private String digestName;//摘要名称
	private Integer seqNumber;//规则序号
	private String resolver;//解析器标识
	private String resolverParam;//解析参数
	private Integer percent;//抽检比例
	
	public SampleRuleDetailEntity id(String id) {
		setId(id);
		return this;
	}

	public SampleRuleDetailEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setRuleId(String ruleId){  
        this.ruleId = ruleId;  
    }  
    
	@FieldDisplay("规则id")
    public String getRuleId(){
        return this.ruleId;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setSeqNumber(Integer seqNumber){  
        this.seqNumber = seqNumber;  
    }  
    
	@FieldDisplay("规则序号")
    public Integer getSeqNumber(){
        return this.seqNumber;  
    }
	
	public void setResolver(String resolver){  
        this.resolver = resolver;  
    }  
    
	@FieldDisplay("解析器标识")
    public String getResolver(){
        return this.resolver;  
    }
	
	public void setResolverParam(String resolverParam){  
        this.resolverParam = resolverParam;  
    }  
    
	@FieldDisplay("解析参数")
    public String getResolverParam(){
        return this.resolverParam;  
    }
	
	public void setPercent(Integer percent){  
        this.percent = percent;  
    }  
    
	@FieldDisplay("抽检比例")
    public Integer getPercent(){
        return this.percent;  
    }

	public String getDigestName() {
		return digestName;
	}

	public void setDigestName(String digestName) {
		this.digestName = digestName;
	}
	
}