package com.hongguang.jaia_main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongguang.jaia.R;
import com.hongguang.jaia_utils.WapConstant;

public class HttpUploa {

	private static HttpUploa httpUpload;

	public static HttpUploa getInstance() {
		if (httpUpload == null) {
			httpUpload = new HttpUploa();
		}
		return httpUpload;
	}

	private ProgressDialog pd = null;

	public void uploadImage(final Context context, final String uploadUrl,
			final Handler handler, final String[] params, final String info) {
		try {
			pd = new ProgressDialog(context);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			pd.setContentView(R.layout.util_progresslayout);
			TextView tvMsg = (TextView) pd.findViewById(R.id.id_tv_loadingmsg);
			tvMsg.setText(info);
			pd.getWindow().getAttributes().gravity = Gravity.CENTER;
			pd.setOnKeyListener(new OnKeyListener() {

				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						if (pd != null) {
							pd.dismiss();
						}
						return true;
					}
					return false;
				}
			});
			pd.setIndeterminateDrawable(context.getResources().getDrawable(
					R.drawable.bg_progress));
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				Message message = handler.obtainMessage();
				message.what = uploadUrl.hashCode();
				HttpClient httpClient = new DefaultHttpClient();
				httpClient.getParams().setParameter(
						CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
				HttpPost httpPost = new HttpPost(uploadUrl);
				int code = 0;
				try {
					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE, null,
							Charset.forName("UTF_8"));

					TypeToken<LinkedHashMap<String, String[]>> type = new TypeToken<LinkedHashMap<String, String[]>>() {
					};
					LinkedHashMap<String, String[]> linkedHashMap = new Gson()
							.fromJson(params[1], type.getType());
					Set<Map.Entry<String, String[]>> entrys = linkedHashMap
							.entrySet();
					for (Entry<String, String[]> entry : entrys) {
						String[] en = entry.getValue();
						for (int i = 0; i < en.length; i++) {
							entity.addPart(entry.getKey(), new FileBody(
									new File(en[i])));
						}
					}
					entity.addPart("salesbean", new StringBody(params[0],
							Charset.forName("UTF-8")));
					httpPost.setEntity(entity);
					HttpResponse response = httpClient.execute(httpPost);
					code = response.getStatusLine().getStatusCode();
					if (code == 200) {
						InputStream is = response.getEntity().getContent();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int length = -1;
						byte[] b = new byte[1024];
						while ((length = is.read(b)) != -1) {
							baos.write(b, 0, length);
						}
						message.arg1 = code;
						message.obj = baos.toString();
					}
				} catch (Exception e) {
					message.arg1 = code;
					message.obj = e.getMessage();
					e.printStackTrace();
				} finally {
					handler.sendMessage(message);
					if (pd != null) {
						pd.dismiss();
					}
				}
			}
		}.start();
	}
}
