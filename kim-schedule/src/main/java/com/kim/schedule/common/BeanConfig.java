package com.kim.schedule.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kim.common.config.CommonBeanConfig;

@Component
public class BeanConfig extends CommonBeanConfig{
	
    /** 域账号前缀 */
    @Value("${ldap.prefix:}")
    private String ldapPrefix;

	public String getLdapPrefix() {
		return ldapPrefix;
	}

}
