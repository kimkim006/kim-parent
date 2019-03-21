package com.kim.impexp.excel.interceptor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kim.impexp.excel.exp.SheetWriterExecutor;

public class ExcelWriterInterceptorAdaptor implements ExcelWriterInterceptor {

	@Override
	public boolean beforeWrite(Workbook workbook, Sheet sheet, SheetWriterExecutor sheetHandler) {
		return true;
	}

	@Override
	public boolean complete(Workbook workbook, Sheet sheet, SheetWriterExecutor sheetHandler) {
		return true;
	}

}
