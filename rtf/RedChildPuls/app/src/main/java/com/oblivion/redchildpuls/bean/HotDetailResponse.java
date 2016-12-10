package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by nick on 2016/12/6.
 */
public class HotDetailResponse implements IResponse {

    /**
     * addressArea : 密云县
     * addressDetail : 街道口地铁c口
     * id : 139
     * name : itcast
     */

    public AddressInfoBean addressInfo;
    /**
     * freight : 10
     * totalCount : 5
     * totalPoint : 30
     * totalPrice : 1250
     */

    public CheckoutAddupBean checkoutAddup;
    /**
     * type : 1
     */

    public DeliveryInfoBean deliveryInfo;
    /**
     * invoiceContent : 传智慧播客教育科技有限公司
     * invoiceTitle : 传智慧播客教育科技有限公司
     */

    public InvoiceInfoBean invoiceInfo;
    /**
     * flag : 3
     * orderId : 098593
     * status : 未处理
     * time : 1449107098604
     */

    public OrderInfoBean orderInfo;
    /**
     * type : 1
     */

    public PaymentInfoBean paymentInfo;
    /**
     * addressInfo : {"addressArea":"密云县","addressDetail":"街道口地铁c口","id":139,"name":"itcast"}
     * checkoutAddup : {"freight":10,"totalCount":5,"totalPoint":30,"totalPrice":1250}
     * deliveryInfo : {"type":"1"}
     * invoiceInfo : {"invoiceContent":"传智慧播客教育科技有限公司","invoiceTitle":"传智慧播客教育科技有限公司"}
     * orderInfo : {"flag":3,"orderId":"098593","status":"未处理","time":"1449107098604"}
     * paymentInfo : {"type":1}
     * productList : [{"prodNum":3,"product":{"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":0,"k":"颜色","v":"红色"},{"id":1,"k":"颜色","v":"绿色"}]}},{"prodNum":2,"product":{"id":2,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100,"productProperty":[{"id":0,"k":"颜色","v":"绿色"},{"id":1,"k":"尺码","v":"M"}]}}]
     * prom : ["促销信息一","促销信息二"]
     * response : orderDetail
     */

    public String response;
    /**
     * prodNum : 3
     * product : {"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":0,"k":"颜色","v":"红色"},{"id":1,"k":"颜色","v":"绿色"}]}
     */

    public List<ProductListBean> productList;
    public List<String> prom;

    public static class AddressInfoBean {
        public String addressArea;
        public String addressDetail;
        public int id;
        public String name;
    }

    public static class CheckoutAddupBean {
        public int freight;
        public int totalCount;
        public int totalPoint;
        public int totalPrice;
    }

    public static class DeliveryInfoBean {
        public String type;
    }

    public static class InvoiceInfoBean {
        public String invoiceContent;
        public String invoiceTitle;
    }

    public static class OrderInfoBean {
        public int flag;
        public String orderId;
        public String status;
        public String time;
    }

    public static class PaymentInfoBean {
        public int type;
    }

    public static class ProductListBean {
        public int prodNum;
        /**
         * id : 1
         * name : 韩版时尚蕾丝裙
         * pic : /images/product/detail/c3.jpg
         * price : 350
         * productProperty : [{"id":0,"k":"颜色","v":"红色"},{"id":1,"k":"颜色","v":"绿色"}]
         */

        public ProductBean product;

        public static class ProductBean {
            public int id;
            public String name;
            public String pic;
            public int price;
            /**
             * id : 0
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
