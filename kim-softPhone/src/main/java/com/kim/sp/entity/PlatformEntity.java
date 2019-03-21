package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 话务平台信息表实体类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class PlatformEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 话务平台id */
	private String platformId;
	/** 平台类型 */
	private String platformType;
	/** 平台名称 */
	private String platformName;
	/** CTI地址 */
	private String ctiIpPort;
	/** 备用CTI地址 */
	private String ctiIpPortSpare;
	/** 是否有日志 */
	private Integer enableLog;
	/** 是否发送状态 */
	private Integer enableSendStateChgData;
	/** 发送状态地址 */
	private String sendStateChgDataServerAdd;
	
	//录音平台信息
	/** 录音平台ID */
	private String recordPlatformId;
	/** 录音平台名字 */
	private String recordPlatformName;
	/** 录音类型 */
	private String recordType;
	/** 录音格式 */
	private String recordFormat;
	/** http地址 */
	private String httpAddress;
	
	public PlatformEntity id(String id) {
		setId(id);
		return this;
	}

	public PlatformEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
    
	@FieldDisplay("话务平台id")
    public String getPlatformId(){
        return this.platformId;  
    }
	
	public void setPlatformType(String platformType){  
        this.platformType = platformType;  
    }  
    
	@FieldDisplay("平台类型")
    public String getPlatformType(){
        return this.platformType;  
    }
	
	public void setPlatformName(String platformName){  
        this.platformName = platformName;  
    }  
    
	@FieldDisplay("平台名称")
    public String getPlatformName(){
        return this.platformName;  
    }
	
	public void setCtiIpPort(String ctiIpPort){  
        this.ctiIpPort = ctiIpPort;  
    }  
    
	@FieldDisplay("CTI地址")
    public String getCtiIpPort(){
        return this.ctiIpPort;  
    }
	
	public void setCtiIpPortSpare(String ctiIpPortSpare){  
        this.ctiIpPortSpare = ctiIpPortSpare;  
    }  
    
	@FieldDisplay("备用CTI地址")
    public String getCtiIpPortSpare(){
        return this.ctiIpPortSpare;  
    }
	
	public void setEnableLog(Integer enableLog){  
        this.enableLog = enableLog;  
    }  
    
	@FieldDisplay("是否有日志")
    public Integer getEnableLog(){
        return this.enableLog;  
    }
	
	public void setEnableSendStateChgData(Integer enableSendStateChgData){  
        this.enableSendStateChgData = enableSendStateChgData;  
    }  
    
	@FieldDisplay("是否发送状态")
    public Integer getEnableSendStateChgData(){
        return this.enableSendStateChgData;  
    }
	
	public void setSendStateChgDataServerAdd(String sendStateChgDataServerAdd){  
        this.sendStateChgDataServerAdd = sendStateChgDataServerAdd;  
    }  
    
	@FieldDisplay("发送状态地址")
    public String getSendStateChgDataServerAdd(){
        return this.sendStateChgDataServerAdd;  
    }

	public String getRecordPlatformId() {
		return recordPlatformId;
	}

	public void setRecordPlatformId(String recordPlatformId) {
		this.recordPlatformId = recordPlatformId;
	}

	public String getRecordPlatformName() {
		return recordPlatformName;
	}

	public void setRecordPlatformName(String recordPlatformName) {
		this.recordPlatformName = recordPlatformName;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordFormat() {
		return recordFormat;
	}

	public void setRecordFormat(String recordFormat) {
		this.recordFormat = recordFormat;
	}

	public String getHttpAddress() {
		return httpAddress;
	}

	public void setHttpAddress(String httpAddress) {
		this.httpAddress = httpAddress;
	}
	
}