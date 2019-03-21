package com.kim.impexp.excel.exp.handler;

import java.util.List;

/**
 * Java对象内容模式的excel写处理器
 * @date 2016年12月1日
 * @author liubo04
 */
public abstract class AbstractObjectContentWriterHandler<T> extends BodyContentWriterHandler implements ObjectWriterHandler<T>{
	
	protected List<T> list;
	
	public AbstractObjectContentWriterHandler(List<T> list) {
		super();
		this.list = list;
	}
	
	public AbstractObjectContentWriterHandler(Object[][] dataList) {
		super();
		this.dataList = dataList;
	}
	
	@Override
	public Object[][] getDataList(){
		if(dataList == null){
			dataList = new Object[list.size()][];
			int i = 0; 
			for (T t : list) {
				dataList[i++] = java2ObjectArr(t);
			}
		}
		return dataList;		
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
