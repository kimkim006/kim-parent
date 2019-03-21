package com.kim.zuul.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kim.common.response.MsgCode;
import com.kim.common.response.ResultResponse;
import com.kim.zuul.common.CommonConstant;
import com.netflix.client.ClientException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.zuul.exception.ZuulException;

@RestController
public class ErrorHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private boolean checkKnownException(Throwable cause){
		return cause instanceof ClientException 
				|| cause instanceof HystrixRuntimeException;
	}
	
	private MsgCode logKnownError(Throwable cause, String path, Object message, Object statusCode){
		MsgCode msgCode = MsgCode.SYSTEM_ERROR;
		if(cause instanceof ClientException){
			msgCode = MsgCode.SERVICE_UNVALIABLE;
        	logger.error("服务调用出错, path:{}, error:{}", path, cause.getMessage());
        }else if (cause instanceof HystrixRuntimeException) {
        	msgCode = MsgCode.SERVICE_TIMEOUT;
			logger.error("服务调用超时, path:{}, error:{}", path, cause.getMessage());
		}
		logger.error("error.status_code:{}", statusCode);
		logger.error("error.message:{}", message);
		logger.error("error.detail.message:{}", cause.getMessage());
		return msgCode;
	}
	
	@GetMapping(value = "/error")
	public ResponseEntity<ResultResponse> error(HttpServletRequest request) {
		Object message = request.getAttribute("javax.servlet.error.message");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Object statusCode = request.getAttribute("javax.servlet.error.status_code");
		
		String path = String.valueOf(request.getAttribute(CommonConstant.REQUEST_PATH));
		Throwable cause = throwable;
		boolean isKnown = checkKnownException(cause);
		if(!isKnown){
			while(cause.getCause() != null){
	        	cause = cause.getCause();
	        	if(isKnown = checkKnownException(cause)){
	        		break;
	        	}
	        }
		}
		MsgCode msgCode = MsgCode.SYSTEM_EXCEPTION;
		if(isKnown){
			msgCode = logKnownError(cause, path, message, statusCode);
		}else{
			if (cause instanceof ZuulException) {
				msgCode = MsgCode.SYSTEM_ERROR;
	        	logger.error("服务转发出错, path:{}, error:{}", path, cause.getMessage());
			}else{
				logger.error(String.format("服务转发出异常, path:%s, error:%s", path, cause.getMessage()), cause);
			}
			logError(cause, message, statusCode);
		}
		
		return new ResponseEntity<>(new ResultResponse(msgCode), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void logError(Throwable cause, Object message, Object statusCode) {
		logger.error("error.status_code:{}", statusCode);
		logger.error("error.message:{}", message);
		logger.error("error.detail.message:{}", cause.getMessage());
		logger.error("error.exception:", cause);
	}

}
