package me.rokevin.android.lib.helper.util.time;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间帮助类
 */
public class CalendarUtil {

    private static final String TAG = CalendarUtil.class.getSimpleName();

    private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String getDateTimeString(Date date, SimpleDateFormat df) {
        df.setLenient(false);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String s = df.format(date);
        return s;
    }

    public static String parseTime(SimpleDateFormat df, String formatTime) {
        df.setLenient(false);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = null;
        try {
            date = df.parse(formatTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (null == date) {
            return "";
        }

        long timeL = date.getTime();

        String timestamp = String.valueOf(timeL / 1000);
        return timestamp;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis() / 1000 + "";
    }

    /***
     * 多久之前,这个是服务端时间的故事
     *
     * @param date
     * @return
     */
    public static String getHowLongAgo(String serverDate, String date) {

        if (TextUtils.isEmpty(serverDate) || TextUtils.isEmpty(date)) {
            return "";
        }

        long serverDateL;
        long dateL;

        try {
            serverDateL = Long.parseLong(serverDate);
            dateL = Long.parseLong(date);
        } catch (NumberFormatException e) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(serverDateL);

        long second = 1000;
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long week = day * 7;
        long month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) * day;
        long year = calendar.getActualMaximum(Calendar.DAY_OF_YEAR) * day;

        long time = (serverDateL - dateL) / 1000;

        if (time / year > 0) {
            return time / year + "年前";
        } else if (time / month > 0) {
            return time / month + "月前";
        } else if (time / week > 0) {
            return time / week + "周前";
        } else if (time / day > 0) {
            return time / day + "天前";
        } else if (time / hour > 0) {
            return time / hour + "小时前";
        } else if (time / minute > 0) {
            return time / minute + "分钟前";
        } else if (time / second > 0) {
            return time / second + "秒钟前";
        } else {
            return "刚刚";
        }
    }
}
