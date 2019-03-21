package com.kim.common.validation;

import java.io.Serializable;

public interface Validator extends Serializable{
	
	String check(String value);

}
