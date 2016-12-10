package com.oblivion.redchildpuls.activity;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class AddressListResponse  implements IResponse {

    /**
     * areaList : [{"id":1,"value":"湖北"},{"id":7,"value":"北京"},{"id":11,"value":"湖南"}]
     * response : addressArea
     */

    public String response;
    public List<AreaListBean> areaList;

    public static class AreaListBean {
        /**
         * id : 1
         * value : 湖北
         */

        public int id;
        public String value;
    }

}
