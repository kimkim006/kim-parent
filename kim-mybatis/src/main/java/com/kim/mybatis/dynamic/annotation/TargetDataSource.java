package com.kim.mybatis.dynamic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kim.mybatis.dynamic.DataSourceType;

@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.METHOD)   
public @interface TargetDataSource {  
  
    /**
     * 数据源，默认主库
     * @return
     */
    DataSourceType value() default DataSourceType.MASTER;
      
} 
