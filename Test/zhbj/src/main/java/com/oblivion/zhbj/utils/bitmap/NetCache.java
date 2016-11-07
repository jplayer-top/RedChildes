package com.oblivion.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.oblivion.zhbj.utils.MD5Encode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by oblivion on 2016/11/6.
 */
public class NetCache {


    private ImageView iv;
    private String url;
    private LocalCache mLocalCache;
    private MemoryCache memoryCache;

    public NetCache(LocalCache localCache, MemoryCache memoryCache) {
        this.mLocalCache = localCache;
        this.memoryCache = memoryCache;
    }

    public void getBitmap(ImageView imageView, String url) {
        new BitmapTask().execute(imageView, url);
    }

    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {
        /**
         * 准备加载
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 正在加载
         *
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            iv = (ImageView) params[0];
            url = (String) params[1];
            //由于ListView的复用机制，在这里设定标签
            iv.setTag(url);
            return getBitmap();
        }

        /**
         * 加载完成
         *
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            String itemUrl = (String) iv.getTag();
            //根据设定的Tag中的URL进行适配
            if (itemUrl.equals(url)) {
                if (bitmap != null) {
                    iv.setImageBitmap(bitmap);
                    System.out.println(MD5Encode.change2MD5(url));
                    mLocalCache.setBitmap(bitmap, url);
                    memoryCache.setBitmap(url, bitmap);
                }
            }
            System.out.println("网络加载");
        }
    }

    /**
     * 网路获取Bitmap
     *
     * @return
     */
    @Nullable//添加这个注解是什么鬼
    private Bitmap getBitmap() {
        HttpURLConnection connection = null;

        try {
            //设置网络
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                //取消掉链接
                connection.disconnect();
            }
        }
        return null;
    }
}
