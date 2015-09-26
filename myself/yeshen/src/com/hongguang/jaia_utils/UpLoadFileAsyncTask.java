package com.hongguang.jaia_utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hongguang.jaia_utils.CustomMultipartEntity.ProgressListener;
import com.hongguang.jaia_view.DownLoadDialog;

public class UpLoadFileAsyncTask extends AsyncTask<String, Integer, Boolean> {

	private DownLoadDialog pd;
	private long totalSize;
	private Context context;

	public UpLoadFileAsyncTask(Context context, DownLoadDialog pd) {
		super();
		this.context = context;
		this.pd = pd;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		HttpPost httpPost = new HttpPost(params[0]);
		try {
			CustomMultipartEntity multipartContent = new CustomMultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE, null,
					Charset.forName("UTF_8"), new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
						}
					});

			// We use FileBody to transfer an image
			// System.out.println("url为："+params[0]+"视频路径为："+params[1]+"id为："+params[2]);
			multipartContent.addPart("file", new FileBody(new File(params[1])));
			multipartContent.addPart("publicid", new StringBody(params[2]));
			totalSize = multipartContent.getContentLength();
			// System.out.println("entity的长度为："+totalSize);
			// Send it
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost);
			// System.out.println("上传视频收到响应");
			if (response.getStatusLine().getStatusCode() == 200) {
				// System.out.println("上传视频响应码为200");
				InputStream is = response.getEntity().getContent();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int length = -1;
				byte[] b = new byte[1024];
				while ((length = is.read(b)) != -1) {
					baos.write(b, 0, length);
				}
				// System.out.println("返回结果为："+baos.toString());
				if (baos.toString().equals("Success")) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		pd.dismiss();
		if (result) {
			Intent intent = new Intent("refresh_benefit");
			context.sendBroadcast(intent);
			Toast.makeText(context, "视频上传成功！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "视频上传失败！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((int) (progress[0]));
	}

}
