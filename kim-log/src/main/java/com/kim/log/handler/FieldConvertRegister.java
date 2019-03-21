package com.kim.log.handler;

public enum FieldConvertRegister {
	
//	USER(new UserDefinedFieldConvert()),
	DEF(new DefaultFieldConvert());
	
	private FieldConvertRegister(FieldConvert fieldConvert) {
		this.fieldConvert = fieldConvert;
	}

	private FieldConvert fieldConvert;

	public FieldConvert getFieldConvert() {
		return fieldConvert;
	}

	public void setFieldConvert(FieldConvert fieldConvert) {
		this.fieldConvert = fieldConvert;
	}

}
