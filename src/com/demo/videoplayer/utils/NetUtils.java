package com.demo.videoplayer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetUtils {
	
	/**
	 * network is connect to wifi or not
	 * @param inContext
	 * @return boolean
	 */
	public static boolean isWiFiConnect(Context inContext) {
		Context context = inContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            Network[] info = connectivity.getAllNetworks();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                	NetworkInfo netWorkInfo;
                	netWorkInfo = connectivity.getNetworkInfo(info[i]);
                	if(netWorkInfo.getTypeName().equals("WIFI") && netWorkInfo.isConnected()){
                		return true;
                	}
                }
            }
        }
        return false;
    }
}
