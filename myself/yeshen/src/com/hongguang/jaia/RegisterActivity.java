package com.hongguang.jaia;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.WapConstant;

/*
 * 注册界面
 */
public class RegisterActivity extends Activity implements OnClickListener {
	private TextView city_tv;
	private ImageView resign_back;
	private EditText ph_Edit, realname, ema_Edit, userpwd, mainname;
	private Button resign_btn;
	private CheckBox cb_xieyi;
	private String s1 = "1[0-9]{10}", s2 = "[a-zA-Z-0-9]{0,99}@136.com";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resign);
		InitView();
	}

	private void InitView() {
		city_tv = (TextView) findViewById(R.id.city);
		city_tv.setOnClickListener(this);
		resign_back = (ImageView) findViewById(R.id.back);
		resign_back.setOnClickListener(this);

		ph_Edit = (EditText) findViewById(R.id.ph_Edit);
		realname = (EditText) findViewById(R.id.realname);
		mainname = (EditText) findViewById(R.id.mainname);
		ema_Edit = (EditText) findViewById(R.id.ema_Edit);
		userpwd = (EditText) findViewById(R.id.userpwd);

		resign_btn = (Button) findViewById(R.id.resign_btn);
		resign_btn.setOnClickListener(this);
		cb_xieyi = (CheckBox) findViewById(R.id.cb_xieyi);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
			case R.id.city :
				intent = new Intent(RegisterActivity.this,
						CitySelectActivity.class);
				startActivityForResult(intent, 200);
				break;
			case R.id.back :
				RegisterActivity.this.finish();
				break;
			case R.id.resign_btn :
				if (!cb_xieyi.isChecked()) {
					Toast.makeText(RegisterActivity.this, R.string.tonyixieyi,
							Toast.LENGTH_LONG).show();
					return;
				}

				List<NameValuePair> params = new ArrayList<NameValuePair>();

				Salesman sale = new Salesman();
				sale.setPhone(ph_Edit.getText().toString().trim());
				sale.setName(mainname.getText().toString().trim());
				sale.setEmail(ema_Edit.getText().toString().trim());
				sale.setRealName(realname.getText().toString().trim());
				sale.setPassWord(userpwd.getText().toString().trim());

				// 判断没有加全
				if (TextUtils.isEmpty(sale.getPhone())) {
					Toast.makeText(RegisterActivity.this, R.string.errorphone,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (TextUtils.isEmpty(sale.getName())) {
					Toast.makeText(RegisterActivity.this, R.string.errorname,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (TextUtils.isEmpty(sale.getEmail())) {
					Toast.makeText(RegisterActivity.this, R.string.erroremail,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (TextUtils.isEmpty(sale.getRealName())) {
					Toast.makeText(RegisterActivity.this,
							R.string.errorrealname, Toast.LENGTH_LONG).show();
					return;
				}
				if (TextUtils.isEmpty(sale.getPassWord())) {
					Toast.makeText(RegisterActivity.this, R.string.errorpass,
							Toast.LENGTH_LONG).show();
					return;
				}
				// 判断手机号码是否开头为1
				Pattern p = Pattern.compile(s1);
				Matcher m = p.matcher(ph_Edit.getText().toString());
				if (!m.matches()) {
					Toast.makeText(RegisterActivity.this,
							R.string.Please_enter_correct_phone, 1000).show();
					return;
				}
				// 判断邮箱不能为@136.com
				Pattern p1 = Pattern.compile(s2);
				Matcher m1 = p1.matcher(ema_Edit.getText().toString());
				if (m1.matches()) {
					Toast.makeText(RegisterActivity.this,
							R.string.email163_cannot_resign, 1000).show();
					return;
				}

				params.add(new BasicNameValuePair("salesbean", Constant
						.getGson().toJson(sale)));
				new Thread(new HttpUtils(this, mHandler, WapConstant.URLSTRING
						+ WapConstant.INSERTString, params)).start();
				break;
			default :
				break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int code = msg.arg1;
			if (code == 200) {
				if (msg.obj.toString().contains("注册成功")) {
					Toast.makeText(
							RegisterActivity.this.getApplicationContext(),
							R.string.success_resign, Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(RegisterActivity.this, LoginActivity.class);
					intent.putExtra("user", ph_Edit.getText().toString().trim());
					intent.putExtra("pwd", userpwd.getText().toString().trim());
					setResult(200, intent);
					RegisterActivity.this.finish();
				} else {
					Log.e("dong", code + "");
					Log.e("dong", msg.obj.toString());
					Toast.makeText(RegisterActivity.this, "---->>>>注册失败",
							Toast.LENGTH_LONG).show();
					Toast.makeText(RegisterActivity.this, msg.obj.toString(),
							Toast.LENGTH_LONG).show();
				}

			} else if (code == 500) {
				Log.e("dong", code + "");
				Log.e("dong", msg.obj.toString());

				Toast.makeText(RegisterActivity.this, "---->>>>@@500",
						Toast.LENGTH_LONG).show();
				Toast.makeText(RegisterActivity.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 201) {
			Bundle bundle = data.getExtras();
			String cityname = bundle.getString("city");
			TextView city = (TextView) findViewById(R.id.city);
			city.setText(cityname);
		} else if (resultCode == 200) {

		}
	}
}
