package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 模板-菜单 表实体类
 * @date 2018-8-2 15:08:10
 * @author bo.liu01
 */
public class Template2MenuEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String templateCode;//模板编码
	private String menuCode;//模板编码
	
	public Template2MenuEntity() {
		super();
	}
	
	public Template2MenuEntity(String templateCode, String menuCode) {
		super();
		this.templateCode = templateCode;
		this.menuCode = menuCode;
	}

	public Template2MenuEntity id(String id) {
		setId(id);
		return this;
	}

	public Template2MenuEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	@FieldDisplay("菜单编码")
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@FieldDisplay("模板编码")
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

}