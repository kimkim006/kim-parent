package com.kim.impexp.excel.imp.executor;

import com.kim.impexp.excel.imp.ContentReaderHandler;
import com.kim.impexp.excel.imp.handler.BodyReaderHandler;
import com.kim.impexp.excel.imp.handler.HeaderReaderHandler;

/**
 * 抽象的sheet页面处理器
 * @author bo.liu01
 *
 */
public class SimpleContentSheetReaderExecutor<T> extends MultipleContentSheetReaderExecutor{
	
	public SimpleContentSheetReaderExecutor(HeaderReaderHandler headerReaderHandler, BodyReaderHandler<T> bodyReaderHandler) {
		super(new ContentReaderHandler[]{
				headerReaderHandler,
				bodyReaderHandler
		});
	}

}
