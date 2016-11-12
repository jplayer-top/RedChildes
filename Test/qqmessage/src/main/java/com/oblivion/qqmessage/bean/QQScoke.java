package com.oblivion.qqmessage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.oblivion.qqmessage.core.QQConnection;

import java.net.Socket;

/**
 * Created by oblivion on 2016/11/10.
 */
public class QQScoke implements Parcelable {
    public static QQConnection mConnection;
    public static String account;
    public static String pswd;

    protected QQScoke(Parcel in) {
    }

    public static final Creator<QQScoke> CREATOR = new Creator<QQScoke>() {
        @Override
        public QQScoke createFromParcel(Parcel in) {
            return new QQScoke(in);
        }

        @Override
        public QQScoke[] newArray(int size) {
            return new QQScoke[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
    }
}
