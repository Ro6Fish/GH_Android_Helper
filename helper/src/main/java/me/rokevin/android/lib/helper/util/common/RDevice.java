package me.rokevin.android.lib.helper.util.common;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import me.rokevin.android.lib.helper.util.RLog;

/**
 * Created by luokaiwen on 15/6/4.
 * <p/>
 * 设置帮助类
 */
public class RDevice {

    /**
     * 获取手机IP地址
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            RLog.log("getLocalIpAddress", "获取IP地址失败");
        }
        return null;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDeviceMac(Context context) {
        try {
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            if (!TextUtils.isEmpty(mac)) {
                return mac;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static String getIMEI(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String imei = telephonyManager.getDeviceId();

        if (TextUtils.isEmpty(imei)) {
            imei = "000000000000";
        }

        return imei;
    }

    public static String getDeviceName() {
//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
        /*
         * //BOARD 主板 String phoneInfo = "BOARD: " + android.os.Build.BOARD;
		 * phoneInfo += ", BOOTLOADER: " + android.os.Build.BOOTLOADER; //BRAND
		 * 运营商 phoneInfo += ", BRAND: " + android.os.Build.BRAND; phoneInfo +=
		 * ", CPU_ABI: " + android.os.Build.CPU_ABI; phoneInfo += ", CPU_ABI2: "
		 * + android.os.Build.CPU_ABI2; //DEVICE 驱动 phoneInfo += ", DEVICE: " +
		 * android.os.Build.DEVICE; //DISPLAY Rom的名字 例如 Flyme 1.1.2（魅族rom）
		 * &nbsp;JWR66V（Android nexus系列原生4.3rom） phoneInfo += ", DISPLAY: " +
		 * android.os.Build.DISPLAY; //指纹 phoneInfo += ", FINGERPRINT: " +
		 * android.os.Build.FINGERPRINT; //HARDWARE 硬件 phoneInfo +=
		 * ", HARDWARE: " + android.os.Build.HARDWARE; phoneInfo += ", HOST: " +
		 * android.os.Build.HOST; phoneInfo += ", ID: " + android.os.Build.ID;
		 * //MANUFACTURER 生产厂家 phoneInfo += ", MANUFACTURER: " +
		 * android.os.Build.MANUFACTURER; //MODEL 机型 phoneInfo += ", MODEL: " +
		 * android.os.Build.MODEL; phoneInfo += ", PRODUCT: " +
		 * android.os.Build.PRODUCT; phoneInfo += ", RADIO: " +
		 * android.os.Build.RADIO; phoneInfo += ", RADITAGSO: " +
		 * android.os.Build.TAGS; phoneInfo += ", TIME: " +
		 * android.os.Build.TIME; phoneInfo += ", TYPE: " +
		 * android.os.Build.TYPE; phoneInfo += ", USER: " +
		 * android.os.Build.USER; //VERSION.RELEASE 固件版本 phoneInfo +=
		 * ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE; phoneInfo
		 * += ", VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME;
		 * //VERSION.INCREMENTAL 基带版本 phoneInfo += ", VERSION.INCREMENTAL: " +
		 * android.os.Build.VERSION.INCREMENTAL; //VERSION.SDK SDK版本 phoneInfo
		 * += ", VERSION.SDK: " + android.os.Build.VERSION.SDK; phoneInfo +=
		 * ", VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT;
		 */

//        if (model.startsWith(manufacturer)) {
//            return capitalize(model);
//        } else {
//            return capitalize(manufacturer) + " " + model;
//        }
        return "";
    }
}
