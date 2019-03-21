package com.kim.impexp.excel.imp.handler;

import com.kim.impexp.excel.imp.ContentReaderHandler;

/**
 * @author bo.liu01
 *
 * @param <T>
 */
public interface BodyReaderHandler<T> extends ContentReaderHandler{
	
	/**
	 * 返回用来承载数据的Java对象类
	 * @return
	 */
	Class<T> getClazz();

	/**
	 * 是否使用map来承载数据，true使用，false不使用
	 * @return
	 */
	boolean isUseMap();
	
	/** 
	 * 解析出来的数据使用自定义对象时，转换过程是否使用默认转换程序，true则使用，false则不使用，
	 * 如果不使用默认的转换程序，则需要实体类重写transfer2Object方法 
	 * @return
	 */
	boolean isUseSelf();
	
	/** 
	 * 解析出来的数据使用自定义对象时，转换过程是否使用默认转换程序，true则使用，false则不使用，
	 * 如果不使用默认的转换程序，则需要实体类重写transfer2Object方法 
	 * @return
	 */
	void setUseSelf(boolean useSelf);

	/**
	 * 设置用来承载数据的Java类，一定是可实例化的，否则会报异常
	 * @param clazz
	 */
	void setClazz(Class<T> clazz);

	void setHeaderReaderHandler(HeaderReaderHandler headerReaderHandler);

	HeaderReaderHandler getHeaderReaderHandler();
	
}
