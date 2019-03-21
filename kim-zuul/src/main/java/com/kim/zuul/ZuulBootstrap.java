package com.kim.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.kim.common.EnableConfig;

@SpringBootApplication
@EnableZuulProxy
@EnableCaching
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableConfig
public class ZuulBootstrap{
    public static void main(String[] args) {
    	
    	//FilterProcessor.setProcessor(new DidiFilterProcessor());
        SpringApplication.run(ZuulBootstrap.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ZuulBootstrap extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ZuulBootstrap.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> zuul系统初始化...");
//        return application.sources(ZuulBootstrap.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ZuulBootstrap.class, args);
//    }
//}
