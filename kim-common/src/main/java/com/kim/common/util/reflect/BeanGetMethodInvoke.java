package com.kim.common.util.reflect;

import com.kim.common.util.BeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class BeanGetMethodInvoke extends GetMethodInvoke{

	private Method md;
	private String field;
	private Class<?> clazz;
	private boolean isMapBean = false;
	
	public BeanGetMethodInvoke(Class<?> clazz, String field) {
		super();
		this.field = field;
		this.clazz = clazz;
		if(Map.class.isAssignableFrom(clazz)){
			setMapBean(true);
		}else{
			md = BeanUtil.getGetterMethod(clazz, field);
		}
	}

	@Override
	public Object invoke(Object obj, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(isMapBean){
			return ((Map<?, ?>)obj).get(field);
		}else{
			return md.invoke(obj, params);
		}
	}

	public Method getMd() {
		return md;
	}

	public void setMd(Method md) {
		this.md = md;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public boolean isMapBean() {
		return isMapBean;
	}

	public void setMapBean(boolean isMapBean) {
		this.isMapBean = isMapBean;
	}
}
