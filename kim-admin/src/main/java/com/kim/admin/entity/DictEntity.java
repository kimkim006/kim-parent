package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 数据字典表实体类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
public class DictEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 默认的编码 */
	public static final String DEFAULT_PARENT_ID = "0";
	
	private String tenantName;//租户名称
	
	private String parentCode;//父code
	private String parentName;//父名称
	private String parentId;//父id
	private String code;//编码
	private String name;//名称
	private int count;//键值对个数
	
	public DictEntity id(String id) {
		setId(id);
		return this;
	}

	public DictEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    } 
	
	public DictEntity name(String name){  
		this.name = name;  
		return this;
	}  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }

	@FieldDisplay("父id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
}