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

public class PingAnYiJianAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ProductDetailinfo> items = new ArrayList<ProductDetailinfo>();

	public PingAnYiJianAdapter(Context context) {
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

			convertView = inflater.inflate(R.layout.ping_an_yijian_adapter,
					null);
			holder = new ViewHolder();
			// holder.xiangmu=(TextView)convertView.findViewById(R.id.xiangmu);
			holder.fanwei = (TextView) convertView.findViewById(R.id.fanwei);
			holder.jingji = (TextView) convertView.findViewById(R.id.jingji);
			holder.quanmian = (TextView) convertView
					.findViewById(R.id.quanmian);
			holder.haohua = (TextView) convertView.findViewById(R.id.haohua);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 加载数据
		// holder.xiangmu.setText(items.get(position).getName());
		holder.fanwei.setText(String.valueOf(items.get(position).getName()));
		holder.jingji.setText(items.get(position).getPlan1());
		holder.quanmian.setText(items.get(position).getPlan2());
		holder.haohua.setText(items.get(position).getPlan3());

		return convertView;
	}

	class ViewHolder {
		TextView fanwei, jingji, quanmian, haohua;
	}

}
