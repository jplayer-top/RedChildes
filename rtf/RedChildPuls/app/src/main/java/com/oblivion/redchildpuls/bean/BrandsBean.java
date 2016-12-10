package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by 周乐 on 2016/12/4.
 */
public class BrandsBean implements IResponse {

    /**
     * brand : [{"key":"孕妈专区","value":[{"id":"1201","name":"ain","pic":""},{"id":"1201","name":"ain","pic":""}]},{"key":"营养食品","value":[{"id":"1201","name":"ain","pic":""},{"id":"1201","name":"ain","pic":""}]}]
     * response : brand
     */

    public String response;
    /**
     * key : 孕妈专区
     * value : [{"id":"1201","name":"ain","pic":""},{"id":"1201","name":"ain","pic":""}]
     */

    public List<BrandBean> brand;

        public static class BrandBean implements IResponse {
        public String key;
        /**
         * id : 1201
         * name : ain
         * pic :
         */

        public List<ValueBean> value;

        public static class ValueBean implements IResponse {
            public String id;
            public String name;
            public String pic;
        }
    }
}
