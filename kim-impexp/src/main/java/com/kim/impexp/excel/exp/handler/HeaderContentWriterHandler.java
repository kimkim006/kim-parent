package com.kim.impexp.excel.exp.handler;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * 表头模式的excel写处理器
 * @date 2016年12月1日
 * @author liubo04
 */
public class HeaderContentWriterHandler extends BodyContentWriterHandler {
	
	public HeaderContentWriterHandler(Object[][] dataList) {
		super(dataList);
	}
	
	/** 
	 * 设置表头样式
	 * @see BodyContentWriterHandler#setStyle(org.apache.poi.ss.usermodel.Workbook, org.apache.poi.ss.usermodel.CellStyle)
	 */
	@Override
	public void setStyle(Workbook workbook, CellStyle celltyle){
		
		// 设置前景颜色蓝色
		if (workbook instanceof HSSFWorkbook) {
			celltyle.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
			HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
			palette.setColorAtIndex(HSSFColor.CORNFLOWER_BLUE.index, (byte) 189, (byte) 215, (byte) 238);
		} else {
			XSSFCellStyle xSSFCellStyle = (XSSFCellStyle) celltyle;
			xSSFCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(189, 215, 238)));
		}
		
		// 生成一个字体
		Font font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		celltyle.setFont(font);
		//自动换行
		celltyle.setWrapText(true);
		setBorderThin(celltyle);
	}
	
}
