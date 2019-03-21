package com.kim.impexp.excel.exp;

/**
 * 通用Excel写工具接口
 * @author bo.liu01
 *
 */
public interface ExcelWriter {

	/**
	 * Excel写操作入口
	 * @return
	 */
	ExcelWriter create();
	
	/**
	 * 文件所在位置，只包含目录，不包含文件本身的文件名称
	 * @return
	 * @date 2016年12月6日
	 * @author liubo04
	 */
	String getFilePath();
	
	/**
	 * Excel文件名称，包含后缀名，否则将默认为.xlsx后缀
	 * @return
	 */
	String getFileName();
	
	/**
	 * 文件的绝对路径
	 * @return
	 */
	String getAbsolutePath();

}
