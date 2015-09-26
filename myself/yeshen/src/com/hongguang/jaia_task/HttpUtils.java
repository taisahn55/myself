package com.hongguang.jaia_task;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_utils.WapConstant;

public class HttpUtils implements Runnable {
	private DefaultHttpClient httpClient;
	private HttpPost post;

	private Handler mHandler;
	private String httpUrl = null;
	private List<NameValuePair> params = null;

	private ProgressDialog progressDialog;

	public HttpUtils(Context mContext, Handler mHandler, String httpUrl,
			List<NameValuePair> params) {
		this.mHandler = mHandler;
		this.httpUrl = WapConstant.getHttpServer_HOST(mContext) + httpUrl;
		this.params = params;
	}

	public HttpUtils(Context mContext, String info, Handler mHandler,
			String httpUrl, List<NameValuePair> params) {
		this.mHandler = mHandler;
		this.httpUrl = WapConstant.getHttpServer_HOST(mContext) + httpUrl;
		this.params = params;
		if (info != null) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
			progressDialog.setContentView(R.layout.util_progresslayout);
			TextView tvMsg = (TextView) progressDialog
					.findViewById(R.id.id_tv_loadingmsg);
			tvMsg.setText(info);
			progressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
			progressDialog.setIndeterminateDrawable(mContext.getResources()
					.getDrawable(R.drawable.bg_progress));
		}
	}

	@Override
	public void run() {
		Message msg = mHandler.obtainMessage();
		String codeMsg = null;
		try {
			httpClient = new DefaultHttpClient();
			post = new HttpPost(httpUrl);
			if (params != null) {
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}
			HttpResponse response = httpClient.execute(post);
			int code = response.getStatusLine().getStatusCode();
			Log.e("dong", code + "");
			msg.arg1 = code;
			if (code == 200) {
				codeMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				codeMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			msg.obj = codeMsg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.arg1 = 0;
			msg.obj = e.getMessage();
		}
		mHandler.sendMessage(msg);
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
