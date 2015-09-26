package com.hongguang.jaia_view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hongguang.jaia.R;
import com.hongguang.jaia_utils.DateSharedPreferences;

public class DownLoadDialog extends Dialog
		implements
			android.view.View.OnClickListener {

	private Context context;
	private LayoutInflater inflater;
	private Button sureBtn;
	private TextView percent, action, progress_text;
	private ProgressBar bar;
	private DateSharedPreferences dsp = DateSharedPreferences.getInstance();
	private boolean isForce;
	public DownLoadDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout ll = (LinearLayout) inflater.inflate(
				R.layout.progress_dialog_layout, null);
		LayoutParams params = new LayoutParams((int) (Float.valueOf(dsp.get(
				context, "screenDensity", "0.0")) * 260),
				LayoutParams.WRAP_CONTENT);
		setContentView(ll, params);
		sureBtn = (Button) ll.findViewById(R.id.progressBar_sure);
		bar = (ProgressBar) ll.findViewById(R.id.progressbar_bar);
		percent = (TextView) ll.findViewById(R.id.show_progress);
		sureBtn.setOnClickListener(this);
		action = (TextView) findViewById(R.id.progress_action);
		progress_text = (TextView) findViewById(R.id.progress_text);
	}

	public void setAction(String action) {
		this.action.setText(action);
	}

	public void setIsForce(boolean isForce) {
		this.isForce = isForce;
	}

	public void setProgress_text(String progress) {
		progress_text.setText(progress);
	}

	public void setProgress(int progress) {
		if (progress > 0) {
			bar.setProgress(progress);
			percent.setText(progress + "%");
			if (progress == 100) {
				this.dismiss();
			}
		}
	}
	@Override
	public void onClick(View v) {
		if (isForce)
			return;
		this.dismiss();
	}
}
