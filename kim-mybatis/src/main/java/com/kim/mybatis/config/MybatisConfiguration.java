package com.kim.mybatis.config;


import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kim.common.util.Env2BeanUtil;

/**
 * mybatis 配置数据源类
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource, MybatisConfigObject mybatisConfig) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigurationProperties(mybatisConfig.getProperties());
        
        if(StringUtils.isNotBlank(mybatisConfig.getTypeAliasesPackage())){
            bean.setTypeAliasesPackage(mybatisConfig.getTypeAliasesPackage());
        }

        if(mybatisConfig.getPlugins() != null && mybatisConfig.getPlugins().length > 0){
            bean.setPlugins(mybatisConfig.getPlugins());
        }

        //添加mapper.xml目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources(mybatisConfig.getMapperLocations()));
            return bean.getObject();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MybatisConfigObject getMybatisConfig(Environment env){

        return Env2BeanUtil.transfer(env, new MybatisConfigObject(), "mybatis");
    }

}
