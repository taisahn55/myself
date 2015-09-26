package com.hongguang.jaia_bean;

public class BaoDanInforation {

	// 保单信息表
	private String policyId;// 保单ID
	private String productId;// 产品ID
	private String amount;// 身份正照片2
	private String applyCode;// '9702004173'
	private String channelId;// 渠道ID

	private String startDate;// 保单开始履行时间
	private String endDate;// 保单有效截止时间

	private String salesman_id;// salesman_id
	private String policyHolder_id;// 受益人ID
	private String orderId;// 交易记录ID
	private String orderCode;// 交易编号
	private String orderCharge_id;// 交易编号
	private String isLegal;//
	private String insuredSelect;//
	private String insuredAndBeneficiaryList_id;// 交易编号
	private String flowCode;// 交易编号
	public BaoDanInforation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaoDanInforation(String policyId, String productId, String amount,
			String applyCode, String channelId, String startDate,
			String endDate, String policyHolder_id, String orderId,
			String orderCode, String orderCharge_id, String isLegal,
			String insuredSelect, String insuredAndBeneficiaryList_id,
			String flowCode) {
		super();
		this.policyId = policyId;
		this.productId = productId;
		this.amount = amount;
		this.applyCode = applyCode;
		this.channelId = channelId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.policyHolder_id = policyHolder_id;
		this.orderId = orderId;
		this.orderCode = orderCode;
		this.orderCharge_id = orderCharge_id;
		this.isLegal = isLegal;
		this.insuredSelect = insuredSelect;
		this.insuredAndBeneficiaryList_id = insuredAndBeneficiaryList_id;
		this.flowCode = flowCode;
	}

	public String getSalesman_id() {
		return salesman_id;
	}
	public void setSalesman_id(String salesman_id) {
		this.salesman_id = salesman_id;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPolicyHolder_id() {
		return policyHolder_id;
	}
	public void setPolicyHolder_id(String policyHolder_id) {
		this.policyHolder_id = policyHolder_id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderCharge_id() {
		return orderCharge_id;
	}
	public void setOrderCharge_id(String orderCharge_id) {
		this.orderCharge_id = orderCharge_id;
	}
	public String getIsLegal() {
		return isLegal;
	}
	public void setIsLegal(String isLegal) {
		this.isLegal = isLegal;
	}
	public String getInsuredSelect() {
		return insuredSelect;
	}
	public void setInsuredSelect(String insuredSelect) {
		this.insuredSelect = insuredSelect;
	}
	public String getInsuredAndBeneficiaryList_id() {
		return insuredAndBeneficiaryList_id;
	}
	public void setInsuredAndBeneficiaryList_id(
			String insuredAndBeneficiaryList_id) {
		this.insuredAndBeneficiaryList_id = insuredAndBeneficiaryList_id;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

}
