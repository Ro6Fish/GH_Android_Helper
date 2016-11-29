package me.rokevin.android.lib.helper.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.rokevin.android.lib.helper.util.common.RChinese;
import me.rokevin.android.lib.helper.util.common.RToast;

/**
 * 校验输入文本是否匹配
 */
public class RVerify {

    private static Pattern pattern;

    /**
     * 是否是手机格式
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        // ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        String regexString = "^\\d{11}$";
        return matchContent(regexString, mobiles);
    }

    /**
     * 密码是否最少包含一个字母和数字,并且长度大于等于6
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {

        if (password.length() < 6) {
            return false;
        }

        //字母 小于20位 弱
        if (password.replaceAll("[a-z]*[A-Z]*", "").length() == 0) {
            return false;
        }

        //数字 小于20位 弱
        if (password.replaceAll("\\d*", "").length() == 0) {
            return false;
        }

        //特殊字符 小于20位 弱
        if (password.replaceAll("[^a-zA-Z0-9]*", "").length() == 0) {
            return false;
        }

        //字母 数字 小于20位 中
        if (password.replaceAll("[a-z]*[A-Z]*\\d*", "").length() == 0) {
            return true;
        }

        //字母 特殊字符 小于20位 中
        if (password.replaceAll("^\\d*", "").length() == 0) {
            return true;
        }

        //数字 特殊字符 小于20位 中
        if (password.replaceAll("[^a-zA-Z]*", "").length() == 0) {
            return true;
        }

        return true;
    }

    /**
     * 是否是电话号码格式
     *
     * @param telephone
     * @return
     */
    public static boolean isTelephone(String telephone) {
        // "^[+]{0,1}[\\(]?(\\d){1,4}[\\)]?[ ]?([-]?((\\d)|[ ]){1,12})+$";
        String regexString = "(^(\\d{2,5}-){1}\\d{7,8})$";
        return matchContent(regexString, telephone);
    }

    /**
     * 验证用户名和密码
     *
     * @param context
     * @param username
     * @param password
     * @return 通过验证为true
     */
    public static boolean verifyUsernamePassword(Context context, String username, String password) {
        if (!verifyEmptyInput(username)) {
            RToast.shortToast(context, "用户名不能为空");
            return false;
        }
        if (!verifyEmptyInput(password)) {
            RToast.shortToast(context, "密码不能为空");
            return false;
        }
        if (!verifyUsernameLength(username)) {
            RToast.shortToast(context, "请输入2-20字符长度的用户名");
            return false;
        }
        if (!verifyPasswordLength(password)) {
            RToast.shortToast(context, "请输入长度为6-20位的密码");
            return false;
        }
        if (!verifyUsernameStart(username)) {
            RToast.shortToast(context, "用户名只能以汉字或者字母开头");
            return false;
        }
        if (!verifyInvalidChar(username)) {
            RToast.shortToast(context, "用户名只能包含汉字、字母以及\"_-@.字符\"");
            return false;
        }
        if (!verifyPassword(password)) {
            RToast.shortToast(context, "输入密码的格式不正确");
            return false;
        }
        return true;
    }

    /**
     * 验证新密码和确认新密码
     *
     * @param context
     * @param password1
     * @param password2
     * @return 通过验证为true
     */
    public static boolean verifyResetPassword(Context context, String password1, String password2) {
        if (!verifyEmptyInput(password1)) {
            RToast.shortToast(context, "密码不能为空");
            return false;
        }
        if (!verifyPasswordLength(password1)) {
            RToast.shortToast(context, "请输入长度为6-20位的密码");
            return false;
        }
        if (!verifyPassword(password1)) {
            RToast.shortToast(context, "输入密码的格式不正确");
            return false;
        }
        if (!password1.equals(password2)) {
            RToast.shortToast(context, "两次输入的密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 验证支付密码是否太弱
     *
     * @param context
     * @param payPassword
     * @return
     */
    public static String verifyPayPassword(Context context, String payPassword) {
        //字母 小于20位 弱
        if (payPassword.replaceAll("[a-z]*[A-Z]*", "").length() == 0) {
            return "弱";
        }

        //数字 小于20位 弱
        if (payPassword.replaceAll("\\d*", "").length() == 0) {
            return "弱";
        }

        //特殊字符 小于20位 弱
        if (payPassword.replaceAll("[^a-zA-Z0-9]*", "").length() == 0) {
            return "弱";
        }

        //字母 数字 小于20位 中
        if (payPassword.replaceAll("[a-z]*[A-Z]*\\d*", "").length() == 0) {
            return "中";
        }

        //字母 特殊字符 小于20位 中
        if (payPassword.replaceAll("^\\d*", "").length() == 0) {
            return "中";
        }

        //数字 特殊字符 小于20位 中
        if (payPassword.replaceAll("[^a-zA-Z]*", "").length() == 0) {
            return "中";
        }

        //字母数字特殊字符 小于20位 强
        return "强";
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param string
     * @return
     */
    private static boolean isSpecialChar(String string) {
        if (string.replaceAll("[a-z]*[A-Z]*\\d*", "").length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证手机和验证码
     *
     * @param context
     * @param checkCode
     * @return
     */
    public static boolean verifyMobileCheckCode(Context context, String mobile, String checkCode) {

        if (!verifyMobile(context, mobile)) {
            return false;
        }
        if (!verifyCheckCode(context, checkCode)) {
            return false;
        }
        return true;
    }

    /**
     * 验证码输入格式校验
     *
     * @param context
     * @param checkCode
     * @return 通过为true
     */
    private static boolean verifyCheckCode(Context context, String checkCode) {
        if (!verifyEmptyInput(checkCode)) {
            RToast.shortToast(context, "请输入验证码");
            return false;
        }
        if (!verifyCheckCodeLength(checkCode)) {
            RToast.shortToast(context, "验证码长度为6位");
            return false;
        }
        return true;
    }

    /**
     * 验证手机格式
     *
     * @param context
     * @param mobile
     * @return
     */
    public static boolean verifyMobile(Context context, String mobile) {
        if (!verifyEmptyInput(mobile)) {
            RToast.shortToast(context, "请输入手机号码");
            return false;
        }
        if (!isMobile(mobile)) {
            RToast.shortToast(context, "请输入正确的手机号码");
        }
        return true;

    }

    /**
     * 检查验证码是否为6位
     *
     * @param checkCode
     * @return 通过为true
     */
    private static boolean verifyCheckCodeLength(String checkCode) {
        return checkCode.length() == 6;
    }

    /**
     * 正则校验
     *
     * @param regex
     * @param content
     * @return 匹配返回true
     */
    private static boolean matchContent(String regex, String content) {
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }

    /**
     * 验证密码长度 6-20位
     *
     * @param password
     * @return 通过验证为true
     */
    private static boolean verifyPasswordLength(String password) {
        int length = password.length();
        return length >= 6 && length <= 20;
    }

    /**
     * 验证用户名长度 2-20位
     *
     * @param username
     * @return 通过验证为true
     */
    private static boolean verifyUsernameLength(String username) {
        int length = computeLength(username);
        return length >= 2 && length <= 20;
    }

    /**
     * @param string
     * @return 不为空时返回true
     */
    private static boolean verifyEmptyInput(String string) {
        return !TextUtils.isEmpty(string);
    }

    /**
     * 字符串长度计算,1个中文字符长度为2
     *
     * @param string
     * @return int长度
     */
    private static int computeLength(String string) {
        char[] charArray = string.toCharArray();
        int length = 0;
        String regex = "[\u4E00-\u9FA5]";
        for (char c : charArray) {
            if (matchContent(regex, String.valueOf(c))) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * 验证用户名是否含有非法字符
     *
     * @param username
     * @return 通过验证为true
     */
    private static boolean verifyInvalidChar(String username) {
        String regex = "^([\u4E00-\u9FA5]|\\w|[_\\-@\\.])+$";
        return matchContent(regex, username);
    }

    /**
     * 验证用户名是否以汉字或者字母开头
     *
     * @param username
     * @return 通过验证为true
     */
    private static boolean verifyUsernameStart(String username) {
        String regex = "^([\u4E00-\u9FA5]|[a-zA-Z])";
        return matchContent(regex, username);
    }

    /**
     * 验证密码是否是字母或数字
     *
     * @param password
     * @return 通过验证为true
     */
    private static boolean verifyPassword(String password) {
        String regex = "[\u4E00-\u9FA5]";
        return !matchContent(regex, password);
    }

    // 收货地址校验规则：
    // 收货人姓名：4-60个字符（一个汉字算3个字符）
    // 电话号码验证规则：正则匹配：$pattern = '/^[+]{0,1}[\(]?(\d){1,4}[\)]?[ ]?([-]?((\d)|[
    // ]){1,12})+$/';
    // 手机号码:1-30个字符
    // 地址：1-255个字符

    /**
     * 验证收件人姓名
     *
     * @param username 收件人名称
     * @return 通过为true
     */
    public static boolean verifyConsignee(String username) {
        int length = RChinese.length(username);

        if (length < 2 || length > 60) {
            return false;
        }

        return true;
    }

    /**
     * 验证收件人地址
     *
     * @param address 收件人地址
     * @return 通过为true
     */
    public static boolean verifyConsigneeAddress(String address) {
        int length = RChinese.length(address);

        if (length < 1 || length > 255) {
            return false;
        }

        return true;
    }

}
