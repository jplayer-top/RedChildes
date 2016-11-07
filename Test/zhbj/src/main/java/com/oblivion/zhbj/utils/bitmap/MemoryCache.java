package com.oblivion.zhbj.utils.bitmap;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.oblivion.zhbj.utils.MD5Encode;

import javax.xml.transform.Source;

/**
 * Created by oblivion on 2016/11/6.
 */
public class MemoryCache {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        //获取到系统分配的最大内存,并将这个值的八分之一传入到LruCache中
        final long maxMemory = Runtime.getRuntime().maxMemory();
        lruCache = new LruCache<String, Bitmap>((int) (maxMemory/8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int singleSize = value.getRowBytes() * value.getHeight();
//                System.out.println(singleSize+"----------");
//                System.out.println(maxMemory+"...............");
                return singleSize;
            }
        };
    }

    public Bitmap getBitmap(String url) {
        Bitmap bitmap = lruCache.get(MD5Encode.change2MD5(url));
//        if (bitmap==null){
//            System.out.println("没数据");
//        }else{
//            System.out.println("获取到了");
//        }
        return bitmap;
    }

    public void setBitmap( String url,Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
