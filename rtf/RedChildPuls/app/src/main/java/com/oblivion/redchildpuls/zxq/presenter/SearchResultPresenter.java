package com.oblivion.redchildpuls.zxq.presenter;

import android.support.annotation.StringDef;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CategoryFilterBean;
import com.oblivion.redchildpuls.zxq.base.BasePresenter;
import com.oblivion.redchildpuls.zxq.contract.SearchResultContract;
import com.oblivion.redchildpuls.zxq.view.SearchResultActivity;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by arkin on 2016/12/7.
 */
public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter, HttpLoader.HttpListener {
    /**
     * 销量降序
     */
    public static final String SALE_DOWN = "saleDown";
    /**
     * 价格升序
     */
    public static final String PRICE_UP = "priceUp";
    /**
     * 价格降序
     */
    public static final String PRICE_DOWN = "priceDown";
    /**
     * 上架降序
     */
    public static final String SHELVES_DOWN = "shelvesDown";
    /**
     * 评价降序
     */
    public static final String COMMENT_DOWN = "commentDown";

    public SearchResultPresenter(SearchResultContract.View view) {
        super(view);
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        List<CategoryFilterBean.ProductListBean> productList = null;

        switch (requestCode) {
            case RBConstants.REQUEST_CODE_SEARCH_PRODUCT:
                productList = ((CategoryFilterBean) response).getProductList();
                if (productList== null || productList.size() == 0) {// 查询结果为空，展示结果为空的界面
                    view.showEmptyView();
                    view.initTitleBar("搜索结果");
                } else {// 有查询结果
                    view.displaySearchResult(productList);
                    view.initTitleBar("搜索结果（"+productList.size()+"条)");
                }
                break;
            case RBConstants.REQUEST_CODE_PRODUCT_LIST:
                productList = ((CategoryFilterBean) response).getProductList();
                if (productList== null || productList.size() == 0) {// 查询结果为空，展示结果为空的界面
                    view.showEmptyView();
                    view.initTitleBar("商品列表");
                } else {// 有查询结果
                    view.displaySearchResult(productList);
                    view.initTitleBar("商品列表（"+productList.size()+"条)");
                }
                break;
            case RBConstants.REQUEST_CODE_PRODUCT_FILTER:
                productList = ((CategoryFilterBean) response).getProductList();
                if (productList== null || productList.size() == 0) {// 查询结果为空，展示结果为空的界面
                    view.showEmptyView();
                    view.initTitleBar("过滤结果");
                } else {// 有查询结果
                    view.displaySearchResult(productList);
                    view.initTitleBar("过滤结果（"+productList.size()+"条)");
                }
                break;
            case RBConstants.REQUEST_CODE_BRAND_PRODUCT_LIST:
                productList = ((CategoryFilterBean) response).getProductList();
                if (productList== null || productList.size() == 0) {// 查询结果为空，展示结果为空的界面
                    view.showEmptyView();
                    view.initTitleBar("品牌商品列表");
                } else {// 有查询结果
                    view.displaySearchResult(productList);
                    view.initTitleBar("品牌商品列表（"+productList.size()+"条)");
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        view.showErrorView();
        view.initTitleBar("请求失败");
    }

    @StringDef({SALE_DOWN, PRICE_UP, PRICE_DOWN, SHELVES_DOWN, COMMENT_DOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrderBy{}

    public static HttpParams createSearchParams(String searchContent, int page, @OrderBy String orderby) {
        return createSearchParams(searchContent, page, 10, orderby);
    }

    public static HttpParams createSearchParams(String searchContent, int page, int pageNum, @OrderBy String orderby) {
        return new HttpParams().put("keyword", searchContent).put("page", page+"").put("pageNum", pageNum+"").put("orderby", orderby);
    }

    public static HttpParams createProductParams(String cId, int page, int pageNum, @OrderBy String orderby, String filter) {
        return new HttpParams().put("cId", cId).put("page", page+"").put("pageNum", pageNum+"").put("orderby", orderby).put("filter",filter);
    }

    public static HttpParams createBrandParams(String id, int page, int pageNum, @OrderBy String orderby) {
        return new HttpParams().put("id", id).put("page", page+"").put("pageNum", pageNum+"").put("orderby", orderby);
    }

    @Override
    public void loadSearchData(String searchContent, int page, int pageNum, @SearchResultPresenter.OrderBy String orderby, String filter, int requestCode) {
        view.showLoadingView();
        view.initTitleBar("");

        HttpParams params = null;
        switch (requestCode) {
            case SearchResultActivity.VALUE_SEARCH:
                params = createSearchParams(searchContent, page, orderby);
                MyApplication.HL.get(RBConstants.URL_SEARCH_PRODUCT, params, CategoryFilterBean.class, RBConstants.REQUEST_CODE_SEARCH_PRODUCT, this);
                break;
            case SearchResultActivity.VALUE_PRODUCT:
                params = createProductParams(searchContent, page, pageNum, orderby, filter);
                MyApplication.HL.get(RBConstants.URL_PRODUCT_LIST, params, CategoryFilterBean.class, RBConstants.REQUEST_CODE_PRODUCT_LIST, this);
                break;
            case SearchResultActivity.VALUE_FILTER:
                params = createProductParams(searchContent, page, pageNum, orderby, filter);
                MyApplication.HL.get(RBConstants.URL_PRODUCT_LIST, params, CategoryFilterBean.class, RBConstants.REQUEST_CODE_PRODUCT_FILTER, this);
                break;
            case SearchResultActivity.VALUE_BRAND:
                params = createBrandParams(searchContent, page, pageNum, orderby);
                MyApplication.HL.get(RBConstants.URL_BRAND_PRODUCT_LIST, params, CategoryFilterBean.class, RBConstants.REQUEST_CODE_BRAND_PRODUCT_LIST, this);
                break;
            default:
                break;
        }
    }
}
