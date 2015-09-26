package com.hongguang.jaia_task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;

public class GetDataFromWeb {
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private InputStream is = null;
	private Context context;
	private HttpClient hc;

	public GetDataFromWeb(Context context) {
		super();
		this.context = context;
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		hc = new DefaultHttpClient(httpParams);
	}

	public boolean loadFile(String url, File file) {
		Log.d("下载apk",
				"下载apk url为：" + url + "    文件路径为：" + file.getAbsolutePath());
		if (!checkNetWorkInfo()) {
			Log.d("下载apk", "网络不可用");
			return false;
		} else {
			Log.d("下载apk", "网络可用");
		}

		if (!file.exists()) {
			Log.d("下载apk", "file不存在");
		}
		if (!file.isFile()) {
			Log.d("下载apk", "file不是文件");
		}

		// 如果文件不存在 或者 文件不是标准文件 两者中有一个为true
		if (!file.exists() || !file.isFile()) {
			File dir = file.getParentFile();
			Log.d("下载apk", "file的parent == " + file.getParent()
					+ "file的parentFile的绝对路径为：" + dir.getAbsolutePath());
			if (!dir.exists() || !dir.isDirectory()) {
				dir.mkdirs();
				if (dir.isDirectory() && dir.exists()) {
					Log.d("下载apk", "创建文件夹成功");
				} else {
					Log.d("下载apk", "创建文件夹失败");
				}
			}
		}
		Log.d("下载apk", "开始下载.....");
		FileOutputStream fos;
		try {
			HttpGet hg = new HttpGet(url);
			Log.d("下载apk", "下载apk url为：" + url);
			HttpResponse hr;
			hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				Log.d("下载apk", "下载apk相应码为200");
				fos = new FileOutputStream(file);
				is = hr.getEntity().getContent();
				byte[] buff = new byte[1024];
				int b = -1;
				while ((b = is.read(buff)) != -1) {
					fos.write(buff, 0, b);
					fos.flush();
				}
				fos.close();
				return true;
			}
		} catch (Exception e) {
			Log.d("下载apk", "下载写入异常：" + e.toString());

			e.printStackTrace();
		}
		return false;
	}

	public byte[] getData(String url) {
		if (!checkNetWorkInfo()) {
			return null;
		}
		if (url == null || url.equals("") || url.equals("null")) {
			return null;
		}
		HttpGet hg = new HttpGet(url);
		HttpResponse hr;
		try {
			hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				is = hr.getEntity().getContent();
				byte[] buff = new byte[1024];
				int b = -1;
				while ((b = is.read(buff)) != -1) {
					baos.write(buff, 0, b);
					baos.flush();
				}
				return baos.toByteArray();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private FileOutputStream fileoutput = null;
	public String getData(String url, String input) {
		if (!checkNetWorkInfo()) {
			return null;
		}

		if (url == null || url.equals("") || url.equals("null")) {
			return null;
		}

		HttpGet hg = new HttpGet(input);
		HttpResponse hr;
		try {
			fileoutput = new FileOutputStream(new File(url));
			hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				is = hr.getEntity().getContent();
				byte[] buff = new byte[1024];
				int b = -1;
				while ((b = is.read(buff)) != -1) {
					fileoutput.write(buff, 0, b);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fileoutput == null) {
				try {
					fileoutput.flush();
					fileoutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return url;
	}

	public boolean checkNetWorkInfo() {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if ((wifi == State.CONNECTING || wifi == State.DISCONNECTED)
				&& (mobile == State.CONNECTING || mobile == State.DISCONNECTED)) {
			return false;
		}
		return true;
	}
}
