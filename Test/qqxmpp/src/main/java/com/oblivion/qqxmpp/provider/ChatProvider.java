package com.oblivion.qqxmpp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

/**
 * Created by oblivion on 2016/11/12.
 */
public class ChatProvider extends ContentProvider {
    public static final String authority = "com.oblivion.qqxmpp.provider.ChatProvider";
    //对外提供的URI接口
    public static final Uri SMS_URI = Uri.parse("content://" + authority + "/sms");

    // 创建Provider 配置名字 Authority
    //设计表
    public static class SMS implements BaseColumns// _id
    {
        public static final String FROM_ID = "from_id";// 发送
        public static final String FROM_NICK = "from_nick";
        public static final String FROM_AVATAR = "from_avatar";
        public static final String BODY = "body";
        public static final String TYPE = "type";// chat
        public static final String TIME = "time";
        public static final String STATUS = "status";
        public static final String UNREAD = "unread";
        //自定义添加的字段，目的为了方便查找
        public static final String SESSION_ID = "session_id";
        public static final String SESSION_NAME = "session_name";

    }

    public class MyOpenHelper extends SQLiteOpenHelper {
        /**
         * 马丹，两个数据库名字一样好像会报错，，好坑----
         * @param context
         */
        public MyOpenHelper(Context context) {
            super(context, "sms.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //在已有的数据库下创建表sms
            String sql = "create table sms(_id integer primary key autoincrement,from_id text,from_nick text,from_avatar text,body text,time text,status text,unread text,session_id text,session_name text,type text)";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private MyOpenHelper helper;

    @Override
    public boolean onCreate() {
        //创建数据库帮助类
        helper = new MyOpenHelper(getContext());
        return helper == null ? false : true;
    }

    private static final String TABLE = "sms";
    private static UriMatcher uriMatcher;
    private static final int SUCCESS = 0;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority, TABLE, SUCCESS);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int update = 0;
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                update = db.update(TABLE, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);//null是通知所有的观察者
                break;
        }
        return update;//受影响的行数
    }

    //-------"content://" + authorities + "/contact"
    //---------"content://" + authorities + "/contact"+id
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                long id = db.insert(TABLE, "", values);
                uri = ContentUris.withAppendedId(uri, id);
                System.out.println(SMS_URI + "----" + uri);
                getContext().getContentResolver().notifyChange(uri, null);//null是通知所有的观察者
                break;
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                cursor = db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = 0;
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                delete = db.delete(TABLE, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);//null是通知所有的观察者
                break;
        }
        return delete;//受影响的行数
    }
}
