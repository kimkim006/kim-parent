package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 菜单资源表参数封装类
 * @date 2017-11-10 13:45:34
 * @author bo.liu01
 */
public class MenuResourceVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String menuCode;  //菜单编码,admin_menu.code
	private String resourceCode;  //资源编码, admin_resource.code
	
	public void setMenuCode(String menuCode){  
        this.menuCode = menuCode;  
    }  
      
    public String getMenuCode(){  
        return this.menuCode;  
    }
	
	public void setResourceCode(String resourceCode){  
        this.resourceCode = resourceCode;  
    }  
      
    public String getResourceCode(){  
        return this.resourceCode;  
    }
	
}