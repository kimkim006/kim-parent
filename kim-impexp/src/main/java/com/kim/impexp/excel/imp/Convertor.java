package com.kim.impexp.excel.imp;

import java.util.Map;

/**
 * 读取Excel数据的转换器
 * @author bo.liu01
 *
 * @param <T>
 */
public interface Convertor {
	
	void transfer2Object(Map<String, Object> map);

}
