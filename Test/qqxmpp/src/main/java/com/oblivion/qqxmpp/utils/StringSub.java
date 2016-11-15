package com.oblivion.qqxmpp.utils;

import com.oblivion.qqxmpp.provider.ChatProvider;

/**
 * Created by oblivion on 2016/11/12.
 */
public class StringSub {
    public static String getString(String account) {
        return account.substring(0, account.indexOf("@"));
    }

    public static String getFromOrSession(String sessionId) {
        return sessionId.substring(0, sessionId.indexOf("@")) + "@oblivion-pc";
    }
}
