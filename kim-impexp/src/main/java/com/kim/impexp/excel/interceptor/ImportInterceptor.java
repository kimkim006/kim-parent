package com.kim.impexp.excel.interceptor;

import java.util.List;

public interface ImportInterceptor<T> {
	
	/**
	 * 在导入之前进行拦截
	 * @param list
	 * @return
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	boolean beforeImport(List<T> list);
	
	/**
	 * 在写导入结果之前的处理操作
	 * @param list
	 * @return
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	boolean beforeWrite(List<T> list);

}
