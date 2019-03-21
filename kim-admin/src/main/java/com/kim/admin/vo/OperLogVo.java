package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 操作日志表参数封装类
 * @date 2018-7-12 10:48:45
 * @author bo.liu01
 */
public class OperLogVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String serviceId;  //服务id
	private String moduleName;  //操作模块
	private String operationName;  //操作名称
	private String ipAddress;  //操作ip
	private String content;  //操作内容
	private String operTimeStart;  //操作时间
	private String operTimeEnd;  //操作时间
	
	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
      
    public String getServiceId(){  
        return this.serviceId;  
    }
	
	public void setModuleName(String moduleName){  
        this.moduleName = moduleName;  
    }  
      
    public String getModuleName(){  
        return this.moduleName;  
    }
	
	public void setOperationName(String operationName){  
        this.operationName = operationName;  
    }  
      
    public String getOperationName(){  
        return this.operationName;  
    }
	
	public void setIpAddress(String ipAddress){  
        this.ipAddress = ipAddress;  
    }  
      
    public String getIpAddress(){  
        return this.ipAddress;  
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperTimeStart() {
		return operTimeStart;
	}

	public void setOperTimeStart(String operTimeStart) {
		this.operTimeStart = operTimeStart;
	}

	public String getOperTimeEnd() {
		return operTimeEnd;
	}

	public void setOperTimeEnd(String operTimeEnd) {
		this.operTimeEnd = operTimeEnd;
	}
	
}