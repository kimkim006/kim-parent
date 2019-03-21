package com.kim.impexp.excel.entity;

import com.kim.common.base.BaseEntity;
import com.kim.common.util.StringUtil;

/**
 * @author bo.liu01
 *
 */
public class ImportEntity extends BaseEntity{

	private static final long serialVersionUID = 4980585350625650689L;
	
	/** 批量导入结果 */
	protected StringBuffer importRes;  
	/** 导入时的数据排序字段 */
	protected int sortNum;
	
	public String getImportRes() {
		if(importRes == null){
			return null;
		}
		return importRes.toString();
	}
	
	public void setImportRes(String importRes) {
		this.importRes = new StringBuffer(importRes);
	}
	
	public ImportEntity addRes(String res) {
		if(StringUtil.isBlank(res)){
			return this;
		}
		if(StringUtil.isBlank(importRes)){
			importRes = new StringBuffer(res);
		}else {
			importRes.append(";").append(res);
		}
		return this;
	}
	
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

}
