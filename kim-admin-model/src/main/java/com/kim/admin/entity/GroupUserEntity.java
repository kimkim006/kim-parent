package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 质检小组用户表实体类
 * @date 2018-8-27 15:20:52
 * @author bo.liu01
 */
public class GroupUserEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	protected String groupCode;//小组编码
	protected String username;//用户名/工号
	protected Integer type;//成员类型, 0组员, 1组长
	
	//非数据库字段
	protected String groupName;//小组编码
	protected Integer groupType;//小组类型,  1质检小组, 2业务小组
	
	public GroupUserEntity id(String id) {
		setId(id);
		return this;
	}

	public GroupUserEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setGroupCode(String groupCode){  
        this.groupCode = groupCode;  
    }  
    
	@FieldDisplay("小组编码")
    public String getGroupCode(){
        return this.groupCode;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户名/工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
    
	@FieldDisplay("成员类型, 0组员, 1组长")
    public Integer getType(){
        return this.type;  
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	
}