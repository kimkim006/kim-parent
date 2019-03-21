package com.kim.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import jodd.io.StreamUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流工具类
 * @date 2017年3月18日
 * @author liubo04
 */
public class StreamUtils extends StreamUtil {
	private final static Logger logger = LoggerFactory.getLogger(StreamUtil.class);
	private static final String CHARSET = "utf-8";
	
	/**
	 * 读取输入流中的字节
	 * @param inputStream
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static byte[] readStreamByte(InputStream inputStream){
		
		byte[] bf = new byte[0];
		byte[] data = new byte[1024];  
		try(InputStream in = new BufferedInputStream(inputStream)){
            while (-1 != in.read(data, 0, data.length)) {
            	bf = ArrayUtils.addAll(bf, data);  
            }  
            return bf;
		}catch(Exception e){
			logger.error("获取流数据失败",e);
	    	return new byte[0];
	    }
	}
	
	/**
	 * 读取输入流中的字符
	 * @param inputStream
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static String readStreamString(InputStream inputStream) {
		return readStreamString(inputStream, CHARSET);
	}
	
	/**
	 * 读取输入流中的字符
	 * @param inputStream
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static String readStreamString(InputStream inputStream, String charset) {
		String tempLine = null;
		charset = StringUtil.isBlank(charset) ? CHARSET : charset;
		StringBuilder resultBuffer = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
			return resultBuffer.toString();
		} catch (Exception e) {
			logger.error("获取流数据失败", e);
			return null;
		}
	}

}
