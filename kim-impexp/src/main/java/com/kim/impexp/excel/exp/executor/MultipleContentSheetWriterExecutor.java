package com.kim.impexp.excel.exp.executor;

import com.kim.impexp.excel.DefaultValue;
import com.kim.impexp.excel.exp.ContentWriterHandler;
import com.kim.impexp.excel.exp.SheetWriterExecutor;
import com.kim.impexp.excel.interceptor.ExcelWriterInterceptor;
import com.kim.common.util.StringUtil;

/**
 * 多内容 写入sheet页的具体内容处理器，支持多内容
 * @author bo.liu01
 *
 */
public class MultipleContentSheetWriterExecutor implements SheetWriterExecutor {

	protected String sheetName;
	protected ExcelWriterInterceptor interceptor;
	protected ContentWriterHandler[] handlers;
	
	public MultipleContentSheetWriterExecutor(String sheetName, ContentWriterHandler[] handlers) {
		this.sheetName = StringUtil.isBlank(sheetName)? DefaultValue.DEFAULT_SHEET_NAME : sheetName;
		this.handlers = handlers;
	}
	
	@Override
	public String getSheetName() {
		return this.sheetName;
	}

	@Override
	public ExcelWriterInterceptor getInterceptor() {
		return this.interceptor;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setInterceptor(ExcelWriterInterceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	@Override
	public ContentWriterHandler[] getHandlers() {
		return handlers;
	}
	
	public void setHandlers(ContentWriterHandler[] handlers) {
		this.handlers = handlers;
	}

}
