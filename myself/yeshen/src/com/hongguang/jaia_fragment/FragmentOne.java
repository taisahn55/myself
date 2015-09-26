package com.hongguang.jaia_fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hongguang.jaia.R;
import com.hongguang.jaia.adapter.ProductListviewAdapter;
import com.hongguang.jaia_accountinfor.AccountRecordActivity;
import com.hongguang.jaia_accountinfor.AccountRecordItem;
import com.hongguang.jaia_bean.ProductInformation;
import com.hongguang.jaia_product.Inforedt;
import com.hongguang.jaia_product.ProductInformationActivity;
import com.hongguang.jaia_product.ProductListActivity;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.WapConstant;

public class FragmentOne extends Fragment implements OnItemClickListener {
	// 车险

	private ListView listview;
	ProductListviewAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		new Thread(new HttpUtils(container.getContext(), "数据加载中...", mHandler,
				WapConstant.URLSTRING + WapConstant.product_cxjiekou, null))
				.start();

		View view = inflater.inflate(R.layout.frag_one, null);
		listview = (ListView) view.findViewById(R.id.listview_frag1);
		listview.setOnItemClickListener(this);
		adapter = new ProductListviewAdapter(container.getContext(), proinf);
		listview.setAdapter(adapter);
		return view;
	}

	List<ProductInformation> proinf = new ArrayList<ProductInformation>();
	ProductInformation info;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int code = msg.arg1;
			if (code == 200) {
				Log.e("dong", "code:" + code);
				Log.e("dong", msg.obj.toString());
				System.out.println(msg.obj.toString());
				try {
					JSONObject obj = new JSONObject(msg.obj.toString());
					JSONArray array = obj.optJSONArray("rows_cx");
					for (int i = 0; i < array.length(); i++) {
						JSONObject objtemp = array.optJSONObject(i);
						info = new ProductInformation(
								objtemp.optString("name"),
								objtemp.optString("amount"),
								objtemp.optString("price"));
						info.setInsuranceType_id(objtemp
								.optString("insuranceType_id"));
						info.setId(objtemp.optString("id"));
						info.setProductImgList(objtemp
								.optString("productImgList"));

						info.setInsuranceDeclaration(objtemp
								.optString("insuranceDeclaration"));
						info.setInsuranceNote(objtemp
								.optString("insuranceNote"));
						info.setInsuranceRules(objtemp
								.optString("insuranceRules"));
						info.setClaimNote(objtemp.optString("claimNote"));
						info.setInterfaceName(objtemp
								.optString("interfaceName"));

						// 保险开始时间
						info.setStartdate(objtemp.optString("startdate"));
						info.setDatelimit(objtemp.optString("datelimit"));

						proinf.add(info);
					}
					adapter.setData(proinf);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null) {
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Intent intent = new Intent();

		intent.putExtra("product_id", proinf.get(arg2).getId());
		intent.putExtra("productname", proinf.get(arg2).getName());
		intent.putExtra("insuranceType_id", proinf.get(arg2)
				.getInsuranceType_id());
		intent.putExtra("price", proinf.get(arg2).getPrice());
		intent.putExtra("interfaceName", proinf.get(arg2).getInterfaceName());
		// 保额加保费
		intent.putExtra("baoe", proinf.get(arg2).getAmount() + "元" + "/"
				+ new Double(proinf.get(arg2).getPrice()).intValue() + "元");

		// 保险开始时间和保额
		intent.putExtra("startdate", proinf.get(arg2).getStartdate());
		intent.putExtra("datelimit", proinf.get(arg2).getDatelimit());

		// 投保规则等
		intent.putExtra("insuranceRules", proinf.get(arg2).getInsuranceRules());
		intent.putExtra("insuranceDeclaration", proinf.get(arg2)
				.getInsuranceDeclaration());
		intent.putExtra("insuranceNote", proinf.get(arg2).getInsuranceNote());
		intent.putExtra("claimNote", proinf.get(arg2).getClaimNote());

		intent.setClass(getActivity(), ProductListActivity.class);
		startActivity(intent);

	}
}
