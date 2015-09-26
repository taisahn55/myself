package com.hongguang.jaia_product;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hongguang.jaia.R;
import com.hongguang.jaia.adapter.ProductInfomationAdapter;
import com.hongguang.jaia_bean.ProductDetailinfo;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_ui.LinearLayoutForListView;
import com.hongguang.jaia_utils.WapConstant;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("UseValueOf")
public class ProductListActivity extends Activity implements OnClickListener {
	private Button tj;// 提交按钮
	private TextView tvpaymoney, product_id;// 支付的金额
	private String price, product_id_1;
	// /协议选择框
	private CheckBox cbxie;
	private LinearLayoutForListView productl;
	private ProductInfomationAdapter adapter;
	private TextView baoe, productname;
	private String baoe_1, productname_1, insuranceType_id;
	private String insuranceRules, insuranceDeclaration, insuranceNote,
			claimNote, baoxianqixian_1, baoxianshengxiaoriqi_1;
	private TextView baoxianqixian;
	private TextView baoxianshengxiaoriqi;
	private String interfaceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_information);
		Intent intent = getIntent();
		price = intent.getStringExtra("price");
		product_id_1 = intent.getStringExtra("product_id");
		productname_1 = intent.getStringExtra("productname");
		insuranceType_id = intent.getStringExtra("insuranceType_id");
		// 接口
		interfaceName = intent.getStringExtra("interfaceName");

		baoe_1 = intent.getStringExtra("baoe");
		// 投保须知等
		insuranceRules = intent.getStringExtra("insuranceRules");
		insuranceDeclaration = intent.getStringExtra("insuranceDeclaration");
		insuranceNote = intent.getStringExtra("insuranceNote");
		claimNote = intent.getStringExtra("claimNote");

		baoxianshengxiaoriqi_1 = intent.getStringExtra("startdate");
		baoxianqixian_1 = intent.getStringExtra("datelimit");

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// baodaninfo.setProductId(proinf.get(arg2).getId());
		params.add(new BasicNameValuePair("product_id", String
				.valueOf(insuranceType_id)));
		// 数据接口
		new Thread(new HttpUtils(this, "数据加载中...", mHandler,
				WapConstant.URLSTRING + WapConstant.product_xx, params))
				.start();

		// 产品详情
		TextView proinf = (TextView) findViewById(R.id.chakanxiangqing);
		proinf.setOnClickListener(this);

		tvpaymoney = (TextView) findViewById(R.id.baofei1);
		tvpaymoney.setText(price + "元");
		// 保险有限期
		baoxianqixian = (TextView) findViewById(R.id.baoxianqixian);
		baoxianqixian.setText(baoxianqixian_1);

		// 保险生效日期
		baoxianshengxiaoriqi = (TextView) findViewById(R.id.baoxianshengxiaoriqi);
		baoxianshengxiaoriqi.setText(baoxianshengxiaoriqi_1);

		// 保额和保费
		baoe = (TextView) findViewById(R.id.baoe);
		baoe.setText(baoe_1);

		// 产品ID
		product_id = (TextView) findViewById(R.id.textView10);
		product_id.setText(product_id_1);
		// 保险的名字
		productname = (TextView) findViewById(R.id.productname);
		productname.setText(productname_1);
		// listView 以及adapter的显示
		productl = (LinearLayoutForListView) findViewById(R.id.prolist);
		adapter = new ProductInfomationAdapter(this);
		productl.setAdapter(adapter);

		// 保险协议条目
		LinearLayout bxxy = (LinearLayout) findViewById(R.id.baoxianxieyi);

		cbxie = (CheckBox) findViewById(R.id.cb_productxieyi);
		bxxy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cbxie.isChecked() == false) {
					cbxie.setChecked(true);
				}
			}
		});

		// 提交保单
		tj = (Button) findViewById(R.id.btn_tj);
		tj.setOnClickListener(this);

	}

	List<ProductDetailinfo> proinf = new ArrayList<ProductDetailinfo>();
	ProductDetailinfo info;
	private String startdate_1;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int code = msg.arg1;
			if (code == 200) {
				Log.e("dong", "code:" + code);
				Log.e("dong", msg.obj.toString());
				System.out.println(msg.obj.toString());
				try {
					JSONObject obj = new JSONObject(msg.obj.toString());
					JSONArray array = obj.optJSONArray("rows");

					for (int i = 0; i < array.length(); i++) {
						JSONObject objtemp = array.optJSONObject(i);
						info = new ProductDetailinfo(objtemp.optString("name"),
								objtemp.optString("amount"),
								objtemp.optString("insuranceliability"));

						proinf.add(info);
					}
					adapter.setLists(proinf);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_tj :
				if (cbxie.isChecked() == false) {
					Toast.makeText(this, "请查看保险协议", Toast.LENGTH_SHORT).show();
				} else {

					Intent intent = new Intent();
					intent.putExtra("price", price);
					intent.putExtra("productname", productname_1);
					intent.putExtra("product_id", product_id_1);
					intent.putExtra("insuranceType_id", insuranceType_id);
					intent.putExtra("interfaceName", interfaceName);
					intent.setClass(this, Inforedt.class);
					startActivity(intent);
				}
				break;

			case R.id.chakanxiangqing :
				Intent intent2 = new Intent();
				intent2.putExtra("insuranceRules", insuranceRules);
				intent2.putExtra("insuranceDeclaration", insuranceDeclaration);
				intent2.putExtra("insuranceNote", insuranceNote);
				intent2.putExtra("claimNote", claimNote);
				intent2.setClass(this, ProductInformationActivity.class);
				startActivity(intent2);
				break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.product_list, menu);
		return true;
	}

}
