package com.kim.impexp.excel.imp.handler;

import java.util.Map;

import com.kim.impexp.excel.imp.ContentReaderHandler;

/**
 * @author bo.liu01
 *
 */
public interface HeaderReaderHandler extends ContentReaderHandler {
	
	/**
	 * 返回表头和字段的映射关系
	 * @return
	 */
	Map<String, String> getHeaderMapping();

	/**
	 * 返回实际读到的Excel表头
	 * @return
	 */
	String[] getHeaders();

	/**
	 * 设置表头映射关系,例如：Map{名字=>name,年龄=>age}
	 * @param headerMapping
	 * @date 2016年12月1日
	 * @author liubo04
	 */
	void setHeaderMapping(Map<String, String> headerMapping);

	/**
	 * 设置表头映射关系,例如：{"名字=>name", "年龄=>age"}
	 * @param headerMapping
	 * @date 2016年12月1日
	 * @author liubo04
	 */
	Map<String, String> setHeaderMapping(String[] mappings);

}
