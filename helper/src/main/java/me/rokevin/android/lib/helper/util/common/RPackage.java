package me.rokevin.android.lib.helper.util.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by luokaiwen on 15/6/3.
 * <p/>
 * 应用包工具类
 */
public class RPackage {

    /**
     * 根据包名检查是否存在此应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return true：存在 false：不存在
     */
    public static boolean checkPackage(Context context, String packageName) {

        if (TextUtils.isEmpty(packageName))

            return false;

        try {

            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);

            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = getPackageInfo(context).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = getPackageInfo(context).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static PackageInfo getPackageInfo(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo;
    }

    public static String getChannel(Context context) {

        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo("com.pengtx.wonderworld", PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String channel = "CHANNEL_OTHER";

        if (null != appInfo && null != appInfo.metaData.getString("UMENG_CHANNEL")) {

            channel = appInfo.metaData.getString("UMENG_CHANNEL");
        }

        return channel;
    }
}
