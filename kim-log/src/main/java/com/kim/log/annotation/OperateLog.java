package com.kim.log.annotation;

import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.OperModule;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import org.slf4j.event.Level;

import java.lang.annotation.*;


/**
 * 记录操作日志，并打印入参和返回结果
 * @date 2017年3月28日
 * @author liubo04
 */
@Documented
@Inherited
@Target(value={ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {
	
	/**
	 * 接口的模块
	 * <p><b>支持类级别和方法级别</b>
	 * 在方法上的配置会覆盖类上面的配置，覆盖策略为，配置了相同属性的进行值覆盖，不同属性的进行配置合并
	 * @return
	 * @date 2017年3月22日
	 * @author liubo04
	 */
	String module() default OperModule.UNDEFINED;

	/**
	 * 操作名称rn
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	String oper() default Operation.UNDEFINED;
	
	/**
	 * 操作
	 * <p><b>只支持方法级别</b>
	 * 若在方法上面加此注解，则所有该类的子类的未覆盖的此方法也将有该注解
	 * @return
	 * @date 2017年3月22日
	 * @author liubo04
	 */
	OperationType operType() default OperationType.QUERY;
	
	/**
	 * 是否记录操作日志
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	boolean logOperation() default true;
	
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
	 * 查询旧数据时的条件字段,删除时使用该些字段为条件删除记录
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	String[] conKey() default {"id"};
	
	/**
	 * 新增和删除的时候需要指定用于获取该条记录的关键值的字段名称
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	String[] priKey() default {};
	
	/**
	 * 额外的日志内容
	 * @return
	 * @date 2017年4月26日
	 * @author liubo04
	 */
	String extraContent() default "";
	
	/**
	 * 要打印的参数从指定的类型中获取
	 * <p><b>只支持方法级别</b>
	 * 若在方法上面加此注解，则所有该类的子类的未覆盖的此方法也将有该注解
	 * @return
	 * @date 2017年3月22日
	 * @author liubo04
	 */
	ParameterType[] parameterType() default {ParameterType.QUERY};
	
	/**
	 * 打印日志级别
	 * <p><b>支持类级别和方法级别</b>
	 * 在方法上的配置会覆盖类上面的配置，覆盖策略为，配置了相同属性的进行值覆盖，不同属性的进行配置合并
	 * @return
	 * @date 2017年3月22日
	 * @author liubo04
	 */
	Level level() default Level.INFO;
	
	/**
	 * 自定义数据对象
	 * <p><b>只支持方法级别</b>
	 * 若在方法上面加此注解，则所有该类的子类的未覆盖的此方法也将有该注解
	 * @return
	 * @date 2017年3月24日
	 * @author liubo04
	 */
	String[] obj() default {};
	
	/**
	 * 在打印返回结果日志的时候，是否打印data中的数据
	 * <p><b>只支持方法级别</b>
	 * 若在方法上面加此注解，则所有该类的子类的未覆盖的此方法也将有该注解
	 * @return
	 * @date 2017年3月24日
	 * @author liubo04
	 */
	boolean printResData() default false;
	
	/**
	 * 是否启用自动打印入参和返回值的日志
	 * <p><b>只支持方法级别</b>
	 * 若在方法上面加此注解，则所有该类的子类的未覆盖的此方法也将有该注解
	 * @return
	 * @date 2017年3月25日
	 * @author liubo04
	 */
	boolean autoPrint() default true;
	
	/**
	 * 用于保存操作时新增和修改的判断依据字段
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	String saveLable() default "id";
	
	/**
	 * 是否注入当前登录用户
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	boolean injectCurntUser() default true;
	/**
	 * 是否注入当前登录用户的租户
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	boolean injectCurntTenant() default true;
	
	/**
	 * 是否注入当前操作时间
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	boolean injectCurntDate() default true;
	
	/**
	 * 打印运行耗时，默认不打印
	 * @return
	 * @date 2017年5月23日
	 * @author liubo04
	 */
	boolean elapsedTime() default false;
	
	/**
	 * 打印请求来源ip，默认不打印
	 * @return
	 * @date 2017年5月23日
	 * @author liubo04
	 */
	boolean printIp() default true;
	

}
