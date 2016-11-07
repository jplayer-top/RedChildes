package com.oblivion.zhbj.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 将字符串转化为MD5
 * Created by oblivion on 2016/11/6.
 */
public class MD5Encode {
    public static String change2MD5(String string) {
        try {
            byte[] buffer = string.getBytes("UTF-8");
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(buffer);
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                String hexString = Integer.toHexString(b & 0Xff);
                if (hexString.length() <= 1) {
                    sb.append("0");
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
