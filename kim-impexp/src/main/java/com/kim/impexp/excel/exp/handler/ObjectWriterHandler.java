package com.kim.impexp.excel.exp.handler;

import com.kim.impexp.excel.exp.ContentWriterHandler;

/**
 * java对象内容模式的excel写处理器
 * @date 2016年12月1日
 * @author liubo04
 */
public interface ObjectWriterHandler<T> extends ContentWriterHandler{
	
	/**
	 * 将java对象转换成数组对象，在写入数据是Java对象集合的时候，需要实现此方法
	 * @param t Java对象
	 * @return
	 * @date 2016年12月1日
	 * @author liubo04
	 */
	Object[] java2ObjectArr(T t);
	
	
}
