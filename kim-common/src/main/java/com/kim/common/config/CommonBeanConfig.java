package com.kim.common.config;

import com.kim.common.constant.CommonConstants;
import com.kim.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonBeanConfig {
	
	public static final long ACCESS_TOKEN_KEEP_TIME_MIN = 600L;
	
	/** 应用的id */
	@Value("${spring.application.name}")
	protected String applicationName;
	/** 应用的上下文根路径 */
	@Value("${server.context-path:}")
	protected String contextPath;
	
	/** 登录session的有效时间  */
	@Value("${access.token.keep-time:0}")
	protected long accessTokenKeepTime;
	
	/** 未登录前白名单url */
	@Value("${access.filter.ignoreAuthUrl:}")
	protected String accessFilterIgnoreAuthUrl;
	/** 登录后的白名单url */
	@Value("${accessFilter.ignoreAuthResUrl:}")
	protected String accessFilterIgnoreAuthResUrl;
	
	/** 导入文件时默认的存储位置 */
	@Value("${import.tmp.path:}")
	protected String importTmpFilePath;

	public long getAccessTokenKeepTime() {
		if(accessTokenKeepTime < ACCESS_TOKEN_KEEP_TIME_MIN){
			return CommonConstants.ACCESS_TOKEN_TIMEOUT;
		}
		return accessTokenKeepTime;
	}
	
	public String getAccessFilterIgnoreAuthUrl() {
		return accessFilterIgnoreAuthUrl;
	}
	public String getAccessFilterIgnoreAuthResUrl() {
		return accessFilterIgnoreAuthResUrl;
	}

	public static long getAccessTokenKeepTimeMin() {
		return ACCESS_TOKEN_KEEP_TIME_MIN;
	}

	public String getImportTmpFilePath() {
		if(StringUtil.isBlank(importTmpFilePath)){
			return CommonConstants.IMPORT_TMP_FILE_PATH;
		}
		return importTmpFilePath;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
}
