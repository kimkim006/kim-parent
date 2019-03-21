package com.kim.quality;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.kim.base.EnableBaseOper;
import com.kim.common.EnableAccessFilter;
import com.kim.common.EnableExecutorConfig;
import com.kim.impexp.EnableImpexp;
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
@EnableImpexp
@EnableExecutorConfig
public class QualityBootstrap {
	public static void main(String[] args) {

		new SpringApplicationBuilder(QualityBootstrap.class).web(true).run(args);
	}
}
