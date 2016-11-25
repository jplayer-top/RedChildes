package com.oblivion.rxjava.bean;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/11/22.
 */
public class HomeBeam {

    /**
     * error : 0
     * status : success
     * date : 2016-11-25
     * results : [{"currentCity":"嘉兴","pm25":"87","index":[{"title":"穿衣","zs":"冷","tipt":"穿衣指数","des":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"},{"title":"洗车","zs":"不宜","tipt":"洗车指数","des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"title":"旅游","zs":"一般","tipt":"旅游指数","des":"天气稍凉，风稍大会加大些凉意，且预报有降水，旅游指数一般，外出旅游请注意防风保暖并携带雨具。"},{"title":"感冒","zs":"易发","tipt":"感冒指数","des":"天冷风大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"},{"title":"运动","zs":"较不宜","tipt":"运动指数","des":"有降水，且风力较强，较适宜在户内进行各种健身休闲运动；若坚持户外运动，请注意保暖。"},{"title":"紫外线强度","zs":"最弱","tipt":"紫外线强度指数","des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}],"weather_data":[{"date":"周五 11月25日 (实时：8℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"小雨","wind":"北风微风","temperature":"10 ~ 8℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"小雨转阴","wind":"西北风4-5级","temperature":"9 ~ 3℃"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"多云转晴","wind":"西北风微风","temperature":"12 ~ 4℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"东北风微风","temperature":"14 ~ 7℃"}]}]
     */

    public int error;
    public String status;
    public String date;
    /**
     * currentCity : 嘉兴
     * pm25 : 87
     * index : [{"title":"穿衣","zs":"冷","tipt":"穿衣指数","des":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"},{"title":"洗车","zs":"不宜","tipt":"洗车指数","des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"title":"旅游","zs":"一般","tipt":"旅游指数","des":"天气稍凉，风稍大会加大些凉意，且预报有降水，旅游指数一般，外出旅游请注意防风保暖并携带雨具。"},{"title":"感冒","zs":"易发","tipt":"感冒指数","des":"天冷风大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"},{"title":"运动","zs":"较不宜","tipt":"运动指数","des":"有降水，且风力较强，较适宜在户内进行各种健身休闲运动；若坚持户外运动，请注意保暖。"},{"title":"紫外线强度","zs":"最弱","tipt":"紫外线强度指数","des":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}]
     * weather_data : [{"date":"周五 11月25日 (实时：8℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/xiaoyu.png","weather":"小雨","wind":"北风微风","temperature":"10 ~ 8℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"小雨转阴","wind":"西北风4-5级","temperature":"9 ~ 3℃"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"多云转晴","wind":"西北风微风","temperature":"12 ~ 4℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"东北风微风","temperature":"14 ~ 7℃"}]
     */

    public List<ResultsBean> results;

    public static class ResultsBean {
        public String currentCity;
        public String pm25;
        /**
         * title : 穿衣
         * zs : 冷
         * tipt : 穿衣指数
         * des : 天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。
         */

        public List<IndexBean> index;
        /**
         * date : 周五 11月25日 (实时：8℃)
         * dayPictureUrl : http://api.map.baidu.com/images/weather/day/xiaoyu.png
         * nightPictureUrl : http://api.map.baidu.com/images/weather/night/xiaoyu.png
         * weather : 小雨
         * wind : 北风微风
         * temperature : 10 ~ 8℃
         */

        public List<WeatherDataBean> weather_data;

        public static class IndexBean {
            public String title;
            public String zs;
            public String tipt;
            public String des;
        }

        public static class WeatherDataBean {
            public String date;
            public String dayPictureUrl;
            public String nightPictureUrl;
            public String weather;
            public String wind;
            public String temperature;
        }
    }
}
