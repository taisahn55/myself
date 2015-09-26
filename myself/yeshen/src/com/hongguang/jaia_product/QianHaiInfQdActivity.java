package com.hongguang.jaia_product;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.BaoDanInforation;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_bean.ShouYiRen;
import com.hongguang.jaia_bean.TouBaoRen;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

public class QianHaiInfQdActivity extends Activity implements OnClickListener {

	private TextView tv_syr_username;
	private TextView tv_syr_gx;
	private TextView tv_syr_certificate;
	private TextView tv_syr_sex;
	private TextView tv_syr_csdate;
	private TextView tv_syr_phone;
	private TextView tv_syr_email;
	private TextView tv_syr_nowprovince;
	private TextView tv_syr_nowcity;
	private TextView tv_tbr_username;
	private TextView tv_tbr_sex;
	private TextView tv_tbr_certificate;
	private TextView tv_tbr_csdate;
	private TextView tv_tbr_phone;
	private TextView tv_tbr_email;
	private TextView tv_tbr_nowprovince;
	private TextView tv_tbr_nowcity;
	private TextView tv_tbr_certificate_type;
	private View tbrxx;

	private String tv_syr_username_1, tv_syr_sex_1, tv_syr_certificate_1,
			tv_syr_certificate_type_1, tv_syr_csdate_1, tv_syr_phone_1,
			tv_syr_nowprovince_1, tv_syr_email_1, tv_syr_nowcity_1,
			tv_syr_gx_1;
	private String tv_tbr_username_1, tv_tbr_sex_1, tv_tbr_certificate_1,
			tv_tbr_certificate_type_1, tv_tbr_csdate_1, tv_tbr_phone_1,
			tv_tbr_nowprovince_1, tv_tbr_email_1, tv_tbr_nowcity_1;
	private Intent intent;
	private DateSharedPreferences dsp;
	private Salesman salesman;
	private String price;
	private String product_id;
	private String insuranceType_id;
	private String baoxianend;
	private String baoxianstart;
	private TextView lijipay;
	private String tv_syr_certificate_no;
	private String tv_syr_gx_no;
	private String tv_syr_certificate_type_no;
	private String tv_tbr_certificate_type_no;
	private String produce_name;
	private TextView hui_back;
	private String interfaceName;
	private LinearLayout qr_linerstart;
	private LinearLayout qr_linerend;
	private TextView qr_syr_startdate;
	private TextView qr_syr_startend;
	private String tv_syr_enddate_1;
	private String tv_syr_startdate_1;
	private TextView qr_syr_enddate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inforedt_queding);
		dsp = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(dsp.getLogin(this),
				Salesman.class);
		intent = getIntent();
		// 产品价格，产品名字
		price = intent.getStringExtra("price");
		produce_name = intent.getStringExtra("produce_name");
		interfaceName = intent.getStringExtra("interfaceName");

		// 产品Id
		product_id = intent.getStringExtra("product_id");
		insuranceType_id = intent.getStringExtra("insuranceType_id");
		baoxianend = intent.getStringExtra("baoxianend");
		baoxianstart = intent.getStringExtra("baoxianstart");
		// 姓名
		tv_syr_username_1 = intent.getStringExtra("et_syr_username");
		// 性别
		tv_syr_sex_1 = intent.getStringExtra("tv_syr_sex");
		// 证件类型
		tv_syr_certificate_type_1 = intent.getStringExtra("tv_syr_certificate");
		tv_syr_certificate_type_no = intent
				.getStringExtra("tv_syr_certificate_no");
		// 证件号码
		tv_syr_certificate_1 = intent.getStringExtra("et_syr_certificate");

		if (insuranceType_id.equals("00820")
				|| insuranceType_id.equals("00821")
				|| insuranceType_id.equals("00822")) {

			tv_syr_enddate_1 = intent.getStringExtra("et_syr_enddate");
			tv_syr_startdate_1 = intent.getStringExtra("et_syr_startdate");
		}

		// 出生日期
		tv_syr_csdate_1 = intent.getStringExtra("et_syr_csdate");
		// 手机号码
		tv_syr_phone_1 = intent.getStringExtra("et_syr_phone");
		// 邮箱
		tv_syr_email_1 = intent.getStringExtra("et_syr_email");
		// 所在省份
		tv_syr_nowprovince_1 = intent.getStringExtra("et_syr_nowprovince");
		// 所在城市
		tv_syr_nowcity_1 = intent.getStringExtra("et_syr_nowcity");
		// 关系
		tv_syr_gx_1 = intent.getStringExtra("syrgx");
		tv_syr_gx_no = intent.getStringExtra("syrgx_no");
		tbrxx = findViewById(R.id.tbrxxidqueren);
		// 提交保单
		lijipay = (TextView) findViewById(R.id.lijipay);
		lijipay.setOnClickListener(this);

		// 投保人信息/
		TouBaoRenAndBeiBaoRenInfo();
	}

	private void TouBaoRenAndBeiBaoRenInfo() {
		qr_linerstart = (LinearLayout) findViewById(R.id.qr_linerstart);
		qr_linerend = (LinearLayout) findViewById(R.id.qr_linerend);
		if (insuranceType_id.equals("00820")
				|| insuranceType_id.equals("00821")
				|| insuranceType_id.equals("00822")) {
			qr_linerstart.setVisibility(View.VISIBLE);
			qr_linerend.setVisibility(View.VISIBLE);

		} else {
			qr_linerstart.setVisibility(View.GONE);
			qr_linerend.setVisibility(View.GONE);
		}

		// 姓名
		tv_tbr_username = (TextView) tbrxx.findViewById(R.id.qr_tbrname);
		// 性别
		tv_tbr_sex = (TextView) tbrxx.findViewById(R.id.qr_tbr_sex);
		// 证件类型
		tv_tbr_certificate_type = (TextView) tbrxx
				.findViewById(R.id.qr_tbr_certificate_type);
		// 证件号码
		tv_tbr_certificate = (TextView) tbrxx
				.findViewById(R.id.qr_tbr_certificate);
		// 出生日期
		tv_tbr_csdate = (TextView) tbrxx.findViewById(R.id.qr_tbr_csdate);
		// 手机号码
		tv_tbr_phone = (TextView) tbrxx.findViewById(R.id.qr_tbr_phone);
		// 电子邮件
		tv_tbr_email = (TextView) tbrxx.findViewById(R.id.qr_tbr_email);
		// 投保人现在居住的地方
		tv_tbr_nowprovince = (TextView) tbrxx
				.findViewById(R.id.qr_tbr_now_province);
		// 投保人现在居住的城市
		tv_tbr_nowcity = (TextView) tbrxx.findViewById(R.id.qr_tbr_now_city);

		// 投保人信息
		// 姓名
		tv_syr_username = (TextView) findViewById(R.id.qr_syr_username);
		tv_syr_username.setText(tv_syr_username_1);

		// 性别
		tv_syr_sex = (TextView) findViewById(R.id.qr_syr_sex);
		tv_syr_sex.setText(tv_syr_sex_1);
		// 证件类型
		tv_syr_certificate = (TextView) findViewById(R.id.qr_syr_certificate_type);
		tv_syr_certificate.setText(tv_syr_certificate_type_1);
		// 证件号码
		tv_syr_certificate = (TextView) findViewById(R.id.qr_syr_certificate);
		tv_syr_certificate.setText(tv_syr_certificate_1);
		// 出生日期
		tv_syr_csdate = (TextView) findViewById(R.id.qr_syr_csdate);
		tv_syr_csdate.setText(tv_syr_csdate_1);
		// 手机号码
		tv_syr_phone = (TextView) findViewById(R.id.qr_syr_phone);
		tv_syr_phone.setText(tv_syr_phone_1);
		// 电子邮件
		tv_syr_email = (TextView) findViewById(R.id.qr_syr_email);
		tv_syr_email.setText(tv_syr_email_1);
		// 投保人现在居住的地方
		tv_syr_nowprovince = (TextView) findViewById(R.id.qr_syr_now_province);
		tv_syr_nowprovince.setText(tv_syr_nowprovince_1);
		// 投保人现在居住的城市
		tv_syr_nowcity = (TextView) findViewById(R.id.qr_syr_now_city);

		tv_syr_nowcity.setText(tv_syr_nowcity_1);
		// 投保人的
		qr_syr_startdate = (TextView) findViewById(R.id.qr_syr_startdate);
		qr_syr_startdate.setText(tv_syr_startdate_1);

		qr_syr_enddate = (TextView) findViewById(R.id.qr_syr_enddate);
		qr_syr_enddate.setText(tv_syr_enddate_1);

		// 受益人和投保人的关系
		tv_syr_gx = (TextView) findViewById(R.id.qr_guanxi);
		tv_syr_gx.setText(tv_syr_gx_1);
		if (tv_syr_gx.getText().toString().trim().equals("本人")) {
			tv_tbr_sex.setText(tv_syr_sex_1);
			tv_tbr_csdate.setText(tv_syr_csdate_1);
			tv_tbr_username.setText(tv_syr_username_1);
			tv_tbr_phone.setText(tv_syr_phone_1);
			tv_tbr_nowcity.setText(tv_syr_nowcity_1);
			tv_tbr_nowprovince.setText(tv_syr_nowprovince_1);
			tv_tbr_certificate_type.setText(tv_syr_certificate_type_1);
			tv_tbr_email.setText(tv_syr_email_1);
			tv_tbr_certificate.setText(tv_syr_certificate_1);
			// 回退
			hui_back = (TextView) findViewById(R.id.tv_back_hedui);
			hui_back.setOnClickListener(this);

		} else {
			Intent intent2 = getIntent();
			// 姓名
			tv_tbr_username_1 = intent2.getStringExtra("et_tbr_username");
			// 性别
			tv_tbr_sex_1 = intent2.getStringExtra("tv_tbr_sex");
			// 证件类型
			tv_tbr_certificate_type_1 = intent2
					.getStringExtra("tv_certificate");
			tv_tbr_certificate_type_no = intent2
					.getStringExtra("tv_certificate_no");
			// 证件号码
			tv_tbr_certificate_1 = intent2.getStringExtra("et_tbr_certificate");
			// 出生日期
			tv_tbr_csdate_1 = intent2.getStringExtra("et_tbr_csdate");
			// 手机号码
			tv_tbr_phone_1 = intent2.getStringExtra("et_tbr_phone");
			// 邮箱
			tv_tbr_email_1 = intent2.getStringExtra("et_tbr_email");
			// 所在省份
			tv_tbr_nowprovince_1 = intent2.getStringExtra("et_tbr_nowprovince");
			// 所在城市
			tv_tbr_nowcity_1 = intent2.getStringExtra("et_tbr_nowcity");

			tv_tbr_sex.setText(tv_tbr_sex_1);
			tv_tbr_csdate.setText(tv_tbr_csdate_1);
			tv_tbr_username.setText(tv_tbr_username_1);
			tv_tbr_phone.setText(tv_tbr_phone_1);
			tv_tbr_nowcity.setText(tv_tbr_nowcity_1);
			tv_tbr_nowprovince.setText(tv_tbr_nowprovince_1);
			tv_tbr_certificate_type.setText(tv_tbr_certificate_type_no);
			tv_tbr_email.setText(tv_tbr_email_1);
			tv_tbr_certificate.setText(tv_tbr_certificate_1);
		}

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.lijipay :
				// 投保人信息
				ShouYiRen shouyirenxx = new ShouYiRen();
				// 生日 '1989-11-12'
				shouyirenxx.setBirthdate(tv_syr_csdate_1.trim());
				// 证件号码
				shouyirenxx.setCertificateNo(tv_syr_certificate_1.trim());
				// 证件类型——数字类型如201
				// shouyirenxx.setCertificateType(syrzjlx.trim());
				shouyirenxx.setCertificateType(tv_syr_certificate_type_no
						.trim());
				// 邮箱
				shouyirenxx.setEmail(tv_syr_email_1.trim());
				// 手机号码
				shouyirenxx.setMoblie(tv_syr_phone_1.trim());
				// 姓名
				shouyirenxx.setName(tv_syr_username_1.trim());
				// 居住城市
				shouyirenxx.setResidentCity(tv_syr_nowcity_1.trim());
				// 所在省份
				shouyirenxx.setResidentProvince(tv_syr_nowprovince_1.trim());
				// 性别
				shouyirenxx.setSex(String.valueOf(tv_syr_sex_1.trim().equals(
						"男") ? 1 : 2));
				// 受益人关系_数字类型如301
				shouyirenxx.setHolderRelation(tv_syr_gx_no.trim());

				TouBaoRen touBaoRen = new TouBaoRen();
				if (tv_syr_gx_1.trim().equals("本人")) {
					// 生日 '1989-11-12'
					touBaoRen.setBirthdate(tv_syr_csdate_1.trim());
					// 证件号码
					touBaoRen.setCertificateNo(tv_syr_certificate_1.trim());
					// 证件类型
					// shouyirenxx.setCertificateType(syrzjlx.trim());
					touBaoRen.setCertificateType(tv_syr_certificate_type_no
							.trim());
					// 邮箱
					touBaoRen.setEmail(tv_syr_email_1.trim());
					// 手机号码
					touBaoRen.setMoblie(tv_syr_phone_1.trim());
					// 姓名
					touBaoRen.setName(tv_syr_username_1.trim());
					// 居住城市
					touBaoRen.setResidentCity(tv_syr_nowcity_1.trim());
					// 所在省份
					touBaoRen.setResidentProvince(tv_syr_nowprovince_1.trim());
					// 性别
					touBaoRen.setSex(String.valueOf(tv_syr_sex_1.trim().equals(
							"男") ? 1 : 2));
				} else {
					touBaoRen.setBirthdate(tv_tbr_csdate_1.trim());
					// 证件号码
					touBaoRen.setCertificateNo(tv_tbr_certificate_1.trim());
					// 证件类型
					// shouyirenxx.setCertificateType(syrzjlx.trim());
					touBaoRen.setCertificateType(tv_tbr_certificate_type_1
							.trim());
					// 邮箱
					touBaoRen.setEmail(tv_tbr_email_1.trim());
					// 手机号码
					touBaoRen.setMoblie(tv_tbr_phone_1.trim());
					// 姓名
					touBaoRen.setName(tv_tbr_username_1.trim());
					// 居住城市
					touBaoRen.setResidentCity(tv_tbr_nowcity_1.trim());
					// 所在省份
					touBaoRen.setResidentProvince(tv_tbr_nowprovince_1.trim());
					// 性别
					touBaoRen.setSex(String.valueOf(tv_tbr_sex_1.trim().equals(
							"男") ? 1 : 2));

				}
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("toubaorenxx", Constant
						.getGson().toJson(touBaoRen)));

				// 受益人信息
				params.add(new BasicNameValuePair("shouyirenxx", Constant
						.getGson().toJson(shouyirenxx)));
				// 保单信息
				BaoDanInforation baodaninfo = new BaoDanInforation();
				baodaninfo.setProductId(insuranceType_id);
				baodaninfo.setAmount(price);
				if (insuranceType_id.equals("00820")
						|| insuranceType_id.equals("00821")
						|| insuranceType_id.equals("00822")) {
					baodaninfo.setEndDate(tv_syr_enddate_1);
					baodaninfo.setStartDate(tv_syr_startdate_1);

				} else {
					baodaninfo.setEndDate(baoxianend);
					baodaninfo.setStartDate(baoxianstart);
				}
				baodaninfo.setSalesman_id(String.valueOf(salesman.getSid()));

				params.add(new BasicNameValuePair("baodanxx", Constant
						.getGson().toJson(baodaninfo)));

				new Thread(new HttpUtils(QianHaiInfQdActivity.this, "订单处理中...",
						handler, WapConstant.URLSTRING + "policyinfo_"
								+ interfaceName + ".action", params)).start();
				break;

			case R.id.tv_back_hedui :
				this.finish();
				break;
		}
	}
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int code = msg.arg1;
			if (code == 200) {
				try {
					Log.e("dong", "code:" + code);
					Log.e("dong", msg.obj.toString());
					System.out.println(msg.obj.toString());
					JSONObject o = new JSONObject(msg.obj.toString());
					JSONObject obj = o.optJSONObject("ret");

					if (obj.optString("msg").equals("success")) {
						Toast.makeText(QianHaiInfQdActivity.this, "投保成功!",
								Toast.LENGTH_LONG).show();
						salesman.setMoney(salesman.getMoney()
								- Double.valueOf(price));
						dsp.saveLogin(QianHaiInfQdActivity.this, Constant
								.getGson().toJson(salesman));
						// 成功跳转到产品页面
						Intent intent = new Intent();
						intent.setClass(QianHaiInfQdActivity.this,
								SuccessQianHaiActivity.class);
						intent.putExtra("price", price);
						intent.putExtra("produce_name", produce_name);
						intent.putExtra("tv_syr_username_1", tv_syr_username_1);

						startActivity(intent);
					} else {
						Toast.makeText(QianHaiInfQdActivity.this, "投保失败!",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qian_hai_inf_qd, menu);
		return true;
	}

}
