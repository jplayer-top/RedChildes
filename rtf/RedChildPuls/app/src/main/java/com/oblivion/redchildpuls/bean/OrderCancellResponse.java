package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by nick on 2016/12/8.
 */
public class OrderCancellResponse implements IResponse {

    /**
     * error : 取消订单失败
     * error_code : 1537
     * response : orderCancel
     */

    public String error;
    public String error_code;
    public String response;    //orderCancel   成功取消
                                //订单取消失败
}
