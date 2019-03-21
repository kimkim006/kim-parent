package com.kim.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author bo.liu01
 */
@Configuration
@ComponentScan(value={"com.kim.common.config", "com.kim.common.init",
				"com.kim.common.context",})
public class AutoConfig {
    
}
