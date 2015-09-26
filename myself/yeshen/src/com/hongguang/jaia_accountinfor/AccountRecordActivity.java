package com.hongguang.jaia_accountinfor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.hongguang.jaia.R;
import com.hongguang.jaia.adapter.AccountRecordAdapter;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_ui.XListView;
import com.hongguang.jaia_ui.XListView.IXListViewListener;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;

public class AccountRecordActivity extends Activity
		implements
			IXListViewListener {
	private XListView listview;
	private Salesman salesman;
	private DateSharedPreferences dsp;
	private AccountRecordAdapter accountRecordAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_record);
		listview = (XListView) findViewById(R.id.account_record);
		listview.setPullRefreshEnable(true);
		listview.setPullLoadEnable(false);
		listview.setXListViewListener(this);
		dsp = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(dsp.getLogin(this),
				Salesman.class);
		accountRecordAdapter = new AccountRecordAdapter(this);
		listview.setAdapter(accountRecordAdapter);
		query(true);
	}

	private void query(boolean showprogress) {
		if (pageIndex == 1) {
			recorditems.clear();
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("salesman_id", String
				.valueOf(salesman.getSid())));
		params.add(new BasicNameValuePair("pageindex", String
				.valueOf(pageIndex)));
		params.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
		if (showprogress) {
			new Thread(new HttpUtils(this, "数据加载中...", mHandler,
					WapConstant.URLSTRING + WapConstant.AccountRecord, params))
					.start();
		} else {
			new Thread(new HttpUtils(this, mHandler, WapConstant.URLSTRING
					+ WapConstant.AccountRecord, params)).start();
		}
	}

	private int pageIndex = 1;
	private int pageSize = 10;
	private List<AccountRecordItem> recorditems = new ArrayList<AccountRecordItem>();
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
					int total = obj.optInt("total");
					if (recorditems.size() > 0 && recorditems.size() == total) {
						Toast.makeText(AccountRecordActivity.this, "数据已经加载完毕!",
								Toast.LENGTH_SHORT).show();
					} else {
						for (int i = 0; i < array.length(); i++) {
							JSONObject objtemp = array.optJSONObject(i);
							AccountRecordItem item = new AccountRecordItem(
									objtemp.optString("id"), "支付宝",
									objtemp.optString("salesman_id"),
									objtemp.optString("createDate"),
									objtemp.optDouble("credit"));
							recorditems.add(item);
						}
						if (recorditems.size() > 0) {
							pageIndex++;
							listview.setPullLoadEnable(true);
						}
					}
					listview.stopRefresh();
					listview.stopLoadMore();
					listview.setRefreshTime("刚刚");
					accountRecordAdapter.setLists(recorditems);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				pageIndex = 1;
				query(false);
			}
		}, 200);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				query(false);
			}
		}, 200);
	}

}
