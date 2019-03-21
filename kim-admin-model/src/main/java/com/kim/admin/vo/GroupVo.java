package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 质检小组表参数封装类
 * @date 2018-8-27 15:20:52
 * @author bo.liu01
 */
public class GroupVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String code;  //小组编码
	private String name;  //小组名称
	private Integer type;  //小组类型,  1质检小组, 2业务小组

	public GroupVo id(String id) {
		setId(id);
		return this;
	}

	public GroupVo tenantId(String tenantId) {
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
	
	public void setType(Integer type){  
        this.type = type;  
    }  
      
    public Integer getType(){  
        return this.type;  
    }
	
}