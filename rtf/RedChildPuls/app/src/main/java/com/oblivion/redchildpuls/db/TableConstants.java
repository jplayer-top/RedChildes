package com.oblivion.redchildpuls.db;

/**
 * 建表语句
 * Created by arkin on 2016/12/6.
 */
public class TableConstants {
    public static final String TABLENAME_SEARCH = "search";
    public static final String USER_ID = "user_id";
    public static final String SEARCH_HISTORY = "search_history";
    public static final String SEARCH_DATE = "search_date";

    public static final String CREATE_TAB_SEARCH = "create table "
            + TABLENAME_SEARCH + "(_id integer primary key autoincrement, "
            + USER_ID + " char(10), "
            + SEARCH_HISTORY + " char(20), "
            + SEARCH_DATE + " integer(20))";
}
