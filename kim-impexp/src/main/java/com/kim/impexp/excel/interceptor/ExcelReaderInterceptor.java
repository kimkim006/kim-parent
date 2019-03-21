package com.kim.impexp.excel.interceptor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.kim.impexp.excel.imp.model.SheetData;

/**
 * @author bo.liu01
 *
 */
public interface ExcelReaderInterceptor {
	
	boolean beforeRead(Workbook workbook, Sheet sheet, SheetReaderExecutor sheetExecutor);
	
	boolean complete(Workbook workbook, Sheet sheet, SheetReaderExecutor sheetExecutor, SheetData sheetData);

}
