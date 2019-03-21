package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 权限表实体类
 * @date 2017-11-10 11:29:23
 * @author bo.liu01
 */
public class AuthorityEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;
    /** 权限主身份，1用户身份 */
	public static final int OWNER_TYPE_USER = 1;
    /** 权限主身份，2角色身份 */
	public static final int OWNER_TYPE_ROLE = 2;

	private String ownerCode;//权限主，用户名(username)或者角色编码(role_code)
	private String menuCode;//菜单编码
	private Integer ownerType;//权限主身份，1用户身份，2角色身份

	public void setOwnerCode(String ownerCode){  
        this.ownerCode = ownerCode;  
    }  
    
	@FieldDisplay("权限主")
    public String getOwnerCode(){
        return this.ownerCode;  
    }
	
	public void setMenuCode(String menuCode){  
        this.menuCode = menuCode;  
    }  
    
	@FieldDisplay("菜单编码")
    public String getMenuCode(){
        return this.menuCode;  
    }
	
	public void setOwnerType(Integer ownerType){  
        this.ownerType = ownerType;  
    }  
    
	@FieldDisplay("权限主身份")
    public Integer getOwnerType(){
        return this.ownerType;  
    }
	
}