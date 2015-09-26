package com.hongguang.jaia_accountinfor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.DepositBean;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

/*
 * 支付专区页面
 */
public class AccountInforActivity extends Activity implements OnClickListener {
	private Salesman salesman;
	private DateSharedPreferences dsp;
	private TextView name, phone;
	private LinearLayout layout;
	private LinearLayout layoutrecord;
	private TextView moneyTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountinfor);
		dsp = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(dsp.getLogin(this),
				Salesman.class);
		// salesman.setMoney(10000);
		// dsp.saveLogin(this, Constant.getGson().toJson(salesman));
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int code = msg.arg1;
			if (code == 200) {
				if (msg.obj.toString().trim().contains("sid")) {
					Log.e("dong", "code:" + code);
					Log.e("dong", msg.obj.toString());
					System.out.println(msg.obj.toString());
					try {
						JSONObject obj = new JSONObject(msg.obj.toString());
						double total = obj.optJSONArray("rows")
								.getJSONObject(0).optDouble("money");
						moneyTv.setText(String.valueOf(total));

					} catch (JSONException e) {
						e.printStackTrace();

					}
				}
			}
		}
	};

	protected void onStart() {
		intView();
		super.onStart();
	}

	private void intView() {
		name = (TextView) findViewById(R.id.account_name);
		phone = (TextView) findViewById(R.id.tv_accountphone);
		name.setText(salesman.getRealName());
		phone.setText(salesman.getPhone());

		layout = (LinearLayout) findViewById(R.id.layout_recharge);
		layout.setOnClickListener(this);
		layoutrecord = (LinearLayout) findViewById(R.id.layout_recharge_record);
		layoutrecord.setOnClickListener(this);
		moneyTv = (TextView) findViewById(R.id.moneyText);
		moneyTv.setText(String.format("%.2f", salesman.getMoney()));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.layout_recharge :
				Intent intent = new Intent();
				intent.setClass(AccountInforActivity.this,
						PayDemoActivity.class);
				intent.putExtra("classname",
						"com.hongguang.jaia_accountinfor.AccountInforActivity");
				startActivityForResult(intent, 100);
				break;

			case R.id.layout_recharge_record :
				Intent intent2 = new Intent(AccountInforActivity.this,
						AccountRecordActivity.class);
				startActivity(intent2);
			default :
				break;
		}
	}

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == RESULT_OK) {
			moneyTv.setText(String.valueOf(salesman.getMoney()
					+ data.getDoubleExtra("money", 0)));
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
			new Thread(new HttpUtils(this, mHandler1, WapConstant.URLSTRING
					+ WapConstant.deposit_addDepositByMobile, params)).start();

		}
	}

	private Handler mHandler1 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int code = msg.arg1;
			if (code == 200) {
				Log.e("dong", "code:" + code);
				Log.e("dong", msg.obj.toString());
				System.out.println(msg.obj.toString());
			}
		}
	};
}