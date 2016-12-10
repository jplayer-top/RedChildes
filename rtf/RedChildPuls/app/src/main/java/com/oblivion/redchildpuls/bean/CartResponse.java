package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/6.
 */
public class CartResponse implements IResponse {

    /**
     * cart : [{"prodNum":3,"product":{"buyLimit":10,"id":1,"name":"韩版时尚蕾丝裙","number":"100","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":4,"k":"尺码","v":"XXL"}]}},{"prodNum":2,"product":{"buyLimit":10,"id":2,"name":"粉色毛衣","number":"13","pic":"/images/product/detail/q1.jpg","price":100,"productProperty":[{"id":2,"k":"颜色","v":"绿色"}]}}]
     * prom : ["促销信息一","促销信息二"]
     * response : cart
     * totalCount : 5
     * totalPoint : 100
     * totalPrice : 1250
     */

    public String response;
    public int totalCount;
    public int totalPoint;
    public int totalPrice;
    public List<CartBean> cart;
    public List<String> prom;
    public static class CartBean {
        /**
         * prodNum : 3
         * product : {"buyLimit":10,"id":1,"name":"韩版时尚蕾丝裙","number":"100","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":4,"k":"尺码","v":"XXL"}]}
         */

        public int prodNum;
        public ProductBean product;

        public static class ProductBean {
            /**
             * buyLimit : 10
             * id : 1
             * name : 韩版时尚蕾丝裙
             * number : 100
             * pic : /images/product/detail/c3.jpg
             * price : 350
             * productProperty : [{"id":4,"k":"尺码","v":"XXL"}]
             */

            public int buyLimit;
            public int id;
            public String name;
            public String number;
            public String pic;
            public int price;
            public List<ProductPropertyBean> productProperty;

            public static class ProductPropertyBean {
                /**
                 * id : 4
                 * k : 尺码
                 * v : XXL
                 */

                public int id;
                public String k;
                public String v;
            }
        }
    }
}
