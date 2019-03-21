package com.kim.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.kim.common.util.reflect.BeanGetMethodInvoke;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一系列bean工具类
 * @author bo.liu01
 * @date 2016年3月24日 下午3:16:34
 */
public class BeanUtil extends BeanUtils {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	private static final String GETTER_PREFIX = "get";
	private static final String SETTER_PREFIX = "set";
	public static final String OBJECT_METHOD_SEPARATOR = ".";

	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("转换对象失败");
		}
	}

	public static Object getValueByName(Object bean, String name) {
		if(bean == null || StringUtil.isBlank(name)){
			return null;
		}
		BeanGetMethodInvoke md = new BeanGetMethodInvoke(bean.getClass(), name);
		try {
			return md.invoke(bean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 如果不存在该字段则返回null
	 *
	 * @param bean
	 * @param name
	 * @return
	 * @date 2017年4月11日
	 * @author liubo04
	 */
	public static Object getValue(Object bean, String name) {
		try {
			return getProperty(bean, name);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 如果不存在该字段则返回null
	 *
	 * @param bean
	 * @param field
	 * @return
	 * @date 2017年4月11日
	 * @author liubo04
	 */
	public static Object getValue(Object bean, Field field) {
		boolean flag = false;
		try {
			flag = field.isAccessible();
			field.setAccessible(true);
			return field.get(bean);
		} catch (Exception e) {
			return null;
		} finally {
			field.setAccessible(flag);
		}
	}

	/**
	 * 获取指定类的所有字段，包括从父类继承过来的（包括静态字段和其他字段）
	 *
	 * @param clazz
	 * @return
	 * @date 2017年4月10日
	 * @author liubo04
	 */
	private static List<Field> getFields(Class<?> clazz) {
		if (clazz.isAssignableFrom(Object.class)) {
			return Collections.emptyList();
		}
		List<Field> fields = new ArrayList<>();
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		fields.addAll(getFields(clazz.getSuperclass()));
		return fields;
	}

	/**
	 * 获取指定类的所有业务字段，包括从父类继承过来的
	 *
	 * @param clazz
	 * @return
	 * @date 2017年4月10日
	 * @author liubo04
	 */
	public static List<Field> getBusinessFields(Class<?> clazz) {
		List<Field> fdsTmp = getFields(clazz);
		List<Field> fds = new ArrayList<>();
		for (Field field : fdsTmp) {
			int mdf = field.getModifiers();
			// 默认,public,private,protected
			if (mdf == 0 || mdf == 1 || mdf == 2 || mdf == 4) {
				fds.add(field);
			}
		}
		return fds;
	}

	/**
	 * 获取指定类的所有字段名称，包括从父类继承过来的
	 *
	 * @param clazz
	 * @return
	 * @date 2017年4月10日
	 * @author liubo04
	 */
	public static List<String> getFieldNames(Class<?> clazz) {
		List<Field> fields = getBusinessFields(clazz);
		List<String> fieldNames = new ArrayList<>(fields.size());
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	/**
	 * 根据字段批量获取getter方法
	 *
	 * @param clazz
	 * @param fields
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static Method[] getGetterMethods(Class<?> clazz, String[] fields) {
		if (clazz == null || fields == null || fields.length < 1) {
			throw new RuntimeException("类和字段不能为空");
		}
		Method[] mds = new Method[fields.length];
		for (int i = 0; i < fields.length; i++) {
			mds[i] = getGetterMethod(clazz, fields[i]);
		}
		return mds;
	}

	/**
	 * 根据字段批量获取setter方法
	 *
	 * @param clazz
	 * @param fields
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static Method[] getSetterMethods(Class<?> clazz, String[] fields) {
		if (clazz == null || fields == null || fields.length < 1) {
			throw new RuntimeException("类和字段不能为空");
		}
		Method[] mds = new Method[fields.length];
		for (int i = 0; i < fields.length; i++) {
			mds[i] = getSetterMethod(clazz, fields[i]);
		}
		return mds;
	}

	/**
	 * 根据字段获取getter方法
	 *
	 * @param clazz
	 * @param field
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static Method getGetterMethod(Class<?> clazz, String field) {
		try {
			return clazz.getMethod(getGetterName(field));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("获取类方法出错");
		}
	}

	/**
	 * 根据字段获取setter方法
	 *
	 * @param clazz
	 * @param field
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static Method getSetterMethod(Class<?> clazz, String field) {
		try {
			return clazz.getMethod(getSetterName(field));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("获取类方法出错");
		}
	}

	/**
	 * 根据前缀和字段获取对应的方法
	 *
	 * @param prefix
	 * @param fieldName
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static String getMethodName(String prefix, String fieldName) {
		if (StringUtil.isBlank(prefix)) {
			return String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
		}
		return prefix + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 根据字段获取对应的getter方法
	 *
	 * @param fieldName
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static String getGetterName(String fieldName) {
		return getMethodName(GETTER_PREFIX, fieldName);
	}

	/**
	 * 根据字段获取对应的setter方法
	 *
	 * @param fieldName
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static String getSetterName(String fieldName) {
		return getMethodName(SETTER_PREFIX, fieldName);
	}

	/**
	 * 根据类型和方法名称调用Bean的方法
	 * @param beanType bean类
	 * @param methodName 方法名称
	 * @param params 方法的参数对象
	 * @param paramTypes 方法的参数对象类型，跟参数对象的顺序一一对应
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	public static Object getMethodInvokeByBeanType(Class<?> beanType, String methodName, Object[] params, Class<?>[] paramTypes) {
		if(beanType == null || Object.class.equals(beanType)){
			logger.error("parameter missing:beanType");
			throw new RuntimeException("parameter missing:beanType");
		}
		if (StringUtil.isBlank(methodName)) {
			logger.error("parameter missing:methodName");
			throw new RuntimeException("parameter missing:methodName");
		}

		Object obj = null;
		try {
			Object bean = HttpServletUtil.getBean(beanType);
			Method method = bean.getClass().getMethod(methodName, paramTypes);
			obj = method.invoke(bean, params);
		} catch (Exception e) {
			logger.error("根据BeanType获取数据时异常", e);
			throw new RuntimeException("根据BeanType获取数据时异常", e);
		}
		return obj;
	}

	/**
	 * 根据beanId和方法名称调用Bean的方法
	 * @param beanAndMethod bean和方法名称，用英文点.分隔
	 * @param params 方法的参数对象
	 * @param paramTypes 方法的参数对象类型，跟参数对象的顺序一一对应
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	public static Object getMethodInvokeByBeanId(String beanAndMethod, Object[] params, Class<?>[] paramTypes) {
		if (StringUtil.isBlank(beanAndMethod) || beanAndMethod.indexOf(OBJECT_METHOD_SEPARATOR) < 0) {
			logger.error("parameter missing：beanAndMethod");
			throw new RuntimeException("parameter missing：beanAndMethod");
		}
		String[] beanAndMethodArr = beanAndMethod.split("\\.");
		if (StringUtil.isBlank(beanAndMethodArr[0]) || StringUtil.isBlank(beanAndMethodArr[1])) {
			logger.error("parameter missing：beanAndMethod");
			throw new RuntimeException("parameter missing：beanAndMethod");
		}
		Object obj = null;
		try {
			Object bean = HttpServletUtil.getBean(beanAndMethodArr[0]);
			Method method = bean.getClass().getMethod(beanAndMethodArr[1], paramTypes);
			obj = method.invoke(bean, params);
		} catch (Exception e) {
			logger.error("根据BeanId获取数据时异常", e);
			throw new RuntimeException("根据BeanId获取数据时异常");
		}
		return obj;
	}

	public static Object[] getValues(Object obj, String[] fieldNames) {
		try {
			Object[] ps = new Object[fieldNames.length];
			for (int i = 0; i < fieldNames.length; i++) {
				ps[i] = getProperty(obj, fieldNames[i]);
			}
			return ps;
		} catch (Exception e) {
			logger.error("批量获取对象字段值失败", e);
			throw new RuntimeException("批量获取对象字段值失败", e);
		}
	}

	public static Object[] getValues(Object obj, List<Field> fields) {
		try {
			Object[] ress = new Object[fields.size()];
			int i = 0;
			for (Field field : fields) {
//				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
//				Method getMethod = pd.getReadMethod();// 获得get方法
//				Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
//				ress[i++] = o;
				ress[i++]=getValue(obj, field);
			}
			return ress;
		} catch (Exception e) {
			logger.error("批量获取对象字段值失败", e);
			throw new RuntimeException("批量获取对象字段值失败", e);
		}
	}

	/**
	 * 根据指定字段进行复制对象
	 * @param origin
	 * @param target
	 * @param fields
	 * @date 2017年6月28日
	 * @author liubo04
	 */
	public static void copyByField(Object origin, Object target, String ... fields){
		if(fields == null || fields.length == 0){
			logger.error("未配置要复制的映射字段");
			throw new RuntimeException("未配置要复制的映射字段");
		}
		String[][] fieldArr = StringUtil.splitFields(fields);
		int len = fieldArr[0].length;
		String[] originFields = fieldArr[0];
		String[] targetFields = fieldArr[1];
		Object val;
		for (int i = 0; i < len; i++) {
			val = getValue(origin, originFields[i]);
			if(val == null){
				continue;
			}
			setField(target, targetFields[i], val);
		}
	}

	public static void setField(Object obj, String fieldName, Object value){
		try {
			setProperty(obj, fieldName, value);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("copy时赋值失败", e);
		}
	}

}
