package com.hongguang.jaia;

import com.hongguang.jaia_product.ProductJiaoYi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class OnLineSale extends Activity implements OnClickListener {

	private RelativeLayout jiaoyi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.onlinesale);
		jiaoyi = (RelativeLayout) findViewById(R.id.rl1_online);
		jiaoyi.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl1_online :
				Intent intent = new Intent(this, ProductJiaoYi.class);
				startActivity(intent);
				break;

			default :
				break;
		}
	}

}
