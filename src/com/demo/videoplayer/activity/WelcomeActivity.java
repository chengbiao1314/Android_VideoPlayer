package com.demo.videoplayer.activity;

import com.demo.videoplayer.R;
import com.demo.videoplayer.base.BaseActivity;
import com.demo.videoplayer.staticstring.TipString;
import com.demo.videoplayer.utils.NetUtils;
import com.demo.videoplayer.utils.SharedPreferencesUtils;
import com.demo.videoplayer.utils.ToastUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends BaseActivity {
	private Button btn_exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		getElement();
		setListener();
		initData();
	}

	@Override
	protected void getElement() {
		btn_exit = (Button) findViewById(R.id.btn_exit);
	}
	
	@Override
	protected void setListener() {
		btn_exit.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				WelcomeActivity.this.finish();
				System.exit(0);
				return false;
			}
		});
	}
	
	@Override
	protected void initData() {
//		updateDataBase();
		
		new Handler().postDelayed(new Thread(){
            public void run() {
            	Intent intent  = new Intent(WelcomeActivity.this,HomeActivity.class);
            	WelcomeActivity.this.startActivity(intent); 
            	WelcomeActivity.this.finish();
		   }
		}, 3000);
	}
	
	private void updateDataBase(){
		if(NetUtils.isWiFiConnect(this)){
			
		}else{
			ToastUtils.show(this, TipString.tip_connectFailed);
		}
	}
	
	private boolean isIPExist(){
		if(SharedPreferencesUtils.getIP(this).isEmpty()){
			//TODO
			return false;
		}
		return true;
	}
	
	
}
