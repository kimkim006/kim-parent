package com.kim.impexp.excel.imp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author bo.liu01
 *
 */
public class ContentData extends ExcelData{
	
	private static final long serialVersionUID = 1L;
	
	private String sheetName;
	private Serializable data;
	private boolean useList = false;
	private Class<?> clazz;
	
	public ContentData(String sheetName, int index, Serializable data, int rowNum, int startRow) {
		super();
		this.index = index;
		this.rowNum = rowNum;
		this.startRow = startRow;
		this.sheetName = sheetName;
		this.data = data;
	}
	
	public ContentData(String sheetName, int index, int startRow) {
		super();
		this.index = index;
		this.startRow = startRow;
		this.sheetName = sheetName;
	}

	@Override
	/** 所在sheet内的内容序号，从0开始 */
	public int getIndex() {
		return index;
	}

	@Override
	/** 所在sheet内的内容序号，从0开始 */
	public ContentData setIndex(int index) {
		this.index = index;
		return this;
	}
	
	@Override
	public ContentData setRowNum(int rowNum) {
		this.rowNum = rowNum;
		return this;
	}
	
	@Override
	public ContentData setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}

	public Object getData() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList() {
		if(useList && data instanceof List){
			return (List<T>)data;
		}
		throw new RuntimeException("读取到的Excel数据无法转为List, dataType:"+ data.getClass().getName());
	}
	
	@SuppressWarnings("unchecked")
	public <T> boolean add(T t) {
		if(useList && data instanceof List){
			rowNum++;
			return ((List<T>)data).add(t);
		}
		throw new RuntimeException("读取到的Excel数据无法转为List, dataType:"+ data.getClass().getName());
	}

	public ContentData setData(Serializable data) {
		this.data = data;
		return this;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public boolean isUseList() {
		return useList;
	}

	public ContentData setUseList(boolean useList) {
		this.useList = useList;
		return this;
	}

	public String getSheetName() {
		return sheetName;
	}

	public ContentData setSheetName(String sheetName) {
		this.sheetName = sheetName;
		return this;
	}
	
}
