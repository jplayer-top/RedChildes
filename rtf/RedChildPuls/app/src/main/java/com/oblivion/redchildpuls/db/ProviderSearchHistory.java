package com.oblivion.redchildpuls.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by arkin on 2016/12/6.
 */
public class ProviderSearchHistory extends ContentProvider {
    public static final String AUTHORITY = "com.oblivion.redchildpuls";
    /**
     * 获取搜索历史
     */
    public static final String PATH_GET_SEARCH_HISTORY = "get_search_history";
    public static final int CODE_GET_SEARCH_HISTORY = 110;
    /**
     * 插入搜索信息
     */
    public static final String PATH_INSERT_SEARCH_HISTORY = "insert_search_history";
    public static final int CODE_INSERT_SEARCH_HISTORY = 111;
    /**
     * 判断是否已经有该搜索历史
     */
    public static final String PATH_FIND_SEARCH_HISTORY = "find_search_history";
    public static final int CODE_FIND_SEARCH_HISTORY = 112;



    static UriMatcher uMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uMatcher.addURI(AUTHORITY, PATH_GET_SEARCH_HISTORY + "/#", CODE_GET_SEARCH_HISTORY);// 路径需要传入当前用户的id
        uMatcher.addURI(AUTHORITY, PATH_INSERT_SEARCH_HISTORY, CODE_INSERT_SEARCH_HISTORY);
        uMatcher.addURI(AUTHORITY, PATH_FIND_SEARCH_HISTORY+"/#", CODE_FIND_SEARCH_HISTORY);// 该路径需要传入要查找的“历史信息”对象
    }

    private MySQLiteOpenHelper openHelper;


    @Override
    public boolean onCreate() {
        openHelper = new MySQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        int uriCode = uMatcher.match(uri);
        if (uriCode == CODE_GET_SEARCH_HISTORY) {
            long userId = ContentUris.parseId(uri);
            SQLiteDatabase db = openHelper.getReadableDatabase();
            // 根据当前用户id，按照降序排序，返回前6条搜索历史记录
            cursor = db.query(TableConstants.TABLENAME_SEARCH,
                    new String[]{"rowid _id",TableConstants.SEARCH_HISTORY},
                    TableConstants.USER_ID+ "=?",
                    new String[]{userId + ""},
                    null,
                    null,
                    TableConstants.SEARCH_DATE + " DESC",
                    "4");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int uriCode = uMatcher.match(uri);
        if (uriCode == 1) {
            return "vnd.android.cursor.dir/search_history";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uriReturn = null;
        int uriCode = uMatcher.match(uri);
        if (uriCode == CODE_INSERT_SEARCH_HISTORY) {
            SQLiteDatabase db = openHelper.getReadableDatabase();
            String userId = values.getAsString(TableConstants.USER_ID);
            String searchHistory = values.getAsString(TableConstants.SEARCH_HISTORY);

            Cursor cursor = db.query(
                    TableConstants.TABLENAME_SEARCH,
                    new String[]{"_id"},
                    TableConstants.USER_ID + "=? AND " + TableConstants.SEARCH_HISTORY + "=?",
                    new String[]{userId, searchHistory},
                    null,
                    null,
                    null);
            int position = -1;// 用来记录当前当前历史记录的位置，默认值-1
            if(cursor.moveToNext()) {
                position = cursor.getInt(0);// 获取数据库中当前历史记录的位置
            }
            if (position != -1) {// 如果已经存在该历史记录，则更新历史信息的时间戳
                int newId = db.update(
                        TableConstants.TABLENAME_SEARCH,
                        values,
                        "_id=?",
                        new String[]{position + ""}
                );
                uriReturn = Uri.withAppendedPath(uri, "update"+newId);
            } else {// 不存在当前记录
                long newId = db.insert(TableConstants.TABLENAME_SEARCH, null, values);
                uriReturn = Uri.withAppendedPath(uri, "add"+newId);
            }
            // 通知发送到哪一个uri上，所有注册在这个uri上的内容观察者都可以收到这个通知
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
