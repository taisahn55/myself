package com.hongguang.jaia_main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.GetDataFromWeb;
import com.hongguang.jaia_utils.BitmapLruCache;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;
import com.squareup.picasso.Picasso;

public class Certificates extends Activity implements OnClickListener {
	private TextView certiedit, text_id, cyid_text, zyid_text;
	private ImageView certifback, text_image1, text_image2, text_image3,
			text_image4, text_image5, text_image6;

	private DateSharedPreferences dateSharedPreferences;
	private Salesman salesman;
	private BitmapLruCache mBitmapLruCache;
	private String IMAGE_URL = "http://113.105.94.20:8080";
	private Bitmap bitmap;
	private GetDataFromWeb get;

	// private String IMAGE_URL="http://192.168.1.110:8080";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.certificates);
		dateSharedPreferences = DateSharedPreferences.getInstance();

		final int memory = ((ActivityManager) getSystemService(ACTIVITY_SERVICE))
				.getMemoryClass();
		final int cacheSize = 1024 * memory / 8;
		mBitmapLruCache = BitmapLruCache.getInstance(cacheSize);
		InitView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		InitView();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void InitView() {
		certiedit = (TextView) findViewById(R.id.certiedit);
		certiedit.setOnClickListener(this);
		certifback = (ImageView) findViewById(R.id.certifback);
		certifback.setOnClickListener(this);

		salesman = Constant.getGson().fromJson(
				dateSharedPreferences.getLogin(Certificates.this),
				Salesman.class);

		text_id = (TextView) findViewById(R.id.text_id);
		text_id.setText(TextUtils.isEmpty(salesman.getIdNum()) ? "" : salesman
				.getIdNum());

		text_image1 = (ImageView) findViewById(R.id.text_image1);
		if (!TextUtils.isEmpty(salesman.getIdPic1())) {
			l(text_image1, IMAGE_URL + salesman.getIdPic1());
		}

		text_image2 = (ImageView) findViewById(R.id.text_image2);
		if (!TextUtils.isEmpty(salesman.getIdPic2())) {
			l(text_image2, IMAGE_URL + salesman.getIdPic2());
		}

		cyid_text = (TextView) findViewById(R.id.cyid_text);
		cyid_text.setText(TextUtils.isEmpty(salesman.getCongYe())
				? ""
				: salesman.getCongYe());
		text_image3 = (ImageView) findViewById(R.id.text_image3);
		if (!TextUtils.isEmpty(salesman.getCyPic1())) {
			l(text_image3, IMAGE_URL + salesman.getCyPic1());
		}

		text_image4 = (ImageView) findViewById(R.id.text_image4);
		if (!TextUtils.isEmpty(salesman.getCyPic2())) {
			l(text_image4, IMAGE_URL + salesman.getCyPic2());

		}

		zyid_text = (TextView) findViewById(R.id.zyid_text);
		zyid_text.setText(TextUtils.isEmpty(salesman.getZhanYe())
				? ""
				: salesman.getZhanYe());
		text_image5 = (ImageView) findViewById(R.id.text_image5);
		if (!TextUtils.isEmpty(salesman.getZyPic1())) {
			l(text_image5, IMAGE_URL + salesman.getZyPic1());
		}

		text_image6 = (ImageView) findViewById(R.id.text_image6);
		if (!TextUtils.isEmpty(salesman.getZyPic2())) {
			l(text_image6, IMAGE_URL + salesman.getZyPic2());

		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			case R.id.certiedit :
				intent = new Intent();
				intent.setClass(Certificates.this, Editficates.class);
				startActivityForResult(intent, 100);
				break;
			case R.id.certifback :
				Certificates.this.finish();
				break;
			default :
				break;
		}
	}

	public void l(final ImageView iv, final String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				GetDataFromWeb get = new GetDataFromWeb(Certificates.this);
				byte[] date = get.getData(url);
				Message msg = mHandler.obtainMessage();
				msg.obj = iv;
				Bundle bundle = new Bundle();
				bundle.putByteArray("date", date);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			byte[] date = bundle.getByteArray("date");
			ImageView iv = (ImageView) msg.obj;
			if (date != null) {
				Log.i("二维码图片地址~~~~~~~~~~~~", WapConstant.URLIMAGEString
						+ salesman.getQrcode());
				bitmap = BitmapFactory.decodeByteArray(date, 0, date.length);
				iv.setImageBitmap(bitmap);
			}
		}
	};

}
