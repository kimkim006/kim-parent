package com.kim.mybatis.statical;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingleDataSource {
	
	@Bean(name = "dataSource")  
    @ConfigurationProperties(prefix = "datasource")   
    public DataSource dataSource() {  
        return DataSourceBuilder.create().build();  
    } 

}
