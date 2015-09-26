package com.hongguang.jaia_bean;

import java.sql.Timestamp;

public class Salesman {
	private int sid;
	private String name;// 昵称
	private String passWord;// 用户密码
	private String realName;// 真实姓名
	private String email;// 邮箱
	private String phone;// 用户名
	private String sex;
	private String idNum;// 身份证号
	private String idPic1;// 身份正照片1
	private String idPic2;// 身份正照片2
	private String gangAo;// 港澳通行证号
	private String gangaoPic1;// 港澳通行证照片
	private String gangaoPic2;// 港澳通行证照片
	private String headPic;// 头像
	private String nowAddr;// 现住址
	private String workAddr;// 工作地址
	private String congYe;// 从业证号
	private String cyPic1;// 从业照片1
	private String cyPic2;// 从业照片2
	private String zhanYe;// 展业证号
	private String zyPic1;// 展业证照片1
	private String zyPic2;// 展业照片2
	private String bank; // 银行名称
	private String bankUser;// 银行开户名
	private String bankNum;// 银行帐号
	private int level;// 等级
	private String state;// 状态
	private Integer workYear;// 工作年限
	private String qrcode;// 二维码
	private String invate;// 邀请人
	private int invateNum;// 邀请人数
	private Timestamp regtime;// 注册时间
	private double money;

	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getIdPic1() {
		return idPic1;
	}
	public void setIdPic1(String idPic1) {
		this.idPic1 = idPic1;
	}
	public String getIdPic2() {
		return idPic2;
	}
	public void setIdPic2(String idPic2) {
		this.idPic2 = idPic2;
	}
	public String getGangAo() {
		return gangAo;
	}
	public void setGangAo(String gangAo) {
		this.gangAo = gangAo;
	}
	public String getGangaoPic1() {
		return gangaoPic1;
	}
	public void setGangaoPic1(String gangaoPic1) {
		this.gangaoPic1 = gangaoPic1;
	}
	public String getGangaoPic2() {
		return gangaoPic2;
	}
	public void setGangaoPic2(String gangaoPic2) {
		this.gangaoPic2 = gangaoPic2;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	public String getNowAddr() {
		return nowAddr;
	}
	public void setNowAddr(String nowAddr) {
		this.nowAddr = nowAddr;
	}
	public String getWorkAddr() {
		return workAddr;
	}
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}
	public String getCongYe() {
		return congYe;
	}
	public void setCongYe(String congYe) {
		this.congYe = congYe;
	}
	public String getCyPic1() {
		return cyPic1;
	}
	public void setCyPic1(String cyPic1) {
		this.cyPic1 = cyPic1;
	}
	public String getCyPic2() {
		return cyPic2;
	}
	public void setCyPic2(String cyPic2) {
		this.cyPic2 = cyPic2;
	}
	public String getZhanYe() {
		return zhanYe;
	}
	public void setZhanYe(String zhanYe) {
		this.zhanYe = zhanYe;
	}
	public String getZyPic1() {
		return zyPic1;
	}
	public void setZyPic1(String zyPic1) {
		this.zyPic1 = zyPic1;
	}
	public String getZyPic2() {
		return zyPic2;
	}
	public void setZyPic2(String zyPic2) {
		this.zyPic2 = zyPic2;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankUser() {
		return bankUser;
	}
	public void setBankUser(String bankUser) {
		this.bankUser = bankUser;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getWorkYear() {
		return workYear;
	}
	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getInvate() {
		return invate;
	}
	public void setInvate(String invate) {
		this.invate = invate;
	}
	public int getInvateNum() {
		return invateNum;
	}
	public void setInvateNum(int invateNum) {
		this.invateNum = invateNum;
	}
	public Timestamp getRegtime() {
		return regtime;
	}
	public void setRegtime(Timestamp regtime) {
		this.regtime = regtime;
	}

}
