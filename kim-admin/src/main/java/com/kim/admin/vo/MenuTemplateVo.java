package com.kim.admin.vo;

import java.util.List;

import com.kim.common.base.BaseVo;

/**
 * 租户菜单模板表参数封装类
 * @date 2018-8-2 15:08:10
 * @author bo.liu01
 */
public class MenuTemplateVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String code;  //模板编码
	private String name;  //模板名称
	
	private String addMenu;  //要添加的菜单编码
    private String delMenu;  //要移除的菜单编码
    private List<String> delMenuList;  //要移除的菜单编码

	public MenuTemplateVo id(String id) {
		setId(id);
		return this;
	}

	public MenuTemplateVo tenantId(String tenantId) {
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
    
    public String getDelMenu() {
		return delMenu;
	}

	public void setDelMenu(String delMenu) {
		this.delMenu = delMenu;
	}

	public String getAddMenu() {
		return addMenu;
	}

	public void setAddMenu(String addMenu) {
		this.addMenu = addMenu;
	}

	public List<String> getDelMenuList() {
		return delMenuList;
	}

	public void setDelMenuList(List<String> delMenuList) {
		this.delMenuList = delMenuList;
	}
	
}