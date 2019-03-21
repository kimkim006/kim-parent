package com.kim.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lu.xu on 2017/6/21.
 * id生成器
 * 参考至  oam SerialNumberUtils
 */
public class IdGeneratorUtils {

    private static long iCount = 100000L;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMddHHmmssSSS");

    /**
     * 获取序列号
     *
     * @return yyyyMMddHHmmssSSSxxxxxxx000000
     */
    public static synchronized String getSerialNo() {
        if (iCount > 999998L) {
            iCount = 100000L;
        }

        String sTime = sdf.format(new Date()); //获取时间

        String sRandom = getMathRandom(); //获取7位随机数

        return sTime + sRandom + iCount++;
    }

    /**
     * 获取以“sPrefix”开头的序列号
     *
     * @param sPrefix [sPrefix字符建议长度两位以内字符,超过两位去前两位]
     * @return **MMddHHmmssSSSxxxxxxx000000
     */
    public static synchronized String getSerialNo(String sPrefix) {
        if (sPrefix != null && !"".equals(sPrefix)) {
            sPrefix = sPrefix.trim();
            if (sPrefix.length() > 2)
                sPrefix = sPrefix.substring(2).toUpperCase();
            else
                sPrefix = sPrefix.toUpperCase();
        }

        if (iCount > 999999L)
            iCount = 100000L;

        String sTime = sdf2.format(new Date()); //获取时间

        String sRandom = getMathRandom(); //获取7位随机数

        return sPrefix + sTime + sRandom + iCount++;
    }

    /**
     * 获取随7位机数（为避免重复，分别获取4位和3位两组随机数）
     * Math.random()产生一个[0，1)之间的随机double数值，是Java、js等语言常用代码。
     * 例如：var a = Math.random() * 2 + 1，设置一个随机1到3(取不到3)的变量。
     *
     * @return
     */
    private static String getMathRandom() {
        //获取4位随机数
        String r1 = Double.toString(Math.random()).substring(2);
        if (r1.length() > 4)
            r1 = r1.substring(0, 4);
        else
            r1 = (r1 + "000").substring(0, 4);

        //获取3位随机数
        String r2 = Double.toString(Math.random()).substring(2);
        if (r2.length() > 3)
            r2 = r2.substring(0, 3);
        else
            r2 = (r2 + "00").substring(0, 3);
        return r1 + r2;
    }

}
