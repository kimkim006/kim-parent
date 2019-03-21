package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 录音小结关联表参数封装类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
public class TapeSummaryVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String serialNumber;  //录音流水号
	private String typeCode;  //类型编码
	private String originTypeCode;  //原始类型编码
	private String firstCode;  //一级编码
	private String secondCode;  //二级编码
	private String thirdCode;  //三级编码
	private String forthCode;  //四级编码
	private String originFirstCode;  //原始一级编码
	private String originSecondCode;  //原始二级编码
	private String originThirdCode;  //原始三级编码
	private String originForthCode;  //原始四级编码
	private String source;  //来源
	
	private String startTime;  //开始时间
	private String endTime;  //结束时间

	public TapeSummaryVo id(String id) {
		setId(id);
		return this;
	}

	public TapeSummaryVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
      
    public String getSerialNumber(){  
        return this.serialNumber;  
    }
	
	public void setTypeCode(String typeCode){  
        this.typeCode = typeCode;  
    }  
      
    public String getTypeCode(){  
        return this.typeCode;  
    }
	
	public void setOriginTypeCode(String originTypeCode){  
        this.originTypeCode = originTypeCode;  
    }  
      
    public String getOriginTypeCode(){  
        return this.originTypeCode;  
    }
	
	public void setFirstCode(String firstCode){  
        this.firstCode = firstCode;  
    }  
      
    public String getFirstCode(){  
        return this.firstCode;  
    }
	
	public void setSecondCode(String secondCode){  
        this.secondCode = secondCode;  
    }  
      
    public String getSecondCode(){  
        return this.secondCode;  
    }
	
	public void setThirdCode(String thirdCode){  
        this.thirdCode = thirdCode;  
    }  
      
    public String getThirdCode(){  
        return this.thirdCode;  
    }
	
	public void setForthCode(String forthCode){  
        this.forthCode = forthCode;  
    }  
      
    public String getForthCode(){  
        return this.forthCode;  
    }
	
	public void setOriginFirstCode(String originFirstCode){  
        this.originFirstCode = originFirstCode;  
    }  
      
    public String getOriginFirstCode(){  
        return this.originFirstCode;  
    }
	
	public void setOriginSecondCode(String originSecondCode){  
        this.originSecondCode = originSecondCode;  
    }  
      
    public String getOriginSecondCode(){  
        return this.originSecondCode;  
    }
	
	public void setOriginThirdCode(String originThirdCode){  
        this.originThirdCode = originThirdCode;  
    }  
      
    public String getOriginThirdCode(){  
        return this.originThirdCode;  
    }
	
	public void setOriginForthCode(String originForthCode){  
        this.originForthCode = originForthCode;  
    }  
      
    public String getOriginForthCode(){  
        return this.originForthCode;  
    }
	
	public void setSource(String source){  
        this.source = source;  
    }  
      
    public String getSource(){  
        return this.source;  
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}