package com.kim.mybatis.dynamic;

public enum DataSourceType {

	/** 主库 */
	MASTER("MASTER"),
	/** SQL Server 数据库 */
	SQL_SERVER("SQL_SERVER");

	private String name;

	private DataSourceType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}