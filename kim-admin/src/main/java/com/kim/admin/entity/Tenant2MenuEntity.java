package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 租户-菜单 表实体类
 * @date 2018-8-2 15:08:10
 * @author bo.liu01
 */
public class Tenant2MenuEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String menuCode;//模板编码
	
	public Tenant2MenuEntity() {
		super();
	}
	
	public Tenant2MenuEntity(String tenantId, String menuCode) {
		super();
		this.tenantId = tenantId;
		this.menuCode = menuCode;
	}

	public Tenant2MenuEntity id(String id) {
		setId(id);
		return this;
	}

	public Tenant2MenuEntity tenantId(String tenantId) {
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

}