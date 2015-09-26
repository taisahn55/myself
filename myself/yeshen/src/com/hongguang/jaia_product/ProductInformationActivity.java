package com.hongguang.jaia_product;

import com.hongguang.jaia.R;
import com.hongguang.jaia.R.layout;
import com.hongguang.jaia.R.menu;
import com.hongguang.jaia_bean.ProductInformation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ProductInformationActivity extends Activity {
	TextView tbxz, tbsms, tbgz, lpxz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_information_guize);
		tbxz = (TextView) findViewById(R.id.toubaoxz);
		tbsms = (TextView) findViewById(R.id.toubaosms);
		tbgz = (TextView) findViewById(R.id.toubaogz);
		lpxz = (TextView) findViewById(R.id.lpxz);

		Intent intent2 = getIntent();
		tbgz.setText(intent2.getStringExtra("insuranceRules"));
		tbsms.setText(intent2.getStringExtra("insuranceDeclaration"));
		tbxz.setText(intent2.getStringExtra("insuranceNote"));
		lpxz.setText(intent2.getStringExtra("claimNote"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_information, menu);
		return true;
	}

}
