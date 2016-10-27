package com.oblivion.utils;

import android.text.TextUtils;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 * Created by oblivion on 2016/10/27.
 */
public class Chinese2Spell {
    /**
     * 将传入的数据转化为汉语拼音
     *
     * @param name 要转化的汉字
     * @return 转化为汉字拼音
     */
    public static String c2e(String name) {
        //将传入的字符串转化为数组
        char[] charArray = name.toCharArray();
        //用于记录转化的字符
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            //将传入的字符转化为汉语拼音
            String pinyinName = "ww";
            try {
                pinyinName = PinyinHelper.convertToPinyinArray(c)[0];
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(name + "--------------------------");
            }
            sb.append(pinyinName);
        }
        return sb.toString();
    }

    public static String teaC2E(String name) {
        String pinyinName = "";
        try {
             pinyinName = PinyinHelper.convertToPinyinString(name, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pinyinName;
    }
}
