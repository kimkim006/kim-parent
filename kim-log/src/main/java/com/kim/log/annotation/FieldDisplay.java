package com.kim.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kim.log.handler.FieldDsplayScope;
import com.kim.log.handler.FieldConvertRegister;


/**
 * 字段显示注释
 * @date 2017年3月27日
 * @author liubo04
 */
@Documented
@Inherited
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDisplay {
	
	/**
	 * 打印日志和记录操作日志的时候，显示的字段名称
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	String value();
	
	/**
	 * 修改操作日志中是否参与比较
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	byte scope() default FieldDsplayScope.ALL;
	
	/**
	 * 是否使用映射取值
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	boolean useMapping() default false;
	
	/**
	 * 优先使用此映射关系作为映射处理
	 * 字段值的映射关系，格式："1=>月租A,2=>月租B,..."
	 */
	String[] mappings() default {};
	
	/**
	 * 查询旧数据的方法,格式："beanName.method",例如："service.find"
	 * 必须保证该bean在Spring IOC中有注册成功，否则将会调用方法失败
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	String beanAndMethod() default "";
	
	/**
	 * 查询旧数据的bean类型
	 * 必须保证该bean在Spring IOC中有注册成功，否则将会调用方法失败
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	Class<?> beanType() default Object.class;
	
	/**
	 * beanType指定的对象的方法名字
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	String methodName() default "findById";
	
	/**
	 * 该字段值的显示依赖于此字段的值, 默认是本字段
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	String dependKey() default "";

//	/**
//	 * 数据字段的类型，如果有填写，将会把pdonKey的值作为数据字典的code值从数据字段中获取内容
//	 * @return
//	 * @date 2017年6月19日
//	 * @author liubo04
//	 */
//	DictionaryDesc dictType() default DictionaryDesc.DefaultType;
	
	/**
	 * 过滤指定的字段
	 * 当注解在类上面的时候，此注解生效
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	String[] filterFields() default {};
	
	/**
	 * 转换器
	 * 当注解在类上面的时候，此注解生效
	 * @return
	 * @date 2017年6月19日
	 * @author liubo04
	 */
	FieldConvertRegister convert() default FieldConvertRegister.DEF;
	
}
