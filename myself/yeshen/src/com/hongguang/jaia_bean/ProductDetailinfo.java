package com.hongguang.jaia_bean;

public class ProductDetailinfo {
	// 投保详情

	private String name;// 责任名称
	private String product_id;// 产品ID
	private String price;// 起始价格
	private String amount;// 保额
	private String pricerate;// 费用率
	private String note;// 备注
	private String id;// ID
	private String createDate;// 创建日期
	private String modifyDate;// 修改日期
	private String insuranceliability;// 保险责任

	private String plan1;// 计划1保额
	private String plan2;// 计划2保额
	private String plan3;// 计划3保额
	private String plan4;// 计划3保额

	public String getPlan1() {
		return plan1;
	}
	public void setPlan1(String plan1) {
		this.plan1 = plan1;
	}
	public String getPlan2() {
		return plan2;
	}
	public void setPlan2(String plan2) {
		this.plan2 = plan2;
	}
	public String getPlan3() {
		return plan3;
	}
	public void setPlan3(String plan3) {
		this.plan3 = plan3;
	}
	public String getPlan4() {
		return plan4;
	}
	public void setPlan4(String plan4) {
		this.plan4 = plan4;
	}
	public ProductDetailinfo(String name, String amount,
			String insuranceliability) {
		super();
		this.name = name;
		this.amount = amount;
		this.insuranceliability = insuranceliability;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPricerate() {
		return pricerate;
	}
	public void setPricerate(String pricerate) {
		this.pricerate = pricerate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getInsuranceliability() {
		return insuranceliability;
	}
	public void setInsuranceliability(String insuranceliability) {
		this.insuranceliability = insuranceliability;
	}
	public ProductDetailinfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
