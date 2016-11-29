package me.rokevin.android.lib.helper.util.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

/**
 * Created by luokaiwen on 15/8/25.
 * <p/>
 * 资源帮助类
 */
public class ResUtil {

    public static Uri getResourceUri(Context context, int drawableId) {

        Resources resources = context.getResources();

        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(drawableId) + "/"
                + resources.getResourceTypeName(drawableId) + "/"
                + resources.getResourceEntryName(drawableId));

        return uri;
    }
}
