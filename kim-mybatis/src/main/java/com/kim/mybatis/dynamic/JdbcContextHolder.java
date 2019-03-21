package com.kim.mybatis.dynamic;

public class JdbcContextHolder {
	
	private final static ThreadLocal<DataSourceType> LOCAL_JDBC = new ThreadLocal<>();
	
	public static void putDataSource(DataSourceType name) {
		LOCAL_JDBC.set(name);
	}
	
	public static DataSourceType getDataSource() {
		return LOCAL_JDBC.get();
	}
	
}