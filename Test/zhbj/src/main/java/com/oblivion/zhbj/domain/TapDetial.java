package com.oblivion.zhbj.domain;

import java.util.ArrayList;

/**
 * 页签网络数据
 */
public class TapDetial {

    public NewsTabData data;

    public class NewsTabData {
        public String more;
        public ArrayList<TopNews> topnews;
        public ArrayList<News> news;

        @Override
        public String toString() {
            return "NewsTabData [topnews=" + topnews + ", news=" + news + "]";
        }

    }

    public class TopNews {
        public String id;
        public String pubdate;
        public String title;
        public String topimage;
        public String url;

        @Override
        public String toString() {
            return "TopNews [title=" + title + "]";
        }

    }

    public class News {
        public String id;
        public String pubdate;
        public String title;
        public String listimage;
        public String url;

        @Override
        public String toString() {
            return "News [title=" + title + "]";
        }

    }

    @Override
    public String toString() {
        return "NewsTab [data=" + data + "]";
    }

}
