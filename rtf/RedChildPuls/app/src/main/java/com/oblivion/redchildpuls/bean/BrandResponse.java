package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by qinyin on 2016/12/8.
 */
public class BrandResponse implements IResponse{


    /**
     * brand : [{"id":1,"key":"孕妈专区","value":[{"id":1218,"name":"雅培","pic":"/images/brand/c.png"},{"id":1219,"name":"贝因美","pic":"/images/brand/d.png"},{"id":1220,"name":"周生生","pic":"/images/brand/a.png"},{"id":1221,"name":"婴姿坊","pic":"/images/brand/e.png"}]},{"id":2,"key":"营养食品","value":[{"id":1202,"name":"咪咪","pic":"/images/brand/b.png"}]},{"id":3,"key":"宝宝用品","value":[{"id":1209,"name":"好奇","pic":"/images/brand/d.png"},{"id":1210,"name":"快乐宝贝","pic":"/images/brand/e.png"},{"id":1211,"name":"环球娃娃","pic":"/images/brand/f.png"},{"id":1212,"name":"Kiddy","pic":"/images/brand/a.png"}]},{"id":4,"key":"儿童服饰","value":[]},{"id":5,"key":"时尚女装","value":[{"id":1206,"name":"Nutrilon","pic":"/images/brand/a.png"},{"id":1207,"name":"Hero","pic":"/images/brand/b.png"},{"id":1208,"name":"Goo.N","pic":"/images/brand/c.png"},{"id":1213,"name":"hogar","pic":"/images/brand/b.png"},{"id":1214,"name":"奇妮孕妇装","pic":"/images/brand/c.png"},{"id":1215,"name":"Bio-oil","pic":"/images/brand/d.png"},{"id":1216,"name":"莫施","pic":"/images/brand/e.png"},{"id":1217,"name":"IMG","pic":"/images/brand/f.png"}]},{"id":6,"key":"成人用品","value":[]},{"id":7,"key":"日常用品","value":[{"id":1201,"name":"爱家","pic":"/images/brand/1378_1333165554_8.png"}]},{"id":8,"key":"化妆品","value":[]},{"id":9,"key":"清洁用品","value":[{"id":1203,"name":"防辐射","pic":"/images/brand/c.png"},{"id":1205,"name":"枕工坊","pic":"/images/brand/d.png"},{"id":1222,"name":"飞利浦","pic":"/images/brand/flp.jpg"}]}]
     * response : brand
     */

    public String response;
    /**
     * id : 1
     * key : 孕妈专区
     * value : [{"id":1218,"name":"雅培","pic":"/images/brand/c.png"},{"id":1219,"name":"贝因美","pic":"/images/brand/d.png"},{"id":1220,"name":"周生生","pic":"/images/brand/a.png"},{"id":1221,"name":"婴姿坊","pic":"/images/brand/e.png"}]
     */

    public List<BrandBean> brand;

    public static class BrandBean {
        public int id;
        public String key;
        /**
         * id : 1218
         * name : 雅培
         * pic : /images/brand/c.png
         */

        public List<ValueBean> value;

        public static class ValueBean {
            public int id;
            public String name;
            public String pic;
        }
    }
}
