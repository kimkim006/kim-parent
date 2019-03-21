package com.kim.log.handler;


import com.kim.log.operatelog.OperationType;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldConvert {
	
	/**
	 * 将字段的值转成指定的显示名字，例如：1=>月租A, 2=>月租B, ...
	 * @param o 数据对象
	 * @param field
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	String convertValue(Object o, Field field);
	
	/**
	 * 将字段的名称转成指定的显示名字
	 * @param o 数据对象
	 * @param field
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	String convert(Object o, Field field);
	
	/**
	 * 获取要处理的字段
	 * @param o 数据对象
	 * @param fields
	 * @return
	 * @date 2017年4月11日
	 * @author liubo04
	 */
	List<Field> getFields(Object o, String[] fields, OperationType operationType);

	/**
	 * 获取要作为比较的字段
	 * @param o 数据对象
	 * @param fields
	 * @return
	 * @date 2017年4月11日
	 * @author liubo04
	 */
	List<Field> getCompareFields(Object o, String[] fields, OperationType operationType);
	
}
