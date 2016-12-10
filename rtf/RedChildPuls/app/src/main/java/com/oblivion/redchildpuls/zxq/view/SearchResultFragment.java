package com.oblivion.redchildpuls.zxq.view;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.activity.DetailActivity_;
import com.oblivion.redchildpuls.bean.CategoryFilterBean;
import com.oblivion.redchildpuls.zxq.base.BaseFragment;
import com.oblivion.redchildpuls.zxq.contract.SearchResultContract;
import com.oblivion.redchildpuls.zxq.presenter.SearchResultPresenter;

import org.senydevpkg.utils.ALog;

import java.util.List;

/**
 * Created by arkin on 2016/12/7.
 */
public class SearchResultFragment extends BaseFragment<SearchResultPresenter> implements SearchResultContract.View {

    private View emptyView;
    private ListView successView;
    private View errorView;
    private View loadingView;
    private String searchContent;
    private int page;
    private String orderby;
    private int pageNum;
    private String filter;
    private int requestCode;

    @Override
    protected void onInit() {
        super.onInit();
        int fragId = getArguments().getInt("fragId");
        if (fragId == 0) {
            searchContent = getArguments().getString("searchInfo");
            requestCode = getArguments().getInt("requestCode");
            presenter.loadSearchData(searchContent, 1, 10,SearchResultPresenter.SALE_DOWN, filter, requestCode);
        }
    }

    /**
     * 加载数据
     *
     * @param searchContent
     * @param page
     * @param orderby
     */
    public void loadData(String searchContent, int page, int pageNum, @SearchResultPresenter.OrderBy String orderby, String filter, int requestCode) {
        this.searchContent = searchContent;
        this.page = page;
        this.orderby = orderby;
        this.pageNum = pageNum;
        this.filter = filter;
        this.requestCode = requestCode;
        presenter.loadSearchData(searchContent, page, pageNum, orderby, filter, requestCode);
    }

    @Override
    protected SearchResultPresenter getPresenter() {
        return new SearchResultPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_searchresult;
    }

    @Override
    public void displaySearchResult(final List<CategoryFilterBean.ProductListBean> productListBeen) {
        ALog.e("展示成功页面");
        if (successView == null) {
            successView = new ListView(getContext());
            successView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            successView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int productId = productListBeen.get(position).getId();
                    DetailActivity_.activitystart(getActivity(), productId+"");
                }
            });
        }
        ProductAdapter adapter = new ProductAdapter(getContext(), productListBeen);

        successView.setAdapter(adapter);
        addChildView(successView);
    }

    @Override
    public void showEmptyView() {
        ALog.e("展示空页面");

        if (emptyView == null) {
            emptyView = View.inflate(getContext(), R.layout.search_empty_view, null);
        }
        addChildView(emptyView);
    }

    @Override
    public void showErrorView() {
        ALog.e("展示错误页面");

        if (errorView == null) {
            errorView = View.inflate(getContext(), R.layout.search_error_view, null);
            Button errorViewBtn = (Button) errorView.findViewById(R.id.error_view_btn);
            errorViewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {// 设置点击事件，
                    presenter.loadSearchData(searchContent, page, pageNum, orderby, filter, requestCode);
                }
            });
        }

        addChildView(errorView);
    }

    @Override
    public void showLoadingView() {
        ALog.e("展示加载页面");

        if (loadingView == null) {
            loadingView = View.inflate(getContext(), R.layout.search_loading_view, null);
        }
        addChildView(loadingView);
    }

    @Override
    public void initTitleBar(String content) {
        ((SearchResultActivity)getActivity()).initTitleBar(content);
    }

    /**
     * 获取该Fragment的根布局对象
     * @return
     */
    private FrameLayout getFragmentRootView() {
        return (FrameLayout) getRootView();
    }

    /**
     * 替换该Fragment的根布局的子控件
     * @param view
     */
    private void addChildView(View view) {
        FrameLayout rootView = getFragmentRootView();
        rootView.removeAllViews();// 先移除根部局所有的子view

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        rootView.addView(view);// 添加新的view
    }

    /**
     * successView的适配器
     */
    class ProductAdapter extends ArrayAdapter<CategoryFilterBean.ProductListBean> {

        public ProductAdapter(Context context, List<CategoryFilterBean.ProductListBean> objects) {
            super(context, R.layout.item_searchresult_product, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.item_searchresult_product, null);
                viewHolder.mSearchResultIvImg = (ImageView) convertView.findViewById(R.id.search_result_iv_img);
                viewHolder.mSearchresultTvTitle = (TextView) convertView.findViewById(R.id.searchresult_tv_title);
                viewHolder.mSearchresultTvPrice = (TextView) convertView.findViewById(R.id.searchresult_tv_price);
                viewHolder.mSearchresultTvMarketprice = (TextView) convertView.findViewById(R.id.searchresult_tv_marketprice);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final CategoryFilterBean.ProductListBean item = getItem(position);
            // 设置图片
            viewHolder.mSearchResultIvImg.setImageResource(R.drawable.product_loading);// 设置默认图片
            viewHolder.mSearchResultIvImg.setTag(item.getPic() + item.getId());// 记录当前图片的id
            MyApplication.IL.get(RBConstants.URL_SERVER + item.getPic(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    String url = item.getPic() + item.getId();
                    Object tag = viewHolder.mSearchResultIvImg.getTag();
                    if (tag != null && tag.equals(url)) {// 在设置图片前验证图片的id，放置发生错位
                        viewHolder.mSearchResultIvImg.setImageBitmap(imageContainer.getBitmap());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            viewHolder.mSearchresultTvMarketprice.setText("￥"+item.getMarketPrice());
            viewHolder.mSearchresultTvMarketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.mSearchresultTvPrice.setText("￥"+item.getPrice());
            viewHolder.mSearchresultTvTitle.setText(item.getName());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView mSearchResultIvImg;
        TextView mSearchresultTvTitle;
        TextView mSearchresultTvPrice;
        TextView mSearchresultTvMarketprice;
    }


}
