package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by 周乐 on 2016/12/8.
 */
public class CategoryFilterBean implements IResponse {
    /**
     * listCount : 8
     * listFilter : [{"key":"品牌","valueList":[{"id":"s9","name":"爱家"},{"id":"s10","name":"咪咪"},{"id":"s11","name":"防辐射"},{"id":"s12","name":"枕工坊"},{"id":"s13","name":"Nutrilon"},{"id":"s14","name":"Hero"},{"id":"s15","name":"Goo.N"},{"id":"s8","name":"好奇"},{"id":"s1","name":"快乐宝贝"},{"id":"s2","name":"环球娃娃"},{"id":"s3","name":"Kiddy"},{"id":"s4","name":"hogar"},{"id":"s5","name":"奇妮孕妇装"},{"id":"s6","name":"Bio-oil"},{"id":"s7","name":"莫施"},{"id":"s16","name":"IMG"},{"id":"s17","name":"雅培"},{"id":"s18","name":"贝因美"},{"id":"s19","name":"周生生"},{"id":"s20","name":"婴姿坊"},{"id":"s21","name":"飞利浦"}]},{"key":"价格","valueList":[{"id":"p1","name":"100"},{"id":"p2","name":"200"},{"id":"p3","name":"300"},{"id":"p4","name":"400"},{"id":"p5","name":"500"},{"id":"p6","name":"600"},{"id":"p7","name":"700"},{"id":"p8","name":"800"},{"id":"p9","name":"900"}]},{"key":"颜色","valueList":[{"id":"t1","name":"红色"},{"id":"t2","name":"粉色"},{"id":"t3","name":"黑色"},{"id":"t4","name":"深色"},{"id":"t5","name":"浅色"},{"id":"t6","name":"白色"}]}]
     * productList : [{"commentCount":10,"id":29,"marketPrice":180,"name":"日本奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"commentCount":10,"id":30,"marketPrice":200,"name":"超凡奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"commentCount":10,"id":31,"marketPrice":260,"name":"天籁牧羊奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"commentCount":10,"id":32,"marketPrice":300,"name":"fullcare奶粉","pic":"/images/product/detail/q26.jpg","price":300},{"commentCount":10,"id":33,"marketPrice":300,"name":"雀巢奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"commentCount":10,"id":34,"marketPrice":200,"name":"安婴儿奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"commentCount":10,"id":35,"marketPrice":200,"name":"贝贝羊金装奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"commentCount":10,"id":36,"marketPrice":200,"name":"飞雀奶粉","pic":"/images/product/detail/q26.jpg","price":160}]
     * response : categoryProductlist
     */

    public int listCount;
    public String response;
    public List<ListFilterBean> listFilter;
    public List<ProductListBean> productList;

    public static class ListFilterBean {
        /**
         * key : 品牌
         * valueList : [{"id":"s9","name":"爱家"},{"id":"s10","name":"咪咪"},{"id":"s11","name":"防辐射"},{"id":"s12","name":"枕工坊"},{"id":"s13","name":"Nutrilon"},{"id":"s14","name":"Hero"},{"id":"s15","name":"Goo.N"},{"id":"s8","name":"好奇"},{"id":"s1","name":"快乐宝贝"},{"id":"s2","name":"环球娃娃"},{"id":"s3","name":"Kiddy"},{"id":"s4","name":"hogar"},{"id":"s5","name":"奇妮孕妇装"},{"id":"s6","name":"Bio-oil"},{"id":"s7","name":"莫施"},{"id":"s16","name":"IMG"},{"id":"s17","name":"雅培"},{"id":"s18","name":"贝因美"},{"id":"s19","name":"周生生"},{"id":"s20","name":"婴姿坊"},{"id":"s21","name":"飞利浦"}]
         */

        public String key;
        public List<ValueListBean> valueList;

        public static class ValueListBean {
            /**
             * id : s9
             * name : 爱家
             */

            public String id;
            public String name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }

    public static class ProductListBean {
        /**
         * commentCount : 10
         * id : 29
         * marketPrice : 180
         * name : 日本奶粉
         * pic : /images/product/detail/q26.jpg
         * price : 160
         */

        public int commentCount;
        public int id;
        public int marketPrice;
        public String name;
        public String pic;

        public int getCommentCount() {
            return commentCount;
        }

        public int getId() {
            return id;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public String getName() {
            return name;
        }

        public String getPic() {
            return pic;
        }

        public int getPrice() {
            return price;
        }

        public int price;
    }

    public int getListCount() {
        return listCount;
    }

    public String getResponse() {
        return response;
    }

    public List<ListFilterBean> getListFilter() {
        return listFilter;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }
}
