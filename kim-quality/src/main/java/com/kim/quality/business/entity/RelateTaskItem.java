package com.kim.quality.business.entity;

import java.util.Date;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;

public interface RelateTaskItem {
	
	/**
	 * 用于排序的时间, 返回一个13位时间戳
	 * @return
	 */
	public default Long getSortTime() {
		if(StringUtil.isBlank(getOperTime())){
			return null;
		}
		Date date = DateUtil.parseDate(getOperTime());
		if(date == null){
			return null;
		}
		return date.getTime();
	}
	
	/**
	 * key，格式：keyType-id
	 * @return
	 */
	public default String getKey() {
		return String.format("%s-%s", getKeyType(), getId());
	}
	
	/**
	 * key类型，参考{@link MaskTask}.KEY_TYPE_XXX
	 * @return
	 */
	String getKeyType();
	
	String getId();
	
	String getOperTime();

}
