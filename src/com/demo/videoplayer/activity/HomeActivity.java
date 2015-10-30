package com.demo.videoplayer.activity;

import com.demo.videoplayer.R;
import com.demo.videoplayer.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends BaseActivity implements OnClickListener{
	private TextView tv_temp_tip;
	private Button btn_temp_tip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		getElement();
		setListener();
		initData();
	}

	@Override
	protected void getElement() {
		tv_temp_tip = (TextView) findViewById(R.id.tv_temp_tip);
		btn_temp_tip = (Button) findViewById(R.id.btn_temp_tip);
	}
	
	@Override
	protected void setListener() {
		btn_temp_tip.setOnClickListener(this);
	}
	
	@Override
	protected void initData() {
//		updateDataBase();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_temp_tip:
			Intent intent = new Intent(this,VideoPlayerActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
