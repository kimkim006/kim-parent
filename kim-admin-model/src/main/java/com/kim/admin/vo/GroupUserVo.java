package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 质检小组用户表参数封装类
 * @date 2018-8-27 15:20:52
 * @author bo.liu01
 */
public class GroupUserVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String groupCode;  //小组编码
	private String username;  //用户名/工号
	private Integer type;  //成员类型, 0组员, 1组长

	public GroupUserVo id(String id) {
		setId(id);
		return this;
	}

	public GroupUserVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setGroupCode(String groupCode){  
        this.groupCode = groupCode;  
    }  
      
    public String getGroupCode(){  
        return this.groupCode;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
      
    public Integer getType(){  
        return this.type;  
    }
	
}