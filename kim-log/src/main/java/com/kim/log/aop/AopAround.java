package com.kim.log.aop;

import com.kim.common.util.BeanUtil;
import com.kim.common.util.paramter.annotation.ParamConvert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;



//@Component
//@Aspect
public class AopAround {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Pointcut("execution(* com.kim..*.controller..*.*(..))")
    public void pointCut(){}
    
    @Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    	//获取方法的参数
    	Object[] args = joinPoint.getArgs();
    	convert(args);
		return joinPoint.proceed(args);
	}

	private void convert(Object[] args) {
		for (Object object : args) {
			List<Field> fields = BeanUtil.getBusinessFields(object.getClass());
			for (Field field : fields) {
				ParamConvert pc = field.getAnnotation(ParamConvert.class);
				if(pc == null){
					continue;
				}
				Object obj = BeanUtil.getValue(object, field);
				if(obj == null){
					continue;
				}
				setValue(object, field, pc.instance().getParamConvert().convert(obj, pc.format()));
			}
		}
	}
	
	private void setValue(Object bean, Field field, Object param) {
		boolean flag = false;
		try {
			flag = field.isAccessible();
			field.setAccessible(true);
			field.set(bean, param);
		} catch (Exception e) {
			
		} finally {
			field.setAccessible(flag);
		}
	}
    
}