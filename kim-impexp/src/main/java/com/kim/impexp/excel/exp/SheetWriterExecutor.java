package com.kim.impexp.excel.exp;

import com.kim.impexp.excel.interceptor.ExcelWriterInterceptor;

/**
 * sheet 页面内容处理器抽象
 * @author bo.liu01
 *
 */
public interface SheetWriterExecutor {
	
	/**
	 * 返回sheet的名称
	 * @return
	 */
	public String getSheetName();
	
	/**
	 * 返回该sheet页面的所有的写处理器
	 * @return
	 */
	public ContentWriterHandler[] getHandlers();
	
	/**
	 * 返回该sheet页面的拦截器
	 * @return
	 */
	public ExcelWriterInterceptor getInterceptor();

}
