package com.kim.log.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.kim.common.util.BeanUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.handler.FieldConvert;
import com.kim.log.handler.FieldDisplayHandler;
import com.kim.log.operatelog.OperationType;
import com.kim.log.handler.FieldConvertRegister;

/**
 * Created by bo.liu01 on 2017/11/1.
 */
public class ObjectUtil {

    /**
     * 对比两个对象
     *
     * @param srcObj
     * @param destObj
     * @return
     * @date 2017年4月10日
     * @author liubo04
     */
    public static String compare(Object srcObj, Object destObj) {
        return compare(srcObj, destObj, null, new String[0]);
    }

    /**
     * 比较两个对象
     *
     * @param srcObj
     * @param destObj
     * @param fieldConvert
     * @return
     * @date 2017年4月11日
     * @author liubo04
     */
    public static String compare(Object srcObj, Object destObj, FieldConvert fieldConvert) {
        return compare(srcObj, destObj, fieldConvert, new String[0]);
    }

    /**
     * 比较两个对象
     *
     * @param srcObj
     * @param destObj
     * @param fields 指定字段
     * @return
     * @date 2017年4月11日
     * @author liubo04
     */
    public static String compare(Object srcObj, Object destObj, String... fields) {
        return compare(srcObj, destObj, null, fields);
    }

    /**
     * 对比两个对象
     *
     * @param srcObj
     * @param destObj
     * @param fieldConvert
     * @param fields 指定字段
     * @return
     * @date 2017年4月10日
     * @author liubo04
     */
    public static String compare(Object srcObj, Object destObj, FieldConvert fieldConvert, String... fields) {
        if (srcObj == null || destObj == null) {
            return null;
        }

        if (fieldConvert == null) {
            fieldConvert = FieldConvertRegister.DEF.getFieldConvert();
        }

        List<Field> fds = fieldConvert.getCompareFields(srcObj, fields, OperationType.UPDATE);

        StringBuilder bf = new StringBuilder();
        Object v1 = null;
        Object v2 = null;
        for (Field field : fds) {
            if (FieldDisplayHandler.checkAnnoMapping(field, OperationType.UPDATE)) {
                v1 = fieldConvert.convertValue(srcObj, field);
                v2 = fieldConvert.convertValue(destObj, field);
            } else {
                v1 = BeanUtil.getValue(srcObj, field);
                v2 = BeanUtil.getValue(destObj, field);
            }

            if (v2 == null) {
                continue;
            }

            String value1 = String.valueOf(v1);
            String value2 = String.valueOf(v2);
            if (value1.contains(",") && value2.contains(",")) {
                String[] arr1 = value1.split(",");
                String[] arr2 = value2.split(",");
                Arrays.sort(arr1);
                Arrays.sort(arr2);
                if (!Arrays.equals(arr1, arr2)) {
                    bf.append("【").append(fieldConvert.convert(srcObj, field)).append("】:[").append(Arrays.toString(arr1))
                            .append("]=>[").append(Arrays.toString(arr2)).append("],");
                }
            } else if (!StringUtil.equals(value1, value2)) {
                bf.append("【").append(fieldConvert.convert(srcObj, field)).append("】:[").append(v1).append("]=>[")
                        .append(v2).append("],");
            }
        }
        if (bf.length() > 0) {
            bf.deleteCharAt(bf.length() - 1);
        }
        return bf.toString();
    }

    public static void main(String[] args) {
//		CustomerEntity c1 = new CustomerEntity();
//		c1.setCustName("1111");
//		c1.setRemarks("尼玛");
//		CustomerEntity c2 = new CustomerEntity();
//		c2.setCustName("1112");
//		c2.setRemarks("尼玛1");
//		String res = compare(c1, c2, "custName", "remarks");
//		System.out.println(res);
    }
}
