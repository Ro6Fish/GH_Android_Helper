package me.rokevin.android.lib.helper.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by luokaiwen on 15/4/28.
 * <p/>
 * 日志工具类
 */
public class RLog {

    private static boolean isDebug = false;

    public static void isDebug(boolean debug) {
        isDebug = debug;
    }

    public static void e(String tag, String msg) {

        if (isDebug) {

            if (TextUtils.isEmpty(tag)) {
                tag = "";
            }

            if (TextUtils.isEmpty(msg)) {
                msg = "";
            }

            Log.e(tag, msg);
        }
    }

    public static void e(Object object, String msg) {

        if (isDebug) {

            String tag = "";

            if (object != null) {
                tag = object.getClass().getSimpleName();
            }

            if (TextUtils.isEmpty(msg)) {
                msg = "";
            }

            Log.e(tag, msg);
        }
    }
}
