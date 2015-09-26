package com.hongguang.jaia_main;


import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.sharesdk.onekeyshare.OnekeyShare;

import com.hongguang.jaia.BaseActivity;
import com.hongguang.jaia.LoginActivity;
import com.hongguang.jaia.OnLineSale;
import com.hongguang.jaia.R;
import com.hongguang.jaia.adapter.MainGridViewAdapter;
import com.hongguang.jaia_accountinfor.AccountInforActivity;
import com.hongguang.jaia_bean.Salesman;
import com.hongguang.jaia_chexian.InsureActivity;
import com.hongguang.jaia_feedback.FeedbackActivity;
import com.hongguang.jaia_product.ProductActivity;
import com.hongguang.jaia_task.GetDataFromWeb;
import com.hongguang.jaia_utils.Constant;
import com.hongguang.jaia_utils.DateSharedPreferences;
import com.hongguang.jaia_utils.WapConstant;
import com.hongguang.jaia_view.DialogView;
import com.hongguang.jaia_view.DialogView.OnBtnClickListener;

public class MainActivity extends BaseActivity  implements OnItemClickListener,OnClickListener{
	private String text[] = {"买保险","办理赔","业务应用",
			"在线业务","保险宝典","保险咨询",
			"用户反馈","支付专区","二维码","买车险" };
	private GridView gridview;
	private RadioGroup radioGroup;
	private RadioButton shouye,wode;

	private RelativeLayout zhujj;
	private LinearLayout woxx;
	private TextView edit, mainname, realname, sex, phone, email, nowaddress,
	workaddress, stop;
	private ImageView imageView,imew;

	private LinearLayout updatepwd, financial, certifLayout;
	private DateSharedPreferences dsp;
	private Salesman salesman;
	private TextView checkversion;
	private View view,more;

	//	广告条
	private int imageIds[];
	private ArrayList<ImageView> images;
	private ArrayList<View> dots;
	private TextView title;
	private ViewPager pager;
	private int oldePosition=0;
	private int currentItem;
	private ScheduledExecutorService scheduledExecutorService;
	private ViewPagerAdapter adapter;
	private ImageView head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dsp = DateSharedPreferences.getInstance();
		salesman = Constant.getGson().fromJson(dsp.getLogin(this),
				Salesman.class);
		//组建初始化
		intView();
		//主界面gridview功能
		MainGridViewAdapter adapter = new MainGridViewAdapter();
		gridview.setAdapter(adapter);
		check(false);	
		//检测版本
		checkversion = (TextView) findViewById(R.id.checkversion);
		checkversion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				check(true);
			}
		});
		//广告条初始化
		initBanner();	

		//广播更新头像
		IntentFilter filter = new IntentFilter(EditDate.action);  
		registerReceiver(broadcastReceiver, filter);  
	}



	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
		@Override
		public void onReceive(Context context, Intent intent) {
			head.setImageBitmap(BitmapFactory.decodeFile((intent.getExtras().getString("data"))));  

		}  
	};  


	protected void onDestroy() {  
		super.onDestroy(); 
		unregisterReceiver(broadcastReceiver);  
	};  


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		InitView();
		super.onActivityResult(requestCode, resultCode, data);
	}



	private void initBanner() {
		imageIds=new int[]{
				R.drawable.banner2,R.drawable.jianan1,
				R.drawable.jianan2,R.drawable.jianan3};		
		images=new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView=new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}

		dots=new ArrayList<View>();
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));
		dots.add(findViewById(R.id.dot_3));

		pager=(ViewPager)findViewById(R.id.banner);
		adapter=new ViewPagerAdapter();
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {				
				dots.get(oldePosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldePosition=position;
				currentItem=position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {			
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {		
			}
		});

	}

	@Override
	protected void onStart() {		
		InitView();
		scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(),4, 5, TimeUnit.SECONDS);

		super.onStart();
	}

	private void InitView() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				GetDataFromWeb get = new GetDataFromWeb(
						MainActivity.this);
				System.out.println("二维码图片数据" + WapConstant.URLIMAGEString
						+ salesman.getQrcode());

				byte[] date = get.getData(WapConstant
						.getHttpServer_HOST(MainActivity.this)
						+ WapConstant.URLIMAGEString + salesman.getQrcode());
				Message msg = mHandler.obtainMessage();
				msg.obj = date;
				msg.arg1 = 1;
				mHandler.sendMessage(msg);
			}
		}).start();

		updatepwd = (LinearLayout) findViewById(R.id.updatepwd);
		updatepwd.setOnClickListener(this);

		financial = (LinearLayout) findViewById(R.id.financial);
		financial.setOnClickListener(this);

		certifLayout = (LinearLayout) findViewById(R.id.certif);
		certifLayout.setOnClickListener(this);


	}

	private void intView() {

		dsp = DateSharedPreferences.getInstance();

		gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setOnItemClickListener(this);
		radioGroup=(RadioGroup)findViewById(R.id.caidan1);
		shouye=(RadioButton) findViewById(R.id.radio0);
		wode=(RadioButton) findViewById(R.id.radio1);
		more=(RadioButton) findViewById(R.id.radio2);
		zhujj=(RelativeLayout)findViewById(R.id.rl1);

		view= findViewById(R.id.wodexx);		
		edit = (TextView) view.findViewById(R.id.edit);
		edit.setOnClickListener(MainActivity.this);

		head=(ImageView)view.findViewById(R.id.touxiang_2);	
		if (salesman.getHeadPic()==null) {
			head.setImageResource(R.drawable.huiyuan);
		}else{		
			head.setImageBitmap(BitmapFactory.decodeFile(salesman.getHeadPic()));
		}
		mainname = (TextView) view.findViewById(R.id.names);
		mainname.setText(salesman.getName());
		realname = (TextView) view.findViewById(R.id.realname);
		realname.setText(salesman.getRealName());
		sex = (TextView) view.findViewById(R.id.sex);
		sex.setText(salesman.getSex());
		phone = (TextView)view. findViewById(R.id.phone);
		phone.setText(salesman.getPhone());
		email = (TextView)view. findViewById(R.id.email);
		email.setText(salesman.getEmail());
		nowaddress = (TextView)view. findViewById(R.id.nowaddress);
		nowaddress.setText(salesman.getNowAddr());
		workaddress = (TextView)view. findViewById(R.id.workaddress);
		workaddress.setText(salesman.getWorkAddr());

		//more 退出程序
		more=findViewById(R.id.more_home);
		stop = (TextView)more.findViewById(R.id.stop);
		stop.setOnClickListener(MainActivity.this);
		//二维码
		RelativeLayout erw=(RelativeLayout) View.inflate(this, R.layout.erweima, null);

		imageView = (ImageView)erw.findViewById(R.id.imageView);


		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {			

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int id= group.getCheckedRadioButtonId();
				switch (group.getCheckedRadioButtonId()) {
				case R.id.radio0:
					zhujj.setVisibility(View.VISIBLE);
					view.setVisibility(View.GONE);
					more.setVisibility(View.GONE);
					break;

				case R.id.radio1:				
					zhujj.setVisibility(View.GONE);				
					view.setVisibility(View.VISIBLE);
					more.setVisibility(View.GONE);
					break;

				case R.id.radio2:
					zhujj.setVisibility(View.GONE);				
					view.setVisibility(View.GONE);
					more.setVisibility(View.VISIBLE);
					break;

				}
			}
		});



	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "你点击了" + text[position],
				Toast.LENGTH_SHORT).show();

		switch (position) {

		case 0:
			//跳转到产品
			Intent intent0 = new Intent();
			intent0.setClass(MainActivity.this, ProductActivity.class);
			startActivity(intent0);
			break;

		case 3:
			//跳转到在线查询
			Intent intent3 = new Intent();
			intent3.setClass(MainActivity.this, OnLineSale.class);
			startActivity(intent3);
			break;

		case 6:
			//在线反馈
			Intent intent6 = new Intent();
			intent6.setClass(MainActivity.this, FeedbackActivity.class);
			startActivity(intent6);
			break;
		case 7:
			//支付
			Intent intent7 = new Intent();
			intent7.setClass(MainActivity.this, AccountInforActivity.class);
			startActivity(intent7);
			break;
		case 8:
			///二维码
			imageView.setVisibility(View.VISIBLE);
			Dialog dialog = new Dialog(MainActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


			View view = LayoutInflater.from(MainActivity.this)
					.inflate(R.layout.dialogimage, null);


			String width = dsp.get(MainActivity.this, "screenW", null);
			int w = 0;
			int h = 0;
			if (width != null) {
				w = (Integer.parseInt(width) / 3) * 2;
				h = (Integer.parseInt(width) / 3) * 2;
			}
			android.view.ViewGroup.LayoutParams params2 = new android.view.ViewGroup.LayoutParams(
					w, h);
			view.setBackgroundDrawable(imageView.getDrawable());
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					OnekeyShare oks = new OnekeyShare();
					//关闭sso授权
					oks.disableSSOWhenAuthorize(); 

					// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
					//oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
					// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
					oks.setTitle(getString(R.string.share));
					// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
					//oks.setTitleUrl("http://sharesdk.cn");
					// text是分享文本，所有平台都需要这个字段
					//oks.setText("建安");					
					// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
					//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

					oks.setImageUrl(WapConstant
							.getHttpServer_HOST(MainActivity.this)+WapConstant.URLIMAGEString
							+ salesman.getQrcode());

					// url仅在微信（包括好友和朋友圈）中使用
					// oks.setUrl("http://sharesdk.cn");
					// comment是我对这条分享的评论，仅在人人网和QQ空间使用
					// oks.setComment("我是测试评论文本");
					// site是分享此内容的网站名称，仅在QQ空间使用
					// oks.setSite(getString(R.string.app_name));
					// siteUrl是分享此内容的网站地址，仅在QQ空间使用
					// oks.setSiteUrl("http://sharesdk.cn");

					// 启动分享GUI
					oks.show(MainActivity.this);


				}
			});
			dialog.addContentView(view, params2);
			dialog.show();		
			break;
		case 9:
			Intent intent9 = new Intent(MainActivity.this,InsureActivity.class);
			startActivity(intent9);
			
			break;
		}
	}

	private boolean isExit = false;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), R.string.exit,
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}


	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.edit:
			intent = new Intent();
			intent.setClass(MainActivity.this, EditDate.class);
			startActivity(intent);
			break;

			//退出程序
		case R.id.stop:
			DialogView dialogView = new DialogView(MainActivity.this,
					R.layout.dialogview, R.style.pager_dialog, null);
			dialogView.setOnBtnClickListener(new OnBtnClickListener() {
				@Override
				public void onclick(int i) {
					if (i == 0) {
						finish();
						System.exit(0);
					} else if (i == 1) {
						dsp.clearLogin(MainActivity.this);
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,
								LoginActivity.class);
						startActivity(intent);
						MainActivity.this.finish();

					}
				}
			});
			dialogView.show();
			break;	

			//更新密码
		case R.id.updatepwd:
			intent = new Intent(MainActivity.this, UpdatePwd.class);
			startActivity(intent);
			break;
		case R.id.financial:
			intent = new Intent(MainActivity.this, Financial.class);
			startActivity(intent);
			break;
		case R.id.certif:
			intent = new Intent(MainActivity.this, Certificates.class);
			startActivity(intent);
			break;
		}
	}




	private Bitmap bitmap;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int i = msg.arg1;
			System.out.println(i);
			if (i == 1) {
				byte[] date = (byte[]) msg.obj;
				if (date != null) {
					Log.i("二维码图片地址~~~~~~~~~~~~", WapConstant.URLIMAGEString
							+ salesman.getQrcode());
					bitmap = BitmapFactory.decodeByteArray(date, 0,
							date.length);
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	};

	//滚动广告条Adapter
	private class ViewPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(images.get(position));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(images.get(position));
			return images.get(position);
		}
	}


	private class ViewPagerTask implements Runnable{

		@Override
		public void run() {
			currentItem=(currentItem+1)%imageIds.length;
			handler.obtainMessage().sendToTarget();
		}

	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			pager.setCurrentItem(currentItem);
		};
	};

	protected void onStop() {
		super.onStop();
	};
}
