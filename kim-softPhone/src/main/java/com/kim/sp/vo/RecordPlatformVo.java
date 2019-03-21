package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 录音平台信息表参数封装类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class RecordPlatformVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String recordPlatformId;  //录音平台ID
	private String recordPlatformName;  //录音平台名字
	private String platformId;  //话务平台ID
	private String recordType;  //录音类型
	private String recordFormat;  //录音格式
	private String httpAddress;  //http地址

	public RecordPlatformVo id(String id) {
		setId(id);
		return this;
	}

	public RecordPlatformVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRecordPlatformId(String recordPlatformId){  
        this.recordPlatformId = recordPlatformId;  
    }  
      
    public String getRecordPlatformId(){  
        return this.recordPlatformId;  
    }
    
	
	public void setRecordPlatformName(String recordPlatformName){  
        this.recordPlatformName = recordPlatformName;  
    }  
      
    public String getRecordPlatformName(){  
        return this.recordPlatformName;  
    }
    
	
	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
      
    public String getPlatformId(){  
        return this.platformId;  
    }
    
	
	public void setRecordType(String recordType){  
        this.recordType = recordType;  
    }  
      
    public String getRecordType(){  
        return this.recordType;  
    }
    
	
	public void setRecordFormat(String recordFormat){  
        this.recordFormat = recordFormat;  
    }  
      
    public String getRecordFormat(){  
        return this.recordFormat;  
    }
    
	
	public void setHttpAddress(String httpAddress){  
        this.httpAddress = httpAddress;  
    }  
      
    public String getHttpAddress(){  
        return this.httpAddress;  
    }
    
	
}