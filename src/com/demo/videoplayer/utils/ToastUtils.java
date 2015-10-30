package com.demo.videoplayer.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	public static void show(Context context,String string) {
		Toast.makeText(context, string, 0).show();
	}
}
