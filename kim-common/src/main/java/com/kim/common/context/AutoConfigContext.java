package com.kim.common.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author bo.liu01
 */
@Configuration
@ComponentScan(value={"com.kim.common.context",
		"com.kim.common.config"})
public class AutoConfigContext {
    
}
