package com.kim.common.filters;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author bo.liu01
 */
@Configuration
@ComponentScan({"com.kim.common.context",
	"com.kim.common.filters",
	"com.kim.common.config"})
public class AutoConfigFilter{
    
}
