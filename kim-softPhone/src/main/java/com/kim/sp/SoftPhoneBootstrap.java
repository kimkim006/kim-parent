package com.kim.sp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.kim.base.EnableBaseOper;
import com.kim.common.EnableAccessFilter;
import com.kim.log.EnableOperLog;
import com.kim.mybatis.EnableMybatis;

/**
 * @author bo.liu01
 *
 */
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableMybatis
@EnableOperLog
@EnableAccessFilter
@EnableBaseOper
public class SoftPhoneBootstrap {
	public static void main(String[] args) {

		new SpringApplicationBuilder(SoftPhoneBootstrap.class).web(true).run(args);
	}
}
