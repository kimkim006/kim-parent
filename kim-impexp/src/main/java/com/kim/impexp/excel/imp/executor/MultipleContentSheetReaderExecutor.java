package com.kim.impexp.excel.imp.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.impexp.excel.DefaultValue;
import com.kim.impexp.excel.imp.ContentReaderHandler;
import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.kim.impexp.excel.interceptor.ExcelReaderInterceptor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 抽象的sheet页面处理器
 * @author bo.liu01
 *
 */
public class MultipleContentSheetReaderExecutor implements SheetReaderExecutor {
	
	protected Workbook workbook;
	protected Sheet sheet;
	
	/** 内容读处理器 */
	protected ContentReaderHandler[] readerHandlers;
	/** 读拦截器 */
	protected ExcelReaderInterceptor interceptor;
	/** 一次导入的最大行数 */
	protected int maxRow = DefaultValue.DEFAULT_IMPORT_MAX_ROW;
	/** 一次导入的最大行数的条件开关，true则maxRow剩下，false不生效 */
	protected boolean maxRowCon = false;
	/** 已读取的行数 */
	protected int rowNum = 0;

	public MultipleContentSheetReaderExecutor(ContentReaderHandler[] readerHandlers) {
		super();
		this.readerHandlers = readerHandlers;
	}

	@Override
	public ExcelReaderInterceptor getInterceptor() {
		return this.interceptor;
	}

	public void setInterceptor(ExcelReaderInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public ContentReaderHandler[] getReaderHandlers() {
		getReaderHandlerMap();
		return readerHandlers;
	}
	
	@Override
	public Map<Integer, ContentReaderHandler> getReaderHandlerMap() {
		if(readerHandlers == null || readerHandlers.length == 0){
			return Collections.emptyMap();
		}
		Map<Integer, ContentReaderHandler> map = new HashMap<>(readerHandlers.length);
		int index = 0;
		int startRow = 0;
		List<ContentReaderHandler> list = new ArrayList<>();
		for (ContentReaderHandler handler : readerHandlers) {
			handler.setIndex(index);
			handler.setStartRow(startRow);
			handler.setSheet(sheet);
			handler.setWorkbook(workbook);
			list.add(handler);
			map.put(startRow, handler);
			if(handler.getRangeNum() < 1){
				break;
			}
			startRow += handler.getRangeNum();
			index ++;
		}
		readerHandlers = new ContentReaderHandler[list.size()];
		readerHandlers = list.toArray(readerHandlers);
		return map;
	}

	public void setReaderHandlers(ContentReaderHandler[] readerHandlers) {
		this.readerHandlers = readerHandlers;
	}

	@Override
	public int getMaxRow() {
		return maxRow;
	}

	@Override
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	
	@Override
	public void activeMaxRowCon(boolean maxRowCon) {
		this.maxRowCon = maxRowCon;
	}
	
	@Override
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	@Override
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	
	@Override
	public boolean getMaxRowCon() {
		return this.maxRowCon;
	}

}
