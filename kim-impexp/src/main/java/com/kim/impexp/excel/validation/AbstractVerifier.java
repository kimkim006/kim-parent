package com.kim.impexp.excel.validation;

public abstract class AbstractVerifier<T> implements Verifier<T>{
	
	protected boolean activeBatchCheck;

	@Override
	public boolean isActiveBatchCheck() {
		return activeBatchCheck;
	}

	public void setActiveBatchCheck(boolean activeBatchCheck) {
		this.activeBatchCheck = activeBatchCheck;
	}

}
