package com.kim.log.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author bo.liu01
 */
@Configuration
@ComponentScan({"com.kim.log", "com.kim.common.filters"})
public class AutoConfiguration {
    
}
