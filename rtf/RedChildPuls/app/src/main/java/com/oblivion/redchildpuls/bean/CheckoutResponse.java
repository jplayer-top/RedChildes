package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by xiongmc on 2016/6/8.
 */
public class CheckoutResponse implements IResponse {
/**
 * "addressInfo": {
 "addressArea": "二七区",
 "addressDetail": "fjghkk",
 "city": "郑州市",
 "id": 147,
 "isDefault": 1,
 "name": "truth tst",
 "phoneNumber": "44554",
 "province": "河南省",
 "zipCode": "450000"
 }
 */
    public CheckAddressInfo addressInfo;
    /**
     * freight : 10
     * totalCount : 5
     * totalPoint : 30
     * totalPrice : 450
     */

    public CheckoutAddupBean checkoutAddup;
    /**
     * checkoutAddup : {"freight":10,"totalCount":5,"totalPoint":30,"totalPrice":450}
     * checkoutProm : ["促销信息一","促销信息二"]
     * deliveryList : [{"des":"周一至周五送货","type":1},{"des":"双休日及公众假期送货","type":2},{"des":"时间不限，工作日双休日及公众假期均可送货","type":3}]
     * paymentList : [{"des":"到付-现金","type":1},{"des":"到付-POS机","type":2},{"des":"支付宝","type":1}]
     * productList : [{"prodNum":3,"product":{"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]}},{"prodNum":2,"product":{"id":2,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100,"productProperty":[{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"}]}}]
     * response : checkOut
     */

    public String response;
    public List<String> checkoutProm;
    /**
     * des : 周一至周五送货
     * type : 1
     */

    public List<DeliveryListBean> deliveryList;
    /**
     * des : 到付-现金
     * type : 1
     */

    public List<PaymentListBean> paymentList;
    /**
     * prodNum : 3
     * product : {"id":1,"name":"韩版时尚蕾丝裙",
     * "pic":"/images/product/detail/c3.jpg","price":350,"productProperty":
     * [{"id":1,"k":"颜色","v":"红色"},
     * {"id":2,"k":"颜色","v":"绿色"},
     * {"id":3,"k":"尺码","v":"M"},
     * {"id":4,"k":"尺码","v":"XXL"}]}
     */

    public List<ProductListBean> productList;

    public static class CheckoutAddupBean {
        public int freight;
        public int totalCount;
        public int totalPoint;
        public int totalPrice;
    }

    public static class DeliveryListBean {
        public String des;
        public int type;
    }

    public static class PaymentListBean {
        public String des;
        public int type;
    }

    public static class ProductListBean {
        public int prodNum;
        /**
         * id : 1
         * name : 韩版时尚蕾丝裙
         * pic : /images/product/detail/c3.jpg
         * price : 350
         * productProperty : [{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]
         */

        public ProductBean product;

        public static class ProductBean {
            public int id;
            public String name;
            public String pic;
            public int price;
            /**
             * id : 1
             * k : 颜色
             * v : 红色
             */

            public List<ProductPropertyBean> productProperty;

            public static class ProductPropertyBean {
                public int id;
                public String k;
                public String v;
            }
        }
    }
}
