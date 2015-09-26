package com.hongguang.jaia_product;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.hongguang.jaia.R;
import com.hongguang.jaia_fragment.FragmentOne;
import com.hongguang.jaia_fragment.FragmentThree;
import com.hongguang.jaia_fragment.FragmentTwo;
import com.slidingmenu.lib.SlidingMenu;

public class ProductActivity extends FragmentActivity
		implements
			OnClickListener {
	private TextView classifytv;
	private SlidingMenu menu;
	private FrameLayout framelayout;
	private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		inti();
		// ProductListviewAdapter adapter = new ProductListviewAdapter();
		// listview.setAdapter(adapter);
		// 设置侧滑菜单
		intiMenu();
		Context context = getApplicationContext();

	}

	private void inti() {
		// TODO Auto-generated method stub
		menu = new SlidingMenu(this);
		classifytv = (TextView) findViewById(R.id.text_product);
		classifytv.setOnClickListener(this);
		framelayout = (FrameLayout) findViewById(R.id.frame_layout);
		setfragment();

	}

	private void intiMenu() {

		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 为侧滑菜单设置布局
		menu.setMenu(R.layout.menu_product);

	}

	public void but1(View v) {
		index = 1;
		setfragment();
	}

	public void but2(View v) {
		index = 2;
		setfragment();
	}

	public void but3(View v) {
		index = 3;
		setfragment();
	}

	private void setfragment() {
		// TODO Auto-generated method stub
		Fragment fragment = (Fragment) fragmentAdapter.instantiateItem(
				framelayout, index);
		fragmentAdapter.setPrimaryItem(framelayout, 0, fragment);
		fragmentAdapter.finishUpdate(framelayout);
	}

	FragmentStatePagerAdapter fragmentAdapter = new FragmentStatePagerAdapter(
			getSupportFragmentManager()) {
		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
				case 1 :
					return new FragmentTwo();
				case 2 :
					return new FragmentThree();

				case 3 :
					return new FragmentOne();

				default :
					return new FragmentTwo();
			}
		}

		@Override
		public int getCount() {
			return 4;
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.text_product :
				menu.toggle();
				break;

			default :
				break;
		}
	}
}
