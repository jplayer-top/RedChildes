package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by 周乐 on 2016/12/4.
 */
public class LimitBuyBean implements IResponse{

    /**
     * response : limitbuy
     * productList : [{"id":"1102539","name":"雅培金装","pic":"","price":"79","limitPrice":"78","leftTime":"3600"},{"id":"1102539","name":"雅培金装","pic":"","price":"79","limitPrice":"78","leftTime":"3600"}]
     * listCount : 15
     */

    public String response;
    public String listCount;
    /**
     * id : 1102539
     * name : 雅培金装
     * pic :
     * price : 79
     * limitPrice : 78
     * leftTime : 3600
     */

    public List<ProductListBean> productList;

    public static class ProductListBean implements IResponse{
        public String id;
        public String name;
        public String pic;
        public String price;
        public String limitPrice;
        public String leftTime;
    }
}
