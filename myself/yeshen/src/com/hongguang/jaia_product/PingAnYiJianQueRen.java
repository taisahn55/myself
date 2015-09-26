package com.hongguang.jaia_product;

import java.util.Calendar;

import com.hongguang.jaia.R;
import com.hongguang.jaia.R.id;
import com.hongguang.jaia.R.layout;
import com.hongguang.jaia.R.menu;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PingAnYiJianQueRen extends Activity implements OnClickListener {

	private EditText mudidi, renshu_1, renshu_3, renshu_2;
	private RadioGroup leixing, baoxianjihua;
	private TextView date_start, date_end;
	private int radioButtonId;
	private Calendar c = Calendar.getInstance();
	private TextView lijipay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ping_an_yi_jian_que_ren);
		// 旅行目的地
		mudidi = (EditText) findViewById(R.id.mudidi);
		// 旅行类型
		leixing = (RadioGroup) findViewById(R.id.radioGroup1);
		leixing.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radio, int pos) {
				// 获取变更后的选中项的ID
				radioButtonId = radio.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) PingAnYiJianQueRen.this
						.findViewById(radioButtonId);
				if (radioButtonId == R.id.radio1) {
					date_end.setVisibility(View.GONE);
					findViewById(R.id.date_end_1).setVisibility(View.GONE);
					date_end.setText(date_start + "二十四时止，共  1 天 年");
				} else {
					findViewById(R.id.date_end_1).setVisibility(View.VISIBLE);
				}
			}
		});

		// 开始时间
		date_start = (TextView) findViewById(R.id.date_start);
		date_start.setOnClickListener(this);
		// 结束时间
		date_end = (TextView) findViewById(R.id.date_end);
		date_end.setOnClickListener(this);

		// 0-66岁人数
		renshu_1 = (EditText) findViewById(R.id.renshu_1);
		// 66-75周岁
		renshu_2 = (EditText) findViewById(R.id.renshu_2);
		// 75-88周岁
		renshu_3 = (EditText) findViewById(R.id.renshu_3);
		// 旅行类型
		baoxianjihua = (RadioGroup) findViewById(R.id.radioGroup2);
		//
		lijipay = (TextView) findViewById(R.id.lijipay_2);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.date_start :
				DatePickerDialog dialog = new DatePickerDialog(
						PingAnYiJianQueRen.this, new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								c = Calendar.getInstance();
								c.set(year, monthOfYear, dayOfMonth);
								date_start.setText(DateFormat.format(
										"yyy-MM-dd", c));
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH));
				dialog.show();
				break;
			case R.id.date_end :
				DatePickerDialog dialog1 = new DatePickerDialog(
						PingAnYiJianQueRen.this, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								Calendar c = Calendar.getInstance();
								c.set(year, monthOfYear, dayOfMonth);
								date_end.setText(DateFormat.format("yyy-MM-dd",
										c));
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH));
				dialog1.show();
				break;
			case R.id.lijipay_2 :
				if (mudidi.getText().toString().trim().isEmpty()) {
					Toast.makeText(this, "目的地不能为空", Toast.LENGTH_LONG).show();
				}

				Intent intent = new Intent(this, Inforedt.class);
				startActivity(intent);
				break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ping_an_yi_jian_que_ren, menu);
		return true;
	}

}
