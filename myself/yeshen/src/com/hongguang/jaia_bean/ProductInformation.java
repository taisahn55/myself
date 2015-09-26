package com.hongguang.jaia_bean;

public class ProductInformation {
	// 产品信息表
	private String name;// 产品名称
	private String productImgList;// 产品缩略图
	private String productType_id;// 产品类型
	private String summary;// 产品简介
	private String insuranceType_id;// 险种ID；
	private String insuranceNote;// 投保须知；
	private String insuranceDeclaration;// 投保声明书；
	private String insuranceRules;// 投保规则；
	private String claimNote;// 理赔须知；
	private String createDate;// 创建日期；
	private String modifyDate;// 修改日期；
	private String price;// 价格；
	private String id;// ID
	private String amount;// 保额
	private String productBrand;// 产品品牌

	private String startdate;// 保险日期

	private String datelimit;// 保险有限期
	private String interfaceName;// 接口

	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getDatelimit() {
		return datelimit;
	}
	public void setDatelimit(String datelimit) {
		this.datelimit = datelimit;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ProductInformation(String name, String productImgList,
			String productType_id, String summary, String insuranceType_id,
			String insuranceNote, String insuranceDeclaration,
			String insuranceRules, String claimNote, String createDate,
			String modifyDate, String price, String id) {
		super();
		this.name = name;
		this.productImgList = productImgList;
		this.productType_id = productType_id;
		this.summary = summary;
		this.insuranceType_id = insuranceType_id;
		this.insuranceNote = insuranceNote;
		this.insuranceDeclaration = insuranceDeclaration;
		this.insuranceRules = insuranceRules;
		this.claimNote = claimNote;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.price = price;
		this.id = id;
	}
	public ProductInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductInformation(String name, String amount, String price) {
		super();
		this.name = name;
		this.amount = amount;
		this.price = price;
	}

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductImgList() {
		return productImgList;
	}
	public void setProductImgList(String productImgList) {
		this.productImgList = productImgList;
	}
	public String getProductType_id() {
		return productType_id;
	}
	public void setProductType_id(String productType_id) {
		this.productType_id = productType_id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getInsuranceType_id() {
		return insuranceType_id;
	}
	public void setInsuranceType_id(String insuranceType_id) {
		this.insuranceType_id = insuranceType_id;
	}
	public String getInsuranceNote() {
		return insuranceNote;
	}
	public void setInsuranceNote(String insuranceNote) {
		this.insuranceNote = insuranceNote;
	}
	public String getInsuranceDeclaration() {
		return insuranceDeclaration;
	}
	public void setInsuranceDeclaration(String insuranceDeclaration) {
		this.insuranceDeclaration = insuranceDeclaration;
	}
	public String getInsuranceRules() {
		return insuranceRules;
	}
	public void setInsuranceRules(String insuranceRules) {
		this.insuranceRules = insuranceRules;
	}
	public String getClaimNote() {
		return claimNote;
	}
	public void setClaimNote(String claimNote) {
		this.claimNote = claimNote;
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

}
