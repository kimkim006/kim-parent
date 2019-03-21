package com.kim.impexp.excel.validation;

import java.util.List;

/**
 * @author bo.liu01
 *
 * @param <T>
 */
public class VerifierAdaptor<T> extends AbstractVerifier<T>{

	@Override
	public boolean check(T entity) {
		return true;
	}

	@Override
	public void checkList(List<T> dataList, List<T> addList, List<T> resultList) {
	}

}
