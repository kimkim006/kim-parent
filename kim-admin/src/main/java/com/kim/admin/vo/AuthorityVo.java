package com.kim.admin.vo;

import com.kim.admin.entity.AuthorityEntity;
import com.kim.admin.entity.MenuEntity;
import com.kim.common.base.BaseVo;

import java.util.List;

/**
 * 权限表参数封装类
 * @date 2017-11-10 11:29:23
 * @author bo.liu01
 */
public class AuthorityVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String ownerCode;  //权限主，用户名(username)或者角色编码(role_code)
	private String menuCode;  //菜单编码，admin_menu.code
	private Integer menuType = MenuEntity.TYPE_BUTTON;  //菜单编码，admin_menu.code
	private Integer ownerType;  //权限主身份，1用户身份，2角色身份

    private String addAuth;  //要添加的菜单编码
    private String delAuth;  //要移除的菜单编码
    private List<String> delAuthList;  //要移除的菜单编码

    public List<String> getDelAuthList() {
        return delAuthList;
    }

    public void setDelAuthList(List<String> delAuthList) {
        this.delAuthList = delAuthList;
    }

    public String getAddAuth() {
        return addAuth;
    }

    public void setAddAuth(String addAuth) {
        this.addAuth = addAuth;
    }

    public String getDelAuth() {
        return delAuth;
    }

    public void setDelAuth(String delAuth) {
        this.delAuth = delAuth;
    }

    public void setUsername(String username){
        this.ownerCode = username;
        this.ownerType = AuthorityEntity.OWNER_TYPE_USER;
    }

    public String getUsername(){
	    if(this.ownerType != null && this.ownerType == AuthorityEntity.OWNER_TYPE_USER){
            return this.ownerCode;
        }
        return null;
    }

    public void setRoleCode(String roleCode){
        this.ownerCode = roleCode;
        this.ownerType = AuthorityEntity.OWNER_TYPE_ROLE;
    }

    public String getRoleCode(){
        if(this.ownerType != null && this.ownerType == AuthorityEntity.OWNER_TYPE_ROLE){
            return this.ownerCode;
        }
        return null;
    }

    public void setOwnerCode(String ownerCode){
        this.ownerCode = ownerCode;
    }

    public String getOwnerCode(){  
        return this.ownerCode;  
    }
	
	public void setMenuCode(String menuCode){  
        this.menuCode = menuCode;  
    }  
      
    public String getMenuCode(){  
        return this.menuCode;  
    }
	
	public void setOwnerType(Integer ownerType){  
        this.ownerType = ownerType;  
    }  
      
    public Integer getOwnerType(){  
        return this.ownerType;  
    }

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	
}