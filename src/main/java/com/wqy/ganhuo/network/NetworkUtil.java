package com.wqy.ganhuo.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by weiquanyun on 15/8/23.
 */
public class NetworkUtil {

    /**
     * 判断是否联网
     * @param context
     * @return
     */
    public static boolean isNetConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    /**
     * 判断当前的网络连接方式是否为WIFI
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
