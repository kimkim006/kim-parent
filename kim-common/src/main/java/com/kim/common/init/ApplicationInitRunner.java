package com.kim.common.init;

import com.kim.common.config.CommonBeanConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

@Component
@Order(1)
public class ApplicationInitRunner implements ApplicationRunner {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	static{
		ParserConfig.getGlobalInstance().setAsmEnable(false);
		SerializeConfig.getGlobalInstance().setAsmEnable(false);
	}
	
	@Autowired
	protected CommonBeanConfig commonBeanConfig;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("=================================================");
		logger.info("             【{}】服务已启动!", commonBeanConfig.getApplicationName());
		logger.info("=================================================");
	}

}
