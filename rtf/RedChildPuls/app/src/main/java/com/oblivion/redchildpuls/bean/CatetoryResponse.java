package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by qinyin on 2016/12/4.
 */
public class CatetoryResponse implements IResponse {

    public String response;

    public List<CategoryBean> category;

    public static class CategoryBean {
        public int id;
        public boolean isLeafNode;
        public String name;
        public int parentId;
        public String pic;
        public String tag;

    }
}
