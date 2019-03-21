package com.kim.impexp.excel.exp.writer;

import org.apache.poi.ss.usermodel.Workbook;

import com.kim.impexp.excel.exp.ContentWriterHandler;
import com.kim.impexp.excel.exp.SheetWriterExecutor;
import com.kim.impexp.excel.exp.executor.MultipleContentSheetWriterExecutor;
import com.kim.impexp.excel.exp.handler.BodyContentWriterHandler;
import com.kim.impexp.excel.exp.handler.HeaderContentWriterHandler;

/**
 * 通用excel下载工具，支持偶sheet页，汇总表头，多表头，合并单元格，自定义样式等
 * @date 2016年11月15日
 * @author liubo04
 */
public class MultipleSheetWriter extends AbstractExcelWriter {
	
	private SheetWriterExecutor[] sheetExecutors;
	
	public static void main(String[] args) {
		ContentWriterHandler[] handlers = {
			new HeaderContentWriterHandler(new String[][]{{"姓名", "电话", "备注"}}),
			new BodyContentWriterHandler(new String[10][3])
		};
		SheetWriterExecutor[] sheetHandlers = {
				new MultipleContentSheetWriterExecutor("123456", handlers),
				new MultipleContentSheetWriterExecutor("333333", handlers)
		};
		new MultipleSheetWriter("file0.xlsx", sheetHandlers).create();
	}
	
	/**
	 * @param fileName 带后缀的文件名称，不包含文件路径
	 * @param sheetHandler 写处理器
	 */
	public MultipleSheetWriter(String fileName, SheetWriterExecutor[] sheetExecutors) {
		super(fileName);
		if(sheetExecutors == null || sheetExecutors.length == 0){
			throw new RuntimeException("未指定SheetWriterExecutor");
		}
		this.setSheetExecutors(sheetExecutors);
	}
	
	@Override
	public MultipleSheetWriter create(){
		
		Workbook workbook = getWorkbook();
		int sheetNum = 0;
		for (SheetWriterExecutor sheetExecutor : sheetExecutors) {
			if(createSheet(workbook, sheetExecutor) != null){
				sheetNum ++ ;
			}
		}
		if(sheetNum == 0){
			logger.warn("所有sheet都已写入中断!");
			return this;
		}
		
		logger.info("成功写入 {}个sheet", sheetNum);
		createFile(workbook, filePath, fileName);
		logger.info("成功写入文件[{}]", fileName);
		return this;
	}

	public SheetWriterExecutor[] getSheetExecutors() {
		return sheetExecutors;
	}

	public void setSheetExecutors(SheetWriterExecutor[] sheetExecutors) {
		this.sheetExecutors = sheetExecutors;
	}
	
}
