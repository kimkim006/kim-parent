package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音平台信息表实体类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class RecordPlatformEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 录音平台ID */
	private String recordPlatformId;
	/** 录音平台名字 */
	private String recordPlatformName;
	/** 话务平台ID */
	private String platformId;
	/** 录音类型 */
	private String recordType;
	/** 录音格式 */
	private String recordFormat;
	/** http地址 */
	private String httpAddress;
	
	public RecordPlatformEntity id(String id) {
		setId(id);
		return this;
	}

	public RecordPlatformEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setRecordPlatformId(String recordPlatformId){  
        this.recordPlatformId = recordPlatformId;  
    }  
    
	@FieldDisplay("录音平台ID")
    public String getRecordPlatformId(){
        return this.recordPlatformId;  
    }
	
	public void setRecordPlatformName(String recordPlatformName){  
        this.recordPlatformName = recordPlatformName;  
    }  
    
	@FieldDisplay("录音平台名字")
    public String getRecordPlatformName(){
        return this.recordPlatformName;  
    }
	
	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
    
	@FieldDisplay("话务平台ID")
    public String getPlatformId(){
        return this.platformId;  
    }
	
	public void setRecordType(String recordType){  
        this.recordType = recordType;  
    }  
    
	@FieldDisplay("录音类型")
    public String getRecordType(){
        return this.recordType;  
    }
	
	public void setRecordFormat(String recordFormat){  
        this.recordFormat = recordFormat;  
    }  
    
	@FieldDisplay("录音格式")
    public String getRecordFormat(){
        return this.recordFormat;  
    }
	
	public void setHttpAddress(String httpAddress){  
        this.httpAddress = httpAddress;  
    }  
    
	@FieldDisplay("http地址")
    public String getHttpAddress(){
        return this.httpAddress;  
    }
	
}