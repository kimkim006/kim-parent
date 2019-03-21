package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 质检小组表实体类
 * @date 2018-8-27 15:20:52
 * @author bo.liu01
 */
public class GroupEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 小组类型, 1质检小组 */
	public static final int TYPE_QUALITY = 1;
	/** 小组类型, 2业务小组 */
	public static final int TYPE_BUSINESS = 2;
	
	/** 成员类型, 0组员 */
	public static final int USER_TYPE_MEMBER = 0;
	/** 成员类型, 1组长 */
	public static final int USER_TYPE_LEADER = 1;
	
	private String code;//小组编码
	private String name;//小组名称
	private Integer type;//小组类型,  1质检小组, 2业务小组
	
	public GroupEntity id(String id) {
		setId(id);
		return this;
	}

	public GroupEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("小组编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("小组名称")
    public String getName(){
        return this.name;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
    
	@FieldDisplay("小组类型")
    public Integer getType(){
        return this.type;  
    }
	
}