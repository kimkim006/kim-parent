package com.kim.impexp.excel.entity;

/**
 * @author bo.liu01
 *
 */
public class TestExcelReadEntity extends ImportEntity{
	
	private static final long serialVersionUID = 1L;
	private String carNo;
	private String carType;
	private String inTime;
	private String outTime;
	private String payType;
	private double fee;
	private double money;
	private String feeTime;
	private String feeUser;
	private String order;
	
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getFeeTime() {
		return feeTime;
	}
	public void setFeeTime(String feeTime) {
		this.feeTime = feeTime;
	}
	public String getFeeUser() {
		return feeUser;
	}
	public void setFeeUser(String feeUser) {
		this.feeUser = feeUser;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "TestExcelRead [carNo=" + carNo + ", carType=" + carType + ", inTime=" + inTime + ", outTime=" + outTime
				+ ", payType=" + payType + ", fee=" + fee + ", money=" + money + ", feeTime=" + feeTime + ", feeUser="
				+ feeUser + ", order=" + order + "]";
	}

}
