package com.hongguang.jaia_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;

public class Financial extends Activity implements OnClickListener {
	private TextView name_bank, kaname, num_bank, type_bank;
	private ImageView addbank, finabank;
	private RelativeLayout relative;

	private DateSharedPreferences dateSharedPreferences;
	private Salesman salesman;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.financial);
		dateSharedPreferences = DateSharedPreferences.getInstance();
	}

	@Override
	protected void onStart() {
		super.onStart();
		salesman = Constant.getGson().fromJson(
				dateSharedPreferences.getLogin(this), Salesman.class);
		InitView();
	}

	private void InitView() {
		relative = (RelativeLayout) findViewById(R.id.finabanks);
		relative.setOnClickListener(this);
		finabank = (ImageView) findViewById(R.id.finabank);
		finabank.setOnClickListener(this);
		addbank = (ImageView) findViewById(R.id.addbank);
		addbank.setOnClickListener(this);
		String num = salesman.getBankNum();
		name_bank = (TextView) findViewById(R.id.name_bank);
		kaname = (TextView) findViewById(R.id.kaname);
		num_bank = (TextView) findViewById(R.id.num_bank);
		type_bank = (TextView) findViewById(R.id.types_bank);

		if (!TextUtils.isEmpty(num)) {
			relative.setVisibility(View.VISIBLE);
			name_bank.setText(salesman.getBank());
			kaname.setText(salesman.getName());
			num = num.substring(num.length(), num.length());
			num_bank.setText(num);
			type_bank.setText("储蓄卡");
		} else {
			relative.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.addbank :
				Intent intent = new Intent();
				intent.setClass(Financial.this, FinancialBank.class);
				startActivity(intent);
				break;
			case R.id.finabank :
				Financial.this.finish();
				break;
			default :
				break;
		}
	}

}
