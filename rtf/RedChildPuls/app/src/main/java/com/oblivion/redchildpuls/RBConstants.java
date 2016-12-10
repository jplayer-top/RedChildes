package com.oblivion.redchildpuls;

/**
 * 常量类
 * 用来保存一些几乎从不轻易改动的变量
 * <p/>
 * Created by xiongmc on 2016/4/28.
 */
public class RBConstants {

    public static final String URL_SERVER = "http://192.168.16.240:8080/RedBabyServer/";

    /**
     * 促销快报
     */
    public static final String URL_TOPIC = URL_SERVER + "topic";
    public static final int REQUEST_CODE_TOPIC = 1;

    /**
     * 购物车
     */
    public static final String URL_CART = URL_SERVER + "cart";
    public static final int REQUEST_CODE_CART = 2;

    /**
     * 结算中心
     */
    public static final String URL_CHECKOUT = URL_SERVER + "checkout";
    public static final int REQUEST_CODE_CHECKOUT = 3;

    /**
     * 登录
     */
    public static final String URL_LOGIN = URL_SERVER + "login";
    public static final int REQUEST_CODE_LOGIN = 4;

    /**
     * 登录
     */
    public static final String URL_ADDRESS_LIST = URL_SERVER + "addresslist";
    public static final int REQUEST_CODE_ADDRESS_LIST = 5;
    /**
     * 三级地址
     */
    public static final String URL_ADDRESS_AREA = URL_SERVER + "addressarea";
    public static final int REQUEST_CODE_ADDRESS_area = 99;
    /**
     * 注册 登录
     */
    public static final String URL_REGISTER = URL_SERVER + "register";
    public static final int REQUEST_CODE_REGISTER = 666;
    /**
     * 热门商品
     */
    public static final String URL_HOTPRODUCT = URL_SERVER + "hotproduct";
    public static final int REQUEST_CODE_HOTPRODUCT = 30;
    /**
     * 最新商品
     */
    public static final String URL_NEWPRODUCT = URL_SERVER + "newproduct";
    public static final int REQUEST_CODE_NEWPRODUCT = 31;
    /**
     * 订单列表
     */
    public static final String URL_ORDERLIST = URL_SERVER + "orderlist";
    public static final int REQUEST_CODE_ORDERLIST = 32;
    /**
     * 订单列表
     */
    public static final String URL_ORDERDDETAIL = URL_SERVER + "orderdetail";
    public static final int REQUEST_CODE_ORDERDETAIL = 33;
    /**
     * 取消订单
     */
    public static final String URL_ORDERCANCELL = URL_SERVER + "ordercancel";
    public static final int REQUEST_CODE_ORDERCANCELL = 34;
    /**
     * 商品详情&商品描述
     */
    public static final String URL_DATAIL = URL_SERVER + "product";
    public static final int REQUEST_CODE_DATAIL = 6;

    public static final String URL_DATAIL_PRO = URL_SERVER + "product/description";
    public static final int REQUEST_CODE_DATAILINFO = 7;


    /**
     * 分类
     */
    public static final String URL_CATEGORY_LIST = URL_SERVER + "category";
    public static final int REQUEST_CODE_CATEGORY_LIST = 88;


    /**
     * 首页轮播图地址
     * owner:zxq
     */
    public static final String URL_HOME_RECOMMEND = URL_SERVER + "home";
    public static final int REQUEST_CODE_HOME_RECOMMEND = 1 + 110;

    /**
     * 搜索页，热门搜索地址
     * owner:zxq
     */
    public static final String URL_SEARCH_RECOMMEND = URL_SERVER + "/search/recommend";
    public static final int REQUEST_CODE_SEARCH_RECOMMEND = 2 + 110;
    /**
     * 收藏
     */
    public static final String URL_COLLECT = URL_SERVER + "/favorites";
    public static final int REQUEST_CODE_COLLET = 98;
    /**
     * 品牌
     */
    public static final String URL_Brand = URL_SERVER + "brand";
    public static final int REQUEST_CODE_Brand = 89;

    /**
     * 搜索商品列表
     * owner:zxq
     */
    public static final String URL_SEARCH_PRODUCT = URL_SERVER + "search";
    public static final int REQUEST_CODE_SEARCH_PRODUCT = 3 + 110;
    /**
     * 添加到收藏列表
     */
    ///product/favorites
    public static final String URL_DETAIL_COLLECT = URL_SERVER + "/product/favorites";
    public static final int REQUEST_CODE_DETAIL_COLLECT = 87;

    /**
     * 商品列表页面
     * owner:zxq
     */
    public static final String URL_PRODUCT_LIST = URL_SERVER + "productlist";
    public static final int REQUEST_CODE_PRODUCT_LIST = 4 + 110;
    public static final int REQUEST_CODE_PRODUCT_FILTER = 5 + 110;

    /**
     * 结算订单
     */
    public static final String URL_DETAIL_ORDERSUMBIT = URL_SERVER + "/ordersumbit";
    public static final int REQUEST_CODE_DETAIL_ORDERSUMBIT = 1023;

    /**
     * 品牌商品列表
     * owner:zxq
     */
    public static final String URL_BRAND_PRODUCT_LIST = URL_SERVER + "brand/plist";
    public static final int REQUEST_CODE_BRAND_PRODUCT_LIST = 6 + 110;
}
