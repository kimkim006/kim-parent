package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 小结表参数封装类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
public class SummaryVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String parentCode;  //父编码
	private String code;  //编码
	private String name;  //名称
	private String originParentCode;  //原始父编码
	private String originCode;  //原始编码
	private String source;  //来源
	private Integer level;//层级, 0服务类型, 1-4表示来电N级

	public SummaryVo id(String id) {
		setId(id);
		return this;
	}

	public SummaryVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setParentCode(String parentCode){  
        this.parentCode = parentCode;  
    }  
      
    public String getParentCode(){  
        return this.parentCode;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setOriginParentCode(String originParentCode){  
        this.originParentCode = originParentCode;  
    }  
      
    public String getOriginParentCode(){  
        return this.originParentCode;  
    }
	
	public void setOriginCode(String originCode){  
        this.originCode = originCode;  
    }  
      
    public String getOriginCode(){  
        return this.originCode;  
    }
	
	public void setSource(String source){  
        this.source = source;  
    }  
      
    public String getSource(){  
        return this.source;  
    }

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
}