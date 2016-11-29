package me.rokevin.android.lib.helper.util.common;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by luokaiwen on 15/8/24.
 * <p/>
 * 网络帮助类
 */
public class RNet {

    public static boolean isWifi(Context context) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isAvailable())
            return true;
        else
            return false;
    }

    public static boolean isMobile(Context context) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobile.isAvailable())
            return true;
        else
            return false;
    }
}
