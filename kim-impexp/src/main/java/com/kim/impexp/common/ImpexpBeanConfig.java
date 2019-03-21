package com.kim.impexp.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kim.common.config.CommonBeanConfig;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;

@Component
public class ImpexpBeanConfig extends CommonBeanConfig{
	
	/** 导入导出的临时文件存放位置 */
	@Value("${impexp.tmp.path:}")
	private String tmpFilePath;
	/** 导入文件的最大值，单位M */
	@Value("${import.max.file.size:5}")
	private int importMaxFileSize;
	
	/** 导入文件后，读取文件时默认的缓存行数 */
	@Value("${import.rowCacheSize:1000}")
	protected int importRowCacheSize;
	/** 导入文件后，读取文件时默认的缓存行数 */
	@Value("${import.rowCacheSize:4096}")
	protected int importBufferSize;
	
	public String getTmpFilePath() {
		if(StringUtil.isBlank(tmpFilePath)){
			return Constant.DEFAULT_FILE_PATH;
		}
		return tmpFilePath;
	}

	public static String getTmpPath() {
		ImpexpBeanConfig beanConfig = HttpServletUtil.getBean(ImpexpBeanConfig.class);
		if(beanConfig == null){
			return Constant.DEFAULT_FILE_PATH;
		}
		return beanConfig.getTmpFilePath();
	}

	public int getImportRowCacheSize() {
		return importRowCacheSize;
	}

	public int getImportBufferSize() {
		return importBufferSize;
	}

	public int getImportMaxFileSize() {
		return importMaxFileSize;
	}
	
	public long getImportMaxFileByteSize() {
		return importMaxFileSize *1024 *1024L;
	}
	
}
