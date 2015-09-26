package com.hongguang.jaia_product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.array;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.hongguang.jaia.LoginActivity;
import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.BaoDanInforation;
import com.hongguang.jaia_bean.DepositBean;
import com.hongguang.jaia_bean.ProductInformation;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_bean.ShouYiRen;
import com.hongguang.jaia_bean.TouBaoRen;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

public class Inforedt extends Activity implements OnClickListener {

	// 含syr的为受益人信息，根据客户的改为，投保人信息
	// 含tbr的为投保人信息，根据客户的改为，被保人信息

	private TextView tv_certificate;
	private String certificate_type[] = new String[]{"身份证", "护照", "军官证", "户口薄",
			"台胞证"};
	private String certificate_type2[] = new String[]{"201", "202", "203",
			"204", "205"};
	private String sex_type[] = new String[]{"男", "女"};
	private String guanxi_type[] = new String[]{"本人", "合法配偶", "子女", "父母"};
	private String guanxi_type2[] = new String[]{"301", "302", "303", "304"};

	private TextView textpay, pricetext;
	private TextView tv_back;
	private String price, product_id;
	private Salesman salesman;
	private DateSharedPreferences dsp;
	private EditText et_tbr_username, et_tbr_certificate, et_tbr_phone,
			et_tbr_email;
	private View tbrxxid;

	private EditText et_tbr_nowprovince, et_tbr_nowcity, et_tbr_nowaddress,
			et_syr_username;
	private EditText et_syr_certificate, et_syr_phone, et_syr_email,
			et_syr_nowprovince, et_syr_nowcity, et_syr_nowaddress;
	private TextView tv_tbr_sex, tv_syr_sex, et_syr_csdate, et_tbr_csdate,
			tv_certificate_syrdate, te_syr_gx, tv_syr_certificate;
	private TouBaoRen toubaorenxx;
	private final Calendar c = Calendar.getInstance();
	private String baoxianend, baoxianstart, insuranceType_id;
	private LinearLayout shouyirenid;
	private ShouYiRen shouyiren;
	private TouBaoRen toubaoren;
	private Intent intent;
	private String produce_name;
	private String interfaceName;
	private TextView et_syr_enddate;
	private TextView et_syr_startdate;
	private LinearLayout linerend;
	private LinearLayout linerstart;
	private String jihua;
	private CharSequence forma3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inforedt);
		dsp = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(dsp.getLogin(this),
				Salesman.class);

		pricetext = (TextView) findViewById(R.id.tv_price);
		textpay = (TextView) findViewById(R.id.lijipay_1);
		textpay.setOnClickListener(this);

		Intent intent = getIntent();
		price = intent.getStringExtra("price");
		jihua = intent.getStringExtra("jihua");
		pricetext.setText(price);

		// 产品Id
		product_id = intent.getStringExtra("product_id");
		insuranceType_id = intent.getStringExtra("insuranceType_id");
		interfaceName = intent.getStringExtra("interfaceName");

		// 产品的名字
		produce_name = intent.getStringExtra("productname");
		// 保险开始结束时间
		baoxianend = intent.getStringExtra("baoxianend");
		baoxianstart = intent.getStringExtra("baoxianstart");

		TouBaoAndShouYi();
		// 投保人信息
		// 受益人信息
		setViewsShouYiRen();
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
	}

	private void TouBaoAndShouYi() {
		// 受益人.投保人信息
		shouyirenid = (LinearLayout) findViewById(R.id.shouyirenid);
		tbrxxid = findViewById(R.id.tbrxxid);
	}

	// 受益人信息
	private void setViewsShouYiRen() {
		linerend = (LinearLayout) findViewById(R.id.linerend);
		linerstart = (LinearLayout) findViewById(R.id.linerstart);
		if (insuranceType_id.equals("00820")
				|| insuranceType_id.equals("00821")
				|| insuranceType_id.equals("00822")) {
			linerend.setVisibility(View.VISIBLE);
			linerstart.setVisibility(View.VISIBLE);

		} else {
			linerend.setVisibility(View.GONE);
			linerstart.setVisibility(View.GONE);
		}

		// 姓名
		et_syr_username = (EditText) findViewById(R.id.et_syr_username);
		// 受益人和投保人的关系
		te_syr_gx = (TextView) findViewById(R.id.tv_guanxi);
		te_syr_gx.setOnClickListener(this);

		// 性别
		tv_syr_sex = (TextView) findViewById(R.id.tv_syr_sex);
		tv_syr_sex.setOnClickListener(this);
		// 证件类型
		tv_syr_certificate = (TextView) findViewById(R.id.tv_syr_certificate);
		tv_syr_certificate.setOnClickListener(this);

		// 证件号码
		et_syr_certificate = (EditText) findViewById(R.id.et_syr_certificate);

		// 出生日期
		et_syr_csdate = (TextView) findViewById(R.id.et_syr_csdate);

		// 保险开始日期
		et_syr_startdate = (TextView) findViewById(R.id.et_syr_startdate);
		// et_syr_startdate.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// //填写时间
		//
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		// int arg3) {}
		//
		// @Override
		// public void afterTextChanged(Editable arg0) {}
		// });
		// 保险结束日期
		et_syr_enddate = (TextView) findViewById(R.id.et_syr_enddate);

		et_syr_enddate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// BaoDanInforation baoDanInforation = new BaoDanInforation();
				// baoDanInforation.setStartDate(et_syr_startdate.getText().toString());
				// baoDanInforation.setEndDate(et_syr_enddate.getText().toString());
				// baoDanInforation.setProductId(insuranceType_id);

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("startDate", et_syr_startdate
						.getText().toString().trim()));
				params.add(new BasicNameValuePair("endDate", et_syr_enddate
						.getText().toString().trim()));
				params.add(new BasicNameValuePair("productId", jihua.trim()));

				new Thread(new HttpUtils(Inforedt.this, Handler,
						WapConstant.URLSTRING
								+ WapConstant.product_priceForPingAn, params))
						.start();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		et_syr_certificate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (tv_syr_certificate.getText().toString().trim()
						.equals("身份证")
						&& s.length() == 15 || s.length() == 18) {
					// 获取出生年月日
					String year = et_syr_certificate.getText().toString()
							.substring(6, 10);
					String month = et_syr_certificate.getText().toString()
							.substring(10, 12);
					String day = et_syr_certificate.getText().toString()
							.substring(12, 14);
					et_syr_csdate.setText(year + "-" + month + "-" + day);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.e("dfghJK", "2222222222222222");
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		et_syr_csdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(Inforedt.this,
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								Calendar c = Calendar.getInstance();
								c.set(year, monthOfYear, dayOfMonth);
								CharSequence format = DateFormat.format(
										"yyy-MM-dd", c);
								et_syr_csdate.setText(format);

							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}
		});

		et_syr_enddate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(Inforedt.this,
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {

								Calendar c = Calendar.getInstance();
								c.set(year, monthOfYear, dayOfMonth);
								CharSequence forma = DateFormat.format(
										"yyy-MM-dd", c);

								try {
									Date dt2 = format.parse(forma.toString());
									// 结束时间
									long end = dt2.getTime() / 1000;
									// 现在时间
									if (et_syr_startdate.getText().toString()
											.trim().equals("请选择日期")) {
										Toast.makeText(Inforedt.this,
												"请填写保险起始时间", Toast.LENGTH_LONG)
												.show();
										et_syr_enddate.setText("请选择时间");
									} else {
										Date dt3 = format.parse(forma3
												.toString());
										long now = dt3.getTime() / 1000;
										// 3天后有限
										long after = 24 * 60 * 60;
										// 不能超过的期限
										long after1 = 182 * 24 * 60 * 60;
										if (end <= now + after) {
											Toast.makeText(Inforedt.this,
													"结束日期应该大于起始2天",
													Toast.LENGTH_LONG).show();
											et_syr_enddate.setText("请选择时间");
										} else if (end > now + after1) {
											Toast.makeText(Inforedt.this,
													"该保险期限为183天",
													Toast.LENGTH_LONG).show();
											et_syr_enddate.setText("请选择时间");
										} else {
											et_syr_enddate.setText(forma);
										}
									}

								} catch (ParseException e) {
									e.printStackTrace();
								}

							}

						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH) + 3);
				dialog.show();
			}
		});

		et_syr_startdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(Inforedt.this,
						new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {

								Calendar c = Calendar.getInstance();
								c.set(year, monthOfYear, dayOfMonth);
								forma3 = DateFormat.format("yyy-MM-dd", c);

								try {
									Date dt2 = format.parse(forma3.toString());
									long statr = dt2.getTime() / 1000;
									// 现在时间
									Date dt3 = new Date();
									long now = dt3.getTime() / 1000;
									long after = 12 * 60 * 60;
									if (statr <= now + after) {
										Toast.makeText(Inforedt.this,
												"请选择投保后第二日时间",
												Toast.LENGTH_LONG).show();
										et_syr_startdate.setText("请选择时间");
									} else {
										et_syr_startdate.setText(forma3);
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}

							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH) + 1);
				dialog.show();
			}
		});

		// 手机号码
		et_syr_phone = (EditText) findViewById(R.id.et_syr_phone);

		// 电子邮件
		et_syr_email = (EditText) findViewById(R.id.et_syr_email);

		// 投保人现在居住的地方
		et_syr_nowprovince = (EditText) findViewById(R.id.et_syr_now_province);

		// 投保人现在居住的城市
		et_syr_nowcity = (EditText) findViewById(R.id.et_syr_now_city);

		// 投保人现在居住的详细地址
		// et_syr_nowaddress=(EditText)findViewById(R.id.et_syr_now_xxadress);

		// 证件类型
		tv_certificate = (TextView) tbrxxid
				.findViewById(R.id.tv_tbr_certificate);
		tv_certificate.setOnClickListener(this);
		// 姓名
		et_tbr_username = (EditText) tbrxxid.findViewById(R.id.et_tbrname);
		// 性别
		tv_tbr_sex = (TextView) tbrxxid.findViewById(R.id.tv_tbr_sex);
		tv_tbr_sex.setOnClickListener(this);
		// 出生日期
		et_tbr_csdate = (TextView) tbrxxid.findViewById(R.id.et_tbr_csdate);
		et_tbr_csdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(Inforedt.this,
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								Calendar c = Calendar.getInstance();
								c.set(year, monthOfYear, dayOfMonth);
								et_tbr_csdate.setText(DateFormat.format(
										"yyy-MM-dd", c));
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH));

				Log.i("t34444444444", "" + Calendar.YEAR);
				Log.i("33333333333", "" + Calendar.MONTH);
				dialog.show();
			}
		});

		// 证件号码
		et_tbr_certificate = (EditText) tbrxxid
				.findViewById(R.id.et_tbr_certificate);

		// 手机号码
		et_tbr_phone = (EditText) tbrxxid.findViewById(R.id.et_tbr_phone);
		// 电子邮件
		et_tbr_email = (EditText) tbrxxid.findViewById(R.id.et_tbr_email);
		// 投保人现在居住的地方
		et_tbr_nowprovince = (EditText) tbrxxid
				.findViewById(R.id.et_tbr_now_province);
		// 投保人现在居住的城市
		et_tbr_nowcity = (EditText) tbrxxid.findViewById(R.id.et_tbr_now_city);
		// 投保人现在居住的详细地址
		// et_tbr_nowaddress=(EditText)tbrxxid.findViewById(R.id.et_tbr_now_xxadress);

	}

	// 判断邮箱格式
	private boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// 判断手机格式是否正确
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	// 判断身份证号码15位或者18位
	public boolean isIDNO(String idnumber) {
		// 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		Pattern idNumPattern = Pattern
				.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		// 通过Pattern获得Matcher
		Matcher m = idNumPattern.matcher(idnumber);
		return m.matches();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 被投保人和投保人关系
			case R.id.tv_guanxi :
				AlertDialog.Builder builder5 = new AlertDialog.Builder(
						Inforedt.this);
				builder5.setItems(guanxi_type,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								te_syr_gx.setText(guanxi_type[which]);
								syrgx = guanxi_type2[which];
								if (te_syr_gx.getText().toString().trim()
										.equals("本人")) {
									tbrxxid.setVisibility(View.GONE);
									et_tbr_csdate.setText("请选择日期");
									et_tbr_certificate.setText("");
									tv_certificate.setText("身份证");
									et_tbr_email.setText("");
									et_tbr_phone.setText("");
									et_tbr_username.setText("");
									et_tbr_nowcity.setText("深圳");
									et_tbr_nowprovince.setText("广东");
									tv_tbr_sex.setText("男");
								} else {
									tbrxxid.setVisibility(View.VISIBLE);
								}
							}
						});
				builder5.create().show();
				break;

			// 投保人证件类型
			case R.id.tv_syr_certificate :
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setItems(certificate_type,
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								tv_syr_certificate
										.setText(certificate_type[which]);
								syrzjlx = certificate_type2[which];
							}
						});
				builder.create().show();
				break;

			// 投保人性别
			case R.id.tv_syr_sex :
				AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
				builder3.setItems(sex_type,
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								tv_syr_sex.setText(sex_type[which]);
							}
						});
				builder3.create().show();
				break;

			// 被投保人证件类型
			case R.id.tv_tbr_certificate :
				AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
				builder6.setItems(certificate_type,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								tv_certificate.setText(certificate_type[which]);
								tbrzjlx = certificate_type2[which];

							}
						});
				builder6.create().show();
				break;

			// 被投保人性别
			case R.id.tv_tbr_sex :
				AlertDialog.Builder builder7 = new AlertDialog.Builder(this);
				builder7.setItems(sex_type,
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								tv_tbr_sex.setText(sex_type[which]);
							}
						});
				builder7.create().show();
				break;

			case R.id.lijipay_1 :
				if (salesman.getMoney() <= Double.valueOf(price)) {
					AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
					builder2.setTitle("温馨提醒");
					builder2.setMessage("余额不足，无法完成订单!");
					builder2.setPositiveButton("前往充值",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Intent intent = new Intent();
									intent.setClass(Inforedt.this,
											PayDemoActivity.class);
									intent.putExtra("classname", Inforedt.this
											.getClass().getName());
									startActivityForResult(intent, 100);
								}
							});
					builder2.setNegativeButton("取消订单", null);
					builder2.create().show();
				} else {
					// 投保人
					if (et_syr_username.getText().toString().trim().length() == 0) {
						Toast.makeText(Inforedt.this, "投保人姓名不能为空!",
								Toast.LENGTH_SHORT).show();

					} else if (et_syr_certificate.getText().toString().trim()
							.length() == 0) {
						Toast.makeText(Inforedt.this, "投保人证件号码不能为空!",
								Toast.LENGTH_SHORT).show();
					} else if (tv_syr_certificate.getText().toString().trim()
							.equals("身份证")
							&& !isIDNO(et_syr_certificate.getText().toString()
									.trim())) {

						Toast.makeText(Inforedt.this, "请输入投保人正确身份证号码!",
								Toast.LENGTH_SHORT).show();
					} else if (et_syr_csdate.getText().toString().trim()
							.equals("请选择日期")) {
						Toast.makeText(Inforedt.this, "投保人出生日期不能为空!",
								Toast.LENGTH_SHORT).show();
					} else if (!isMobileNO(et_syr_phone.getText().toString()
							.trim())) {
						Toast.makeText(Inforedt.this, "请输入11位正确投保人手机号码!",
								Toast.LENGTH_SHORT).show();
					} else if (et_syr_phone.getText().toString().trim()
							.length() == 0) {
						Toast.makeText(Inforedt.this, "投保人手机号码不能为空!",
								Toast.LENGTH_SHORT).show();
					} else if (et_syr_email.getText().toString().trim()
							.length() == 0) {
						Toast.makeText(Inforedt.this, "投保人电子邮件不能为空!",
								Toast.LENGTH_SHORT).show();
					} else if (!isEmail(et_syr_email.getText().toString()
							.trim())) {
						Toast.makeText(this, "投保人邮箱格式不对", Toast.LENGTH_SHORT)
								.show();
					} else if (et_syr_nowprovince.getText().toString().trim()
							.length() == 0) {
						Toast.makeText(Inforedt.this, "被投保人现在居住省份不能为空!",
								Toast.LENGTH_SHORT).show();
					} else if (et_syr_nowcity.getText().toString().trim()
							.length() == 0) {
						Toast.makeText(Inforedt.this, "被投保人现在居住城市不能为空!",
								Toast.LENGTH_SHORT).show();
					} else if (te_syr_gx.getText().toString().trim()
							.equals("请选择关系类别")) {
						Toast.makeText(Inforedt.this, "请选择被投保人与被投保人关系类型!",
								Toast.LENGTH_SHORT).show();
					} else {

						intent = new Intent();
						intent.setClass(Inforedt.this,
								QianHaiInfQdActivity.class);
						// 保险类型，id,名字等信息
						intent.putExtra("price", pricetext.getText().toString()
								.trim());
						intent.putExtra("product_id", product_id);
						intent.putExtra("produce_name", produce_name);
						intent.putExtra("insuranceType_id", insuranceType_id);
						intent.putExtra("baoxianstart", baoxianstart);
						intent.putExtra("baoxianend", baoxianend);
						intent.putExtra("interfaceName", interfaceName);

						// 姓名
						intent.putExtra("et_syr_username", et_syr_username
								.getText().toString().trim());
						// 性别
						intent.putExtra("tv_syr_sex", tv_syr_sex.getText()
								.toString().trim());
						// 证件类型
						intent.putExtra("tv_syr_certificate",
								tv_syr_certificate.getText().toString().trim());
						intent.putExtra("tv_syr_certificate_no", String
								.valueOf(tv_syr_certificate.getText()
										.toString().trim().equals("身份证")
										? 201
										: syrzjlx.trim()));

						// 证件号码
						intent.putExtra("et_syr_certificate",
								et_syr_certificate.getText().toString().trim());
						// 出生日期
						intent.putExtra("et_syr_csdate", et_syr_csdate
								.getText().toString().trim());
						// 手机号码
						intent.putExtra("et_syr_phone", et_syr_phone.getText()
								.toString().trim());
						// 邮箱
						intent.putExtra("et_syr_email", et_syr_email.getText()
								.toString().trim());
						// 所在省份
						intent.putExtra("et_syr_nowprovince",
								et_syr_nowprovince.getText().toString().trim());
						// 所在城市
						intent.putExtra("et_syr_nowcity", et_syr_nowcity
								.getText().toString().trim());
						if (insuranceType_id.equals("00820")
								|| insuranceType_id.equals("00821")
								|| insuranceType_id.equals("00822")) {
							intent.putExtra("et_syr_enddate", et_syr_enddate
									.getText().toString().trim());
							intent.putExtra("et_syr_startdate",
									et_syr_startdate.getText().toString()
											.trim());

						}

						// 关系
						// intent.putExtra("syrgx", syrgx.trim());
						intent.putExtra("syrgx", te_syr_gx.getText().toString()
								.trim());
						intent.putExtra("syrgx_no", syrgx.trim());

						if (tbrxxid.getVisibility() == View.VISIBLE) {
							if (et_tbr_username.getText().toString().trim()
									.length() == 0) {
								Toast.makeText(Inforedt.this, "被保人姓名不能为空!",
										Toast.LENGTH_SHORT).show();
							} else if (et_tbr_certificate.getText().toString()
									.trim().length() == 0) {
								Toast.makeText(Inforedt.this, "被保人证件号码不能为空!",
										Toast.LENGTH_SHORT).show();

							} else if (tv_certificate.getText().toString()
									.trim().equals("身份证")
									&& !isIDNO(et_tbr_certificate.getText()
											.toString().trim())) {

								Toast.makeText(Inforedt.this, "请输入被保人正确身份证号码！",
										Toast.LENGTH_SHORT).show();

							} else if (et_tbr_csdate.getText().toString()
									.trim().equals("请选择日期")) {
								Toast.makeText(Inforedt.this, "被保人出生日期不能为空!",
										Toast.LENGTH_SHORT).show();
							} else if (et_tbr_phone.getText().toString().trim()
									.length() == 0) {
								Toast.makeText(Inforedt.this, "被保人手机号码不能为空!",
										Toast.LENGTH_SHORT).show();
							} else if (!isMobileNO(et_tbr_phone.getText()
									.toString().trim())) {
								Toast.makeText(Inforedt.this,
										"请输入11位正确被保人手机号码!", Toast.LENGTH_SHORT)
										.show();
							} else if (et_tbr_email.getText().toString().trim()
									.length() == 0) {
								Toast.makeText(Inforedt.this, "被保人电子邮件不能为空!",
										Toast.LENGTH_SHORT).show();
							} else if (!isEmail(et_tbr_email.getText()
									.toString().trim())) {
								Toast.makeText(Inforedt.this, "被保人邮箱格式不对",
										Toast.LENGTH_SHORT).show();

							} else if (et_tbr_nowprovince.getText().toString()
									.trim().length() == 0) {
								Toast.makeText(Inforedt.this, "被保人常驻省份不能为空!",
										Toast.LENGTH_SHORT).show();
							} else if (et_tbr_nowcity.getText().toString()
									.trim().length() == 0) {
								Toast.makeText(Inforedt.this, "被保人常驻城市不能为空!",
										Toast.LENGTH_SHORT).show();
							} else {
								// 姓名
								intent.putExtra("et_tbr_username",
										et_tbr_username.getText().toString()
												.trim());
								// 性别
								intent.putExtra("tv_tbr_sex", tv_tbr_sex
										.getText().toString().trim());
								// 证件类型
								intent.putExtra("tv_certificate",
										tv_certificate.getText().toString()
												.trim());
								intent.putExtra("tv_certificate_no", String
										.valueOf(tv_certificate.getText()
												.toString().trim()
												.equals("身份证") ? 201 : tbrzjlx
												.trim()));
								// 证件号码
								intent.putExtra("et_tbr_certificate",
										et_tbr_certificate.getText().toString()
												.trim());
								// 出生日期
								intent.putExtra("et_tbr_csdate", et_tbr_csdate
										.getText().toString().trim());
								// 手机号码
								intent.putExtra("et_tbr_phone", et_tbr_phone
										.getText().toString().trim());
								// 邮箱intent
								intent.putExtra("et_tbr_email", et_tbr_email
										.getText().toString().trim());
								// 所在省份
								intent.putExtra("et_tbr_nowprovince",
										et_tbr_nowprovince.getText().toString()
												.trim());
								// 所在城市
								intent.putExtra("et_tbr_nowcity",
										et_tbr_nowcity.getText().toString()
												.trim());

							}
						}
						startActivity(intent);
					}
				}
				break;

			case R.id.tv_back :
				this.finish();
				break;

		}
	}
	private String syrzjlx;
	private String tbrzjlx;
	private String syrgx;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == RESULT_OK) {
			salesman.setMoney(salesman.getMoney()
					+ data.getDoubleExtra("money", 0));

			dsp.saveLogin(this, Constant.getGson().toJson(salesman));
			DepositBean depositBean = new DepositBean();
			depositBean.setSalesman_id(salesman.getSid());
			depositBean.setCredit(data.getDoubleExtra("money", 0) + "");
			depositBean.setDepositType(1);
			depositBean.setCreateDate(format.format(new Date()));

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("depositBean", Constant.getGson()
					.toJson(depositBean)));
			new Thread(new HttpUtils(this, mHandler, WapConstant.URLSTRING
					+ WapConstant.deposit_addDepositByMobile, params)).start();

		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

		};
	};
	private String pinganjiage;
	private Handler Handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (msg.obj != null) {

				try {
					JSONObject demoJson = new JSONObject(msg.obj.toString());
					pinganjiage = demoJson.optString("price");
					pricetext.setText(pinganjiage);

				} catch (JSONException e) {
					e.printStackTrace();

				}
			}
		}
	};

}
