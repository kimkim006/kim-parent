package com.kim.impexp.excel.imp.model;

/**
 * @author bo.liu01
 *
 */
public class SheetData extends ExcelData{

	private static final long serialVersionUID = 1L;
	
	private String sheetName;
	private ContentData[] datas;
	
	public SheetData(String sheetName, int size) {
		super();
		this.sheetName = sheetName;
		this.datas = new ContentData[size];
	}
	
	/** Workbook内sheet所在的序号 ,从0开始 */
	@Override
	public int getIndex() {
		return index;
	}

	/** Workbook内sheet所在的序号 ,从0开始 */
	@Override
	public SheetData setIndex(int index) {
		this.index = index;
		return this;
	}
	
	@Override
	public SheetData setRowNum(int rowNum) {
		this.rowNum = rowNum;
		return this;
	}
	
	@Override
	public SheetData setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public ContentData[] getDatas() {
		return datas;
	}
	
	public int getDataSize() {
		if(datas == null){
			return 0;
		}
		return datas.length;
	}
	
	public void setDatas(ContentData[] datas) {
		this.datas = datas;
		for (ContentData data : datas) {
			this.rowNum += data.getRowNum();
		}
	}
	
	public void add(ContentData data) {
		this.datas[data.getIndex()] = data;
		this.rowNum += data.getRowNum();
	}
	
	public ContentData get(int index) {
		if(index < 0 || index >= datas.length){
			throw new RuntimeException("SheetData中的ContentData数组越界，允许的范围0-"+(datas.length-1));
		}
		return this.datas[index];
	}
	

}
