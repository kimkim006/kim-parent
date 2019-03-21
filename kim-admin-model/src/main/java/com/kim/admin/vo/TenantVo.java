package com.kim.admin.vo;

import java.util.List;

import com.kim.common.base.BaseVo;

/**
 * 租户表参数封装类
 * @date 2018-7-5 13:05:21
 * @author bo.liu01
 */
public class TenantVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String name;  //租户名称
	private String templateCode;  //租户模板编码
	
	private String addMenu;  //要添加的菜单编码
    private String delMenu;  //要移除的菜单编码
    private List<String> delMenuList;  //要移除的菜单编码
	
	public TenantVo setTenantId(String tenantId){  
        this.tenantId = tenantId;  
        return this;
    }  
      
    public String getTenantId(){  
        return this.tenantId;  
    }
	
	public TenantVo setName(String name){  
        this.name = name;  
        return this;
    }  
      
    public String getName(){  
        return this.name;  
    }

	public String getAddMenu() {
		return addMenu;
	}

	public void setAddMenu(String addMenu) {
		this.addMenu = addMenu;
	}

	public String getDelMenu() {
		return delMenu;
	}

	public void setDelMenu(String delMenu) {
		this.delMenu = delMenu;
	}

	public List<String> getDelMenuList() {
		return delMenuList;
	}

	public void setDelMenuList(List<String> delMenuList) {
		this.delMenuList = delMenuList;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
}