package com.id0304.utils;

/**
 * @Author WuZhengHua
 * @Description TODO md5和base64与邮箱密码双重加密加盐工具类
 * @Date 2019/7/14 13:16
 */
public class Md5AndBase64SoltUtil {
    public static String Md5AndBase64Solt(String password, String email) {
        String solt = password + email;
        String ps_md5 = Md5Util.getMD5(solt);
        return Base64Util.encode(ps_md5);
    }
}
