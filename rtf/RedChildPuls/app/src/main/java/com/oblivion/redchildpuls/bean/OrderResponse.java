package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by nick on 2016/12/6.
 */
public class OrderResponse implements IResponse {

    /**
     * orderList : [{"flag":3,"orderId":"286342","price":208,"status":"未处理","time":"1439529286360"},{"flag":1,"orderId":"657072","price":208,"status":"未处理","time":"1439529657077"},{"flag":1,"orderId":"818792","price":208,"status":"未处理","time":"1439529818827"},{"flag":1,"orderId":"432294","price":208,"status":"未处理","time":"1439531432317"},{"flag":2,"orderId":"490503","price":208,"status":"未处理","time":"1439531490519"},{"flag":1,"orderId":"624490","price":208,"status":"未处理","time":"1439533624526"},{"flag":3,"orderId":"873768","price":450,"status":"未处理","time":"1449037873786"},{"flag":2,"orderId":"879495","price":450,"status":"未处理","time":"1449037879509"},{"flag":2,"orderId":"983263","price":450,"status":"未处理","time":"1449107002398"},{"flag":3,"orderId":"611556","price":450,"status":"未处理","time":"1449114660063"},{"flag":1,"orderId":"458804","price":450,"status":"未处理","time":"1449543458820"},{"flag":1,"orderId":"227485","price":160,"status":"未处理","time":"1450619227636"},{"flag":3,"orderId":"902766","price":350,"status":"未处理","time":"1450619902907"},{"flag":3,"orderId":"618656","price":160,"status":"未处理","time":"1450657618916"},{"flag":1,"orderId":"098463","price":160,"status":"未处理","time":"1450658098564"},{"flag":1,"orderId":"282641","price":160,"status":"未处理","time":"1450658282728"},{"flag":3,"orderId":"983026","price":160,"status":"未处理","time":"1450659983163"},{"flag":3,"orderId":"087185","price":160,"status":"未处理","time":"1450660087268"},{"flag":1,"orderId":"871822","price":160,"status":"未处理","time":"1450661871904"},{"flag":2,"orderId":"014727","price":160,"status":"未处理","time":"1450662014812"},{"flag":1,"orderId":"373538","price":160,"status":"未处理","time":"1450662373635"},{"flag":1,"orderId":"837503","price":200,"status":"未处理","time":"1450662837634"},{"flag":3,"orderId":"922024","price":160,"status":"未处理","time":"1450666922109"},{"flag":2,"orderId":"240270","price":160,"status":"未处理","time":"1450667240348"},{"flag":1,"orderId":"494780","price":160,"status":"未处理","time":"1450667494864"},{"flag":3,"orderId":"910488","price":450,"status":"未处理","time":"1450680910605"},{"flag":2,"orderId":"999455","price":350,"status":"未处理","time":"1450680999547"},{"flag":1,"orderId":"322480","price":160,"status":"未处理","time":"1450681322579"},{"flag":3,"orderId":"457011","price":160,"status":"未处理","time":"1450681457111"},{"flag":1,"orderId":"490602","price":160,"status":"未处理","time":"1450681490969"}]
     * response : orderList
     */

    public String response;
    /**
     * flag : 3
     * orderId : 286342
     * price : 208
     * status : 未处理
     * time : 1439529286360
     */

    public List<OrderListBean> orderList;

    public static class OrderListBean {
        public int flag;
        public String orderId;
        public int price;
        public String status;
        public String time;
    }
}
