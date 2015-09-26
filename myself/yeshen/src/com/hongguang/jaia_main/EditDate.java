package com.hongguang.jaia_main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hongguang.jaia.R;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_task.GetDataFromWeb;
import com.hongguang.jaia_task.HttpUpload;
import com.hongguang.jaia_task.HttpUtils;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.Utils;
import com.hongguang.jaia_utils.WapConstant;
import com.hongguang.jaia_view.DialogView;
import com.hongguang.jaia_view.DialogView.OnBtnClickListener;

public class EditDate extends Activity implements OnClickListener {
	private DateSharedPreferences dateSharedPreferences;
	private Salesman salesman;
	private TextView upname, uprealname, upphone, upemail, upnowaddress,
			upworkaddress, preservation;
	private RadioButton sex, sex1;
	private RadioGroup radiogroup;
	private ImageView edit_back;
	private String s1 = "1[0-9]{10}", s2 = "[a-zA-Z-0-9]{0,99}@136.com";
	private ImageView head;
	public static final String action = "jason.broadcast.action";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editdate);
		dateSharedPreferences = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(
				dateSharedPreferences.getLogin(this), Salesman.class);
		InitView();
	}

	private RadioGroup.OnCheckedChangeListener mradiogroup = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == sex1.getId()) {
				i = i + 1;
				salesman.setSex(sex1.getText().toString().trim());
			} else if (checkedId == sex.getId()) {
				i = i + 1;
				salesman.setSex(sex.getText().toString().trim());
			}
		}
	};

	private void InitView() {
		//
		upname = (TextView) findViewById(R.id.upname);
		upname.setText(salesman.getName());

		uprealname = (TextView) findViewById(R.id.uprealname);
		uprealname.setText(salesman.getRealName());

		// 头像
		head = (ImageView) findViewById(R.id.touxiang);
		if (salesman.getHeadPic() == null) {
			head.setImageResource(R.drawable.huiyuan);
		} else {
			head.setImageBitmap(BitmapFactory.decodeFile(salesman.getHeadPic()));
		}
		head.setOnClickListener(this);

		sex = (RadioButton) findViewById(R.id.sex);
		sex1 = (RadioButton) findViewById(R.id.sex1);

		if ("男".equals(salesman.getSex())) {
			sex.setChecked(true);
		} else if ("女".equals(salesman.getSex())) {
			sex1.setChecked(true);
		}

		upphone = (TextView) findViewById(R.id.upphone);
		upphone.setText(salesman.getPhone());

		upemail = (TextView) findViewById(R.id.upemail);
		upemail.setText(salesman.getEmail());

		upnowaddress = (TextView) findViewById(R.id.upnowaddress);
		upnowaddress.setText(salesman.getNowAddr());

		upworkaddress = (TextView) findViewById(R.id.upworkaddress);
		upworkaddress.setText(salesman.getWorkAddr());

		preservation = (TextView) findViewById(R.id.preservation);
		preservation.setOnClickListener(this);

		edit_back = (ImageView) findViewById(R.id.edit_back);
		edit_back.setOnClickListener(this);

		radiogroup = (RadioGroup) findViewById(R.id.radiogroup1);
		radiogroup.setOnCheckedChangeListener(mradiogroup);
	}

	private int i = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.preservation :

				if (!TextUtils.isEmpty(upname.getText())) {
					i = i + 1;
					salesman.setName(upname.getText().toString().trim());
				}
				if (!TextUtils.isEmpty(uprealname.getText())) {
					i = i + 1;
					salesman.setRealName(uprealname.getText().toString().trim());
				}
				if (!TextUtils.isEmpty(upphone.getText())) {
					i = i + 1;
					salesman.setPhone(upphone.getText().toString().trim());
				}
				if (!TextUtils.isEmpty(upemail.getText())) {
					i = i + 1;
					salesman.setEmail(upemail.getText().toString().trim());
				}
				if (!TextUtils.isEmpty(upnowaddress.getText())) {
					i = i + 1;
					salesman.setNowAddr(upnowaddress.getText().toString()
							.trim());
				}
				if (!TextUtils.isEmpty(upworkaddress.getText())) {
					i = i + 1;
					salesman.setWorkAddr(upworkaddress.getText().toString()
							.trim());
				}

				if (i == 0) {
					Toast.makeText(EditDate.this, "没有做任何修改哦",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// 判断手机号码是否开头为1
				Pattern p = Pattern.compile(s1);
				Matcher m = p.matcher(upphone.getText().toString());
				System.out.println("手机号码" + upphone.getText().toString());
				if (!m.matches()) {
					Toast.makeText(EditDate.this, "请输入正确的手机号", 1000).show();
					return;
				}
				// 判断邮箱不能为@136.com
				Pattern p1 = Pattern.compile(s2);
				Matcher m1 = p1.matcher(upemail.getText().toString());
				if (m1.matches()) {
					Toast.makeText(EditDate.this, "136邮箱暂时不能注册", 1000).show();
					return;
				}

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("salesbean", Constant
						.getGson().toJson(salesman)));
				new Thread(new HttpUtils(this, mHandler, WapConstant.URLSTRING
						+ WapConstant.UPDATEString, params)).start();

				// 广播
				Intent intent = new Intent(action);
				intent.putExtra("data", salesman.getHeadPic());
				sendBroadcast(intent);
				finish();
				break;

			case R.id.edit_back :
				EditDate.this.finish();
				break;

			case R.id.touxiang ://
				getPhone();
				break;
			default :
				break;
		}
	}

	private void getPhone() {
		DialogView dialogView = new DialogView(EditDate.this,
				R.layout.dialogview, R.style.pager_dialog, "phone");
		dialogView.setOnBtnClickListener(new OnBtnClickListener() {
			@Override
			public void onclick(int i) {
				if (i == 0) {
					// 选折照片
					Intent picture = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					picture.setType("image/*");
					startActivityForResult(picture, 101);
				} else if (i == 1) {
					Intent takeIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					// 下面这句指定调用相机拍照后的照片存储的路径
					takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(tempFile));
					startActivityForResult(takeIntent, 102);

				}

			}
		});
		dialogView.show();
	}

	private File tempFile = new File(Utils.savePath + "touxiang.jpg");

	// main
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == RESULT_OK) {
			startPhotoZoom(data.getData());
		} else if (requestCode == 102 && resultCode == RESULT_OK) {
			startPhotoZoom(Uri.fromFile(tempFile));
		} else if (requestCode == 103 && resultCode == RESULT_OK) {
			if (data != null) {
				savePicToView(data);
			}

		}
		refreshUI();
	}

	public synchronized void refreshUI() {
		if (salesman.getHeadPic() != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					GetDataFromWeb get = new GetDataFromWeb(EditDate.this);
					System.out.println("头像..." + WapConstant.URLIMAGEString
							+ salesman.getHeadPic());
					byte[] date = get.getData(WapConstant
							.getHttpServer_HOST(EditDate.this)
							+ WapConstant.URLIMAGEString
							+ salesman.getHeadPic());
					Message msg = mHandler.obtainMessage();
					msg.obj = date;
					msg.arg1 = 1;
					mHandler.sendMessage(msg);
				}
			}).start();
		}
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 103);
	}

	private void savePicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap image = extras.getParcelable("data");
			if (image != null) {
				try {
					save(image, tempFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (tempFile.exists()) {
					LinkedHashMap<String, String[]> sendMap = new LinkedHashMap<String, String[]>();
					String[] headimages = {tempFile.getAbsolutePath()};
					sendMap.put("headPic", headimages);

					String[] params = new String[2];
					params[1] = Constant.getGson().toJson(sendMap);
					params[0] = Constant.getGson().toJson(salesman);
					HttpUploa.getInstance().uploadImage(
							this,
							WapConstant.getHttpServer_HOST(EditDate.this)
									+ WapConstant.URLSTRING
									+ WapConstant.UPDATEHEADIMAGE, handler,
							params, "头像上传...");
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

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int m = msg.arg1;
			if (m == 200) {
				if (msg.obj.toString().contains("success")) {
					Toast.makeText(EditDate.this, "修改资料成功", Toast.LENGTH_LONG)
							.show();
					dateSharedPreferences.saveLogin(EditDate.this, Constant
							.getGson().toJson(salesman));

					EditDate.this.finish();

				} else {
					Log.e("dong", "响应:" + m);
					Log.e("dong", "响应:" + msg.obj.toString());
					Toast.makeText(EditDate.this, msg.obj.toString(),
							Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (msg.what == (WapConstant.getHttpServer_HOST(EditDate.this)
					+ WapConstant.URLSTRING + WapConstant.UPDATEHEADIMAGE)
					.hashCode() && msg.obj != null) {
				try {
					Log.e("yuqi", msg.obj.toString());
					JSONObject object = new JSONObject(msg.obj.toString());
					if (object.optBoolean("successs")) {
						JSONObject obj = object.optJSONObject("sale");
						salesman.setHeadPic(tempFile.getAbsolutePath());
						dateSharedPreferences.saveLogin(EditDate.this,
								obj.toString());
						head.setImageBitmap(BitmapFactory.decodeFile(tempFile
								.getAbsolutePath()));

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

}
