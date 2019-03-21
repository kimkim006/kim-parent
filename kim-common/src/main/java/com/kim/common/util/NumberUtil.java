package com.kim.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by bo.liu01 on 2017/11/17.
 */
public class NumberUtil extends NumberUtils{
	
	public static final DecimalFormat DF_0 = new DecimalFormat("#");
	public static final DecimalFormat DF_2 = new DecimalFormat("#.##");
	public static final DecimalFormat DF_4 = new DecimalFormat("#.####");
	
	
	
	/**
	 * 指定格式
	 * @param i
	 * @return
	 */
	public static String format(Number i, Format format){
		return format.format(i);
	}
	
	/**
	 * 只保留整数
	 * @param i
	 * @return
	 */
	public static String format(Number i){
		return DF_0.format(i);
	}
	
	/**
	 * 最多保留两位小数
	 * @param i
	 * @return
	 */
	public static String format2Bit(Number i){
		return DF_2.format(i);
	}
	
	/**
	 * 最多保留4位小数
	 * @param i
	 * @return
	 */
	public static String format4Bit(Number i){
		return DF_4.format(i);
	}
	
    public static boolean equals(Number i1, Number i2){
        if(i1 == null || i2 == null){
            return false;
        }
        return i1.intValue() == i2.intValue();
    }
    
    /**
     * 比较两个数字的大小
     * @param i1
     * @param i2
     * @return 若 i1 > i2 则 1，若 i1 < i2 则 -1, 否则 0
     */
    public static int compare(Number i1, Number i2){
        if(i1 == null && i2 == null){
            return 0;
        }
        if(i1 != null && i2 != null){
        	if(i1.intValue() < i2.intValue()){
        		return -1;
        	}else if(i1.intValue() > i2.intValue()){
        		return 1;
        	}else{
        		return 0;
        	}
        }
        if(i1 == null){
            return -1;
        }
        return 1;
    }
    
    /**
     * d1/d2， 并保留4位小数，四舍五入
     * @param d1
     * @param d2
     * @return
     */
    public static double divide(double d1, double d2){
    	if(d2 == 0){
    		return 0;
    	}
    	return Double.parseDouble(DF_4.format(d1/d2));
    }
    
    /**
     * d1/d2， 百分比为单位，并保留2位小数，四舍五入
     * @param d1
     * @param d2
     * @return
     */
    public static double dividePercent(double d1, double d2){
    	if(d2 == 0){
    		return 0;
    	}
    	return Double.parseDouble(DF_2.format(d1/d2 * 100));
    }
    
    /**
	 * double 相加
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double sum(Double d1, Double d2) {
		if(d1 == null && d2 == null){
			return null;
		}
		if(d1 == null){
			return d2;
		}
		if(d2 == null){
			return d1;
		}
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}
	
	/**
	 * double 相减
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double sub(Double d1, Double d2) {
		if(d1 == null && d2 == null){
			return null;
		}
		if(d1 == null){
			return null;
		}
		if(d2 == null){
			return d1;
		}
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.subtract(bd2).doubleValue();
	}

}
