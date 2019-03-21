package com.kim.quality.business.vo;

import com.kim.common.base.BaseVo;

/**
 * 附件信息表参数封装类
 * @date 2018-12-7 13:37:58
 * @author liubo
 */
public class AttachmentVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String taskId;  //任务id
	private String itemId;  //业务id
	private Integer itemType;  //业务类型, 1申诉, 其他暂未定
	private String name;  //名称
	private String type;  //附件类型, jpg, rar,zip,doc等
	private String fileName;  //文件名
	private String path;  //文件路径

	public AttachmentVo id(String id) {
		setId(id);
		return this;
	}

	public AttachmentVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTaskId(String taskId){  
        this.taskId = taskId;  
    }  
      
    public String getTaskId(){  
        return this.taskId;  
    }
	
	public void setItemId(String itemId){  
        this.itemId = itemId;  
    }  
      
    public String getItemId(){  
        return this.itemId;  
    }
	
	public void setItemType(Integer itemType){  
        this.itemType = itemType;  
    }  
      
    public Integer getItemType(){  
        return this.itemType;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setType(String type){  
        this.type = type;  
    }  
      
    public String getType(){  
        return this.type;  
    }
	
	public void setFileName(String fileName){  
        this.fileName = fileName;  
    }  
      
    public String getFileName(){  
        return this.fileName;  
    }
	
	public void setPath(String path){  
        this.path = path;  
    }  
      
    public String getPath(){  
        return this.path;  
    }
	
}