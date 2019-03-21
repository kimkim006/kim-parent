package com.kim.common.util.paramter.annotation;

import com.kim.common.util.paramter.ParamConverter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * java对象转换到map的时候，对参数字段进行转换
 * @date 2017年5月12日
 * @author liubo04
 */
@Documented
@Inherited
@Target(value={ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamConvert {
	
	/**
	 * 转换器
	 * @return
	 * @date 2017年5月12日
	 * @author liubo04
	 */
	ParamConverter instance() default ParamConverter.DATE_STR2TIMESTAMP;
	
	/**
	 * 转换的时候需要的格式
	 * @return
	 * @date 2017年5月12日
	 * @author liubo04
	 */
	String format() default "yyyy-MM-dd HH:mm:ss";

}
