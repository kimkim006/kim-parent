package com.kim.quality.file.vo;

import com.kim.common.base.BaseVo;

/**
 * 录音本地映射表参数封装类
 * @date 2018-9-28 13:42:48
 * @author bo.liu01
 */
public class FileLocalMappingVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String tapeId;  //录音id
	private String mainTaskId;  //任务id
	private String serialNumber;  //录音流水号
	private String platform;  //录音平台
	private String httpAddress;  //原始地址
	private String localPath;  //本地地址
	private String fileName;  //文件名
	private Integer active;  //是否可用, 1可用, 0不可用

	private String limitTime;  //超期时间
	
	public FileLocalMappingVo id(String id) {
		setId(id);
		return this;
	}

	public FileLocalMappingVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTapeId(String tapeId){  
        this.tapeId = tapeId;  
    }  
      
    public String getTapeId(){  
        return this.tapeId;  
    }
	
	public void setMainTaskId(String mainTaskId){  
        this.mainTaskId = mainTaskId;  
    }  
      
    public String getMainTaskId(){  
        return this.mainTaskId;  
    }
	
	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
      
    public String getSerialNumber(){  
        return this.serialNumber;  
    }
	
	public void setPlatform(String platform){  
        this.platform = platform;  
    }  
      
    public String getPlatform(){  
        return this.platform;  
    }
	
	public void setHttpAddress(String httpAddress){  
        this.httpAddress = httpAddress;  
    }  
      
    public String getHttpAddress(){  
        return this.httpAddress;  
    }
	
	public void setLocalPath(String localPath){  
        this.localPath = localPath;  
    }  
      
    public String getLocalPath(){  
        return this.localPath;  
    }
	
	public void setFileName(String fileName){  
        this.fileName = fileName;  
    }  
      
    public String getFileName(){  
        return this.fileName;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
      
    public Integer getActive(){  
        return this.active;  
    }

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	
}