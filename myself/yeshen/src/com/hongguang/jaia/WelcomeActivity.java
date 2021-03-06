package com.hongguang.jaia;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hongguang.jaia_main.MainActivity;
import com.hongguang.jaia_utils.DateSharedPreferences;

public class WelcomeActivity extends Activity {
	private DateSharedPreferences sp;
	public static ExecutorService pool = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors() + 1);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		setContentView(R.layout.welcome);

		PackageManager pm = getPackageManager();

		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			System.out.println("VersionName:" + info.versionName
					+ " VersionCode:" + info.versionCode);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		sp = DateSharedPreferences.getInstance();
		// dm.widthPixels在显示器的像素的绝对宽度。
		sp.save(this, "screenW", dm.widthPixels + "");
		sp.save(this, "screenH", dm.heightPixels + "");
		sp.save(this, "screenDensity", dm.density + "");

		final String loginMessahe = sp.getLogin(this);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent();
				if (!TextUtils.isEmpty(loginMessahe)) {
					intent.setClass(getApplicationContext(), MainActivity.class);
				} else {
					intent.setClass(getApplicationContext(),
							LoginActivity.class);
				}
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		}, 2000);
	}

}
