package com.hongguang.jaia;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.WapConstant;

public class BaseActivity extends Activity {

	private boolean type = false;

	public void check(boolean type) {
		this.type = type;
		if (type) {
			new Thread(new HttpUtils(this, "检测新版本", handler,
					WapConstant.READ_VERSION, null)).start();
		} else {
			new Thread(new HttpUtils(this, null, handler,
					WapConstant.READ_VERSION, null)).start();
		}
	}

	private AlertDialog.Builder mbuilder;
	private AlertDialog alertDialog;
	private ProgressBar progressBar;
	private TextView teView;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.arg1 == 200) {
				try {
					JSONObject obj = new JSONObject(msg.obj.toString());
					JSONObject obj_ = obj.optJSONArray("rows").getJSONObject(0);
					String versionCode = obj_.optString("versioncode");
					String versionInfo = obj_.optString("versioninfo");
					String oldcode = getResources().getString(R.string.version);
					if (needUpdate(oldcode.trim().split("\\."), versionCode
							.trim().split("\\."))) {

						Builder builder = new Builder(BaseActivity.this);
						builder.setTitle("发现新版本!");
						builder.setCancelable(false);
						builder.setMessage(versionInfo);
						builder.setPositiveButton("马上更新",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										showLoadlayout();
										task.execute(WapConstant
												.getHttpServer_HOST(BaseActivity.this)
												+ WapConstant.UPDATE_VERSION);
									}
								});
						builder.setNeutralButton("以后再说",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								});
						builder.show();

					} else {
						if (type) {
							Toast.makeText(BaseActivity.this, "已是最新版本!",
									Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	private boolean isruning = false;

	private void showLoadlayout() {
		mbuilder = new Builder(BaseActivity.this);
		mbuilder.setTitle("下载新版本!");
		mbuilder.setCancelable(false);

		LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(
				BaseActivity.this).inflate(R.layout.layout_loadapk, null);
		progressBar = (ProgressBar) linearLayout.findViewById(R.id.down_pb);
		progressBar.setProgress(0);
		teView = (TextView) linearLayout.findViewById(R.id.tv2);
		mbuilder.setView(linearLayout);

		mbuilder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				isruning = false;
				alertDialog.dismiss();
			}
		});
		alertDialog = mbuilder.create();
		alertDialog.show();

	}

	AsyncTask<String, Integer, File> task = new AsyncTask<String, Integer, File>() {

		@Override
		protected File doInBackground(String... params) {
			isruning = true;
			String APK_PATH = Environment.getExternalStorageDirectory()
					+ "/saleman_cahe/";
			File file = new File(APK_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			File apkfile = new File(file, "a.apk");
			if (!apkfile.exists()) {
				try {
					apkfile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				URL murl = new URL(params[0]);
				URLConnection conexion = murl.openConnection();
				float length = conexion.getContentLength();
				InputStream input = new BufferedInputStream(murl.openStream());
				OutputStream output = new FileOutputStream(apkfile);
				int count = 0;
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1 && isruning) {
					total += count;
					output.write(data, 0, count);
					publishProgress((int) ((total / (float) length) * 100));
				}
				output.flush();
				output.close();
				input.close();
				return apkfile;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(File file) {
			alertDialog.dismiss();
			// 判断文件是否存在 file是否代表一个标准文件
			if (file == null || !file.exists() || !file.isFile()) {
				Toast.makeText(BaseActivity.this, "下载安装包失败!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (isruning) {
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setDataAndType(Uri.fromFile(file),
						"application/vnd.android.package-archive");
				startActivity(intent1);
				finish();
			}
			super.onPostExecute(file);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressBar.setProgress(values[0]);
			teView.setText(values[0] + "%");
			super.onProgressUpdate(values);
		}

	};

	public boolean needUpdate(String[] oldcode, String[] newcode) {
		boolean update = false;
		if (Integer.valueOf(oldcode[0]) < Integer.valueOf(newcode[0])) {
			update = true;
		} else if ((Integer.valueOf(oldcode[0]) == Integer.valueOf(newcode[0]))
				&& (Integer.valueOf(oldcode[1]) < Integer.valueOf(newcode[1]))) {
			update = true;
		} else if ((Integer.valueOf(oldcode[0]) == Integer.valueOf(newcode[0]))
				&& (Integer.valueOf(oldcode[1]) == Integer.valueOf(newcode[1]))
				&& (Integer.valueOf(oldcode[2]) < Integer.valueOf(newcode[2]))) {
			update = true;
		}
		return update;
	}

}
