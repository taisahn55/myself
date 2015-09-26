package com.hongguang.jaia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.ProductInformation;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.GetAndStoreBitmap;
import com.hongguang.jaia_utils.WapConstant;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductListviewAdapter extends BaseAdapter {

	List<ProductInformation> proinf = new ArrayList<ProductInformation>();
	private Context context;
	private ArrayList<String> urls;
	private int width;
	private DateSharedPreferences dsp = DateSharedPreferences.getInstance();
	public ProductListviewAdapter(ArrayList<String> urls) {
		super();
		this.urls = urls;

	}

	public ProductListviewAdapter(Context context,
			List<ProductInformation> proinf) {
		super();
		this.context = context;
		width = (int) ((Integer.valueOf(dsp.get(context, "screenW", "0")) - 40 * Float
				.valueOf(dsp.get(context, "screenDensity", "0.0"))) / 3);
		this.proinf = proinf;
	}
	public void setData(List<ProductInformation> proinf) {
		this.proinf = proinf;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return proinf.size();
	}

	@Override
	public Object getItem(int position) {

		return proinf.get(position);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler hodler;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_listview_product, null);
			hodler.img = (ImageView) convertView
					.findViewById(R.id.img_itemlist_product);
			hodler.tv = (TextView) convertView
					.findViewById(R.id.text_itemlist_product);
			// hodler.tv1=(TextView) convertView.findViewById(R.id.baofei);
			hodler.tv2 = (TextView) convertView.findViewById(R.id.baofei);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}
		ProductInformation info = proinf.get(position);
		hodler.tv.setText(info.getName());
		// hodler.tv2.setText(info.getAmount()+"元"+"/"+new
		// Double(info.getPrice()).intValue()+"元");
		hodler.tv2
				.setText(info.getAmount() + "元" + "/" + info.getPrice() + "元");
		info.getProductImgList();

		Bitmap loading = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.loading_pic);
		loading = handleBitmap(width, width, loading);
		hodler.img.setImageBitmap(loading);

		String URL = WapConstant.getHttpServer_HOST(context)
				+ info.getProductImgList();
		hodler.img.setTag(URL);
		new GetAndStoreBitmap(context).getBitmap(width, width, hodler.img, URL,
				null, false, true);
		return convertView;

	}
	class ViewHodler {
		private ImageView img;
		private TextView tv, tv2;
	}
	public Bitmap handleBitmap(int w, int h, Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleHeight = (float) h / height;
		float scaleWidht = (float) w / width;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBitmap;
	}

}
