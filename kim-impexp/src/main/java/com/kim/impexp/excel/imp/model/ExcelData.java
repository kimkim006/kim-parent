package com.kim.impexp.excel.imp.model;

import com.kim.common.base.BaseObject;

public class ExcelData extends BaseObject{
	
	private static final long serialVersionUID = 8943143723290104526L;
	
	/** 从0开始，该对象数据的序号 */
	protected int index;
	/** 获取一共读取的行数 */
	protected int rowNum;
	/** 添加读取的行数 */
	protected int startRow;
	
	public int getIndex() {
		return index;
	}

	public ExcelData setIndex(int index) {
		this.index = index;
		return this;
	}

	public int addRowNum(int num){
		return this.rowNum += num;
	}
	
	public int getRowNum(){
		return this.rowNum;
	}
	
	public ExcelData setRowNum(int rowNum) {
		this.rowNum = rowNum;
		return this;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public ExcelData setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}

}
