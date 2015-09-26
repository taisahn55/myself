package com.hongguang.jaia_bean;

import java.io.Serializable;

public class TouBaoRen implements Serializable {
	private String birthdate;// 生日 '1989-11-12'
	private String certEndDate;// 证件结束有效期
	private String certStartDate;// 证件起始日期
	private String certValidFlag;// 证书标识
	private String certificateNo;// 证件号码
	private String certificateType;// 证件类型
	private String city;// 所在城市
	private String county;// 所在国家
	private String detailAddress;// 详细地址
	private String email;// 邮箱
	private String moblie;// 手机号码
	private String name;// 姓名
	private String nationality;// 国籍
	private String policyId;// 保单ID
	private String profession;// 职业
	private String province;// 省
	private String residentCity;// 居住城市
	private String residentProvince;// 所在省份
	private String sex;// 性别
	private String supplierBranch;// 供应商分公司
	private String telephone;// 电话号码
	private String zipNo;// 邮编
	public TouBaoRen() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TouBaoRen(String birthdate, String certEndDate,
			String certStartDate, String certValidFlag, String certificateNo,
			String certificateType, String city, String county,
			String detailAddress, String email, String moblie, String name,
			String nationality, String policyId, String profession,
			String province, String residentCity, String residentProvince,
			String sex, String supplierBranch, String telephone, String zipNo) {
		super();
		this.birthdate = birthdate;
		this.certEndDate = certEndDate;
		this.certStartDate = certStartDate;
		this.certValidFlag = certValidFlag;
		this.certificateNo = certificateNo;
		this.certificateType = certificateType;
		this.city = city;
		this.county = county;
		this.detailAddress = detailAddress;
		this.email = email;
		this.moblie = moblie;
		this.name = name;
		this.nationality = nationality;
		this.policyId = policyId;
		this.profession = profession;
		this.province = province;
		this.residentCity = residentCity;
		this.residentProvince = residentProvince;
		this.sex = sex;
		this.supplierBranch = supplierBranch;
		this.telephone = telephone;
		this.zipNo = zipNo;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getCertEndDate() {
		return certEndDate;
	}
	public void setCertEndDate(String certEndDate) {
		this.certEndDate = certEndDate;
	}
	public String getCertStartDate() {
		return certStartDate;
	}
	public void setCertStartDate(String certStartDate) {
		this.certStartDate = certStartDate;
	}
	public String getCertValidFlag() {
		return certValidFlag;
	}
	public void setCertValidFlag(String certValidFlag) {
		this.certValidFlag = certValidFlag;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getResidentCity() {
		return residentCity;
	}
	public void setResidentCity(String residentCity) {
		this.residentCity = residentCity;
	}
	public String getResidentProvince() {
		return residentProvince;
	}
	public void setResidentProvince(String residentProvince) {
		this.residentProvince = residentProvince;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSupplierBranch() {
		return supplierBranch;
	}
	public void setSupplierBranch(String supplierBranch) {
		this.supplierBranch = supplierBranch;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getZipNo() {
		return zipNo;
	}
	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}

}
