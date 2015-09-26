package com.hongguang.jaia_main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.HttpUpload;
import com.hongguang.jaia_utils.BitmapLruCache;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;
import com.hongguang.jaia_view.DialogView;
import com.hongguang.jaia_view.DialogView.OnBtnClickListener;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

public class Editficates extends Activity implements OnClickListener {
	private DateSharedPreferences dateSharedPreferences;
	private Button userid, practitioners, industry;
	private ImageView idPics, idPictos, cyPics, cyPictos, zyPics, zyPictos,
			editback;
	private EditText id, cyid, zyid;
	private TextView submitid;
	private BitmapLruCache mBitmapLruCache;
	private Salesman salesman;
	private Dialog mDialog;
	private int width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editcertificates);
		dateSharedPreferences = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(
				dateSharedPreferences.getLogin(this), Salesman.class);
		final int memory = ((ActivityManager) getSystemService(ACTIVITY_SERVICE))
				.getMemoryClass();

		final int cacheSize = 1024 * memory / 8;
		mBitmapLruCache = BitmapLruCache.getInstance(cacheSize);
		width = (int) ((Integer.valueOf(dateSharedPreferences.get(this,
				"screenW", "0")) - 40 * Float.valueOf(dateSharedPreferences
				.get(this, "screenDensity", "0.0"))) / 3);
		InitView();
	}

	private void InitView() {

		mDialog = new Dialog(Editficates.this, R.style.pager_dialog);
		mDialog.setCancelable(false);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(Editficates.this).inflate(
				R.layout.progressbar, null);
		mDialog.setContentView(view);

		userid = (Button) findViewById(R.id.userid);
		userid.setOnClickListener(this);

		practitioners = (Button) findViewById(R.id.practitioners);
		practitioners.setOnClickListener(this);

		industry = (Button) findViewById(R.id.industry);
		industry.setOnClickListener(this);

		submitid = (TextView) findViewById(R.id.submitid);
		submitid.setOnClickListener(this);

		editback = (ImageView) findViewById(R.id.editback);
		editback.setOnClickListener(this);

		// 身份证
		idPics = (ImageView) findViewById(R.id.idpic);
		idPictos = (ImageView) findViewById(R.id.idpicto);
		id = (EditText) findViewById(R.id.id);

		if (!TextUtils.isEmpty(salesman.getIdNum())) {
			id.setText(salesman.getIdNum());
		}
		// 从业
		cyPics = (ImageView) findViewById(R.id.cypics);
		cyPictos = (ImageView) findViewById(R.id.cypictos);
		cyid = (EditText) findViewById(R.id.cyid);
		if (!TextUtils.isEmpty(salesman.getCongYe())) {
			cyid.setText(salesman.getCongYe());
		}
		//
		zyPics = (ImageView) findViewById(R.id.zypics);
		zyPictos = (ImageView) findViewById(R.id.zypictos);
		zyid = (EditText) findViewById(R.id.zyid);
		if (!TextUtils.isEmpty(salesman.getZhanYe())) {
			zyid.setText(salesman.getZhanYe());
		}

	}

	private int getphone = 0;

	@Override
	public void onClick(View v) {
		String[] str = null;
		switch (v.getId()) {
			case R.id.userid :// 身份证 1
				getphone = 1;
				getPhone();
				break;

			case R.id.practitioners :// 从业 2
				str = sendMap.get("idPic");
				if (str != null && str[0] != null && str[1] != null
						&& !TextUtils.isEmpty(id.getText())) {
					getphone = 2;
					getPhone();
				} else {
					Toast.makeText(Editficates.this, "请完善身份证信息",
							Toast.LENGTH_SHORT).show();
					// getphone = 2;
					// getPhone();
				}
				break;
			case R.id.industry :// 展业 3
				str = sendMap.get("cyPic");
				if (str != null && str[0] != null && str[1] != null
						&& !TextUtils.isEmpty(cyid.getText())) {
					getphone = 3;
					getPhone();
				} else {
					Toast.makeText(Editficates.this, "请完善从业证信息",
							Toast.LENGTH_SHORT).show();
					// getphone = 3;
					// getPhone();
				}

				break;
			case R.id.submitid :// 提交
				str = sendMap.get("zyPic");
				if (str != null && str[0] != null && str[1] != null
						&& !TextUtils.isEmpty(zyid.getText())) {

				} else {
					Toast.makeText(Editficates.this, "请完善展业证信息",
							Toast.LENGTH_SHORT).show();
					return;
				}

				salesman.setPhone(salesman.getPhone());
				salesman.setIdNum(id.getText().toString().trim());
				salesman.setCongYe(cyid.getText().toString().trim());
				salesman.setZhanYe(zyid.getText().toString().trim());
				salesman.setSid(salesman.getSid());

				String[] params = new String[2];
				params[0] = Constant.getGson().toJson(salesman);
				params[1] = Constant.getGson().toJson(sendMap);

				mDialog.show();
				HttpUpload upload = new HttpUpload(mHandler);
				String url = WapConstant.getHttpServer_HOST(Editficates.this)
						+ WapConstant.URLSTRING + WapConstant.iNSERTIDString;
				upload.execute(url, params);
				break;
			case R.id.editback :
				Editficates.this.finish();
				break;
			default :
				break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int m = msg.arg1;
			mDialog.dismiss();
			System.out.println("返回数据________>" + msg.obj.toString());
			if (m == 200) {
				if (msg.obj.toString().contains("error")) {// 失败
					Log.e("dong", "响应:" + m);
					Log.e("dong", "响应:" + msg.obj.toString());
					Toast.makeText(Editficates.this, "提交信息失败",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(Editficates.this, "上传数据成功",
							Toast.LENGTH_LONG).show();
					dateSharedPreferences.saveLogin(Editficates.this,
							msg.obj.toString());
					Editficates.this.finish();
				}
			} else {
				Log.e("dong", "响应:" + m);
				Log.e("dong", "响应:" + msg.obj.toString());
				Toast.makeText(Editficates.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
	};

	private String getFile() {
		String url = Environment.getExternalStorageDirectory() + "" + "/Love"
				+ "/";
		return url;
	}

	//
	private LinkedHashMap<String, String[]> sendMap = new LinkedHashMap<String, String[]>();
	String url;
	private Bitmap b;
	private void getPhone() {
		DialogView dialogView = new DialogView(Editficates.this,
				R.layout.dialogview, R.style.pager_dialog, "phone");
		dialogView.setOnBtnClickListener(new OnBtnClickListener() {
			@Override
			public void onclick(int i) {
				if (i == 0) {
					Intent picture = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(picture, 200);
				} else if (i == 1) {
					Intent takephoto = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

					url = getFile();
					String[] str = null;
					if (getphone == 1) {// 身份证
						if ((str = sendMap.get("idPic")) == null) {
							str = new String[2];
							str[0] = url;
							sendMap.put("idPic", str);
						} else {
							str[1] = url;
						}
					} else if (getphone == 2) {// 从业
						if ((str = sendMap.get("cyPic")) == null) {
							str = new String[2];
							str[0] = url;
							sendMap.put("cyPic", str);
						} else {
							str[1] = url;
						}
					} else if (getphone == 3) {// 展业
						if ((str = sendMap.get("zyPic")) == null) {
							str = new String[2];
							str[0] = url;
							sendMap.put("zyPic", str);
						} else {
							str[1] = url;
						}
					}

					Uri uri = Uri.fromFile(new File(url));
					takephoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(takephoto, 100);

				}
			}
		});
		dialogView.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 200) {
			if (resultCode == RESULT_OK) {
				Uri selectedImage = data.getData();
				String[] filePathColumns = {MediaStore.Images.Media.DATA};
				Cursor c = this.getContentResolver().query(selectedImage,
						filePathColumns, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePathColumns[0]);
				String url = c.getString(columnIndex);
				File file = new File(getFile(), new Date().getTime() + ".jpg");
				try {
					b = revitionImageSize(url, 200);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String[] str = null;
				if (getphone == 1) {// 身份证
					if ((str = sendMap.get("idPic")) == null) {
						str = new String[2];
						str[0] = url;
						sendMap.put("idPic", str);
						// idPics.setImageBitmap(getbitmap(str[0]));
						idPics.setImageBitmap(b);
						salesman.setIdPic1(file.getAbsolutePath());
					} else {
						str[1] = url;
						sendMap.put("idPic", str);
						// idPictos.setImageBitmap(getbitmap(str[1]));
						idPictos.setImageBitmap(b);
						salesman.setIdPic2(file.getAbsolutePath());
					}

				} else if (getphone == 2) {// 从业
					if ((str = sendMap.get("cyPic")) == null) {
						str = new String[2];
						str[0] = url;
						sendMap.put("cyPic", str);
						cyPics.setImageBitmap(b);
						salesman.setCyPic1(file.getAbsolutePath());
					} else {
						str[1] = url;
						cyPictos.setImageBitmap(b);
						salesman.setCyPic2(file.getAbsolutePath());
					}
				} else if (getphone == 3) {// 展业
					if ((str = sendMap.get("zyPic")) == null) {
						str = new String[2];
						str[0] = url;
						sendMap.put("zyPic", str);
						zyPics.setImageBitmap(b);
						salesman.setZyPic1(file.getAbsolutePath());
					} else {
						str[1] = url;
						zyPictos.setImageBitmap(b);
						salesman.setZyPic2(file.getAbsolutePath());
					}
				}
				// c.close();
				try {
					save(b, file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (resultCode == RESULT_CANCELED) {
				System.out.println("为选择图片");
			}
		}

		if (requestCode == 100) {
			System.out.println(resultCode);
			if (resultCode == RESULT_OK) {
				// 拍照处理
				System.out.println("拍照处理");
				String[] str = null;
				switch (getphone) {
					case 1 :
						str = sendMap.get("idPic");
						if (str[1] == null) {
							idPics.setImageBitmap(b);
						} else {
							idPictos.setImageBitmap(b);
						}
						break;
					case 2 :
						str = sendMap.get("cyPic");
						if (str[1] == null) {
							cyPics.setImageBitmap(b);
						} else {
							cyPictos.setImageBitmap(b);
						}
						break;
					case 3 :
						str = sendMap.get("zyPic");
						if (str[1] == null) {
							zyPics.setImageBitmap(b);
						} else {
							zyPictos.setImageBitmap(b);
						}
						break;
					default :
						break;
				}

			} else if (resultCode == RESULT_CANCELED) {
				String[] str = null;
				switch (getphone) {
					case 1 :
						str = sendMap.get("idPic");
						if (str[1] == null) {
							sendMap.remove("idPic");
						} else {
							str[1] = null;
						}
						break;
					case 2 :
						str = sendMap.get("cyPic");
						if (str[1] == null) {
							sendMap.remove("cyPic");
						} else {
							str[1] = null;
						}
						break;
					case 3 :
						str = sendMap.get("zyPic");
						if (str[1] == null) {
							sendMap.remove("zyPic");
						} else {
							str[1] = null;
						}
						break;
					default :
						break;
				}
			}
		}
	}

	public static void save(Bitmap bm, File savePath) throws IOException {
		if (bm != null && savePath != null) {
			if (!savePath.getParentFile().exists()) {
				savePath.getParentFile().mkdirs();
			}
			if (!savePath.exists()) {
				savePath.createNewFile();
			}
			bm.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(savePath));
		}
	}

	private Bitmap revitionImageSize(String path, int size) throws IOException {
		File file = new File(path);
		// 取得图片
		InputStream temp = new FileInputStream(file);
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
		options.inJustDecodeBounds = true;
		// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
		BitmapFactory.decodeStream(temp, null, options);
		// 关闭流
		temp.close();

		// 生成压缩的图片
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			// 这一步是根据要设置的大小，使宽和高都能满足
			if ((options.outWidth >> i <= size)
					&& (options.outHeight >> i <= size)) {
				// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
				temp = new FileInputStream(file);
				// 这个参数表示 新生成的图片为原始图片的几分之一。
				options.inSampleSize = (int) Math.pow(2.0D, i);
				// 这里之前设置为了true，所以要改为false，否则就创建不出图片
				options.inJustDecodeBounds = false;

				bitmap = BitmapFactory.decodeStream(temp, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;

	}

}
