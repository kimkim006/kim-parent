package com.kim.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* MD5摘要算法
* @author liubo
* @date 2016年3月24日 下午3:08:25 
*
 */
public class Md5Algorithm {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static Md5Algorithm instance;
	
	private final static String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	private final static String CHARSET = "utf-8";
	private final static String MD5 = "MD5";
	private final static String HMAC_SHA1 = "HmacSHA1";

	private Md5Algorithm(){
		
	}
	
	public synchronized static Md5Algorithm getInstance(){
		if(null == instance){
			instance = new Md5Algorithm();
		}
		return instance;
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 转换字节数组为高位字符串
	 * @param b 字节数组
	 * @return
	 */
	private String byteToHexString(byte b) {
		int n = b;
		if (n < 0){
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	/**
	 * MD5 摘要计算(byte[]).
	 * @param src byte[]
	 * @throws Exception
	 * @return String
	 */
	public String md5Digest(byte[] src) {
		try {
			return calc(src, MD5);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public String md5Encode(String origin) {
		try {
			return calc(origin.getBytes(CHARSET), MD5);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
    }
    
    /**
     * @Description 获取摘要信息
     * @param bites
     * @param algorithm
     * @return
     * @throws Exception
     * @date 2016年9月5日
     * @author liubo04
     */
    public String calc(byte[] bites, String algorithm){
         try {
             return byteArrayToHexString(MessageDigest.getInstance(algorithm).digest(bites));
         } catch (Exception e) {
             throw new RuntimeException("获取摘要失败", e);
         }
    }
    
	/**
	 * 获取HmacSHA1加密结果
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 * @date 2016年9月5日
	 * @author liubo04
	 */
	public String hmacSha1Encrypt(String str, String key){
		return byteArrayToHexString(hmacSha1EncryptByte(str, key));
	}
	
	/**
	 * 获取HmacSHA1加密结果
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 * @date 2016年9月5日
	 * @author liubo04
	 */
	public byte[] hmacSha1EncryptByte(String str, String key){
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(new SecretKeySpec(key.getBytes(CHARSET), HMAC_SHA1));
			return mac.doFinal(str.getBytes(CHARSET));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	
}
