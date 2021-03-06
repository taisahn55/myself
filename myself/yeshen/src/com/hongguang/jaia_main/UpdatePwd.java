package com.hongguang.jaia_main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

public class UpdatePwd extends Activity implements OnClickListener {
	private EditText oldpass, newpass, newpassto;
	private TextView submit;
	private ImageView back;
	private DateSharedPreferences dateSharedPreferences;
	private Salesman salesman;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepwd);
		dateSharedPreferences = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(
				dateSharedPreferences.getLogin(this), Salesman.class);

		InitView();

	}

	private void InitView() {
		oldpass = (EditText) findViewById(R.id.oldpass);
		newpass = (EditText) findViewById(R.id.newpass);
		newpassto = (EditText) findViewById(R.id.newpassto);
		submit = (TextView) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.submit :
				String opass = oldpass.getText().toString().trim();
				String nopass = newpass.getText().toString().trim();
				String nopasst = newpassto.getText().toString().trim();

				if (TextUtils.isEmpty(opass)) {
					Toast.makeText(UpdatePwd.this, "旧密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!opass.equals(salesman.getPassWord())) {
					Toast.makeText(UpdatePwd.this, "旧密码错误", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (TextUtils.isEmpty(nopass)) {
					Toast.makeText(UpdatePwd.this, "新密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!nopass.equals(nopasst)) {
					Toast.makeText(UpdatePwd.this, "两次密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (opass.equals(nopasst)) {
					Toast.makeText(UpdatePwd.this, "没有进行修改", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String[] str = new String[]{salesman.getPhone(), nopasst};
				params.add(new BasicNameValuePair("modpass", Constant.getGson()
						.toJson(str)));
				new Thread(new HttpUtils(this, mHandler, WapConstant.URLSTRING
						+ WapConstant.UPDATEPASS, params)).start();

				break;
			case R.id.back :
				UpdatePwd.this.finish();
				break;
			default :
				break;
		}

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int m = msg.arg1;
			if (m == 200) {
				if (msg.obj.toString().contains("密码修改成功")) {
					Toast.makeText(UpdatePwd.this, msg.obj.toString(),
							Toast.LENGTH_LONG).show();
					salesman.setPassWord(newpassto.getText().toString().trim());
					dateSharedPreferences.saveLogin(UpdatePwd.this, Constant
							.getGson().toJson(salesman));
					UpdatePwd.this.finish();
				} else {
					Log.e("dong", "响应:" + m);
					Log.e("dong", "响应:" + msg.obj.toString());
					Toast.makeText(UpdatePwd.this, msg.obj.toString(),
							Toast.LENGTH_LONG).show();
				}
			} else {
				Log.e("dong", "响应:" + m);
				Log.e("dong", "响应:" + msg.obj.toString());
				Toast.makeText(UpdatePwd.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
	};
}
