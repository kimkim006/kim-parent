package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 组织人员关系表实体类
 * @date 2018-9-4 14:40:25
 * @author yonghui.wu
 */
public class UserOrgEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;
	/** 工号类型，1用户工号 */
	public final static int CODE_TYPE_USER = 1;
	/** 工号类型，2小组编码 */
	public final static int CODE_TYPE_GROUP = 2;
	
	private String itemCode;//用户工号或组编码
	private Integer codeType;//item类型，1用户工号, 2组编码
	private String upperSuperior;//上级领导
	
	//非数据库字段
	private String upperSuperiorName;//上级领导
	private String name;//工号姓名或组名称
	
	public UserOrgEntity id(String id) {
		setId(id);
		return this;
	}

	public UserOrgEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setItemCode(String itemCode){  
        this.itemCode = itemCode;  
    }  
    
	@FieldDisplay("用户工号或组编码")
    public String getItemCode(){
        return this.itemCode;  
    }
	
	public void setCodeType(Integer codeType){  
        this.codeType = codeType;  
    }  
    
	@FieldDisplay("item类型，1用户工号, 2组编码")
    public Integer getCodeType(){
        return this.codeType;  
    }
	
	public void setUpperSuperior(String upperSuperior){  
        this.upperSuperior = upperSuperior;  
    }  
    
	@FieldDisplay("上级领导")
    public String getUpperSuperior(){
        return this.upperSuperior;  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpperSuperiorName() {
		return upperSuperiorName;
	}

	public void setUpperSuperiorName(String upperSuperiorName) {
		this.upperSuperiorName = upperSuperiorName;
	}
}