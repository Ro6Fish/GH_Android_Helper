package me.rokevin.android.lib.helper.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 */
public class RChinese {

    /**
     * 获取有汉字的总长度
     *
     * @param str
     * @return
     */
    public static int length(String str) {

        char[] chars = str.toCharArray();

        int length = 0;

        for (char char1 : chars) {

            if (isChineseChar(char1 + "")) {
                length = length + 3;// UTF-8 中汉字编码占3个字节
            } else {
                length = length + 1;
            }
        }
        return length;
    }

    /**
     * 判断是否是汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {

        boolean temp = false;

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

        Matcher m = p.matcher(str);

        if (m.find()) {
            temp = true;
        }
        return temp;
    }
}
