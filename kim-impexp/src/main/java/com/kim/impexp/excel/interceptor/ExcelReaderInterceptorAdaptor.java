package com.kim.impexp.excel.interceptor;

import com.kim.impexp.excel.imp.model.SheetData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kim.impexp.excel.imp.SheetReaderExecutor;

public class ExcelReaderInterceptorAdaptor implements ExcelReaderInterceptor {

	@Override
	public boolean beforeRead(Workbook workbook, Sheet sheet, SheetReaderExecutor sheetExecutor) {
		return true;
	}

	@Override
	public boolean complete(Workbook workbook, Sheet sheet, SheetReaderExecutor sheetExecutor, SheetData sheetData) {
		return true;
	}

}
