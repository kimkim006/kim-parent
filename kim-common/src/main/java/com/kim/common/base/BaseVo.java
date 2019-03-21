package com.kim.common.base;

import com.kim.common.page.PageVo;
import com.kim.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @date 2017-10-11 10:33:59
 * @author bo.liu01
 */
public class BaseVo extends BaseTable {

	private static final long serialVersionUID = 1L;

	//分页信息
	protected Integer curPage; //当前页码
	protected Integer pageSize; //每页大小
	protected String orderBy;// 排序字段
	protected String orderType = "ASC";// 排序类型，升序和降序,默认升序

	@JsonIgnore
	public PageVo getPage() {
		curPage = curPage == null || curPage < 1 ? PageVo.DEFAULT_CUR_PAGE : curPage;
		pageSize = pageSize == null || pageSize < 1 ? PageVo.DEFAULT_PAGE_SIZE : pageSize;
		return new PageVo(curPage, pageSize, orderBy, orderType);
	}

	@JsonIgnore
	public Integer getCurPage() {
		return curPage;
	}

	public BaseVo setCurPage(Integer curPage) {
		this.curPage = curPage;
		return this;
	}

	@JsonIgnore
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@JsonIgnore
	public String getOrderType() {
		if(StringUtil.isBlank(orderType)){
			return null;
		}
		return StringUtil.equalsIgnoreCase(orderType, PageVo.ORDER_TYPE_DESC) ?
				PageVo.ORDER_TYPE_DESC : PageVo.ORDER_TYPE_ASC;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public void setId(String id) {
		super.setId(id);
    }
}
