package com.kim.schedule;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.kim.base.EnableBaseOper;
import com.kim.common.EnableAccessFilter;
import com.kim.impexp.EnableImpexp;
import com.kim.log.EnableOperLog;
import com.kim.mybatis.EnableMybatisDynamic;

/**
 * @author bo.liu01
 *
 */
@EnableFeignClients
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableMybatisDynamic
@EnableOperLog
@EnableAccessFilter
@EnableBaseOper
@EnableImpexp
public class ScheduleBootstrap {
	
	@Bean
    public SchedulerFactoryBean schedulerFactory(){
        return new SchedulerFactoryBean ();
    }
	
	public static void main(String[] args) {

		new SpringApplicationBuilder(ScheduleBootstrap.class).web(true).run(args);
	}
}
