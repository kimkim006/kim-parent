package com.kim.impexp.excel.imp.reader;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.kim.impexp.excel.ExcelVersion;
import com.kim.impexp.excel.imp.ExcelReader;
import com.kim.impexp.excel.imp.model.SheetData;
import com.kim.impexp.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.exception.BusinessException;
import com.kim.impexp.excel.imp.ContentReaderHandler;
import com.kim.impexp.excel.imp.SheetReaderExecutor;
import com.monitorjbl.xlsx.StreamingReader;

/**
 * @author bo.liu01
 *
 */
public abstract class AbstractExcelReader implements ExcelReader {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 解析小文件的标准：小于2M的xlsx文件 */
	protected final static long SMALL_FILE_SIZE = 2048L * 1024;
	
	protected int rowCacheSize;
	protected int bufferSize;
	
	protected InputStream inputStream;
	protected ExcelVersion version;
	protected SheetReaderExecutor[] sheetExecutors;
	protected Workbook workbook;

    public AbstractExcelReader(InputStream inputStream, ExcelVersion version, SheetReaderExecutor[] sheetExecutors) { 
    	super();
 		this.inputStream = inputStream;
 		this.version = version;
 		this.sheetExecutors = sheetExecutors;
    }

	public AbstractExcelReader(File file, SheetReaderExecutor[] sheetExecutors) { 
    	super();
    	if (file == null || !file.exists()) {
            throw new BusinessException("文件为空或文件不存在");
        }
        if(!ExcelUtil.isSupport(file.getAbsolutePath())){
        	throw new BusinessException("不支持的文件类型");
        }
        this.sheetExecutors = sheetExecutors;
        this.version = ExcelUtil.getExcelVersion(file.getAbsolutePath()); 
 		try {
			this.inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("文件不存在", e);
			throw new BusinessException("文件不存在");
		}
    }
	
	public AbstractExcelReader(String pathname, SheetReaderExecutor[] sheetExecutors) {
		this(new File(pathname), sheetExecutors);
	}
    
	/**
	 * 校验必须的参数是否符合要求
	 */
	protected void checkParam() {
		String tip = getCheckTip();
		if (tip != null) {
			logger.error(tip);
			throw new RuntimeException(tip);
		}
	}
	
	protected String getCheckTip() {
		if(version == null){
			return "未指定Excel版本";
		}
		try {
			if(inputStream == null || inputStream.available() == 0){
				return "未指定文件或文件已不可读";
			}
		} catch (IOException e) {
			logger.error("文件不可读", e);
			close(inputStream);
			return "未指定文件或文件已不可读";
		} 
		if(sheetExecutors == null || sheetExecutors.length == 0){
			return "未设置SheetReaderExecutor执行器";
		}
		return null;
	}
	
    @Override
	public List<SheetData> read() {
    	
    	try {
    		//检查必须的参数
    		checkParam();
    		workbook = getWorkbook(inputStream, version);
 			return readWorkbook();
 		} catch (BusinessException e) {
 			throw e;
 		}catch (Exception e) {
 			logger.error(e.getMessage(), e);
 			throw new BusinessException("解析excel失败");
 		} finally {
 			close(inputStream);
 		}
	}

	protected Workbook getWorkbook(InputStream in, ExcelVersion version) throws IOException {
		if(ExcelVersion.V_2007_2010.equals(version)){
			if(in.available() > SMALL_FILE_SIZE){
				for (SheetReaderExecutor executor : sheetExecutors) {
					executor.activeMaxRowCon(false);
				}
				return StreamingReader.builder()
						// 缓存到内存中的行数，默认是10
						.rowCacheSize(rowCacheSize < 1?1000:rowCacheSize) 
						// 读取资源时，缓存到内存的字节大小，默认是1024
						.bufferSize(bufferSize < 1?1024:bufferSize) 
						// 打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
						.open(in); 
			}else{
				return new XSSFWorkbook(in);
			}
		}else{
			return new HSSFWorkbook(in);
		}
	}
	
	
    /**
     * 具体的读操作
     * @param workbook Excel文档
     * @return
     * @throws Exception
     */
    protected abstract List<SheetData> readWorkbook() throws Exception;
    
    /**
     * 读取单个Sheet页内容
     * @param workbook 
     * @param sheet
     * @param sheetExecutor
     * @return
     * @throws Exception
     */
    protected SheetData readSheet(Workbook workbook, Sheet sheet, SheetReaderExecutor sheetExecutor) throws Exception {
    	
    	sheetExecutor.setWorkbook(workbook);
    	sheetExecutor.setSheet(sheet);
        //判断数量限制
    	if(sheetExecutor.getMaxRowCon() && sheet.getLastRowNum() > sheetExecutor.getMaxRow()){
        	logger.error("该sheet页一次导入数据超过最大限制，maxRow:{}, sheet:{}, row:{}", new Object[]{
        			sheetExecutor.getMaxRow(), sheet.getSheetName(), sheet.getLastRowNum()});
        	throw new BusinessException("每个sheet页一次导入数据最多"+sheetExecutor.getMaxRow()+"条，sheet["+sheet.getSheetName()+"]已超限制");
        }
        
        //读取数据前调用拦截器
    	if(sheetExecutor.getInterceptor() != null){
    		if(!sheetExecutor.getInterceptor().beforeRead(workbook, sheet, sheetExecutor)){
    			logger.info("读取Excel sheet[{}]之前已中断！", sheet.getSheetName());
    			return null;
    		}
    	}
    	
    	//开始读取数据
        SheetData sheetData = readData(sheet, sheetExecutor);
        logger.info("已读取sheet[{}]数据{}行!", sheet.getSheetName(), sheetData.getRowNum());
		
    	//读取完成后调用拦截器
    	if(sheetExecutor.getInterceptor() != null){
    		if(!sheetExecutor.getInterceptor().complete(workbook, sheet, sheetExecutor, sheetData)){
    			logger.info("读取Excel sheet[{}]完成时已中断！", sheet.getSheetName());
    			return null;
    		}
    	}
		
		return sheetData;
	}

    protected SheetData readData(Sheet sheet, SheetReaderExecutor sheetExecutor) throws Exception {
    	
    	ContentReaderHandler[] readerHandlers = sheetExecutor.getReaderHandlers();
    	SheetData sheetData = new SheetData(sheet.getSheetName(), readerHandlers.length);
    	
    	int handlerIndex = 0;
    	ContentReaderHandler handler = readerHandlers[handlerIndex];
    	handler.ready(sheet, sheetExecutor);
    	int cursor = 0;
    	int contentEnd = handler.getStartRow() + handler.getRangeNum()-1;
    	for (Row row : sheet) {
    		if (row == null) {
            	logger.error("行数据为空，sheet[{}]页第{}行", sheet.getSheetName(), cursor);
            	throw new RuntimeException("解析行数据异常");
            }
    		if(handler.getRangeNum() > 0){
    			if(cursor > contentEnd){
    				//将旧的结束
    				sheetData.add(handler.complete(sheet, sheetExecutor));
    				//重新进行下一个，并开始
        			handler = readerHandlers[++handlerIndex];
        			handler.ready(sheet, sheetExecutor);
        			contentEnd = handler.getStartRow() + handler.getRangeNum();
        		}
    		}
    		handler.read(sheet, sheetExecutor, row, cursor);
    		cursor ++;
		}
    	sheetData.add(handler.complete(sheet, sheetExecutor));
    	
//    	for (ContentReaderHandler readerHandler : readerHandlers) {
//			sheetData.add(readerHandler.read(sheet, sheetExecutor));
//		}
		return sheetData;
	}
    
    protected void close(Closeable c){
    	if(c != null){
			try {
				c.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
    }
    
    public ExcelVersion getVersion() {
		return version;
	}

	public void setVersion(ExcelVersion version) {
		this.version = version;
	}

	public SheetReaderExecutor[] getSheetExecutors() {
		return sheetExecutors;
	}

	public void setSheetExecutors(SheetReaderExecutor[] sheetExecutors) {
		this.sheetExecutors = sheetExecutors;
	}

	public int getRowCacheSize() {
		return rowCacheSize;
	}

	public AbstractExcelReader setRowCacheSize(int rowCacheSize) {
		this.rowCacheSize = rowCacheSize;
		return this;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public AbstractExcelReader setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
		return this;
	}
	
	

}
