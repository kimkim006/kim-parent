package com.kim.impexp.excel.exp;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * excel写处理器
 * @date 2016年12月1日
 * @author liubo04
 */
public interface ContentWriterHandler{
	
	/**
	 * 将数据写入sheet
	 * @param sheet Excel的sheet对象
	 * @param sheetExecutor sheet对应的执行器对象
	 * @return 向该sheet中写入数据的行数
	 */
	int write(Sheet sheet, SheetWriterExecutor sheetExecutor);
	
	/**
	 * 设置开始的行号
	 * @param startRow 设置ContentWriterHandler的开始行号
	 * @return this指向的ContentWriterHandler对象
	 */
	ContentWriterHandler setStartRow(int startRow);

	/**
	 * 获取开始的行号
	 * @return 返回开始的行号
	 */
	int getStartRow();
	
	/**
	 * 返回一共要写入的行数
	 * @return 一共要写入数据的行数
	 */
	int getRowCount();

}
