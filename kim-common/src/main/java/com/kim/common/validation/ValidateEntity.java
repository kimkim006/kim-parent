package com.kim.common.validation;

import com.kim.common.base.BaseObject;

public class ValidateEntity extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	protected String fieldCode;
	protected String fieldDesc;
	protected Integer minLength;
	protected Integer maxLength;
	protected String reg;
	protected String regTip;
	protected String validatorName;
	protected Validator validator;
	protected boolean require = false;
	
	public ValidateEntity() {
	}
	
	public ValidateEntity(String fieldCode, String fieldDesc, boolean require, int maxLength) {
		super();
		this.fieldCode = fieldCode;
		this.fieldDesc = fieldDesc;
		this.require = require;
		this.maxLength = maxLength;
	}
	
	public ValidateEntity(String fieldCode, String fieldDesc, int maxLength) {
		super();
		this.fieldCode = fieldCode;
		this.fieldDesc = fieldDesc;
		this.maxLength = maxLength;
	}
	
	public ValidateEntity(String fieldCode, String fieldDesc, int minLength, int maxLength) {
		super();
		this.fieldCode = fieldCode;
		this.fieldDesc = fieldDesc;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}
	
	public ValidateEntity(String fieldCode, String fieldDesc, String reg, String regTip) {
		super();
		this.fieldDesc = fieldDesc;
		this.fieldCode = fieldCode;
		this.reg = reg;
		this.regTip = regTip;
	}
	
	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getRegTip() {
		return regTip;
	}
	public void setRegTip(String regTip) {
		this.regTip = regTip;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public String getValidatorName() {
		return validatorName;
	}
	public void setValidatorName(String validatorName) {
		this.validatorName = validatorName;
	}
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public boolean isRequire() {
		return require;
	}

	public ValidateEntity setRequire(boolean require) {
		this.require = require;
		return this;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public ValidateEntity setMinLength(int minLength) {
		this.minLength = minLength;
		return this;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public ValidateEntity setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		return this;
	}

}
