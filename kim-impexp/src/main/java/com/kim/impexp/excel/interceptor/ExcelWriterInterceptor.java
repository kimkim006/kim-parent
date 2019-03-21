package com.kim.impexp.excel.interceptor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kim.impexp.excel.exp.SheetWriterExecutor;

public interface ExcelWriterInterceptor{
	
	boolean beforeWrite(Workbook workbook, Sheet sheet, SheetWriterExecutor sheetHandler);
	
	boolean complete(Workbook workbook, Sheet sheet, SheetWriterExecutor sheetHandler);

}
