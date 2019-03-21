package com.kim.common.page;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageRet<T> implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private int curPage; //当前页码 
	private int pageSize; //每页大小
	private int totalPage;//总页数
	private int totalSize;//总数

	private List<T> list = null;
	
	public PageRet(){
		this.curPage = PageVo.DEFAULT_CUR_PAGE;
		this.pageSize = PageVo.DEFAULT_PAGE_SIZE;
		this.list = new ArrayList<>();
	}
	
	public PageRet(PageRes<T> pageRes) {
		super();
		this.curPage = pageRes.getCurPage();
		this.pageSize = pageRes.getPageSize();
		this.totalPage = pageRes.getTotalPage();
		this.totalSize = pageRes.getTotalSize();
		this.list = pageRes.getList();
	}
	
	/**
	 * 序列化之前需要先转换一次
	 * @param pageRes
	 * @return
	 * @date 2016年11月14日
	 * @author liubo04
	 */
	@JsonIgnore
	public static <T> PageRet<T> convert(PageRes<T> pageRes){
		return new PageRet<>(pageRes);
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

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
