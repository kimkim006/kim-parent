package com.kim.common.page;

import java.io.Serializable;

import com.kim.common.util.StringUtil;

/**
 * @Description 分页参数封装类
 * @date 2016年8月18日
 * @author liubo04
 */
public class PageVo implements Serializable {

	private static final long serialVersionUID = 2812277347460961243L;

	public static final int MAX_ROW = 10000;
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final int DEFAULT_CUR_PAGE = 1;
	public static final String ORDER_TYPE_DESC = "desc";
	public static final String ORDER_TYPE_ASC = "asc";

	private int curPage; //当前页码 
	private int pageSize; //每页大小
	private String orderBy;
	private String orderType;

	private Integer offset;
	private Integer rows;


	public PageVo(int curPage, int pageSize, String orderBy, String orderType) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.setOrderBy(orderBy);
		this.setOrderType(orderType);
	}

	public PageVo(int curPage, int pageSize) {
		this.curPage = curPage;
		this.pageSize = pageSize;
	}


	public int getOffset() {
		return offset = (offset == null || offset < 0) ? ((getCurPage() - 1) * getPageSize()) : offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRows() {
		if (rows == null || rows < 1) {
			rows = getPageSize();
		}else{
			rows = rows > MAX_ROW ? MAX_ROW : rows;
		}
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCurPage() {
		return curPage = curPage < 1 ? DEFAULT_CUR_PAGE : curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderType() {
		if(StringUtil.isBlank(orderType)){
			return null;
		}
		return StringUtil.equalsIgnoreCase(orderType, ORDER_TYPE_DESC) ? ORDER_TYPE_DESC : ORDER_TYPE_ASC;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
