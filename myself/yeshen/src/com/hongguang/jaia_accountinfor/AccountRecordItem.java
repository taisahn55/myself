package com.hongguang.jaia_accountinfor;

public class AccountRecordItem {

	private String id;
	private String depositType;
	private String salesman_id;
	private String datetime;
	private double credit;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepositType() {
		return depositType;
	}
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	public String getSalesman_id() {
		return salesman_id;
	}
	public void setSalesman_id(String salesman_id) {
		this.salesman_id = salesman_id;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public AccountRecordItem(String id, String depositType, String salesman_id,
			String datetime, double credit) {
		super();
		this.id = id;
		this.depositType = depositType;
		this.salesman_id = salesman_id;
		this.datetime = datetime;
		this.credit = credit;
	}

}
