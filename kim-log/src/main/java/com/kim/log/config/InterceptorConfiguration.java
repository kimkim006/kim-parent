package com.kim.log.config;

import com.kim.log.interceptor.DefinedInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class InterceptorConfiguration extends WebMvcConfigurerAdapter{

  public void addInterceptors(InterceptorRegistry registry) {
      //配置拦截器
      registry.addInterceptor(definedInterceptor()).addPathPatterns("/**");
  }
  
  @Bean  
  public DefinedInterceptor definedInterceptor() {
      return new DefinedInterceptor();  
  }  

}
