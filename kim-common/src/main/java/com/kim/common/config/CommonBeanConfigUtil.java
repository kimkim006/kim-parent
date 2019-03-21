package com.kim.common.config;

import com.kim.common.util.HttpServletUtil;

public class CommonBeanConfigUtil {
	
	private static CommonBeanConfig commonBeanConfig;
	private static final Object CONFIG_LOCKER = new Object();
	
	public static CommonBeanConfig getConfig(){
    	if(commonBeanConfig == null){
    		synchronized (CONFIG_LOCKER) {
    			if(commonBeanConfig == null){
    				commonBeanConfig = (CommonBeanConfig) HttpServletUtil.getBean("commonBeanConfig");
    			}
    		}
    	}
    	return commonBeanConfig;
    }
	
}
