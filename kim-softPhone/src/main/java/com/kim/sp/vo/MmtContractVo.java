package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

public class MmtContractVo extends BaseVo{

	private static final long serialVersionUID = -5501310331745742814L; 
	
	private String idCard;
	private String contractNo;
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

}
