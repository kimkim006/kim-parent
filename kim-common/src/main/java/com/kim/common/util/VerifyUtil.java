package com.kim.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.exception.BusinessException;
import com.kim.common.response.MsgCode;

public class VerifyUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifyUtil.class);
	
	public static void verify(String msg, String oper){
		if(msg != null){
			logger.error(msg);
			throw new BusinessException(msg, MsgCode.INVALID_PARAMETER.getCode());
		}
	}

}
