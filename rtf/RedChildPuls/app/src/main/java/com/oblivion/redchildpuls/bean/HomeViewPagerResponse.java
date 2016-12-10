package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by arkin on 2016/12/4.
 */
public class HomeViewPagerResponse implements IResponse {

    /**
     * homeTopic : [{"id":130,"pic":"/images/home/topic1.jpg","title":"活动"},{"id":131,"pic":"/images/home/topic2.jpg","title":"活动"},{"id":133,"pic":"/images/home/topic4.jpg","title":"活动"},{"id":136,"pic":"/images/home/topic7.jpg","title":"活动"},{"id":139,"pic":"/images/home/topic11.png","title":"活动"},{"id":140,"pic":"/images/home/topic14.jpg","title":"活动"}]
     * response : home
     */

    private String response;
    /**
     * id : 130
     * pic : /images/home/topic1.jpg
     * title : 活动
     */
    private List<HomeTopicBean> homeTopic;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<HomeTopicBean> getHomeTopic() {
        return homeTopic;
    }

    public void setHomeTopic(List<HomeTopicBean> homeTopic) {
        this.homeTopic = homeTopic;
    }



    public static class HomeTopicBean {
        private int id;
        private String pic;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
