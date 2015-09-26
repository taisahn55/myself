package com.hongguang.jaia_view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_utils.DateSharedPreferences;

public class DialogView extends Dialog implements OnClickListener {

	private Context context;
	private LayoutInflater inflater;
	private int position;
	private TextView stop, sgnout;
	private OnBtnClickListener oncl;
	private int view;
	private String intent;
	private DateSharedPreferences dsp = DateSharedPreferences.getInstance();
	public DialogView(Context context, int view, int theme, String intent) {
		super(context, theme);
		this.context = context;
		this.view = view;
		this.intent = intent;
		inflater = LayoutInflater.from(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout ll = (LinearLayout) inflater.inflate(view, null);
		if (intent != null && intent.contains("phone")) {
			TextView sgnout = (TextView) ll.findViewById(R.id.sgnout);
			sgnout.setText("相机拍照");
			TextView stop = (TextView) ll.findViewById(R.id.stop);
			stop.setText("相册选择");
		}
		LayoutParams params = new LayoutParams((int) (Float.valueOf(dsp.get(
				context, "screenDensity", "0.0")) * 220),
				LayoutParams.WRAP_CONTENT);

		setContentView(ll, params);
		stop = (TextView) findViewById(R.id.stop);
		stop.setOnClickListener(this);
		sgnout = (TextView) findViewById(R.id.sgnout);
		sgnout.setOnClickListener(this);
	}

	public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
		this.oncl = onBtnClickListener;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {

		return position;
	}

	public void setContent(String content) {
		stop.setText(content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.stop :
				if (oncl != null) {
					oncl.onclick(0);
				}
				this.dismiss();
				break;
			case R.id.sgnout :
				if (oncl != null) {
					oncl.onclick(1);
				}
				this.dismiss();
				break;

			default :
				break;
		}
	}

	public interface OnBtnClickListener {
		void onclick(int i);
	}

}
