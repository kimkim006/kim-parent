package com.kim.sp.entity;

import com.kim.common.base.BaseEntity;
import com.kim.common.util.StringUtil;

/**
 * @author liubo
 *
 */
public class MmtContract extends BaseEntity {
	
	private static final long serialVersionUID = -6220800909835696627L;
	
	/** 0 从NCP系统copy过来的，源头没写注释，也没找到啥意思，想要了解具体的，还是去NCP或者CRM系统查吧  */
	public static final String SYSTEM_CODE_ZORE = "0" ;
	/** 1 旧循环消费分期 编码 */
	public static final String SYSTEM_CODE_OLD_LOOP_CONSUME = "1";
	public static final String SYSTEM_NAME_OLD_LOOP_CONSUME = "旧循环消费分期";

	/** 2 新循环消费分期 编码 */
	public static final String SYSTEM_CODE_NEW_LOOP_CONSUME = "2";
	public static final String SYSTEM_NAME_NEW_LOOP_CONSUME = "新循环消费分期";
	
	/**3 买买提个人消费分期/佰仟CRM */
	public static final String SYSTEM_CODE_BQ_MMT_PERSON_CONSUME = "3";
	public static final String SYSTEM_NAME_MMT_PERSON_CONSUME = "买买提个人消费分期";
	public static final String SYSTEM_NAME_BQ_PERSON_CONSUME = "佰仟CRM";
	/** 合同状态301 */
	public static final String CONTRACT_STATUS_301 = "301";
	
	//合同编号
	private String contractNo;
	//客户编号
	private String custId;
	//分期时间
	private String registerDate;
	//订单类型
	private String loanType;
	//分期期数
	private String loanTerm;
	//分期金额
	private String loanPrin;
	//每期应还金额
	private String loanFixedPrin;
	//账单日
	private String dueDate;
	//合同状态
	private String contractStatus;
	//系统编码
	private String systemCode;
	//系统名称
	private String systemName;
	//服务编码
	private String serviceId;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getLoanPrin() {
		return loanPrin;
	}

	public void setLoanPrin(String loanPrin) {
		this.loanPrin = loanPrin;
	}

	public String getLoanFixedPrin() {
		return loanFixedPrin;
	}

	public void setLoanFixedPrin(String loanFixedPrin) {
		this.loanFixedPrin = loanFixedPrin;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getSystemCode() {
		return systemCode;
	}
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemName() {
		if(StringUtil.isBlank(systemName) && StringUtil.isNotBlank(systemCode)){
			switch (systemCode) {
			case SYSTEM_CODE_OLD_LOOP_CONSUME: this.systemName = SYSTEM_NAME_OLD_LOOP_CONSUME;break;
			case SYSTEM_CODE_NEW_LOOP_CONSUME: this.systemName = SYSTEM_NAME_NEW_LOOP_CONSUME;break;
			case SYSTEM_CODE_BQ_MMT_PERSON_CONSUME: 
				this.systemName = CONTRACT_STATUS_301.equals(contractStatus)?
						SYSTEM_NAME_BQ_PERSON_CONSUME:SYSTEM_NAME_MMT_PERSON_CONSUME;
				break;
			default:
				this.systemName = systemCode;
			}
		}
		return systemName;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
