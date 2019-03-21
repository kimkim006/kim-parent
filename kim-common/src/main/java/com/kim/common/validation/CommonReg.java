package com.kim.common.validation;

public class CommonReg {
	
	/** 日期正则表达式，2018-02-05格式 */
	public final static String DATE_STR_REG = "^((([0-9]{2})(0[48]|[2468][048]|[13579][26]))|((0[48]|[2468][048]|[13579][26])00)-02-29)|([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
	/** 日期时间正则表达式，2018-02-05 12:45:12格式 */
	public final static String DATETIME_STR_REG = "^((([0-9]{2})(0[48]|[2468][048]|[13579][26]))|((0[48]|[2468][048]|[13579][26])00)-02-29)|([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
	/** 日期和日期时间正则表达式，2018-02-05格式 或 2018-02-05 12:45:12格式 */
	public final static String DATE_DATETIME_STR_REG = DATE_STR_REG+"|"+DATETIME_STR_REG;
	
	/** 电话号码正则表达式（支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号） */
	public static final String PHONE_NUMBER_REG = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
	/** 性别正则表达式 */
	public static final String SEX_REG = "男|女";
	/** 性别正则表达式, 匹配1(男)和0(女) */
	public static final String SEX_CODE_REG = "1|0";
	/** 非负数正则表达式 */
	public static final String NONNEGATIVE_REG = "^\\d+(\\.{0,1}\\d+){0,1}$";
	/** 标识符正则表达式 */
	public static final String IDENTIFIER_REG = "^[A-Za-z_$]+[A-Za-z_$\\d]+$";
	/** oracle字段名称正则表达式 */
	public static final String ORACLE_FIELD_NAME_REG = "^[A-Za-z$]+[A-Za-z_$\\d]+$";
	
	
	/** n-m个长度的数字正则表达式 */
	private static final String N_M_NUMBER_REG = "^\\d{%s,%s}$";
	/** n-m个长度的字符正则表达式 */
	private static final String N_M_CHAR_REG = "^[a-zA-Z0-9]{%s,%s}$";
	
	/**
	 * 获取n-m位的字符正则表达式
	 * @param n
	 * @param m
	 * @return
	 */
	public static String getnMCharReg(int n, int m) {
		return String.format(N_M_CHAR_REG, n, m);
	}
	
	/**
	 * 获取n-m位的数字正则表达式
	 * @param n
	 * @param m
	 * @return
	 */
	public static String getnMNumberReg(int n, int m) {
		return String.format(N_M_NUMBER_REG, n, m);
	}

}
