package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 附件信息表实体类
 * @date 2018-12-7 13:37:58
 * @author liubo
 */
public class AttachmentEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 业务类型, 1申诉  */
	public static final int ITEM_TYPE_APPEAL = 1;
	
	private String taskId;//任务id
	private String itemId;//业务id
	private Integer itemType;//业务类型, 1申诉, 其他暂未定
	private String name;//名称
	private String type;//附件类型, jpg, rar,zip,doc等
	private String fileName;//文件名
	private String path;//文件路径
	
	private String f;//下载文件用的编码
	private String s;//下载文件用的签名
	
	public AttachmentEntity id(String id) {
		setId(id);
		return this;
	}

	public AttachmentEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
    
	@FieldDisplay("任务id")
    public String getTaskId(){
        return this.taskId;  
    }
	
	public void setItemId(String itemId){  
        this.itemId = itemId;  
    }  
    
	@FieldDisplay("业务id")
    public String getItemId(){
        return this.itemId;  
    }
	
	public void setItemType(Integer itemType){  
        this.itemType = itemType;  
    }  
    
	@FieldDisplay("业务类型, 1申诉, 其他暂未定")
    public Integer getItemType(){
        return this.itemType;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setType(String type){  
        this.type = type;  
    }  
    
	@FieldDisplay("附件类型, jpg, rar,zip,doc等")
    public String getType(){
        return this.type;  
    }
	
	public void setFileName(String fileName){  
        this.fileName = fileName;  
    }  
    
	@FieldDisplay("文件名")
    public String getFileName(){
        return this.fileName;  
    }
	
	public void setPath(String path){  
        this.path = path;  
    }  
    
	@FieldDisplay("文件路径")
    public String getPath(){
        return this.path;  
    }

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
	
}