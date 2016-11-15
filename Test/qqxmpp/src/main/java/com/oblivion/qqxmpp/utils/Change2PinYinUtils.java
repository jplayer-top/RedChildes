package com.oblivion.qqxmpp.utils;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 * Created by oblivion on 2016/11/11.
 */
public class Change2PinYinUtils {
    public static String getPinYin(String ACCOUNT) {
        //PinyinHelper.convertToPinyinString(要转化的字段,"分离器", 是否带声调);
        String pinyinString = PinyinHelper.convertToPinyinString(ACCOUNT, "", PinyinFormat.WITHOUT_TONE);
        return pinyinString;
    }

    public static String getNick(String ACCOUNT) {
        //PinyinHelper.convertToPinyinString(要转化的字段,"分离器", 是否带声调);
        String pinyinString = PinyinHelper.convertToPinyinString(StringSub.getString(ACCOUNT), "", PinyinFormat.WITHOUT_TONE);
        return pinyinString;
    }
}
