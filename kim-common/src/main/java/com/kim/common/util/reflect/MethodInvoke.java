package com.kim.common.util.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @author bo.liu01
 *
 */
public interface MethodInvoke{
	
	/**
	 * 调用某个对象的此方法
	 * @param obj 要调用的宿主对象
	 * @param params 方法的参数
	 * @return 调用后的返回值，如果该方法没有返回值，则返回null
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	Object invoke(Object obj, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
