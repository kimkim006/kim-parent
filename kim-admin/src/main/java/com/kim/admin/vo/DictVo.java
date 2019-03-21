package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 数据字典表参数封装类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
public class DictVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String parentId;  //父id
	private String code;  //编码
	private String name;  //名称

	public DictVo id(String id) {
		setId(id);
		return this;
	}

	public DictVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}