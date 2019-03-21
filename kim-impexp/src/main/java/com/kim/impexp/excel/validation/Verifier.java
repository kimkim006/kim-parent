package com.kim.impexp.excel.validation;

import java.util.List;

/**
 * @author bo.liu01
 *
 * @param <T>
 */
public interface Verifier<T> {
	
	/**
	 * 单条数据校验方法
	 * @param entity
	 * @return
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	boolean check(T entity);
	
	/**
	 * 是否启用批量数据校验方法，true启用，false不启用，默认为false
	 * @return
	 */
	boolean isActiveBatchCheck();
	
	/**
	 * 多条数据校验方法，若启用批量校验方法，则单条校验方法将失效
	 * 如需启用该批量校验，请将isActiveBatchCheck方法的返回值设置为true；
	 * @param dataList 原数据列表
	 * @param addList 通过校验的数据列表
	 * @param resultList 未通过校验的数据列表
	 * @date 2017年4月7日
	 * @author liubo04
	 */
	void checkList(List<T> dataList, List<T> addList, List<T> resultList);

}
