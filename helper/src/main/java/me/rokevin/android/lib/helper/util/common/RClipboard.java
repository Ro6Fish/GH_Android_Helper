package me.rokevin.android.lib.helper.util.common;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by luokaiwen on 15/4/28.
 * <p/>
 * 剪切板工具类
 */
public class RClipboard {

    public static void copy(Context context, String content) {

        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(content); // 复制
    }
}
