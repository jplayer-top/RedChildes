package com.oblivion.redchildpuls.zxq.contract;

import com.oblivion.redchildpuls.bean.CategoryFilterBean;
import com.oblivion.redchildpuls.zxq.base.Contract;
import com.oblivion.redchildpuls.zxq.presenter.SearchResultPresenter;

import java.util.List;

/**
 * Created by arkin on 2016/12/7.
 */
public interface SearchResultContract extends Contract {
    interface Presenter extends Contract.BaseContractPresenter {
        /**
         * 加载 搜索数据
         *
         */
        void loadSearchData(String searchContent, int page, int pageNum, @SearchResultPresenter.OrderBy String orderby, String filter, int requestCode);


    }

    interface View extends Contract.BaseContractView {
        /**
         * 显示搜索结果
         */
        void displaySearchResult(List<CategoryFilterBean.ProductListBean> productListBeen);
        /**
         * 显示搜索结果为空的界面
         */
        void showEmptyView();
        /**
         * 显示异常界面
         */
        void showErrorView();
        /**
         * 显示加载界面
         */
        void showLoadingView();

        /**
         * 显示titlebar的内容
         * @param content
         */
        void initTitleBar(String content);

    }
}
