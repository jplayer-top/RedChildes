package com.oblivion.qqxmpp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oblivion on 2016/11/9.
 */
public class CurrentTimeUtils {
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
