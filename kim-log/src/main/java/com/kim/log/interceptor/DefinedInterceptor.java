package com.kim.log.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kim.log.annotation.OperateLog;
import com.kim.common.util.HttpServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 打印入参返回结果数据
 * @date 2017年3月23日
 * @author liubo04
 */
public class DefinedInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			
			HandlerMethod handlerMethod = ((HandlerMethod) handler);
			hand(handlerMethod, request);
		}
		return true;
	}
	
	protected void hand(HandlerMethod handlerMethod, HttpServletRequest request){
		OperateLog clazzOperateLog = handlerMethod.getBeanType().getAnnotation(OperateLog.class);
		OperateLog methodOperateLog = handlerMethod.getMethodAnnotation(OperateLog.class);
		ParamSet paramSet = OperateLogHandler.mergeOperateLog(clazzOperateLog, methodOperateLog);
		if(paramSet == null){
			logger.info(String.format("[%s][request URI]%s", HttpServletUtil.getIpAddress(), request.getRequestURI()));
			return;
		}
		
		getParam(request, paramSet);
		paramSet.setUrl(request.getRequestURI());
		
		logger.debug("入参数拦截开始");
		OperateLogHandlerUtil.loggingInputParam(paramSet);
		logger.debug("入参拦截结束");
		
	}
	
	private static ParamSet getParam(HttpServletRequest request, ParamSet paramSet){
		if(paramSet.getParameterTypeList().contains(ParameterType.BODY)){
			paramSet.setBodyParam(HttpServletUtil.getParamterBodyString(request));
			JSONObject json = HttpServletUtil.getParamter(request);
			paramSet.setQueryParam(json.size() > 0 ? json.toJSONString() : null);
		}else{
			paramSet.setQueryParam(HttpServletUtil.getParamter(request).toJSONString());
		}
		return paramSet;
	}	
	
}
