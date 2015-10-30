package com.demo.videoplayer.utils;

import com.demo.videoplayer.staticstring.ConfigFileName;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

	//get IP
	public static String getIP(Context ct){
		return getStringFromShares(ct, ConfigFileName.Config_IP, "");
	}
	public static boolean setIP(Context ct,String value){
		return setStringToShares(ct, ConfigFileName.Config_IP, value);
	}

	
	public static boolean setString(Context ct, String key,String value) {
		return setStringToShares(ct, key, value);
	}

	public static String getString(Context ct,String key,String defaultvalue) {
		return getStringFromShares(ct, key, defaultvalue);
	}
	public static boolean setBoolean(Context ct, String key,boolean value) {
		return setTagFromShares(ct, key, value);
	}
	
	public static boolean getBoolean(Context ct,String key,boolean defaultvalue) {
		return getTagFromShares(ct, key, defaultvalue);
	}
	

	/**
	 * util method
	 * @param ct
	 * @param key
	 * @param value
	 * @return
	 */
	private static boolean setStringToShares(Context ct, String key,String value) {
		if (key.isEmpty() || value.isEmpty())
			return false;
		@SuppressWarnings("deprecation")
		SharedPreferences shares = ct.getSharedPreferences(ConfigFileName.Config,Context.MODE_WORLD_WRITEABLE);
		Editor editor = shares.edit();
		editor.putString(key.trim(), value.trim());
		editor.commit();
		return true;
	}
	
	private static String getStringFromShares(Context ct, String key,
			String defaultValue) {
		if (key.isEmpty())
			return "";
		SharedPreferences shares = ct.getSharedPreferences(ConfigFileName.Config,Context.MODE_PRIVATE);
		String returnValue = shares.getString(key.trim(), defaultValue);
		return returnValue;
	}

	private static boolean setTagFromShares(Context ct, String key,
			boolean defaultValue) {
		if (key.isEmpty())
			return false;

		SharedPreferences shares = ct.getSharedPreferences(ConfigFileName.Config,Context.MODE_PRIVATE);
		Editor editor = shares.edit();
		editor.putBoolean(key.trim(), defaultValue);
		editor.commit();
		return true;
	}

	private static boolean getTagFromShares(Context ct, String key,
			boolean defaultValue) {
		if (key.isEmpty())
			return false;
		SharedPreferences shares = ct.getSharedPreferences(ConfigFileName.Config,Context.MODE_PRIVATE);
		boolean returnValue = shares.getBoolean(key.trim(), defaultValue);
		return returnValue;
	}

}
