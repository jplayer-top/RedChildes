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

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 * Created by oblivion on 2016/11/11.
 */
public class ContactProvider extends ContentProvider {
    private static final String authorities = "com.oblivion.qqxmpp.provider.ContactProvider";
    //书写格式，不要写错,是为了给外界调用-----
    public static final Uri URI = Uri.parse("content://" + authorities + "/contact");
    //uriMatcher匹配成功后结果
    private static final int SUCCESS = 0;
    //表的名称--匹配器能用到
    private static String tableName = "contact";
    //创建表的SQL 语句
    private static final String SQL =
            "create table contact(_id integer primary key autoincrement,account text,nick text,avatar text,sort text)";

    //匹配器---用来判断匹配是否成功
    private static UriMatcher uriMatcher = null;

    //静态代码块设定匹配器
    static {
        //初始化匹配器
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);//初始状态设定为NO_MATCH;
        //明白三个参数分别是什么
        uriMatcher.addURI(authorities, tableName, SUCCESS);
    }


    // BaseColumns _id，继承他是为了有自增长的id----表结构--也是外界使用
    public static class CONTACT_FIELD implements BaseColumns {
        public static final String ACCOUNT = "account";
        public static final String NICK = "nick";
        public static final String AVATAR = "avatar";
        public static final String SORT = "sort";// 拼音
    }

    /**
     * 创建数据库帮助类
     */
    private class MyOpenHelper extends SQLiteOpenHelper {
        public MyOpenHelper(Context context) {
            super(context, "contact.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //创建表
            db.execSQL(SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private MyOpenHelper helper;

    @Override
    public boolean onCreate() {
        //创建帮助类
        helper = new MyOpenHelper(getContext());
        return helper == null ? false : true;//判断是否为空，根据判断确定返回值
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    //-------"content://" + authorities + "/contact"
    //---------"content://" + authorities + "/contact"+id
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                long id = db.insert("contact", "", values);
                uri = ContentUris.withAppendedId(uri, id);
                break;
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = 0;
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                delete = db.delete(tableName, selection, selectionArgs);
                break;
        }
        return delete;//受影响的行数
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int update = 0;
        switch (uriMatcher.match(uri)) {
            case SUCCESS://匹配成功
                update = db.update(tableName, values, selection, selectionArgs);
                break;
        }
        return update;//受影响的行数
    }
}
