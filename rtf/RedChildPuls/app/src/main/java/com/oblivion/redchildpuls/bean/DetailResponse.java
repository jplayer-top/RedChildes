package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * 商品详情页的bean
 */

public class DetailResponse implements IResponse {

    public ProductBean product;
    public String response;

    public static class ProductBean {

        public boolean available;
        public String buyLimit;
        public String commentCount;
        public String id;
        public String inventoryArea;
        public String leftTime;
        public String limitPrice;
        public String marketPrice;
        public String name;
        public String price;
        public float score;
        public List<String> bigPic;
        public List<String> pics;
        public List<ProductPropertyBean> productProperty;

        public static class ProductPropertyBean {
            public String id;
            public String k;
            public String v;
        }
    }
}
