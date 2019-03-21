package com.kim.log.handler;

import com.kim.log.operatelog.OperationType;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class DefaultFieldConvert extends AbstractFieldConvert {
	
	@Override
	public String convertValue(Object o, Field field) {
		return FieldDisplayHandler.getFieldDisplayValue(o, field);
	}
	
	@Override
	public String convert(Object o, Field field) {
		return FieldDisplayHandler.getFieldDisplayName(field);
	}

	@Override
	public List<Field> getFields(Object o, String[] fields, OperationType operationType) {
		return getFields(o, fields, operationType, false);
	}
	
	@Override
	public List<Field> getCompareFields(Object o, String[] fields, OperationType operationType) {
		return getFields(o, fields, operationType, false);
	}
	
	private List<Field> getFields(Object o, String[] fields, OperationType operationType, boolean isCheck) {
		List<Field> fds = BeanUtil.getBusinessFields(o.getClass());
		fds = filter(fds);
		
		if(fields == null || fields.length == 0){
			return fds;
		}
		
		Map<String, Field> fdsMap = CollectionUtil.getMapByProperty(fds, "name");
		fds.clear();
		Field fd = null;
		if(isCheck){
			for (String str : fields) {
				if((fd = fdsMap.get(str)) == null){
					continue;
				}
				if(FieldDisplayHandler.checkAnnoCompare(fd, operationType)){
					fds.add(fd);
				}
			}
		}else{
			for (String str : fields) {
				if((fd = fdsMap.get(str)) == null){
					continue;
				}
				fds.add(fd);
			}
		}
		return fds;
	}
	
	private List<Field> filter(List<Field> fds) {
		//过滤没有注解的字段
		fds = FieldDisplayHandler.filter(fds);
		
		//调用过滤
		List<Field> _fds = new ArrayList<>();
		for (Field fd : fds) {
			if(filterType(fd)){
				continue;
			}
			_fds.add(fd);
		}
		return _fds;
	}
	
	/**
	 * 写点啥吧
	 * @param fd
	 * @return true将被过滤掉，false会参与处理
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	private boolean filterType(Field fd){
		if(fd.getType().isAssignableFrom(Collection.class)){
			logger.info("字段类型为集合，将忽略记录该字段的操作日志，Class:{}, fieldName：{}", fd.getDeclaringClass().getSimpleName(), fd.getName());
			return true;
		}
		if(fd.getType().isAssignableFrom(Map.class)){
			logger.info("字段类型为Map，将忽略记录该字段的操作日志，Class:{}, fieldName：{}", fd.getDeclaringClass().getSimpleName(), fd.getName());
			return true;
		}
		String typeName = fd.getType().getSimpleName();
		if(StringUtil.equalsIgnoreCase(typeName, "String") || StringUtil.equalsIgnoreCase(typeName, "Boolean")
			|| StringUtil.equalsIgnoreCase(typeName, "int")|| StringUtil.equalsIgnoreCase(typeName, "Integer")
			|| StringUtil.equalsIgnoreCase(typeName, "Long")  
			|| StringUtil.equalsIgnoreCase(typeName, "Double") || StringUtil.equalsIgnoreCase(typeName, "Float")
			|| StringUtil.equalsIgnoreCase(typeName, "Character") || StringUtil.equalsIgnoreCase(typeName, "char")
			|| StringUtil.equalsIgnoreCase(typeName, "Byte")){
			return false;
		}
		logger.info("字段类型为非基础类型，将忽略记录该字段的操作日志，Class:{}, fieldName：{}", fd.getDeclaringClass().getSimpleName(), fd.getName());
		return true;		
	}
	
}
