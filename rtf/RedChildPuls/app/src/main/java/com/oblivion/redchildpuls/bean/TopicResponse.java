package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by yanjingpan on 2016/8/5.
 */
public class TopicResponse implements IResponse {

    /**
     * response : topic
     * topic : [{"id":2,"name":"儿童玩具聚划算","pic":"/images/topic/2.jpg"},{"id":3,"name":"尿不湿大酬宾","pic":"/images/topic/3.png"},{"id":4,"name":"美女","pic":"/images/topic/4.png"},{"id":5,"name":"蝴蝶","pic":"/images/topic/5.png"},{"id":6,"name":"黄昏下的城市","pic":"/images/topic/6.png"},{"id":7,"name":"激情冲浪","pic":"/images/topic/7.png"},{"id":8,"name":"润微","pic":"/images/topic/8.png"},{"id":9,"name":"美倪","pic":"/images/topic/9.png"}]
     */

    public String response;
    /**
     * id : 2
     * name : 儿童玩具聚划算
     * pic : /images/topic/2.jpg
     */

    public List<TopicBean> topic;

    public static class TopicBean {
        public int id;
        public String name;
        public String pic;
    }
}
