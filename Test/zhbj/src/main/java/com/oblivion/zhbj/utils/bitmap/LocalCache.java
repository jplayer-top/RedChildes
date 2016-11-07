package com.oblivion.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.oblivion.zhbj.utils.MD5Encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by oblivion on 2016/11/6.
 */
public class LocalCache {

    private File directory = Environment.getExternalStorageDirectory();
    private MemoryCache memoryCache;

    public LocalCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    /**
     * 本地存在的话，在本地读取文件
     *
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url) {
        File file = new File(directory, MD5Encode.change2MD5(url) + ".cache");
        if (file.isFile() && file.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                System.out.println("本地加载");
                //写入缓存
                memoryCache.setBitmap(MD5Encode.change2MD5(url), bitmap);
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过网路获取后存贮
     *
     * @param bitmap
     * @param url
     */
    public void setBitmap(Bitmap bitmap, String url) {
        File file = new File(directory, MD5Encode.change2MD5(url) + ".cache");
        if (!directory.exists() || directory.isFile()) {
            directory.mkdir();//如果文件夹不存在，创建文件夹
        }
        try {
            //将文件存储到文件夹中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            System.out.println(MD5Encode.change2MD5(url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
