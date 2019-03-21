package com.kim.mybatis.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType key = JdbcContextHolder.getDataSource();
		if(key == null){
			key = DataSourceType.MASTER;
		}
		logger.debug("当前数据源为: {}", key.getName());
		return key;
	}
	
}