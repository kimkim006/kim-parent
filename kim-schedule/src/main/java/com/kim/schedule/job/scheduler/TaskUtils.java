package com.kim.schedule.job.scheduler;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.context.ContextHolder;
import com.kim.common.exception.BusinessException;
import com.kim.common.lock.RedisLockOnce;
import com.kim.common.util.Base64Util;
import com.kim.common.util.HttpServletUtil;
import com.kim.schedule.job.annotation.TaskTarget;
import com.kim.schedule.job.entity.TaskJobEntity;

public class TaskUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);
	
	private static final String JOB_ORIG_REDIS_LOCK_KEY = "job:%s";

	public static void invokMethod(TaskJobEntity scheduleJob) {
		long l = System.currentTimeMillis();
		logger.info("调度任务开始执行,job：{}, random：{}", scheduleJob, l);
		
		RedisLockOnce lockOnce = new RedisLockOnce(String.format(JOB_ORIG_REDIS_LOCK_KEY, 
				Base64Util.encode(scheduleJob.getJobName())));
		try {
			boolean res = lockOnce.lock();
			if(!res){
				logger.error("任务正在被其他线程或服务执行，当前线程将终止执行命令! lockKey:{}", lockOnce.getLockKey());
				return ;
			}
			invoke(scheduleJob);
		} catch (Exception e) {
			logger.error("执行任务异常, traceId:"+ContextHolder.getTraceId(), e);
			throw e;
		} finally{
			logger.info("调度任务执行结束, random：{}", l);
			lockOnce.unLock();
		}
	}
	
	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	private static void invoke(TaskJobEntity scheduleJob) {
		Object object = null;
		// 优先根据beanId查找bean
		if (StringUtils.isNotBlank(scheduleJob.getBeanId())) {
			try {
				object = HttpServletUtil.getBean(scheduleJob.getBeanId());
			} catch (Exception e) {
				logger.error("任务计划执行目标对象名称无效, job:{}", scheduleJob);
				throw new BusinessException("执行目标对象名称无效，无法执行任务", e);
			}
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			object = getTargetObjectByType(scheduleJob);
		}else{
			logger.error("任务计划未配置执行目标对象, job:{}", scheduleJob);
			throw new BusinessException("未配置执行目标对象");
		}
		if (object == null) {
			logger.error("任务计划未配置执行目标对象为空，请检查配置, job:{}", scheduleJob);
			return;
		}
		Method method = null;
		try {
			method = object.getClass().getDeclaredMethod(scheduleJob.getMethodName(), Map.class);
		} catch (Exception e) {
			logger.error("任务计划启动失败，方法设置错误, job:{}", scheduleJob);
			throw new BusinessException("任务计划启动失败, 方法设置错误!", e);
		}
		Map<String, Object> param = new HashMap<>();
		if(StringUtils.isNotBlank(scheduleJob.getExeParam())){
			try {
				param = JSONObject.parseObject(scheduleJob.getExeParam());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		try {
			method.invoke(object, param);
		} catch (Exception e) {
			logger.error("任务计划执行失败, job", scheduleJob);
			throw new BusinessException("任务计划执行失败, 方法调用失败!", e);
		}
	}

	private static Object getTargetObjectByType(TaskJobEntity scheduleJob){
		
		try {
			Class<?> clazz = Class.forName(scheduleJob.getBeanClass());
			Object object = HttpServletUtil.getBean(clazz);
			if(object == null){
				object = clazz.newInstance();
			}
			return object;
		} catch (ClassNotFoundException e) {
			logger.error("调度任务执行找不到执行目标类, job:{}", scheduleJob);
			throw new BusinessException("找不到执行目标类", e);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("调度任务执行执行目标类实例化失败, job:{}", scheduleJob);
			throw new BusinessException("执行目标类实例化失败", e);
		} catch (Exception e) {
			logger.error("调度任务执行任务计划配置执行目标对象类型异常, job:{}", scheduleJob);
			throw new BusinessException("任务计划启动失败", e);
		}
	}
	
	/**
	 * 检查job的有效性
	 * @param job
	 * @return
	 * @date 2017年5月23日
	 * @author liubo04
	 */
	public static String checkJob(TaskJobEntity job) {
		if(StringUtils.isBlank(job.getJobName())){
			return "任务名称不能为空";
		}
		if(StringUtils.isBlank(job.getCronExpression())){
			return "表达式不能为空";
		}
		try {
			new CronExpression(job.getCronExpression());
		} catch (ParseException e) {
			return "无效的表达式";
		}
		if(StringUtils.isBlank(job.getCronExpDesc())){
			return "表达式描述不能为空";
		}
		
		Class<?> clazz = null;
		if(StringUtils.isNotBlank(job.getBeanId())){
			try {
				Object bean = HttpServletUtil.getBean(job.getBeanId());
				if(bean == null){
					return "不存在的beanId";
				}
				clazz = bean.getClass();
			} catch (Exception e) {
				return "无效的beanId";
			}
		}else if(StringUtils.isNotBlank(job.getBeanClass())){
			try {
				clazz = Class.forName(job.getBeanClass());
				if(clazz.getAnnotation(TaskTarget.class) == null){
					return "目标类不可用于定时调度";
				}
				if(!clazz.isInterface()){
					clazz.newInstance();
				}
			} catch (ClassNotFoundException e) {
				return "无效的类路径";
			} catch (InstantiationException e) {
				return "所配类无法实例化对象";
			} catch (IllegalAccessException e) {
				return "所配类没有实例化权限";
			} 
		}else{
			return "执行目标至少指定一个，beanClass 或 beanId";
		}
		
		if(StringUtils.isBlank(job.getMethodName())){
			return "执行方法不能为空";
		}
		try {
			Method method = clazz.getMethod(job.getMethodName(), Map.class);
			if(method.getAnnotation(TaskTarget.class) == null){
				return "目标方法不可用于定时调度";
			}
		} catch (NoSuchMethodException e) {
			return "执行方法不存在或无效, 方法参数类型必须为Map<String, Object>";
		} catch (SecurityException e) {
			return "执行方法不可访问";
		}
		
		if(StringUtils.isNotBlank(job.getExeParam())){
			try {
				JSONObject.parse(job.getExeParam());
			} catch (Exception e) {
				return "参数请填写json格式";
			}
		}
		
		return null;
	}
}