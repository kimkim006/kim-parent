package com.kim.impexp.excel.exe;

import java.util.List;

import com.kim.impexp.excel.interceptor.ImportInterceptor;

public interface ExcelImpExecutor<T extends Object> {
	
	public final static String EXCEL_2003_SUFFIX = ".xls";
	public final static String EXCEL_2007_$_SUFFIX = ".xlsx";
	
	
	/**
	 * 返回导入执行的拦截器
	 * @return 拦截器
	 */
	ImportInterceptor<T> getInterceptor();

	/**
	 * 执行导入操作
	 * @param addList 要导入的数据list
	 * @return 导入后的数据list，跟原始的一样
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	List<T> execute(List<T> addList);

}
