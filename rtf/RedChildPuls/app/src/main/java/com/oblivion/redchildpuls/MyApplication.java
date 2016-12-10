package com.oblivion.redchildpuls;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.android.volley.toolbox.ImageLoader;

import org.senydevpkg.net.HttpLoader;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class MyApplication extends Application {
    public static Context mContext;
    public static int mMainThreadId;
    public static Handler mMainThreadHandler;
    public static HttpLoader HL;
    public static ImageLoader IL;
    public static int userId;
    public static StringBuffer stringBuffer;


    @Override

    public void onCreate() {
        super.onCreate();

        stringBuffer = new StringBuffer();
        stringBuffer.append("1:3:2|2:2:1,3|3:3:3|4:2:1|5:2:1");
        //上下文
        mContext = getApplicationContext();
        //主线程的Handler
        mMainThreadHandler = new Handler();
        //主线程的线程id
        mMainThreadId = android.os.Process.myTid();
        HL = HttpLoader.getInstance(mContext);
        IL = HL.getImageLoader();
    }
}
