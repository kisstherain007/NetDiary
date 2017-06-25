package com.mall.android.common.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络状态帮助类
 * @author zhoubo
 *
 */
public final class NetworkHelper {
	/**
	 * 检测手机是否开启GPRS网络,需要调用ConnectivityManager,TelephonyManager 服务.
	 * 
	 * @param Context
	 *            context
	 * */
	final public static boolean CheckGPRSNetwork(Context context) {
		boolean has = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mTelephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		int netType = info.getType();
		int netSubtype = info.getSubtype();
		if (netType == ConnectivityManager.TYPE_MOBILE
				&& netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
				&& !mTelephony.isNetworkRoaming()) {
			has = info.isConnected();
		}
		return has;

	}

	/**
	 * 检测手机是否开启WIFI网络,需要调用ConnectivityManager服务.
	 * 
	 * @param Context
	 *            context
	 * */
	final public static boolean CheckWifiNetwork(Context context) {
		boolean has = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		int netType = info.getType();
//		int netSubtype = info.getSubtype();
		if (netType == ConnectivityManager.TYPE_WIFI) {
			has = info.isConnected();
		}
		return has;
	}

	/**
	 * 检测当前手机是否联网
	 * */
	final public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}


}
