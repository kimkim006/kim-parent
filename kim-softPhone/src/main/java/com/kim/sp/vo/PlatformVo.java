package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 话务平台信息表参数封装类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
public class PlatformVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String platformId;  //话务平台id
	private String platformType;  //平台类型
	private String platformName;  //平台名称
	private String ctiIpPort;  //CTI地址
	private String ctiIpPortSpare;  //备用CTI地址
	private Integer enableLog;  //是否有日志
	private Integer enableSendStateChgData;  //是否发送状态
	private String sendStateChgDataServerAdd;  //发送状态地址

	public PlatformVo id(String id) {
		setId(id);
		return this;
	}

	public PlatformVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
      
    public String getPlatformId(){  
        return this.platformId;  
    }
    
	
	public void setPlatformType(String platformType){  
        this.platformType = platformType;  
    }  
      
    public String getPlatformType(){  
        return this.platformType;  
    }
    
	
	public void setPlatformName(String platformName){  
        this.platformName = platformName;  
    }  
      
    public String getPlatformName(){  
        return this.platformName;  
    }
    
	
	public void setCtiIpPort(String ctiIpPort){  
        this.ctiIpPort = ctiIpPort;  
    }  
      
    public String getCtiIpPort(){  
        return this.ctiIpPort;  
    }
    
	
	public void setCtiIpPortSpare(String ctiIpPortSpare){  
        this.ctiIpPortSpare = ctiIpPortSpare;  
    }  
      
    public String getCtiIpPortSpare(){  
        return this.ctiIpPortSpare;  
    }
    
	
	public void setEnableLog(Integer enableLog){  
        this.enableLog = enableLog;  
    }  
      
    public Integer getEnableLog(){  
        return this.enableLog;  
    }
    
	
	public void setEnableSendStateChgData(Integer enableSendStateChgData){  
        this.enableSendStateChgData = enableSendStateChgData;  
    }  
      
    public Integer getEnableSendStateChgData(){  
        return this.enableSendStateChgData;  
    }
    
	
	public void setSendStateChgDataServerAdd(String sendStateChgDataServerAdd){  
        this.sendStateChgDataServerAdd = sendStateChgDataServerAdd;  
    }  
      
    public String getSendStateChgDataServerAdd(){  
        return this.sendStateChgDataServerAdd;  
    }
    
	
}