package com.kim.mybatis.config;

import java.util.Properties;

import com.kim.mybatis.interceptor.PagingInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import com.kim.common.util.StringUtil;

/**
 * Created by bo.liu01 on 2017/11/23.
 */
public class MybatisConfigObject {

    private String mapperLocations;
    private String typeAliasesPackage;
    private String basePackage;
    private String mapUnderscoreToCamelCase;

    private Interceptor[] plugins =  new Interceptor[]{
            new PagingInterceptor()
    };

    public Properties getProperties(){
    	Properties p = new Properties();
    	if(StringUtil.isNotBlank(mapUnderscoreToCamelCase)){
    		p.setProperty("mapUnderscoreToCamelCase", mapUnderscoreToCamelCase);
    	}
    	return p;
    }
    
    public Interceptor[] getPlugins() {
        return plugins;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

	public String getMapUnderscoreToCamelCase() {
		return mapUnderscoreToCamelCase;
	}

	public void setMapUnderscoreToCamelCase(String mapUnderscoreToCamelCase) {
		this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
	}
}
