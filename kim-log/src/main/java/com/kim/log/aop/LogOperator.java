package com.kim.log.aop;

import com.kim.log.interceptor.OperateLogHandler;
import com.kim.log.interceptor.ParamSet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgCode.MsgCodeType;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.NumberUtil;
import com.kim.log.interceptor.OperateLogHandlerUtil;


@Component
@Aspect
public class LogOperator {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Pointcut("execution(* com.kim..*.controller..*.*(..)) &&@annotation(com.kim.log.annotation.OperateLog)")
    public void pointCut(){}
    
    @Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    	//获取方法的参数
    	Object[] args = joinPoint.getArgs();
    	
    	CurrentUserArgumentResolver.inject(args);
    	operateLog(args);
    	
    	Object object = joinPoint.proceed(args);
    	submitLog(object);
		return object;
	}

	private void submitLog(Object object) {
		try {
    		if(object == null){
    			OperateLogHandler.handOperLogSubmit();
    			return;
    		}

    		OperateLogHandlerUtil.logOutputParam(object);
			int code = 0;
			if(object instanceof ResultResponse){
				code = ((ResultResponse)object).getCode();
			}else{
				String codeStr = String.valueOf(BeanUtil.getValue(object, "code")).trim();
				if(NumberUtil.isNumber(codeStr)){
    				code = Integer.parseInt(codeStr);
    			}else{
    				logger.error("无法识别的结果状态, 将放弃记录操作日志, code:{}", codeStr);
    				return ;
    			}
			}
			
			MsgCode msgCode = MsgCode.parseMsgCode(code);
			if(msgCode == null || msgCode.getType() == null){
				logger.error("无法识别的结果状态, 将放弃记录操作日志, code:{}", code);
				return ;
			}
			if(msgCode.getType() == MsgCodeType.SUCCESS){
				OperateLogHandler.handOperLogSubmit();
			}else{
				logger.debug("操作结果为失败，放弃记录操作日志!");
			}
		} catch (Exception e) {
			logger.error("操作日志处理出现异常", e);
		}
	}

	private void operateLog(Object[] args) {
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
    	paramSet.setMethodParams(args);
    	logger.debug("收集操作日志开始...");
		OperateLogHandler.handOperationLog(paramSet);
		logger.debug("收集操作日志结束!");
	}
    
}