package com.hongguang.jaia_chexian;

import com.hongguang.jaia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InsureActivity extends Activity {

	private Button bt_toubao;
	private TextView jiantou,diqu;
	EditText chepaihao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maichexian);
		diqu = (TextView) findViewById(R.id.diqu);
		jiantou = (TextView) findViewById(R.id.jiantou);
		chepaihao = (EditText) findViewById(R.id.chepaihao);
		jiantou.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		diqu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	public void insure(View view){
		
		Intent intent = new Intent(this,BasicInformation.class);
		startActivity(intent);
		
	}
}
