package com.wqy.ganhuo.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by weiquanyun on 15/8/23.
 */
public class NetworkUtil {

    public static NetworkInfo[] networkInfos;

    /**
     * 判断是否联网
     * @param context
     * @return
     */
    public static boolean isNetConnected(Context context){
        if(networkInfos == null) {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network[] networks = connectivityManager.getAllNetworks();
                if (networks != null) {
                    networkInfos = new NetworkInfo[networks.length];
                    int i = 0;
                    for (Network network : networks) {
                        networkInfos[i++] = connectivityManager.getNetworkInfo(network);
                    }
                }
            }
        }
        if (networkInfos != null) {
            for (int i = 0; i < networkInfos.length; i++) {
                if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
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
