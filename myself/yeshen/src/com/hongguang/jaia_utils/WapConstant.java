package com.hongguang.jaia_utils;

import com.hongguang.jaia.R;

import android.content.Context;

public class WapConstant {
	// 192.168.1.110
	// 123.1.149.100
	// public static String URLSTRING =
	// "http://192.168.1.110:8080/InsuranceService/back/";
	//
	// public static String URLIMAGEString =
	// "http://192.168.1.110:8080/InsuranceData";
	// // 读取版本号
	// public static String READ_VERSION =
	// "http://192.168.1.110:8080/InsuranceService/back/version_findNewVersionByMobile.action";

	public static String URLSTRING = "/InsuranceService/back/";
	public static String IP = "http://192.168.1.151:8080:8080";

	public static String URLIMAGEString = "/InsuranceData";
	// 读取版本号
	public static String READ_VERSION = "/InsuranceService/back/version_findNewVersionByMobile.action";
	// 下载新版本
	public static String UPDATE_VERSION = "/InsuranceData/apk/Jaia.apk";
	// 注册
	public static String INSERTString = "salesman_registerByMobile.action";
	// 登陆
	public static String LOGINString = "salesman_login.action";
	// 修改密码
	public static String UPDATEPASS = "salesman_modifyPass.action";
	// 添加银行卡
	public static String ADDBANKString = "salesman_updateByMobile.action";
	// 修改个人资料
	public static String UPDATEString = "salesman_updateByMobile.action";
	// 添加证件信息
	public static String iNSERTIDString = "salesman_updateZJ.action";
	// 添加头像
	public static String addHead = "salesman_uploadhead.action";
	// 修改头像
	public static String UPDATEHEADIMAGE = "salesman_uploadhead.action";

	// 读取余额的信息
	public static String TOTALMoneyString = "finance_list.action";
	// 充值
	public static String deposit_addDepositByMobile = "deposit_addDepositByMobile.action";
	// 查询用户充值记录
	public static String AccountRecord = "deposit_selectPagerDepositByMobile.action";

	// 投保
	public static String product_send = "/InsuranceService/back/product_sendToWebService.action";

	// //投保人信息
	// public static String product_tbr = "policyholder_insert.action";
	// //受益人信息
	// public static String product_syr = "policyinsured_insert.action";

	public static String product_insert = "policyinfo_insert.action";

	// 车险
	public static String product_cxjiekou = "product_mobile_showProductCX.action";
	// 人寿险
	public static String product_rsjiekou = "product_mobile_showProductRSX.action";
	// 综合险
	public static String product_zhjiekou = "product_mobile_showProductZHX.action";
	// 保险详细
	public static String product_xx = "productDetailinfo_list.action";

	// 平安保险价格
	public static String product_priceForPingAn = "productprice_getPrice.action";

	// policyinfo_submitPolicyinfo.action提交保单接口

	public static String getHttpServer_HOST(Context context) {
		return context.getResources().getString(R.string.server_host_release);
	}

}
