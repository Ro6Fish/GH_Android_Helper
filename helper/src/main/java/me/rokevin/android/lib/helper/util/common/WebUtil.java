package me.rokevin.android.lib.helper.util.common;

import android.content.Context;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import me.rokevin.android.lib.helper.util.RLog;

/**
 * Created by luokaiwen on 2015/1/8 2015/1/8.
 * <p/>
 * 处理web工具类
 */
public class WebUtil {

    private final String TAG = WebUtil.class.getSimpleName();

    private Context mContext;

    public WebUtil() {

    }

    public WebUtil(Context context) {
        mContext = context;
    }

    /**
     * 设置使用够执行JS脚本
     *
     * @param view     WebView
     * @param isEnable 是否可使用 true：可使用 false：不可使用
     * @return
     */
    public WebUtil setJSEnabled(WebView view, boolean isEnable) {

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);

        return this;
    }

    /**
     * 设置是否开启定位
     *
     * @param view     WebView
     * @param isEnable 是否可使用 true：可使用 false：不可使用
     * @return
     */
    public WebUtil setGeoLocation(WebView view, boolean isEnable) {

        WebSettings settings = view.getSettings();

        //启用数据库
        settings.setDatabaseEnabled(true);
        String dir = mContext.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();

        //启用地理定位
        settings.setGeolocationEnabled(true);

        //设置定位的数据库路径
        settings.setGeolocationDatabasePath(dir);

        settings.setDomStorageEnabled(true);

        view.setWebChromeClient(new WebChromeClient() {

            //配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        return this;
    }

    /**
     * 设置Agent
     *
     * @param view WebView
     * @return
     */
    public WebView setAgent(WebView view) {

        WebSettings settings = view.getSettings();

        String agent = settings.getUserAgentString();

        RLog.e(TAG, "agent is:" + agent);

        // settings.setUserAgentString(Constant.AGENT + "(" + agent + ")");

        return view;
    }

    //wvWeb.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
    //webView.getSettings().setDefaultFontSize(5);
}
