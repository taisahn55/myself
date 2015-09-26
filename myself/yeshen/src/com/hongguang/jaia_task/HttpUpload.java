package com.hongguang.jaia_task;

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
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hongguang.jaia.R;
import com.hongguang.jaia_task.CustomMultipartEntity.ProgressListener;
import com.hongguang.jaia_utils.Constant;

public class HttpUpload extends AsyncTask<Object, Integer, Boolean> {

	private long totalSize;
	private Handler myhandler;
	private Message handlermessge = null;
	public HttpUpload(Handler myhandler) {
		super();
		this.myhandler = myhandler;
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		handlermessge = myhandler.obtainMessage();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		HttpPost httpPost = new HttpPost((String) params[0]);

		int code = 0;
		try {
			CustomMultipartEntity multipartContent = new CustomMultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE, null,
					Charset.forName("UTF_8"), new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
						}
					});

			String[] p = (String[]) params[1];
			TypeToken<LinkedHashMap<String, String[]>> type = new TypeToken<LinkedHashMap<String, String[]>>() {
			};
			LinkedHashMap<String, String[]> linkedHashMap = Constant.getGson()
					.fromJson(p[1], type.getType());

			Set<Map.Entry<String, String[]>> url = linkedHashMap.entrySet();
			for (Entry<String, String[]> u : url) {
				System.out.println("~~~~~~~~~~~~~" + u.toString());
				multipartContent.addPart(u.getKey(),
						new FileBody(new File(u.getValue()[0])));
				multipartContent.addPart(u.getKey(),
						new FileBody(new File(u.getValue()[1])));
				System.out.println("~~~~~~~~~~~~" + u.getValue()[0].toString()
						+ "    " + u.getValue()[1].toString());
			}

			multipartContent.addPart("salesbean",
					new StringBody(p[0], Charset.forName("UTF-8")));
			totalSize = multipartContent.getContentLength();
			httpPost.setEntity(multipartContent);
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
				System.out.println("返回结果为：" + baos.toString());
				handlermessge.arg1 = code;
				handlermessge.obj = baos.toString();
			}

			return true;
		} catch (Exception e) {
			handlermessge.arg1 = code;
			handlermessge.obj = e.getMessage();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// if(result){
		System.out.println(result);
		myhandler.sendMessage(handlermessge);
		// }
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
	}

}
