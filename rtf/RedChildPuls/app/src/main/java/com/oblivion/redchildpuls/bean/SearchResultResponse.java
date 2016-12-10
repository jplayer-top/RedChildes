package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by arkin on 2016/12/6.
 */
public class SearchResultResponse implements IResponse{
    /**
     * productList : [{"id":30,"marketPrice":200,"name":"超凡奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":31,"marketPrice":260,"name":"天籁牧羊奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":32,"marketPrice":300,"name":"fullcare奶粉","pic":"/images/product/detail/q26.jpg","price":300},{"id":33,"marketPrice":300,"name":"雀巢奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":34,"marketPrice":200,"name":"安婴儿奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":35,"marketPrice":200,"name":"贝贝羊金装奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":36,"marketPrice":200,"name":"飞雀奶粉","pic":"/images/product/detail/q26.jpg","price":160}]
     * response : search
     */

    private String response;
    /**
     * id : 30
     * marketPrice : 200
     * name : 超凡奶粉
     * pic : /images/product/detail/q26.jpg
     * price : 160
     */

    private List<ProductListBean> productList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        private int id;
        private int marketPrice;
        private String name;
        private String pic;
        private int price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
