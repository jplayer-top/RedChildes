package com.oblivion.zhbj.utils.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.oblivion.zhbj.utils.MD5Encode;

import java.net.URL;

/**
 * Created by oblivion on 2016/11/6.
 */
public class MyBitmapUtils {

    private NetCache netCache;
    private LocalCache localCache;
    private MemoryCache memoryCache;

    public MyBitmapUtils(Activity activity) {
        //内存加载
        memoryCache = new MemoryCache();
        //本地缓存
        localCache = new LocalCache(memoryCache);
        //网络缓存
        netCache = new NetCache(localCache, memoryCache);
    }

    public void display(ImageView imageView, String url) {
        Bitmap bitmap = memoryCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.i("System.out", "内存加载成功----");
            return;
        }
        bitmap = localCache.getBitmap(url);
        if (bitmap != null) {//如果本地有数据，选择从本地获取
            imageView.setImageBitmap(bitmap);
            memoryCache.setBitmap(MD5Encode.change2MD5(url),bitmap);
            return;
        }
        netCache.getBitmap(imageView, url);
    }
}
