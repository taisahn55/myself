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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

public class FinancialBank extends Activity implements OnClickListener {
	private ImageView back_fina;
	private EditText chika, chikahao, yinhang;
	private Button submit;
	private Salesman salesman;
	private DateSharedPreferences dateSharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.financialbank);
		dateSharedPreferences = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(
				dateSharedPreferences.getLogin(this), Salesman.class);
		InitView();
	}

	private void InitView() {
		back_fina = (ImageView) findViewById(R.id.back_finas);
		back_fina.setOnClickListener(this);
		chika = (EditText) findViewById(R.id.chika);
		chikahao = (EditText) findViewById(R.id.chikahao);
		yinhang = (EditText) findViewById(R.id.yinhang);
		submit = (Button) findViewById(R.id.addsubmit);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.back_finas :
				FinancialBank.this.finish();
				break;
			case R.id.addsubmit :
				String chikaren = chika.getText().toString().trim();
				String chikahaom = chikahao.getText().toString().trim();
				String yinhangString = yinhang.getText().toString().trim();
				if (TextUtils.isEmpty(chikaren)) {
					Toast.makeText(FinancialBank.this, "持卡人名称不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (TextUtils.isEmpty(chikahaom)) {
					Toast.makeText(FinancialBank.this, "卡号不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (TextUtils.isEmpty(yinhangString)) {
					Toast.makeText(FinancialBank.this, "所属银行必须填写",
							Toast.LENGTH_SHORT).show();
					return;
				}
				salesman.setBank(yinhangString);
				salesman.setBankNum(chikahaom);
				salesman.setBankUser(chikaren);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("salesbean", Constant
						.getGson().toJson(salesman)));
				new Thread(new HttpUtils(this, mHandler, WapConstant.URLSTRING
						+ WapConstant.ADDBANKString, params)).start();
				break;
			default :
				break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int m = msg.arg1;
			if (m == 200) {
				if (msg.obj.toString().contains("success")) {
					Toast.makeText(FinancialBank.this, "添加银行信息成功",
							Toast.LENGTH_LONG).show();
					dateSharedPreferences.saveLogin(FinancialBank.this,
							Constant.getGson().toJson(salesman));
					FinancialBank.this.finish();
				} else {
					Log.e("dong", "响应:" + m);
					Log.e("dong", "响应:" + msg.obj.toString());
					Toast.makeText(FinancialBank.this, msg.obj.toString(),
							Toast.LENGTH_LONG).show();
				}
			} else {
				Log.e("dong", "响应:" + m);
				Log.e("dong", "响应:" + msg.obj.toString());
				Toast.makeText(FinancialBank.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
	};
}
