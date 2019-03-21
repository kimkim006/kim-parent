package com.kim.impexp.excel.imp;

import java.util.Map;

import com.kim.impexp.excel.interceptor.ExcelReaderInterceptor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author bo.liu01
 *
 */
public interface SheetReaderExecutor {
	
	/**
	 * 设置Sheet对象
	 */
	void setSheet(Sheet sheet);
	
	/**
	 * 返回该sheet页面所有的读处理器
	 * @return
	 */
	ContentReaderHandler[] getReaderHandlers();
	
	/**
	 * 返回该sheet页面所有的读处理器Map，key为开始行
	 * @return
	 */
	Map<Integer, ContentReaderHandler> getReaderHandlerMap();
	
	/**
	 * 返回该sheet页面的读拦截器
	 * @return
	 */
	ExcelReaderInterceptor getInterceptor();
	
	/**
	 * 返回允许导入的最大的行数，如果小于1，则不限制
	 * @return
	 */
	int getMaxRow();
	
	/**
	 * 设置允许导入的最大的行数，如果小于1，则不限制
	 * @return
	 */
	void setMaxRow(int maxRow);
	
	/**
	 * 激活最大数量限制开关，默认关闭
	 */
	void activeMaxRowCon(boolean flag);
	
	/**
	 * 获取最大数量限制开关,当Excel文件大小超过100k时，该设置必须为false，否则将导致解析中断
	 */
	boolean getMaxRowCon();

	void setWorkbook(Workbook workbook);
	
}
