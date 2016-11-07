package com.oblivion.zhbj.domain;

import java.util.ArrayList;

/**
 * Created by oblivion on 2016/11/5.
 */
public class NewsPhotos {
    public NewsData data;

    public class NewsData {
        public ArrayList<NewsDetial> news;

        public class NewsDetial {
            public String listimage;
            public String title;
        }
    }

}
