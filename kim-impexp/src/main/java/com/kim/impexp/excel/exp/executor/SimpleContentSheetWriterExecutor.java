package com.kim.impexp.excel.exp.executor;

import com.kim.impexp.excel.exp.ContentWriterHandler;
import com.kim.impexp.excel.exp.handler.BodyContentWriterHandler;
import com.kim.impexp.excel.exp.handler.HeaderContentWriterHandler;

/**
 * 简单的sheet写入处理器，只支持单页sheet，而且只能有一个表头（可以是多行表头）
 * @author bo.liu01
 *
 * @param <T> Java对象类型
 */
public class SimpleContentSheetWriterExecutor<T> extends MultipleContentSheetWriterExecutor {
	
	public SimpleContentSheetWriterExecutor(String sheetName, String[][] headers, Object[][] dataList) {
		super(sheetName, new ContentWriterHandler[]{
			new HeaderContentWriterHandler(headers),
			new BodyContentWriterHandler(dataList)
		});
	}
	
	public SimpleContentSheetWriterExecutor(String sheetName, String[] headers, Object[][] dataList) {
		this(sheetName, new String[][]{headers}, dataList);
	}
	
	/**
	 * 多表头
	 * @param sheetName 簿名称
	 * @param headers 多行表头
	 * @param bodyContentWriterHandler 内容处理器
	 */
	public SimpleContentSheetWriterExecutor(String sheetName, String[][] headers, ContentWriterHandler objectWriterHandler) {
		super(sheetName, new ContentWriterHandler[]{
			new HeaderContentWriterHandler(headers),
			objectWriterHandler
		});
	}
	
	public SimpleContentSheetWriterExecutor(String sheetName, String[] headers, ContentWriterHandler objectWriterHandler) {
		this(sheetName, new String[][]{headers}, objectWriterHandler);
	}
	
	
}
