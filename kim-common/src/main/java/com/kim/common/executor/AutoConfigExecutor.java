package com.kim.common.executor;

import java.util.concurrent.ThreadPoolExecutor;

import com.kim.common.util.Env2BeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 
 * @author bo.liu01
 */
@Configuration
@ComponentScan(value={"com.kim.common.executor"})
public class AutoConfigExecutor {
	
	@Bean
	public ExecutorParam getExecutorParam(Environment env) {

		return Env2BeanUtil.transfer(env, new ExecutorParam(), "spring.executor");
	}
	
	@Bean
	public ThreadPoolExecutor getExecutor(ExecutorParam param) {
		
		return new ThreadPoolExecutor(param.getCorePoolSize(), 
				param.getMaximumPoolSize(), param.getKeepAliveTime(), 
				param.getUnit(), param.getWorkQueue(),
				param.getThreadFactory(), param.getHandler());
	}
    
}
