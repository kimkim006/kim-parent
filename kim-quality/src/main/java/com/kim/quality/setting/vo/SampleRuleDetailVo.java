package com.kim.quality.setting.vo;

import com.kim.common.base.BaseVo;

/**
 * 抽检规则明细表参数封装类
 * @date 2018-8-23 15:10:03
 * @author bo.liu01
 */
public class SampleRuleDetailVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String ruleId;  //规则id
	private String name;  //名称
	private Integer seqNumber;  //规则序号
	private String resolver;  //解析器标识
	private String resolverParam;  //解析参数
	private Double percent;  //抽检比例

	public SampleRuleDetailVo id(String id) {
		setId(id);
		return this;
	}

	public SampleRuleDetailVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRuleId(String ruleId){  
        this.ruleId = ruleId;  
    }  
      
    public String getRuleId(){  
        return this.ruleId;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setSeqNumber(Integer seqNumber){  
        this.seqNumber = seqNumber;  
    }  
      
    public Integer getSeqNumber(){  
        return this.seqNumber;  
    }
	
	public void setResolver(String resolver){  
        this.resolver = resolver;  
    }  
      
    public String getResolver(){  
        return this.resolver;  
    }
	
	public void setResolverParam(String resolverParam){  
        this.resolverParam = resolverParam;  
    }  
      
    public String getResolverParam(){  
        return this.resolverParam;  
    }
	
	public void setPercent(Double percent){  
        this.percent = percent;  
    }  
      
    public Double getPercent(){  
        return this.percent;  
    }
	
}