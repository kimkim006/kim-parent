package com.kim.impexp.excel.exp.handler;

import java.util.Calendar;
import java.util.Date;

import com.kim.impexp.excel.exp.SheetWriterExecutor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.impexp.excel.exp.ContentWriterHandler;

/**
 * 内容模式的excel写处理器
 * @date 2016年12月1日
 * @author liubo04
 */
public class BodyContentWriterHandler implements ContentWriterHandler{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 要写的数据 */
	protected Object[][] dataList;
	/** 开始读取数据的行 */
	protected int startRow;
	
	public BodyContentWriterHandler(Object[][] dataList) {
		super();
		this.dataList = dataList;
	}
	
	public BodyContentWriterHandler(){
		super();
	}
	
	@Override
	public int write(Sheet sheet, SheetWriterExecutor sheetExecutor){
		Workbook workbook = sheet.getWorkbook();
		CellStyle cellStyle = workbook.createCellStyle();
		setStyle(workbook, cellStyle);
		//一定要执行此步骤
		int length = getRowCount();
		Cell cell = null;
		Row row = null;
		Object[] rowData = null;
		for (int i=0;i<length; i++) {
			row = sheet.createRow(startRow + i);
			rowData = dataList[i];
			for(int j=0;j<rowData.length; j++){
				cell = row.createCell(j);
				setCellValue(cell, rowData[j]);
				cell.setCellStyle(cellStyle);
			}
		}
		return length;
	}
	
	protected void setCellValue(Cell cell, Object obj){
		if(obj == null){
			cell.setCellValue("");
		}else if(obj instanceof Integer){
			cell.setCellValue(((Integer)obj)/1.00);
		}else if(obj instanceof Float){
			cell.setCellValue(((Float)obj)/1.00);
		}else if(obj instanceof Double){
			cell.setCellValue((Double)obj);
		}else if(obj instanceof Calendar){
			cell.setCellValue((Calendar)obj);
		}else if(obj instanceof Date){
			cell.setCellValue((Date)obj);
		}else{
			cell.setCellValue(String.valueOf(obj));
		}
	}
	
	protected void setStyle(Workbook workbook, CellStyle celltyle){
		
		// 设置前景颜色白色
		celltyle.setFillForegroundColor(HSSFColor.WHITE.index); 
		// 生成字体4
		Font font4 = workbook.createFont();
		font4.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font4.setColor(HSSFColor.BLACK.index);
		// 把字体应用到当前的样式
		celltyle.setFont(font4);
		//自动换行
		celltyle.setWrapText(true);
		setBorderThin(celltyle);
	}
	
	/**
	 * 设置边框样式和单元格填充样式
	 * @param style
	 */
	protected void setBorderThin(CellStyle style){
		
		// 设置单元格填充样式
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 四个边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		style.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		// 水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		// 垂直居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	}
	
	/**
	 * 设置数据的有效性限制，下拉列表
	 * @param sheet
	 * @param textList
	 * @param firstRow
	 * @param endRow
	 * @param firstCol
	 * @param endCol
	 * @return
	 * @date 2016年12月23日
	 * @author liubo04
	 */
	protected DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {

        DataValidationHelper helper = sheet.getDataValidationHelper();
        //加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
        constraint.setExplicitListValues(textList);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        //数据有效性对象
        return helper.createValidation(constraint, regions);
    }
	
	public Object[][] getDataList(){
		if(dataList == null){
			dataList = new Object[0][];
		}
		return dataList;		
	}
	
	public void setDataList(Object[][] dataList) {
		this.dataList = dataList;
	}
	
	@Override
	public int getRowCount() {
		return getDataList().length;
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

	@Override
	public BodyContentWriterHandler setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}
	

}
