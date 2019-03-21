package com.kim.common.validation;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import com.kim.common.util.BeanUtil;
import com.kim.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
	
	private static final String CHARSET = "utf-8";
	private static final String FORMAT_REQUIRE_CHECK = "%s不能为空!";
	private static final String FORMAT_MIN_LENGTH_CHECK = "%s长度不能少于%s个字符!";
	private static final String FORMAT_MAX_LENGTH_CHECK = "%s长度不能超过%s个字符!";
	
	public static String checkEntity(Object o, ValidateEntity ... valids ){
		return checkEntity(o, Arrays.asList(valids));
	}
	
	public static String checkEntity(Object o, List<ValidateEntity> list){
		String res;
		Object value;
		for (ValidateEntity valid : list) {
			value = BeanUtil.getValueByName(o, valid.getFieldCode());
			res = ValidateUtil.check(valid, String.valueOf(value));
			if(res != null){
				return res;
			}
		}
		return null;
	}
	
	public static String check(ValidateEntity v, String value){
		if(value == null || v == null){
			return null;
		}
		String tipStr = (StringUtil.isBlank(v.getFieldDesc())? v.getFieldCode() : v.getFieldDesc());
		if(StringUtil.isBlank(value)){
			if(v.isRequire()){
				return String.format(FORMAT_REQUIRE_CHECK, tipStr);
			}else{
				return null;
			}
		}
		
		if(v.getMinLength() != null || v.getMaxLength() != null){
			int length = getStringLength(value);
			if(v.getMaxLength() != null && length > v.getMaxLength()){
				return String.format(FORMAT_MAX_LENGTH_CHECK, tipStr, v.getMaxLength());
			}
			if(v.getMinLength() != null && v.getMinLength() > 0 && length < v.getMinLength()){
				return String.format(FORMAT_MIN_LENGTH_CHECK, tipStr, v.getMinLength());
			}
		}
		
		
		if(StringUtil.isBlank(v.getReg()) && StringUtil.isBlank(v.getValidatorName()) && v.getValidator() == null){
			return null;
		}
		if(StringUtil.isNotBlank(v.getReg()) && !value.matches(v.getReg())){
			return StringUtil.isNotBlank(v.getRegTip())?v.getRegTip():"匹配失败,reg:"+v.getReg();
		}
		if(v.getValidator() != null){
			return v.getValidator().check(value);
		}
		if(StringUtil.isNotBlank(v.getValidatorName())){
			v.setValidator(getValidator(v.getValidatorName()));
			if(v.getValidator() != null){
				return v.getValidator().check(value);
			}
		}
		return null;
	}

	private static int getStringLength(String value) {
		try {
			return value.trim().getBytes(CHARSET).length;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}
	
	public static Validator getValidator(String validatorName) {
		try {
			Class<?> clazz = Class.forName(validatorName);
			if(clazz != null && clazz.isAssignableFrom(Validator.class)){
				return (Validator)clazz.newInstance();
			}
		} catch (ClassNotFoundException e) {
			logger.error("未找到该类文件，class:"+validatorName, e);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("类文件实例化失败，class:"+validatorName, e);
		}
		return null;
	}

}
