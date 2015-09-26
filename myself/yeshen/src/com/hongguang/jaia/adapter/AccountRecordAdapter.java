package com.hongguang.jaia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_accountinfor.AccountRecordItem;

public class AccountRecordAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	private List<AccountRecordItem> items = new ArrayList<AccountRecordItem>();
	public AccountRecordAdapter(Context context) {
		super();
		this.inflater = LayoutInflater.from(context);
	}

	public void setLists(List<AccountRecordItem> items) {
		this.items = items;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = inflater.inflate(R.layout.account_record_adapter,
					null);
			hodler.date = (TextView) convertView
					.findViewById(R.id.account_record_date);
			hodler.mony = (TextView) convertView
					.findViewById(R.id.account_record_mony);
			hodler.type = (TextView) convertView
					.findViewById(R.id.account_type);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}
		hodler.date.setText(items.get(position).getDatetime());
		hodler.mony.setText(String.valueOf(items.get(position).getCredit()));
		hodler.type.setText(items.get(position).getDepositType() + "");
		return convertView;
	}

	class ViewHodler {
		TextView date;
		TextView mony;
		TextView type;
	}
}
