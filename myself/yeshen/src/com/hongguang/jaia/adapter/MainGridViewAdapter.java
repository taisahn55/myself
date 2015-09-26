package com.hongguang.jaia.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongguang.jaia.R;

public class MainGridViewAdapter extends BaseAdapter {
	// private String text[]={"用户专区","会员专区","业务应用","保险产品","理赔查询",
	// "在线业务","保险宝典","保险咨询","用户反馈","支付专区","用户帮助","二维码"};
	private String text[] = {"买保险", "办理赔", "业务应用", "在线业务", "保险宝典", "保险咨询",
			"用户反馈", "支付专区", "二维码","买车险"};
	private int icons[] = new int[]{

	R.drawable.baoxiancp, R.drawable.lipeicx, R.drawable.yewuyy,
			R.drawable.zaixianyw, R.drawable.baoxianbd, R.drawable.baoxianzx,
			R.drawable.yonghufk, R.drawable.zhifuzq, R.drawable.erweima,R.drawable.erweima};

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return text[position];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler hodler;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_main_gridview, null);
			hodler.img = (ImageView) convertView
					.findViewById(R.id.item_maingrid_img);
			hodler.tv = (TextView) convertView
					.findViewById(R.id.item_maingrid_tv);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}
		hodler.tv.setText(text[position]);
		Drawable drawable = parent.getContext().getResources()
				.getDrawable(icons[position]);

		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());

		hodler.tv.setCompoundDrawables(null, drawable, null, null);
		return convertView;
	}
	class ViewHodler {
		private ImageView img;
		private TextView tv;
	}
}
