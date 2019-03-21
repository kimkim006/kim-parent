package com.kim.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 分页返回结果集
 * @date 2016年8月18日
 * @author liubo04
 */
public class PageRes<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	private int curPage; //当前页码 
	private int pageSize; //每页大小
	private int totalPage;//总页数
	private int totalSize;//总数

	private int offset;
	private int rows;
	
	private List<T> list = null;
	
	public PageRes(){
		this.curPage = PageVo.DEFAULT_CUR_PAGE;
		this.pageSize = PageVo.DEFAULT_PAGE_SIZE;
	}
	
	public PageRes(PageVo page){
		this.curPage = page.getCurPage();
		this.pageSize = page.getPageSize();
		this.offset = page.getOffset();
		this.rows = page.getRows();
	}
	
	public int getTotalPage() {
		if (totalPage == 0) {
			if (totalSize % pageSize == 0) {
				totalPage = totalSize / pageSize;
			} else {
				totalPage = totalSize / pageSize + 1;
			}
		}
		return totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
