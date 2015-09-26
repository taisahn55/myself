package com.hongguang.jaia_bean;

public class DepositBean {

	private String createDate;
	private String credit;

	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	private int depositType;
	private int salesman_id;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getDepositType() {
		return depositType;
	}
	public void setDepositType(int depositType) {
		this.depositType = depositType;
	}
	public int getSalesman_id() {
		return salesman_id;
	}
	public void setSalesman_id(int salesman_id) {
		this.salesman_id = salesman_id;
	}
}
