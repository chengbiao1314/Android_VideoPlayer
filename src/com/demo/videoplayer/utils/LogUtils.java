package com.demo.videoplayer.utils;

import android.util.Log;


public class LogUtils{
	private static String logname = "logtest";
	
	public static void logprint(String str1){
		Log.v(logname, str1);
	}
}
