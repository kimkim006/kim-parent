package com.kim.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class EurekaBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(EurekaBootstrap.class, args);
    }
}

/**
 * 不使用内置tomcat运行（war包运行），需SpringBootServletInitializer加载
 */
//public class EurekaBootstrap extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(EurekaBootstrap.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> Eureka系统初始化...");
//        return application.sources(EurekaBootstrap.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(EurekaBootstrap.class, args);
//    }
//}
