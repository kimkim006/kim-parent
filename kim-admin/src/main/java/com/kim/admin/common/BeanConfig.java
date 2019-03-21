package com.kim.admin.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kim.common.config.CommonBeanConfig;
import com.kim.common.constant.CommonConstants;
import com.kim.common.util.StringUtil;

@Component
public class BeanConfig extends CommonBeanConfig{
	
	/** 默认的菜单图标 */
	@Value("${menu-icon.default}")
	private String menuIconDefault;
	/** 删除租户时检查表数据时忽略的表，逗号分隔 */
	@Value("${tenant-ignore-table}")
	private String tenantIgnoreTable;
	
	@Value("${portrait.tmp.path:}")
	protected String portraitTmpPath;
	
	///域账号登录连接配置=========================
	/** 域账号 连接IP */
	@Value("${ldap.host}")
    private String ldapHost;
	/** 域账号 连接端口 */
    @Value("${ldap.port}")
    private String ldapPort;
    /** 域账号 搜索域节点 */
    @Value("${ldap.searchBase}")
    private String ldapSearchBase;
    /** 域账号 搜索的属性 */
    @Value("${ldap.searchFilter}")
    private String ldapSearchFilter;
    /** 域账号后缀 */
    @Value("${ldap.suffix}")
    private String ldapSuffix;
    /** 域账号前缀 */
    @Value("${ldap.prefix}")
    private String ldapPrefix;
    ///域账号登录连接配置=========================

    public String getPortraitTmpPath() {
		if(StringUtil.isBlank(portraitTmpPath)){
			return CommonConstants.PORTRAIT_TMP_PATH;
		}
		return portraitTmpPath;
	}
    
	public String getLdapHost() {
		return ldapHost;
	}

	public String getMenuIconDefault() {
		return menuIconDefault;
	}

	public void setMenuIconDefault(String menuIconDefault) {
		this.menuIconDefault = menuIconDefault;
	}

	public String getLdapPort() {
		return ldapPort;
	}

	public String getLdapSearchBase() {
		return ldapSearchBase;
	}

	public String getLdapSearchFilter() {
		return ldapSearchFilter;
	}

	public String getLdapSuffix() {
		return ldapSuffix;
	}
	
	public String getLdapPrefix() {
		return ldapPrefix;
	}

	public String getTenantIgnoreTable() {
		return tenantIgnoreTable;
	}
	
}
