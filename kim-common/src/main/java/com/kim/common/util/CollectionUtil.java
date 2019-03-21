package com.kim.common.util;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kim.common.util.collectioninter.CollectionProperty;
import com.kim.common.util.collectioninter.FilterCondition;
import com.kim.common.util.paramter.annotation.ParamConvert;
import com.kim.common.util.reflect.BeanGetMethodInvoke;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class CollectionUtil extends CollectionUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

	public static final int DEFAULT_BATCH_NUM = 500;
	
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && map.size() > 0;
	}
	
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}
	
	public static <T extends Object> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}
	
	public static <T extends Object> boolean isNotEmpty(T[] arr) {
		return arr != null && arr.length > 0;
	}
	
	public static Object[] addAll(Object[] arr1, Object[] arr2){
		if(arr1 == null && arr2 == null){
			return null;
		}
		if(arr1 == null){
			return arr2;
		}
		if(arr2 == null){
			return arr1;
		}
		List<Object> list1 = array2List(arr1);
		addAll(list1, arr2);
		return list1.toArray();
	}

	/**
	 * 根据自定义条件进行过滤数据
	 * @param list
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> List<T> filter(Collection<T> list, FilterCondition<T> condition){
		List<T> newList = new ArrayList<>();
		for(T t:list){
			if(condition.evaluate(t)){
				newList.add(t);
			}
		}
		return newList;
	}

	/**
	 * java对象转map对象
	 * @param object
	 * @param fields
	 * @return
	 * @date 2016年12月30日
	 * @author liubo04
	 */
	public static Map<String, Object> java2Map(Object object, String... fields){
		if(object == null){
			return null;
		}
		if(fields == null || fields.length < 1){
			logger.error("fields为空");
			throw new RuntimeException("转换mapList失败，没有指定字段");
		}
		String[][] fieldArr = StringUtil.splitFields(fields);
		Method[] methods = BeanUtil.getGetterMethods(object.getClass(), fieldArr[0]);
		return java2Map(object, methods, fieldArr[1]);
	}
	
	/**
	 * 将java对象list转换为map对象list，需要的字段自己指定
	 * @param list
	 * @param fields
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	public static List<Map<String, Object>> javaList2MapList(List<?> list, String ... fields){
		if(list == null){
			return null;
		}
		if(list.size() < 1){
			return Collections.emptyList();
		}
		if(fields == null || fields.length < 1){
			logger.error("fields为空");
			throw new RuntimeException("转换mapList失败，没有指定字段");
		}
		String[][] fieldArr = StringUtil.splitFields(fields);
		List<Map<String, Object>> mapList = new ArrayList<>(list.size());
		Iterator<?> it = list.iterator();
		Object object = it.next();
		Method[] methods = BeanUtil.getGetterMethods(object.getClass(), fieldArr[0]);
		mapList.add(java2Map(object, methods, fieldArr[1]));
		while(it.hasNext()) {
			mapList.add(java2Map(it.next(), methods, fieldArr[1]));
		}
		return mapList;
	}

//	public static void main(String[] args) {
//		BaseEntity t = new BaseEntity();
//		t.setOperateDate("2017-04-02 12:45:12");
//		Map<String, Object> map = java2Map(t, "operateDate");
//		System.out.println(map);
//	}
	
	/**
	 * java对象转map对象
	 * @param object
	 * @param mds
	 * @param fields
	 * @return
	 * @date 2016年10月14日
	 * @author liubo04
	 */
	private static Map<String, Object> java2Map(Object object, Method[] mds, String[] fields) {
		if(object == null){
			return null;
		}
		Map<String, Object> map = new HashMap<>(fields.length);
		try {
			for (int i = 0; i < fields.length; i++) {
				map.put(fields[i], getConvertValue(object, mds[i]));
			}
		} catch (Exception e) {
			logger.error("java对象转map出现异常");
			throw new RuntimeException("java对象转map出现异常");
		}
		return map;
	}
	
	private static Object getConvertValue(Object object, Method md) throws Exception{
		ParamConvert ct = md.getAnnotation(ParamConvert.class);
		if(ct == null){
			return md.invoke(object);
		}else{
			return ct.instance().getParamConvert().convert(md.invoke(object), ct.format());
		}
	}
	
	/**
	 * 将Map list对象转换为指定的Java对象list
	 * @param list
	 * @param clazz
	 * @return
	 * @date 2016年9月26日
	 * @author liubo04
	 */
	public static <T> List<T> objectList2javaList(List<? extends Object> list, Class<T> clazz) {
		if(list != null){
			List<T> listTmp = new ArrayList<>();
			for (Object obj : list) {
				listTmp.add(JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(obj)), clazz));
			}
			return listTmp;
		}
		return Collections.emptyList();
	}
	
	/**
	 * @Description 将list分成若干批数据,每批按照默认的大小
	 * @param list 
	 * @return
	 * @date 2016年9月8日
	 * @author liubo04
	 */
	public static <T> List<List<T>> list2BatchList(List<T> list){
		return list2BatchList(list, 0);
	}
	
	/**
	 * @Description 将list根据固定大小分成若干批数据
	 * @param list 
	 * @param batchNum 每批的数量
	 * @return
	 * @date 2016年9月8日
	 * @author liubo04
	 */
	public static <T> List<List<T>> list2BatchList(List<T> list, int batchNum){
		batchNum = batchNum < 1 ? DEFAULT_BATCH_NUM : batchNum;
		List<List<T>> batchList = new ArrayList<>();
		if(list == null || list.size() == 0){
			return batchList;
		}
		List<T> lsTmp = new ArrayList<>();
		for (T t : list) {
			lsTmp.add(t);
			if(lsTmp.size() >= batchNum){
				batchList.add(lsTmp);
				lsTmp = new ArrayList<>();
			}			
		}
		if(lsTmp.size() > 0){
			batchList.add(lsTmp);
		}
		return batchList;
	}
	
	/**
	 * 从一个对象集合里面获取某个字段，并生成对应的list
	 * @param colle 对象的集合
	 * @param pro 要获取的属性的类型
	 * @return 
	 * @date 2016年6月8日
	 * @author liubo04
	 */
	public static <T, P> List<P> getPropertyList(Collection<T> colle, CollectionProperty<T, P> pro) {
		List<P> list = new ArrayList<>();
		for (T t : colle) {
			P p = pro.getProperty(t);
			if (p != null) {
				list.add(p);
			}
		}
		return list;
	}
	
	/**
	 * 从一个对象集合里面获取某个字段的Set集合
	 * @param colle 对象的集合
	 * @param field 要获取的属性
	 * @return 
	 * @date 2016年6月8日
	 * @author liubo04
	 */
	public static <T> List<String> getPropertyList(Collection<T> colle, String field) {
		if(isEmpty(colle)){
			return Collections.emptyList();
		}
		if(StringUtil.isBlank(field)){
			throw new RuntimeException("未指定字段值");
		}
		List<String> list = new ArrayList<>();
		try {
			Iterator<T> it = colle.iterator();
			T t = it.next();
			BeanGetMethodInvoke md = new BeanGetMethodInvoke(t.getClass(), field);
			list.add(String.valueOf(md.invoke(t)));
			while(it.hasNext()){
				String p = String.valueOf(md.invoke(it.next()));
				if (p != null) {
					list.add(p);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return list;
	}
	
	/**
	 * 从一个对象集合里面获取某个字段的Set集合
	 * @param colle 对象的集合
	 * @param pro 要获取的属性
	 * @return 
	 * @date 2016年6月8日
	 * @author liubo04
	 */
	public static <T, P> Set<P> getPropertySet(Collection<T> colle, CollectionProperty<T, P> pro) {
		Set<P> set = new HashSet<>();
		for (T t : colle) {
			P p = pro.getProperty(t);
			if (p != null) {
				set.add(p);
			}
		}
		return set;
	}
	
	/**
	 * 从一个对象集合里面获取某个字段的Set集合
	 * @param colle 对象的集合
	 * @param field 要获取的属性
	 * @return 
	 * @date 2016年6月8日
	 * @author liubo04
	 */
	public static <T> Set<String> getPropertySet(Collection<T> colle, String field) {
		return new HashSet<>(getPropertyList(colle, field));
	}
	
	/**
	 * 从一个对象集合里面根据指定属性获取Map，key为属性值，value为对象
	 * @param colle 对象集合
	 * @param fieldName 属性名称
	 * @return
	 * @date 2016年6月8日
	 * @author liubo04
	 */
	public static <T> Map<String, T> getMapByProperty(Collection<T> colle, String fieldName) {
		if (colle == null) {
			return null;
		}
		Map<String, T> map = new HashMap<>((colle.size()+1)/2);
		if (colle.size() == 0) {
			return map;
		}
		Iterator<T> it = colle.iterator();
		T t = it.next();
		try {
			BeanGetMethodInvoke method = new BeanGetMethodInvoke(t.getClass(), fieldName); 
			String val = String.valueOf(method.invoke(t));
			map.put(val, t);
			while (it.hasNext()) {
				t = it.next();
				val = String.valueOf(method.invoke(t));
				map.put(val, t);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return map;
	}
	
	/**
	 * 从一个对象集合里面根据指定属性获取Map，key为属性值，value为对象
	 * @param colle 对象集合
	 * @param pro 属性获取处理器
	 * @return
	 * @date 2016年6月8日
	 * @author liubo04
	 */
	public static <T, P> Map<String, T> getMap(Collection<T> colle, CollectionProperty<T, P> pro) {
		if (colle == null) {
			return null;
		}
		if (colle.isEmpty()) {
			return new HashMap<>();
		}
		Map<String, T> map = new HashMap<>((colle.size()+1)/2);
		Iterator<T> it = colle.iterator();
		T t = it.next();
		try {
			
			String val = String.valueOf(pro.getProperty(t));
			map.put(val, t);
			while (it.hasNext()) {
				t = it.next();
				val = String.valueOf(pro.getProperty(t));
				map.put(val, t);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return map;
	}
	
	/**
	 * 将集合按照某个字段转换成map
	 * @param colle
	 * @param fieldName
	 * @return
	 * @date 2017年6月14日
	 * @author liubo04
	 */
	public static <T> Map<String, List<T>> getMapListByProperty(Collection<T> colle, String fieldName){
		Map<String, List<T>> map = new HashMap<>(colle.size()/2);
		if(isEmpty(colle)){
			return map;
		}
		Iterator<T> it = colle.iterator();
		T t = it.next();
		try {
			BeanGetMethodInvoke method = new BeanGetMethodInvoke(t.getClass(), fieldName); 
			deal(map, String.valueOf(method.invoke(t)), t);
			while (it.hasNext()) {
				t = it.next();
				deal(map, String.valueOf(method.invoke(t)), t);
			}
		} catch (Exception e) {
			logger.error("转换失败");
			throw new RuntimeException("集合转换失败", e);
		}
		return map;
	}

	private static <T> void deal(Map<String, List<T>> map, String k, T t) {
		List<T> ls = map.get(k);
		if(ls == null){
			ls = new ArrayList<>();
			map.put(k, ls);
		}
		ls.add(t);
	}
	
	public static <T> List<T> array2List(T[] arr){
		if(isEmpty(arr)){
			return new ArrayList<>();
		}
		List<T> list = new ArrayList<>();
		for (T i : arr) {
			list.add(i);
		}
		return list;
	}
	
	public static List<Integer> array2List(int[] arr){
		if(arr == null || arr.length == 0){
			return new ArrayList<>();
		}
		List<Integer> list = new ArrayList<>();
		for (int i : arr) {
			list.add(i);
		}
		return list;
	}
	
	
	public static <T> List<T> intersection(List<T> c1, List<T> c2, String fieldName){
		if(isEmpty(c1) || isEmpty(c2)){
			return new ArrayList<>();
		}
		Map<String, T> map1 = getMapByProperty(c1, fieldName);
		List<T> list = new ArrayList<>();
		T t0 = c2.get(0);
		BeanGetMethodInvoke method = new BeanGetMethodInvoke(t0.getClass(), fieldName); 
		try {
			for (T t : c2) {
				if(map1.containsKey(method.invoke(t))){
					list.add(t);
				}
			}
		} catch (Exception e) {
			logger.error("求交集失败");
			throw new RuntimeException("求交集失败", e);
		} 
		return list;
	}
	

}
