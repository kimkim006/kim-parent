package com.kim.impexp.excel.imp.reader;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.kim.impexp.excel.ExcelVersion;
import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.kim.impexp.excel.imp.executor.SimpleContentSheetReaderExecutor;
import com.kim.impexp.excel.imp.handler.BodyContentReaderHandler;
import com.kim.impexp.excel.imp.handler.BodyReaderHandler;
import com.kim.impexp.excel.imp.handler.HeaderContentReaderHandler;
import com.kim.impexp.excel.imp.model.SheetData;
import com.kim.common.util.CollectionUtil;

/**
 * 单sheet页excel解析工具，只支持单个sheet页的解析
 * @date 2016年12月1日
 * @author liubo04
 */
public class SimpleExcelReader extends MultipleExcelReader{
    
    public SimpleExcelReader(File file, String[] mappings) {
		this(file, mappings, (Class<?>)null);
	}
    
    public SimpleExcelReader(File file, String[] mappings, Class<?> clazz) {
    	this(file, new BodyContentReaderHandler<>(new HeaderContentReaderHandler(mappings), clazz));
    }
    
    public SimpleExcelReader(File file, BodyReaderHandler<?> bodyReaderHandler) {
    	this(file, new SimpleContentSheetReaderExecutor<>(bodyReaderHandler.getHeaderReaderHandler(), bodyReaderHandler));
    }
    
    public SimpleExcelReader(File file, SheetReaderExecutor sheetExecutor) {
    	super(file, new SheetReaderExecutor[]{sheetExecutor});
    }
    
    public SimpleExcelReader(String pathname, SheetReaderExecutor sheetExecutor) {
    	super(pathname, new SheetReaderExecutor[]{sheetExecutor});
    }
    
    public SimpleExcelReader(InputStream inputStream, ExcelVersion version, SheetReaderExecutor sheetExecutor) {
    	super(inputStream, version, new SheetReaderExecutor[]{sheetExecutor});
    }
    
    /**
     * 读取单个sheet页的Excel文件
     * @return
     * @author liubo04
     */
    public SheetData readSingleSheet(){
		List<SheetData> list = read();
		if(CollectionUtil.isEmpty(list)){
			logger.error("解析出现异常,未解析出数据");
			return null;
		}
		return list.get(0);
	}
    
    @Override
    public SimpleExcelReader setRowCacheSize(int rowCacheSize) {
		this.rowCacheSize = rowCacheSize;
		return this;
	}

    @Override
	public SimpleExcelReader setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
		return this;
	}
    

}
