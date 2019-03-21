package com.kim.impexp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kim.common.util.StringUtil;
import com.kim.impexp.excel.DefaultValue;
import com.kim.impexp.excel.ExcelVersion;

public class ExcelUtil {
	
	/** 获取文件后缀名 */
	public final static Pattern PATTERN_FIND_PREFIX = Pattern.compile("\\.(\\w+)$");
	
	/**
	 * 根据文件后缀获取Excel版本
	 * @param fileName
	 * @return
	 */
	public static ExcelVersion getExcelVersion(String fileName) {
		String prefix = getFilePrefix(fileName);
		if(prefix == null){
			return null;
		}
		return DefaultValue.SUPPORT_VERSION_MAP.get(prefix);
	}
	
	/**
	 * 获取文件的后缀名
	 * @return
	 */
	public static String getFilePrefix(String fileName) {
		if(StringUtil.isBlank(fileName)){
			return null;
		}
		Matcher matcher = PATTERN_FIND_PREFIX.matcher(fileName.toLowerCase());
		if(matcher.find()){
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 根据文件后缀判断本工具是否支持导出或者解析
	 * @param fileName
	 * @return true支持，false不支持
	 */
	public static boolean isSupport(String fileName) {
		String prefix = getFilePrefix(fileName);
		if(prefix == null){
			return false;
		}
		return DefaultValue.SUPPORT_VERSION_MAP.containsKey(prefix);
	}

}
