package com.kim.impexp.util;

import java.util.Date;

import com.kim.impexp.excel.exp.SheetWriterExecutor;
import com.kim.impexp.excel.exp.executor.SimpleContentSheetWriterExecutor;
import com.kim.impexp.excel.exp.writer.SimpleSheetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.Base64Util;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.impexp.excel.exp.ContentWriterHandler;

public class DownloadUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(DownloadUtil.class);
	
	public static final String KEY = "bqjr_icm_589I4jsZes=";
	
	public static String decode(String file, String sign) {
		if(!StringUtil.checkSign(file, KEY, sign)){
			logger.error("下载签名sign不一致！");
			throw new BusinessException("下载签名sign不一致，请联系管理员!");
		}
		return Base64Util.safeUrlBase64Decode(file);
	}
	
	public static String encode(String filename) {
		return Base64Util.encodeBase64URLSafeString(filename);
	}
	
	public static String sign(String filename) {
		return StringUtil.sign(Base64Util.encodeBase64URLSafeString(filename), KEY);
	}
	
	public static JSONObject simpleDownload(String name, String[] header, ContentWriterHandler bodyWriterHandler) {
		
		SheetWriterExecutor executor = new SimpleContentSheetWriterExecutor<>(name, header, bodyWriterHandler);
		String fileName = String.format("%s_%s.xlsx", name, DateUtil.formatDate(new Date(), DateUtil.PATTERN_YYYYMMDDHHMMSS_SSS));
		String path = new SimpleSheetWriter(fileName, executor).create().getAbsolutePath();
		JSONObject json = new JSONObject();
		json.put("f", encode(path));
		json.put("s", sign(path));
		return json;
	}

}
