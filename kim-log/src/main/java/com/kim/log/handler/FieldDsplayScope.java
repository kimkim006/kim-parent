package com.kim.log.handler;

public final class FieldDsplayScope {
	
	/** 适用于新增范围生效 */
	public static final byte ADD = 1;
	/** 适用于修改范围生效 */
	public static final byte UPDATE = 2;
	/** 适用于删除范围生效 */
	public static final byte DELETE = 4;
	
	/** 关闭，任何范围都不会生效 */
	public static final byte OFF = 0;
	/** 全部打开，新增，修改，删除都会生效 */
	public static final byte ALL = 7;
	/** 部分打开，新增，修改会生效 */
	public static final byte ADD_UPDATE = 3;
	/** 部分打开，新增，删除会生效 */
	public static final byte ADD_DELETE = 5;
	/** 部分打开，修改，删除会生效 */
	public static final byte UPDATE_DELETE = 6;
	
}
