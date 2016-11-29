package me.rokevin.android.lib.helper.util.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import me.rokevin.android.lib.helper.util.RLog;

/**
 * Created by luokaiwen on 15/4/28.
 * <p/>
 * 吐丝帮助类
 */
public class RToast {

    public static final String TAG = RToast.class.getSimpleName();

    public static void shortToast(Context context, String content) {

        if (null == context) {
            return;
        }

        if (TextUtils.isEmpty(content)) {
            RLog.e(TAG, "content is null");
            return;
        }

        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String content) {

        if (null == context) {
            return;
        }

        if (TextUtils.isEmpty(content)) {
            RLog.e(TAG, "content is null");
            return;
        }

        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    public static void shortToast(Context context, int content) {

        if (null == context) {
            return;
        }

        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, int content) {

        if (null == context) {
            return;
        }

        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
