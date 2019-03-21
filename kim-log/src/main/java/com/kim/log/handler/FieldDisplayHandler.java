package com.kim.log.handler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.log.operatelog.OperationType;
import com.kim.log.annotation.FieldDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.StringUtil;

/**
 * FieldDisplay注解的处理类
 * @date 2017年6月19日
 * @author liubo04
 */
public class FieldDisplayHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(FieldDisplayHandler.class);
	
	/**
	 * 通过@FieldDisplay注解的配置将字段值转成名称
	 * @param o
	 * @param field
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	public static String getFieldDisplayValue(Object o, Field field) {
		
		FieldDisplay fd = getAnnoByField(field);
		//没有注解的情况
		if(fd == null){
			return null;
		}
		
		if(!fd.useMapping()){
			return null;
		}
		
		//有静态映射的情况
		Map<String, String> mapping = getMap(fd.mappings());
		if(mapping.size() != 0){
			return dealMapping(o, field, mapping);
		}
		
		//一般情况和数据字段情况校验参数
		if(StringUtil.isBlank(fd.dependKey())){
			logger.error("FieldDisplay注解参数缺失：pdonKey为空，Class：{}， fieldName：{}", field.getDeclaringClass().getSimpleName(), field.getName());
			throw new RuntimeException("FieldDisplay注解参数缺失");
		}
		
//		//处理数据字典情况
//		if(!DictionaryDesc.DefaultType.equals(fd.dictType())){
//			return dealDict(o, field, fd);
//		}
		
		//处理一般情况
		return dealGeneric(o, field, fd);
	}

	/**
	 * 使用静态映射的情况
	 * @param o
	 * @param field
	 * @param mapping
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	private static String dealMapping(Object o, Field field, Map<String, String> mapping) {
		String res;
		res = String.valueOf(BeanUtil.getValue(o, field));
		String _res = mapping.get(res);
		if(_res == null){
			logger.info("根据{}获取名称为空，将使用原值记录日志", res);
			return res;
		}
		return _res;
	}

//	/**
//	 * 使用数据字典的情况
//	 * @param o
//	 * @param field
//	 * @param fd
//	 * @return
//	 * @date 2017年6月19日
//	 * @author liubo04
//	 */
//	private static String dealDict(Object o, Field field, FieldDisplay fd) {
//		String dponVal = getDponValue(o, fd.dependKey());
//		if(dponVal == null){
//			return null;
//		}
//		Object obj = null;
//		try {
//			obj = BeanUtils.getMethodInvokeByBeanType(SysDictionaryService.class, "getSysDictionaryByCodeType",
//					new Object[]{fd.dictType().getType() + "", dponVal},getTypeClass(2));//固定两个参数
//		} catch (Exception e) {
//			logger.error("获取数据字典内容异常", e);
//			throw new RuntimeException("获取数据字典内容异常", e);
//		}
//		if(obj != null){
//			return ((SysDictionaryEntity)obj).getName();
//		}else{
//			return dponVal;
//		}
//	}
	
	private static String getDponValue(Object o, String fieldName){
		try {
			Object v = BeanUtil.getValueByName(o, fieldName);
			if(v == null){
				logger.error("关联依赖的字段并没有值，Class：{}， fieldName：{}", o.getClass().getSimpleName(), fieldName);
				return null;
			}
			return String.valueOf(v);
		} catch (Exception e) {
			logger.error("FieldDisplay注解中的取值异常", e);
			throw new RuntimeException("FieldDisplay注解中的取值异常", e);
		}
	}

	/**
	 * 使用一般的取值情况
	 * @param o
	 * @param field
	 * @param fd
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	private static String dealGeneric(Object o, Field field, FieldDisplay fd) {
		String dponVal = getDponValue(o, fd.dependKey());
		if(dponVal == null){
			return null;
		}
		Object val = null;
		if(StringUtil.isNotBlank(fd.beanAndMethod())){
			val = BeanUtil.getMethodInvokeByBeanId(fd.beanAndMethod(),
					new Object[]{dponVal}, new Class[]{String.class});
			
		}else if(fd.beanType() != null && StringUtil.isNotBlank(fd.methodName())){
			val = BeanUtil.getMethodInvokeByBeanType(fd.beanType(), fd.methodName(),
					new Object[]{dponVal}, new Class[]{String.class});
		}else{
			logger.error("logging parameter is missing, FieldDisplay:{}", JSON.toJSONString(fd));
			throw new RuntimeException("logging parameter is missing");
		}
	
		if(val == null){
			return dponVal;
		}else{
			if(val instanceof String){
				return val.toString();
			}else{
				return String.valueOf(BeanUtil.getValue(val, "name"));
			}
		}
	}

//	private static Class<?>[] getTypeClass(int length){
//		Class<?>[] arr = new Class<?>[length];
//		for (int i = 0; i < length; i++) {
//			arr[i]=String.class;
//		}
//		return arr;
//	}
	
	private static Map<String, String> getMap(String[] mappings){
		if(mappings.length == 0){
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<>();
		for (String mp : mappings) {
			String[] mps = mp.split("=>");
			if(StringUtil.isBlank(mps[0]) || StringUtil.isBlank(mps[1])){
				continue;
			}
			map.put(mps[0], mps[1]);
		}
		return map;
	}
	
	private static FieldDisplay getAnnoByField(Field field){
		FieldDisplay fd = getAnno(field);
		if(fd == null){
			logger.warn("该字段的getter方法没有 @FieldDisplay注解，Class:{}, fieldName:{}", field.getDeclaringClass().getSimpleName(), field.getName());
		}
		return fd;
	}
	
	/**
	 * 通过@FieldDisplay注解的配置将字段name转成名称
	 * @param field
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	public static String getFieldDisplayName(Field field){
		FieldDisplay fd = getAnnoByField(field);
		if(fd != null){
			return fd.value();
		}else{
			return field.getName();
		}
	}
	
	private static FieldDisplay getAnno(Field field){
		return BeanUtil.getGetterMethod(field.getDeclaringClass(), field.getName()).getAnnotation(FieldDisplay.class);
	}
	
	/**
	 * 检查是否需要对比
	 * @param fd
	 * @return
	 * @date 2017年4月11日
	 * @author liubo04
	 */
	public static boolean checkAnnoCompare(Field fd, OperationType operationType){
		FieldDisplay dsp = getAnno(fd);
		if(dsp == null){
			return false;
		}
		byte level = FieldDsplayScope.OFF;
		switch (operationType) {
		case INSERT: level = FieldDsplayScope.ADD;break;
		case UPDATE: level = FieldDsplayScope.UPDATE;break;
		case DELETE: level = FieldDsplayScope.DELETE;break;
		default:logger.debug("非新增、修改、删除操作，不可使用FieldDisplay注解");break;
		}
		return (dsp.scope() & level) == level;
	}
	
	/**
	 * 检查是否需要使用映射取值
	 * @param fd
	 * @return
	 * @date 2017年4月11日
	 * @author liubo04
	 */
	public static boolean checkAnnoMapping(Field fd, OperationType operationType){
		FieldDisplay dsp = getAnno(fd);
		if(dsp == null){
			return false;
		}
		byte level = FieldDsplayScope.OFF;
		switch (operationType) {
		case INSERT: level = FieldDsplayScope.ADD;break;
		case UPDATE: level = FieldDsplayScope.UPDATE;break;
		case DELETE: level = FieldDsplayScope.DELETE;break;
		default:logger.debug("非新增、修改、删除操作，不支持FieldDisplay注解");break;
		}
		return ((dsp.scope() & level) == level) && dsp.useMapping();
	}
	
	public static List<Field> filter(List<Field> fds) {
		//过滤没有注解的字段
		List<Field> _fds = new ArrayList<>();
		for (Field field : fds) {
			if(getAnno(field) != null){
				_fds.add(field);
			}
		}
		return _fds;
	}

}
