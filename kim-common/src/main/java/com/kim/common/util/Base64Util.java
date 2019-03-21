package com.kim.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @Description base64编码/解码
 * @date 2016年7月27日
 * @author liubo04
 */
public class Base64Util{
	private final static Logger logger = LoggerFactory.getLogger(Base64Util.class);

	public static final String CHARSET = "utf-8";

	public static String encode(String str, String charset) throws UnsupportedEncodingException {
		if(StringUtil.isEmpty(str)){
			return null;
		}
		charset = StringUtil.isBlank(charset) ? CHARSET : charset;
		byte[] res = Base64.encodeBase64(str.getBytes(charset));
		return new String(res, charset);
	}
	
	public static String encodeByte(byte[] bytes){
		return new String(Base64.encodeBase64(bytes));
	}
	
	public static String encode(String str) {
		try {
			return encode(str, CHARSET);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static byte[] decodeByte(String str) {
		return org.apache.commons.codec.binary.Base64.decodeBase64(str);
	}

	public static String decode(String str, String charset) throws UnsupportedEncodingException {
		if(StringUtil.isBlank(str)){
			return null;
		}
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
		charset = StringUtil.isBlank(charset) ? CHARSET : charset;
		return new String(b, charset);
	}
	
	public static String decode(String str) {
		try {
			return decode(str, CHARSET);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String encodeBase64URLSafeString(String str) {
		if(StringUtil.isNotBlank(str)){
			try {
				
				return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(str.getBytes(CHARSET));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	public static String encodeBase64URLSafeString(byte[] data) {
		
		return encodeByte(data).replace('+', '-').replace('/', '_');
	}
	
	public static String safeUrlBase64Decode(String safeBase64Str){
		try {
			return safeUrlBase64Decode(safeBase64Str, null);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String safeUrlBase64Decode(String safeBase64Str, String charset) throws UnsupportedEncodingException {
		String base64Str = safeBase64Str.replace('-', '+').replace('_', '/');
		int mod4 = base64Str.length() % 4;
		if (mod4 > 0) {
			base64Str = base64Str + "====".substring(mod4);
		}
		byte[] b = decodeByte(base64Str);
		charset = StringUtil.isBlank(charset) ? CHARSET : charset;
		return new String(b, charset);
	}

}
