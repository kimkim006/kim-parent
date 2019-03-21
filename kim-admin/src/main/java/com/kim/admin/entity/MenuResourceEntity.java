package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 菜单资源表实体类
 * @date 2017-11-10 13:45:34
 * @author bo.liu01
 */
public class MenuResourceEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String menuCode;//菜单编码,admin_menu.code
	private String resourceCode;//资源编码, admin_resource.code
	
	public void setMenuCode(String menuCode){  
        this.menuCode = menuCode;  
    }  
    
	@FieldDisplay("菜单编码")
    public String getMenuCode(){
        return this.menuCode;  
    }
	
	public void setResourceCode(String resourceCode){  
        this.resourceCode = resourceCode;  
    }  
    
	@FieldDisplay("资源编码, admin_resource.code")
    public String getResourceCode(){
        return this.resourceCode;  
    }
	
}