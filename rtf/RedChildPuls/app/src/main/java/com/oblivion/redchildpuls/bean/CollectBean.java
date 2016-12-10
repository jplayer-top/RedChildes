package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by lxgl on 2016/12/7.
 */

public class CollectBean implements IResponse {

    /**
     * listCount : 10
     * productList : [{"id":1,"marketPrice":500,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350},{"id":2,"marketPrice":180,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100},{"id":5,"marketPrice":98,"name":"时尚女裙","pic":"/images/product/detail/a1.jpg","price":108},{"id":28,"marketPrice":300,"name":"春装新款","pic":"/images/product/detail/q26.jpg","price":200},{"id":17,"marketPrice":300,"name":"春装新款","pic":"/images/product/detail/q15.jpg","price":200},{"id":25,"marketPrice":150,"name":"新款秋装","pic":"/images/product/detail/q23.jpg","price":160},{"id":24,"marketPrice":200,"name":"春秋新款外套","pic":"/images/product/detail/q22.jpg","price":160},{"id":11,"marketPrice":180,"name":"韩版秋装","pic":"/images/product/detail/q9.jpg","price":160},{"id":18,"marketPrice":200,"name":"短裙","pic":"/images/product/detail/q16.jpg","price":160},{"id":3,"marketPrice":500,"name":"女裙","pic":"/images/product/detail/c1.jpg","price":300}]
     * response : favorites
     */

    public int listCount;
    public String response;
    public List<ProductListBean> productList;

    public static class ProductListBean {
        /**
         * id : 1
         * marketPrice : 500
         * name : 韩版时尚蕾丝裙
         * pic : /images/product/detail/c3.jpg
         * price : 350
         */

        public int id;
        public int marketPrice;
        public String name;
        public String pic;
        public int price;

        @Override
        public String toString() {
            return "ProductListBean{" +
                    "id=" + id +
                    ", marketPrice=" + marketPrice +
                    ", name='" + name + '\'' +
                    ", pic='" + pic + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "listCount=" + listCount +
                ", response='" + response + '\'' +
                ", productList=" + productList +
                '}';
    }
}
