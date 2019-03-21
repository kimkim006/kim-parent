package com.kim.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bo.liu01 on 2017/10/9.
 */
public class StringUtil extends StringUtils{
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 将数字转换成指定位数的数字字符串，例如：9，指定2位，转换后是"09"
     * @param n
     * @param length
     * @return
     */
    public static String getNumberStr(int n, int length){
        String v = String.valueOf(n);
        if(length > v.length()){
            length = length - v.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i<length; i++ ) {
                sb.append('0');
            }
            sb.append(v);
            return sb.toString();
        }
        return v;
    }

    /**
     * 正则表达式匹配到的内容
     * @param str
     * @return
     */
    public static List<String> getRegSubUtil(String str, String rgex){
        List<String> list = new ArrayList<>();
        // 匹配的模式
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(str);
        int i = 1;
        while (m.find()) {
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 是否存在空字符串或者空白字符串
     * @param strings
     * @return true 存在，false不存在
     * @date 2016年11月16日
     * @author liubo04
     */
    public static boolean isExistBlank(String ... strings){
        if(strings == null || strings.length == 0){
            return true;
        }
        for (String string : strings) {
            if(isBlank(string)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否全部为非空空字符串
     * @param strings
     * @return true全为非空字符串，false存在空串
     * @date 2016年11月16日
     * @author liubo04
     */
    public static boolean isAllNotBlank(String ... strings){
        if(strings == null || strings.length == 0){
            return false;
        }
        for (String string : strings) {
            if(isBlank(string)){
                return false;
            }
        }
        return true;
    }


    public static String formatNumber(Double num) {
        try {
            return new DecimalFormat("##.##").format(num);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatTime(Date date) {
        if (date != null){
        	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        }
        return null;
    }

    public static String formatTime(String format, Date date) {
        if (date != null){
            return new SimpleDateFormat(format).format(date);
        }
        return null;
    }

    /**
     * 将集合转换为字符串，使用英文逗号作为分隔符 例如：[str1,str2,str3...]=>"str1,str2,str3..."
     *
     * @param colle
     * @return
     * @date 2016年9月5日
     * @author liubo04
     */
    public static String list2Str(Collection<String> colle) {
        return list2Str(colle, null);
    }

    /**
     * 将集合转换为字符串，根据给定分隔符 例如：用逗号分隔，[str1,str2,str3...]=>"str1,str2,str3..."
     *
     * @param colle
     * @param separator
     * @return
     * @date 2016年9月5日
     * @author liubo04
     */
    public static String list2Str(Collection<String> colle, String separator) {
        if (colle == null) {
            return null;
        }
        if (colle.isEmpty()) {
            return "";
        }
        if (separator == null) {
            separator = ",";
        }
        StringBuilder s = new StringBuilder();
        Iterator<String> it = colle.iterator();
        if (it.hasNext()) {
            s.append(it.next());
        }
        while (it.hasNext()) {
            s.append(separator).append(it.next());
        }
        return s.toString();
    }

    /**
     * 将字符串转换为List，使用英文逗号作为分隔符 例如："str1,str2,str3..."=>[str1,str2,str3...]
     *
     * @param str
     * @return
     * @date 2016年9月5日
     * @author liubo04
     */
    public static List<String> str2List(String str) {
        return str2List(str, null);
    }

    /**
     * 将字符串转换为List，根据给定分隔符 例如：用逗号分隔，"str1,str2,str3..."=>[str1,str2,str3...]
     *
     * @param str
     * @param separator
     * @return
     * @date 2016年9月5日
     * @author liubo04
     */
    public static List<String> str2List(String str, String separator) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return Collections.emptyList();
        }
        if (separator == null) {
            separator = ",";
        }
        String[] strs = str.split(separator);
        return Arrays.asList(strs);
    }

    /**
     * 分隔字段
     * @param fields
     * @return
     * @date 2016年12月30日
     * @author liubo04
     */
    public static String[][] splitFields(String[] fields) {
        String[] fssTmp = null;
        String[] fds = new String[fields.length];
        String[] keys = new String[fields.length];
        for (int i = 0;i<fields.length;i++) {
            if(fields[i].indexOf("=>") != -1 && (fssTmp = fields[i].split("=>")).length > 1){
                fds[i] =fssTmp[0];
                keys[i] =fssTmp[1];
            }else{
                fds[i]=fields[i];
                keys[i]=fields[i];
            }
        }
        return new String[][]{fds, keys};
    }
    
    /**
     * 验证签名是否一直
     * @param str
     * @param key
     * @param sign
     * @return
     */
    public static boolean checkSign(String str, String key, String sign) {
		String signTmp = sign(str, key);
		boolean flag = equals(signTmp, sign);
		if(!flag){
			logger.error("checkSign:{}, sign:{}", signTmp, sign);
		}
		return flag;
	}

	/**
	 * 签名
	 * @param str
	 * @param key
	 * @return
	 */
	public static String sign(String str, String key) {
		return Md5Algorithm.getInstance().md5Encode(str + key);
	}

}
