package com.kim.impexp.excel.imp.reader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.kim.impexp.excel.ExcelVersion;
import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.kim.impexp.excel.imp.model.SheetData;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 通用多sheet页excel解析工具，支持多个sheet页面的解析
 * @date 2016年12月1日
 * @author liubo04
 */
public class MultipleExcelReader extends AbstractExcelReader{
    
    public MultipleExcelReader(File file, SheetReaderExecutor[] sheetExecutors) {
		super(file, sheetExecutors);
	}
    
    public MultipleExcelReader(String pathname, SheetReaderExecutor[] sheetExecutors) {
    	super(pathname, sheetExecutors);
    }
    
    public MultipleExcelReader(InputStream inputStream, ExcelVersion version, SheetReaderExecutor[] sheetExecutors) {
    	super(inputStream, version, sheetExecutors);
    }
    
    @Override
	protected List<SheetData> readWorkbook() throws Exception  {
        Sheet sheet;
        List<SheetData> list = new ArrayList<>();
        int i=0;
        for (;i<sheetExecutors.length;i++) {
        	sheet = workbook.getSheetAt(i);
        	list.add(readSheet(workbook, sheet, sheetExecutors[i]));
		}
        
        logger.info("已读取{}个sheet数据", i);
        return list;
    }
    
    @Override
    public MultipleExcelReader setRowCacheSize(int rowCacheSize) {
		this.rowCacheSize = rowCacheSize;
		return this;
	}

    @Override
	public MultipleExcelReader setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
		return this;
	}

}
