package com.hongguang.jaia_product;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hongguang.jaia.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SuccessQianHaiActivity extends Activity implements OnClickListener {

	private String price, tv_syr_username_1, produce_name;
	private TextView success_name, success_produce_name, date;
	private TextView su_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success_qian_hai);
		Intent intent = getIntent();
		// 产品价格，产品名字
		price = intent.getStringExtra("price");
		produce_name = intent.getStringExtra("produce_name");
		tv_syr_username_1 = intent.getStringExtra("tv_syr_username_1");
		// 成功购买人的名字
		success_name = (TextView) findViewById(R.id.success_name);
		success_name.setText(tv_syr_username_1 + ":");
		// 购买的产品的名字
		success_produce_name = (TextView) findViewById(R.id.success_produce_name);
		success_produce_name.setText(produce_name);
		// 时间显示
		date = (TextView) findViewById(R.id.date);
		date.setText(format.format(new Date()));

		su_back = (TextView) findViewById(R.id.tv_back_sucess);
		su_back.setOnClickListener(this);
	}

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.success_qian_hai, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_back_sucess :
				Intent intent = new Intent(this, ProductActivity.class);
				startActivity(intent);

				break;

		}

	}

}
