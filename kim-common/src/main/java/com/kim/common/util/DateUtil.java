package com.kim.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bo.liu01 on 2017/9/28.
 */
public final class DateUtil extends DateUtils{
	
	private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String PATTERN_YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";
    public static final String PATTERN_HH_MM_SS = "yyyyMMddHHmmssSSS";
    
    public static final long ONE_DAY_LONG = 86400000;

    public static String getCurrentTime(){
        return formatDate(new Date(), DEFAULT_PATTERN);
    }
    
    /**
     * 获取指定天后的时间 
     * @param n n天后的时间, 若n小于0，则表示|n|天前的时间
     * @return
     */
    public static Date getDurationDate(int n){
    	return new Date(System.currentTimeMillis() + ONE_DAY_LONG*n);
    }
    
    /**
     * 获取指定天后的时间 
     * @param n n天后的时间, 若n小于0，则表示|n|天前的时间
     * @return
     */
    public static String getDurationDateStr(int n){
    	return getDurationDateStr(n, PATTERN_YYYY_MM_DD);
    }
    
    public static String getDurationDateStr(int n, String pattern){
    	return formatDate(getDurationDate(n), pattern);
    }

    public static String formatDateTime(Date date){
        return formatDate(date, DEFAULT_PATTERN);
    }
    
    public static String formatDate(Date date){
        return formatDate(date, PATTERN_YYYY_MM_DD);
    }
    
    public static String formatDate(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parseDate(String str) {
        try {
			return parseDate(str, DEFAULT_PATTERN);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
    }
    
    public static Date parseDate_YYYY_MM_DD(String str) {
        try {
			return parseDate(str, PATTERN_YYYY_MM_DD);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
    }
    
    /**
     * 比较两个时间的相差值, 返回天数, dateStr2 - dateStr1
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int getDiffCount(String dateStr1, String dateStr2){
    	Date date1 = parseDate_YYYY_MM_DD(dateStr1);
    	Date date2 = parseDate_YYYY_MM_DD(dateStr2);
    	if(date1 == null || date2 == null){
    		logger.error("两个时间有至少一个是空值，无法比较! date1:{}, date2:{}", dateStr1, dateStr2);
    		throw new RuntimeException("两个时间有至少一个是空值，无法比较!");
    	}
    	long du = (date2.getTime() - date1.getTime())/ONE_DAY_LONG;
    	if(du < 0){
    		return -1;
    	}
    	return (int) (du+1);
    }
    
    /**
     * 获取两个日期中间的日期集合
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static List<String> getDateDuration(String dateStr1, String dateStr2){
    	Date date1 = parseDate_YYYY_MM_DD(dateStr1);
    	Date date2 = parseDate_YYYY_MM_DD(dateStr2);
    	if(date1 == null || date2 == null){
    		logger.error("两个时间有至少一个是空值，无法比较! date1:{}, date2:{}", dateStr1, dateStr2);
    		throw new RuntimeException("两个时间有至少一个是空值，无法比较!");
    	}
    	long du = (date2.getTime() - date1.getTime())/ONE_DAY_LONG;
    	if(du < 0){
    		return Collections.emptyList();
    	}
    	List<String> dateList = new ArrayList<>();
    	for (int i = 0; i <= du; i++) {
			dateList.add(formatDate(new Date(date1.getTime() + ONE_DAY_LONG*i)));
		}
    	return dateList;
    }
    
}
