package com.kim.impexp.excel.exp.writer;

import com.kim.impexp.excel.exp.SheetWriterExecutor;
import com.kim.impexp.excel.exp.executor.SimpleContentSheetWriterExecutor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kim.impexp.excel.exp.ContentWriterHandler;
import com.kim.impexp.excel.exp.executor.MultipleContentSheetWriterExecutor;
import com.kim.impexp.excel.exp.handler.BodyContentWriterHandler;
import com.kim.impexp.excel.exp.handler.HeaderContentWriterHandler;

/**
 * 单页sheet的excel下载工具
 * @date 2016年11月15日
 * @author liubo04
 */
public class SimpleSheetWriter extends AbstractExcelWriter{
	
	private SheetWriterExecutor sheetExecutor;
	
	public static void main(String[] args) {
		String[] header = new String[]{"姓名", "电话", "备注"};
		ContentWriterHandler[] handlers = {
			new HeaderContentWriterHandler(new String[][]{{"姓名", "电话", "备注"}}),
			new BodyContentWriterHandler(new String[][]{{"1112","4444", "555555"}}),
			new BodyContentWriterHandler(new String[10][3]),
		};
		new SimpleSheetWriter("file1.xlsx", new MultipleContentSheetWriterExecutor("sheet34", handlers)).create();
		
		SheetWriterExecutor executor = new SimpleContentSheetWriterExecutor<>("sheet23",
				header, new Object[10][3]);
		new SimpleSheetWriter("file2.xlsx", executor).create();
		
	}
	
	/**
	 * @param fileName 带后缀的文件名称，不包含文件路径
	 * @param sheetExecutor 写处理器
	 */
	public SimpleSheetWriter(String fileName, SheetWriterExecutor sheetExecutor) {
		super(fileName);
		if(sheetExecutor == null){
			throw new RuntimeException("未指定SheetWriterHandler");
		}
		this.fileName = fileName;
		this.setSheetHandler(sheetExecutor);
	}
	
	@Override
	public SimpleSheetWriter create(){
		
		Workbook workbook = getWorkbook();
		Sheet sheet = createSheet(workbook, sheetExecutor);
		if(sheet == null){
			logger.warn("sheet[{}]写入已中断, 导出操作停止!", sheetExecutor.getSheetName());
			return this;
		}
		
		logger.info("成功写入sheet[{}]", sheetExecutor.getSheetName());
		createFile(workbook, filePath, fileName);
		logger.info("成功写入文件[{}]", fileName);
		return this;
	}

	public SheetWriterExecutor getSheetHandler() {
		return sheetExecutor;
	}

	public void setSheetHandler(SheetWriterExecutor sheetHandler) {
		this.sheetExecutor = sheetHandler;
	}
	
	@Override
	public SimpleSheetWriter setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
}
