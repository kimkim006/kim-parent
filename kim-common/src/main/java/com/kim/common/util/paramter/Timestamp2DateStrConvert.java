package com.kim.common.util.paramter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将指定格式的字符串时间转成13位时间戳
 * @date 2017年5月12日
 * @author liubo04
 */
public class Timestamp2DateStrConvert implements ParameterConvert{
	
	public final static String TIMESTAMP_REG = "^[1-9]\\d*$";
	public final static int TIMESTAMP_MIN = 9;
	public final static int TIMESTAMP_MAX = 14;

	@Override
	public Object convert(Object obj, String format) {
		if(obj == null){
			return null;
		}
		String stp = String.valueOf(obj);
		if(stp.length() > TIMESTAMP_MIN && stp.length() < TIMESTAMP_MAX && stp.matches(TIMESTAMP_REG)){
			Date date = null;
			switch(stp.length()){
			case 10:date = new Date(Long.parseLong(stp)*1000);break;
			case 11:date = new Date(Long.parseLong(stp)*100);break;
			case 12:date = new Date(Long.parseLong(stp)*10);break;
			default:date = new Date(Long.parseLong(stp));break;
			}
			return new SimpleDateFormat(format).format(date);
		}
		return obj;
	}

}
