package com.kim.quality.setting.vo;

import com.kim.common.base.BaseVo;

/**
 * 抽检规则表参数封装类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
public class SampleRuleVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	/** 数据详情类型， ALL全部数据 */
	public static final String DATA_TYPE_ALL = "ALL";
	/** 数据详情类型，ASSG可分配数据 */
	public static final String DATA_TYPE_ASSG = "ASSG";
	/** 数据详情类型， ACT已分配数据 */
	public static final String DATA_TYPE_ACT = "ACT";
	/** 数据详情类型， DEL全部数据 */
	public static final String DATA_TYPE_DEL = "DEL";
	
	private String modeCode;  //质检模式, tape录音质检, text文本, mixed混合
	private String businessCode;  //业务线
	private String name;  //名称
	private Integer active;  //是否可用, 1可用, 0不可用
	private String sampleType;//抽检规则类型

	public SampleRuleVo id(String id) {
		setId(id);
		return this;
	}

	public SampleRuleVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setModeCode(String modeCode){  
        this.modeCode = modeCode;  
    }  
      
    public String getModeCode(){  
        return this.modeCode;  
    }
	
	public void setBusinessCode(String businessCode){  
        this.businessCode = businessCode;  
    }  
      
    public String getBusinessCode(){  
        return this.businessCode;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
      
    public Integer getActive(){  
        return this.active;  
    }

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

}