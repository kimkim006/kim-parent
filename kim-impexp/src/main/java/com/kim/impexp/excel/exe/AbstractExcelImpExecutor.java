package com.kim.impexp.excel.exe;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.impexp.excel.exp.executor.SimpleContentSheetWriterExecutor;
import com.kim.impexp.excel.exp.handler.AbstractObjectContentWriterHandler;
import com.kim.impexp.excel.imp.handler.BodyReaderHandler;
import com.kim.impexp.excel.imp.model.ContentData;
import com.kim.impexp.excel.imp.model.SheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.exception.BusinessException;
import com.kim.impexp.excel.exp.ContentWriterHandler;
import com.kim.impexp.excel.exp.handler.ObjectWriterHandler;
import com.kim.impexp.excel.exp.writer.SimpleSheetWriter;
import com.kim.impexp.excel.imp.executor.SimpleContentSheetReaderExecutor;
import com.kim.impexp.excel.imp.handler.BodyContentReaderHandler;
import com.kim.impexp.excel.imp.handler.HeaderContentReaderHandler;
import com.kim.impexp.excel.imp.handler.HeaderReaderHandler;
import com.kim.impexp.excel.imp.reader.SimpleExcelReader;
import com.kim.impexp.excel.interceptor.ImportInterceptor;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.StringUtil;


/**
 * 写点啥吧
 * @date 2017年4月7日
 * @author liubo04
 */
public abstract class AbstractExcelImpExecutor<T extends Object> implements ExcelImpExecutor<T>{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Class<T> clazz;
	protected String[] headerMapping;
	protected String writeFilePath;
	protected String readFileName;
	
	protected BodyReaderHandler<T> bodyReaderHandler;
	protected HeaderReaderHandler headerReaderHandler;
	protected ContentWriterHandler headerWriterHandler;
	protected ObjectWriterHandler<T> bodyWriterHandler;
	
	protected SimpleContentSheetReaderExecutor<T> sheetReaderExecutor;
	protected SimpleContentSheetWriterExecutor<T> sheetWriterExecutor;
	protected ImportInterceptor<T> interceptor;
	
	protected int rowCacheSize;
	protected int bufferSize;
	
	public static final int DEFAULT_ROW_CACHE_SIZE = 1000;
	public static final int DEFAULT_BUFFER_SIZE = 4096;
	
	protected List<T> dataList;
	
	public AbstractExcelImpExecutor(String[] headerMapping, Class<T> clazz){
		this.clazz = clazz;
		this.headerMapping = headerMapping;
	}
	
	public AbstractExcelImpExecutor(String[] headerMapping){
		this.headerMapping = headerMapping;
	}
	
	/**
	 * 读取Excel数据
	 * @param path
	 * @return
	 */
	public List<T> readData(String path) {
		if(StringUtil.isBlank(path)){
			logger.error("导入文件名不能为空");
			throw new BusinessException("导入文件名不能为空");
		}
		if(!path.endsWith(EXCEL_2003_SUFFIX) && !path.endsWith(EXCEL_2007_$_SUFFIX)){
			logger.error("导入文件必须是Excel文件，path:{}", path);
			throw new BusinessException("导入文件必须是Excel文件");
		}
		
		this.readFileName = path;
		SheetData sheetData = new SimpleExcelReader(path, getSheetReaderExecutor())
				.setBufferSize(bufferSize()).setRowCacheSize(rowCacheSize()).readSingleSheet();
		ContentData contentData;
		if(sheetData == null || sheetData.getDataSize() == 0 || (contentData = sheetData.get(1)) == null){
			return null;
		}
        return dataList = contentData.getList();
	}
	
	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public String getWriteFilePath() {
		return writeFilePath;
	}

	public void setWriteFilePath(String writeFilePath) {
		this.writeFilePath = writeFilePath;
	}

	public String[] getHeaderMapping() {
		return headerMapping;
	}

	public void setHeaderMapping(String[] headerMapping) {
		this.headerMapping = headerMapping;
	}

	public BodyReaderHandler<T> getBodyReaderHandler() {
		//设置表格体的读Handler
		if(bodyReaderHandler == null){
			bodyReaderHandler = new BodyContentReaderHandler<>(getHeaderReaderHandler());
		}
		if(clazz != null){
			bodyReaderHandler.setClazz(clazz);
		}
		return bodyReaderHandler;
	}

	public void setBodyReaderHandler(BodyReaderHandler<T> bodyReaderHandler) {
		this.bodyReaderHandler = bodyReaderHandler;
	}

	public HeaderReaderHandler getHeaderReaderHandler() {
		//设置表头的读Handler
		if(headerReaderHandler == null){
			headerReaderHandler = new HeaderContentReaderHandler();
		}
		if(getHeaderMapping() != null && getHeaderMapping().length > 0){
			headerReaderHandler.setHeaderMapping(getHeaderMapping());
		}
		return headerReaderHandler;
	}

	public void setHeaderReaderHandler(HeaderReaderHandler headerReaderHandler) {
		this.headerReaderHandler = headerReaderHandler;
	}

	public ContentWriterHandler getHeaderWriterHandler() {
		return headerWriterHandler;
	}

	public void setHeaderWriterHandler(ContentWriterHandler headerWriterHandler) {
		this.headerWriterHandler = headerWriterHandler;
	}

	public ObjectWriterHandler<T> getBodyWriterHandler(List<T> list, String[] fields) {
		ObjectWriterHandler<T> hd = getUserBodyWriterHandler(list, fields);
		
		return hd!=null ? hd : getDefaultBodyWriterHandler(list, fields);
	}
	
	public ObjectWriterHandler<T> getUserBodyWriterHandler(List<T> list, String[] fields) {
		return null;
	}
	
	public ObjectWriterHandler<T> getDefaultBodyWriterHandler(List<T> list, final String[] fields) {
		if(bodyWriterHandler == null){
			bodyWriterHandler = new AbstractObjectContentWriterHandler<T>(list) {
				
				@Override
				public Object[] java2ObjectArr(T t) {
					try {
						Object[] r = new Object[fields.length];
						for (int j = 0; j < fields.length; j++) {
							r[j] = BeanUtil.getValueByName(t, fields[j]);
						}
						return r;
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new RuntimeException("导出结果失败");
					}
				}
			};
		}
		return bodyWriterHandler;
	}
	
	public ObjectWriterHandler<T> getBodyWriterHandler() {
		return bodyWriterHandler;
	}

	public void setBodyWriterHandler(ObjectWriterHandler<T> bodyWriterHandler) {
		this.bodyWriterHandler = bodyWriterHandler;
	}

	public void setSheetReaderExecutor(SimpleContentSheetReaderExecutor<T> sheetReaderExecutor) {
		this.sheetReaderExecutor = sheetReaderExecutor;
	}
	
	public SimpleContentSheetReaderExecutor<T> getSheetReaderExecutor() {
		if(sheetReaderExecutor != null){
			return sheetReaderExecutor;
		}
		
		//检查必填参数
		checkParam();
		
		//设置单sheet页处理器
		sheetReaderExecutor = new SimpleContentSheetReaderExecutor<>(getHeaderReaderHandler(), getBodyReaderHandler());
		return sheetReaderExecutor;
	}

	protected void checkParam() {
		if(getHeaderReaderHandler().getHeaderMapping() == null || getHeaderReaderHandler().getHeaderMapping().size() == 0){
			logger.error("ExcelImpExecutor未设置headerMapping");
			throw new RuntimeException("ExcelImpExecutor未设置headerMapping");
		}
		if(getBodyReaderHandler().getClazz() == null){
			logger.error("ExcelImpExecutor未设置clazz");
			throw new RuntimeException("ExcelImpExecutor未设置clazz");
		}
	}

	public void setSheetWriterExecutor(SimpleContentSheetWriterExecutor<T> sheetWriterExecutor) {
		this.sheetWriterExecutor = sheetWriterExecutor;
	}
	
	public SimpleContentSheetWriterExecutor<T> getSheetWriterExecutor(List<T> list) {
		if(sheetWriterExecutor != null){
			return sheetWriterExecutor;
		}
		
		//回写导入结果
		Map<String, String> headerMap = new LinkedHashMap<>(headerReaderHandler.getHeaderMapping());
        headerMap.put("导入结果", "importRes");
        String[] headers = new String[headerMap.size()];
        final String[] fields = new String[headerMap.size()];
        int i = 0;
        for (Entry<String, String> entry : headerMap.entrySet()) {
        	headers[i] = entry.getKey();
        	fields[i++] = entry.getValue();
		}
        headerMap.keySet().toArray(headers);
		
		return sheetWriterExecutor = new SimpleContentSheetWriterExecutor<T>("导入结果", headers, getBodyWriterHandler(list, fields));
	}
	
	public String writeData(List<T> list) {
		
		//处理文件位置
		String filePath = writeFilePath;
		String fileName = readFileName;
		
		int index = fileName.lastIndexOf(File.separator);
		if(index > -1){
			if(StringUtil.isBlank(filePath)){
				filePath = fileName.substring(0, index);
			}
			fileName = fileName.substring(index + 1);
		}
		
		fileName = dealResultName(fileName);
		SimpleSheetWriter ew = new SimpleSheetWriter(fileName, getSheetWriterExecutor(list));
		if(filePath != null){
			ew.setFilePath(filePath);
		}
		return ew.create().getAbsolutePath();
		
	}

	private static String dealResultName(String fileName){
		return fileName.endsWith(EXCEL_2003_SUFFIX) ? fileName.replace(EXCEL_2003_SUFFIX, "_result.xls") :(
				fileName.endsWith(EXCEL_2007_$_SUFFIX) ? fileName.replace(EXCEL_2007_$_SUFFIX, "_result.xlsx") : fileName + "_result.xls"
			);
	}

	@Override
	public ImportInterceptor<T> getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(ImportInterceptor<T> interceptor) {
		this.interceptor = interceptor;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getRowCacheSize() {
		return rowCacheSize;
	}
	
	public int rowCacheSize() {
		if(rowCacheSize == 0){
			return DEFAULT_ROW_CACHE_SIZE;
		}
		return rowCacheSize;
	}

	public void setRowCacheSize(int rowCacheSize) {
		this.rowCacheSize = rowCacheSize;
	}

	public int getBufferSize() {
		return bufferSize;
	}
	
	public int bufferSize() {
		if(bufferSize == 0){
			return DEFAULT_BUFFER_SIZE;
		}
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
}
