package com.kim.mybatis.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration  
public class DataSourceConfig {  
  
    @Bean(name = "masterDataSource")  
    @ConfigurationProperties(prefix = "datasource.master")   
    public DataSource dataSource1() {  
        return DataSourceBuilder.create().build();  
    }  
  
    @Bean(name = "sqlserverDataSource")  
    @ConfigurationProperties(prefix = "datasource.sqlserver")   
    public DataSource dataSource2() {  
        return DataSourceBuilder.create().build();  
    }  
      
    @Bean(name="dynamicDataSource")  
    @Primary    //优先使用，多数据源  
    public DataSource dataSource() {  
        DynamicDataSource dynamicDataSource = new DynamicDataSource();  
        DataSource master = dataSource1();  
        DataSource sqlserver = dataSource2();  
        //设置默认数据源  
        dynamicDataSource.setDefaultTargetDataSource(master);     
        //配置多数据源  
        Map<Object,Object> map = new HashMap<>();  
        map.put(DataSourceType.MASTER, master);   //key需要跟ThreadLocal中的值对应  
        map.put(DataSourceType.SQL_SERVER, sqlserver);  
        dynamicDataSource.setTargetDataSources(map);              
        return dynamicDataSource;  
    }  
      
}  
