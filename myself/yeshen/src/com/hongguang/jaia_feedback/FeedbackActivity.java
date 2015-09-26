package com.hongguang.jaia_feedback;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_bean.Suggestion;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

public class FeedbackActivity extends Activity {
	private EditText suggestion_context, suggestion_name, suggestion_phone;

	private TextView suggestion_but;

	private DateSharedPreferences dsp;
	private Salesman salesman;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		dsp = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(dsp.getLogin(this),
				Salesman.class);
		// 内容
		suggestion_context = (EditText) findViewById(R.id.suggest_et1);
		// 名字
		suggestion_name = (EditText) findViewById(R.id.suggest_name);
		suggestion_name.setText(salesman.getRealName());

		// 电话
		suggestion_phone = (EditText) findViewById(R.id.suggest_phone);
		suggestion_phone.setText(salesman.getPhone());
		// 提交按钮
		suggestion_but = (TextView) findViewById(R.id.suggest_but);
		suggestion_but.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Suggestion suggestion = new Suggestion();
				suggestion.setContext(suggestion_context.getText().toString());
				suggestion.setName(suggestion_name.getText().toString());
				suggestion.setPhone(suggestion_phone.getText().toString());

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("suggestion", Constant
						.getGson().toJson(suggestion)));
				new Thread(new HttpUtils(FeedbackActivity.this, "订单处理中...",
						mHandler, WapConstant.URLSTRING
								+ WapConstant.product_insert, params)).start();
			}
		});

	}
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
		};
	};

}
