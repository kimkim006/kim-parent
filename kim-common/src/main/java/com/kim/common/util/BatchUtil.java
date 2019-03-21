package com.kim.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;


/**
 * @Description 批量处理工具
 * @date 2016年9月7日
 * @author liubo04
 */
public class BatchUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchUtil.class);
	
	/** 批量插入方法名 */
	public static final String INSERT_BATCH = "insertBatch";
	public static final String VOID = "void";
	
	/**
	 * 批量插入工具，可分批插入数据，每批500条数据，使用默认的批量插入方法 insertBatch
	 * 
	 * @param dao dao层对象
	 * @param list 插入的数据
	 * @return
	 * @throws Exception
	 * @date 2016年9月7日
	 * @author liubo04
	 */
	public static <T> int batchInsert(Object dao, List<T> list){
		return batchInsert(dao, null, list);
	}
	
	/**
	 * 批量插入工具，可分批插入数据，每批batchNum条数据，使用默认的批量插入方法 insertBatch
	 * 
	 * @param dao dao层对象
	 * @param list 插入的数据
	 * @param batchNum 每批次的数量
	 * @return
	 * @throws Exception
	 * @date 2016年9月7日
	 * @author liubo04
	 */
	public static <T> int batchInsert(Object dao, List<T> list, int batchNum){
		return batchInsert(dao, null, list, batchNum);
	}
	
	/**
	 * 批量插入工具，可分批插入数据，每批500条数据
	 * 
	 * @param dao dao层对象
	 * @param methodName 方法名称
	 * @param list 插入的数据
	 * @return
	 * @throws Exception
	 * @date 2016年9月7日
	 * @author liubo04
	 */
	public static <T> int batchInsert(Object dao, String methodName, List<T> list){
	    return batchInsert(dao, methodName, list, 0);
	}
	
	/**
	 * 批量插入工具，可分批插入数据
	 * 
	 * @param dao dao层对象
	 * @param methodName 方法名称
	 * @param list 插入的数据
	 * @param batchNum 每批的数量
	 * @return
	 * @throws Exception
	 * @date 2016年9月7日
	 * @author liubo04
	 */
	public static <T> int batchInsert(Object dao, String methodName, List<T> list, int batchNum){
		if(dao == null || list == null){
			throw new RuntimeException("批量处理参数为空");
		}
		if(list.size() == 0){
			return 0;
		}
		methodName = StringUtil.isBlank(methodName) ? INSERT_BATCH : methodName;
		List<List<T>> batchList = CollectionUtil.list2BatchList(list, batchNum);
		int n;
		try {
			Method method = dao.getClass().getMethod(methodName, List.class);
			Class<?> returnType = method.getReturnType();
			n = 0;
			if(VOID.equals(returnType.getName())){
				for (List<T> listTmp : batchList) {
					method.invoke(dao, listTmp);
				}
			}else{
				for (List<T> listTmp : batchList) {
					n += (int)method.invoke(dao, listTmp);
				}
			}
		} catch (Exception e) {
			logger.error("批量处理方法调用失败", e);
			throw new RuntimeException("批量处理方法调用失败", e);
		}
		return n;
	}
	
}
