package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by xiongmc on 2016/6/8.
 */
public class LoginResponse implements IResponse {

    /**
     * response :  login
     * userInfo : {" userid ":154636}
     */

    public String response;
    /**
     *  userid  : 154636
     */

    public UserInfoBean userInfo;

    public static class UserInfoBean {
        public  int userid;
    }
}
