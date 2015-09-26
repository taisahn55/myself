package com.hongguang.jaia.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.ProductDetailinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductInfomationAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ProductDetailinfo> items = new ArrayList<ProductDetailinfo>();

	public ProductInfomationAdapter(Context context) {
		super();
		this.inflater = LayoutInflater.from(context);
	}

	public void setLists(List<ProductDetailinfo> items) {
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.product_information_adapter, null);
			holder = new ViewHolder();
			holder.xianzhong = (TextView) convertView
					.findViewById(R.id.xianzhong);
			holder.zeren = (TextView) convertView.findViewById(R.id.zeren);
			holder.baoe = (TextView) convertView.findViewById(R.id.zerenprice);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 加载数据
		holder.xianzhong.setText(items.get(position).getName());
		holder.zeren.setText(String.valueOf(items.get(position)
				.getInsuranceliability()));
		holder.baoe.setText(items.get(position).getAmount());

		return convertView;
	}

	class ViewHolder {
		TextView xianzhong, zeren, baoe;
	}

}
