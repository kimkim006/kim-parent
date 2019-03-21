package com.kim.mybatis.dynamic;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kim.mybatis.dynamic.annotation.TargetDataSource;

@Aspect
@Order(Ordered.LOWEST_PRECEDENCE-1)	//设置AOP执行顺序(需要在事务之前，否则事务只发生在默认库中)
@Component
public class DataSourceAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//切点 
	@Pointcut("execution(* com.kim..*.service..*.*(..)) "
			+ "&&@annotation(com.kim.mybatis.dynamic.annotation.TargetDataSource)")
	public void aspect() { }
	
	//@Before("aspect()")
//	private void before(JoinPoint point) {
//        Method m = ((MethodSignature) point.getSignature()).getMethod();  
//        if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {  
//        	TargetDataSource data = m.getAnnotation(TargetDataSource.class);
//            JdbcContextHolder.putDataSource(data.value());
//            logger.debug("动态切换数据源:{}",data.value().getName());
//        }
//        
//	}
	
	@Around("aspect()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    	//获取方法的参数
    	Object[] args = joinPoint.getArgs();
    	DataSourceType curDataSource = null;
    	Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();  
        if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {  
        	TargetDataSource data = m.getAnnotation(TargetDataSource.class);
        	curDataSource = JdbcContextHolder.getDataSource();
            JdbcContextHolder.putDataSource(data.value());
            logger.debug("动态切换数据源:{}", data.value().getName());
        }
        Object object;
		try {
			object = joinPoint.proceed(args);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw e;
		}finally {
			curDataSource = curDataSource == null? DataSourceType.MASTER:curDataSource;
			JdbcContextHolder.putDataSource(curDataSource);
		}
		return object;
	}
}
