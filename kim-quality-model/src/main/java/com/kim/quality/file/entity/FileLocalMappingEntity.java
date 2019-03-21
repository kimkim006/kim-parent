package com.kim.quality.file.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音本地映射表实体类
 * @date 2018-9-28 13:42:48
 * @author bo.liu01
 */
public class FileLocalMappingEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 是否可用, 1可用 */
	public static final int ACTIVE_YES = 1;
	/** 是否可用, 0不可用 */
	public static final int ACTIVE_NO = 0;
	
	private String tapeId;//录音id
	private String mainTaskId;//任务id
	private String serialNumber;//录音流水号
	private String platform;//录音平台
	private String httpAddress;//原始地址
	private String localPath;//本地地址
	private String fileName;//文件名
	private Integer active;//是否可用, 1可用, 0不可用
	
	public FileLocalMappingEntity id(String id) {
		setId(id);
		return this;
	}

	public FileLocalMappingEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setTapeId(String tapeId){  
        this.tapeId = tapeId;  
    }  
    
	@FieldDisplay("录音id")
    public String getTapeId(){
        return this.tapeId;  
    }
	
	public void setMainTaskId(String mainTaskId){  
        this.mainTaskId = mainTaskId;  
    }  
    
	@FieldDisplay("任务id")
    public String getMainTaskId(){
        return this.mainTaskId;  
    }
	
	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
    
	@FieldDisplay("录音流水号")
    public String getSerialNumber(){
        return this.serialNumber;  
    }
	
	public void setPlatform(String platform){  
        this.platform = platform;  
    }  
    
	@FieldDisplay("录音平台")
    public String getPlatform(){
        return this.platform;  
    }
	
	public void setHttpAddress(String httpAddress){  
        this.httpAddress = httpAddress;  
    }  
    
	@FieldDisplay("原始地址")
    public String getHttpAddress(){
        return this.httpAddress;  
    }
	
	public void setLocalPath(String localPath){  
        this.localPath = localPath;  
    }  
    
	@FieldDisplay("本地地址")
    public String getLocalPath(){
        return this.localPath;  
    }
	
	public void setFileName(String fileName){  
        this.fileName = fileName;  
    }  
    
	@FieldDisplay("文件名")
    public String getFileName(){
        return this.fileName;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
    
	@FieldDisplay("是否可用, 1可用, 0不可用")
    public Integer getActive(){
        return this.active;  
    }
	
}