package com.oblivion.redchildpuls.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.base.BaseFragment;
import com.oblivion.redchildpuls.bean.SearchRecommendResponse;
import com.oblivion.redchildpuls.db.ProviderSearchHistory;
import com.oblivion.redchildpuls.db.TableConstants;
import com.oblivion.redchildpuls.zxq.view.SearchResultActivity;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;
import org.senydevpkg.utils.MyToast;

import java.util.List;


/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class SearchFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mHomeSearchInput;
    private Button mHomeSearchBtn;
    private ListView mSearchLvRecommend;
    private ListView mSearchLvHistory;
    private Context mContext;
    List<String> mRecommmendDatas = null;
    private SimpleCursorAdapter mHistoryAdapter;
    public final int CURSOR_LOADER_ID = 111;

    @Override
    public View initView() {
        mContext = MyApplication.mContext;
        View rootView = View.inflate(mContext, R.layout.frag_search, null);
        mHomeSearchInput = (EditText) rootView.findViewById(R.id.home_search_input);
        mHomeSearchBtn = (Button) rootView.findViewById(R.id.home_search_btn);
        mSearchLvRecommend = (ListView) rootView.findViewById(R.id.search_lv_recommend);
        mSearchLvHistory = (ListView) rootView.findViewById(R.id.search_lv_history);

        // 获取“热门搜索”的数据
        MyApplication.HL.get(RBConstants.URL_SEARCH_RECOMMEND, null, SearchRecommendResponse.class, RBConstants.REQUEST_CODE_SEARCH_RECOMMEND, new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                if (requestCode != RBConstants.REQUEST_CODE_SEARCH_RECOMMEND) {
                    return;
                }
                mRecommmendDatas = ((SearchRecommendResponse) response).getSearchKeywords();
                final SearchListViewAdapter recommendAdapter = new SearchListViewAdapter(mRecommmendDatas);
                mSearchLvRecommend.setAdapter(recommendAdapter);
                // 设置“热门搜索”的点击监听事件
                mSearchLvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startSearch(recommendAdapter.getItem(position));
                    }
                });
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        });

        // 设置输入框enter键的监听
        mHomeSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mHomeSearchBtn.performClick();// 触发搜索键的点击事件，进行搜索
                    return true;
                }
                return false;
            }
        });

        // 初始化“搜索历史”ListView的适配器
        mHistoryAdapter = new SimpleCursorAdapter(
                mContext,
                R.layout.item_list_simple_search,
                null,
                new String[]{TableConstants.SEARCH_HISTORY},
                new int[]{R.id.search_tv_item},
                0);


        mSearchLvHistory.setAdapter(mHistoryAdapter);
        // 设置“搜索历史”的点击监听事件
        mSearchLvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    String searchInfo = ((TextView) view).getText().toString().trim();
                    startSearch(searchInfo);
                }
            }
        });


        // 添加搜索历史数据库查询任务
        LoaderManager lm = getLoaderManager();
        Bundle args = new Bundle();
        args.putInt("userId", MyApplication.userId);
        lm.initLoader(CURSOR_LOADER_ID, args, this);

        // 设置“搜索”按钮的点击事件
        mHomeSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchInfo = mHomeSearchInput.getText().toString().trim();
                mHomeSearchInput.setText("");//  复原输入框
                ALog.e("复原输入框");
                startSearch(searchInfo);
            }
        });

        return rootView;
    }

    /**
     * 进行搜索
     * @param searchInfo
     */
    public void startSearch(String searchInfo) {
        if (TextUtils.isEmpty(searchInfo)) {
            MyToast.show(mContext, "输入内容不能为空");
            return;// 如果输入的搜索信息为空，则不做任何处理
        }

        // 跳转到搜索结果界面
        Intent starter = new Intent(mContext, SearchResultActivity.class);
        starter.putExtra(SearchResultActivity.KEY_REQUEST_CONTENT, searchInfo);
        starter.putExtra(SearchResultActivity.KEY_REQUEST_CODE, SearchResultActivity.VALUE_SEARCH);
        getActivity().startActivity(starter);


        // 将搜索信息记录在本地
        int userid = MyApplication.userId;
        Uri uri = Uri.parse("content://com.oblivion.redchildpuls/" + ProviderSearchHistory.PATH_INSERT_SEARCH_HISTORY);
        ContentResolver resolver = mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(TableConstants.USER_ID, userid);
        values.put(TableConstants.SEARCH_HISTORY, searchInfo);
        values.put(TableConstants.SEARCH_DATE, System.currentTimeMillis());
        resolver.insert(uri, values);

        LoaderManager lm = getLoaderManager();
        Bundle args = new Bundle();
        args.putInt("userId", MyApplication.userId);
        lm.restartLoader(CURSOR_LOADER_ID, args, SearchFragment.this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int userId = args != null ? args.getInt("userId") : -1;
        Uri uri = Uri.parse("content://com.oblivion.redchildpuls/" + ProviderSearchHistory.PATH_GET_SEARCH_HISTORY + "/" + userId);
        return new CursorLoader(mContext, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mHistoryAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mHistoryAdapter.swapCursor(null);
    }

    class SearchListViewAdapter extends AbsBaseAdapter<String> {

        public SearchListViewAdapter(List<String> data) {
            super(data);
        }

        @Override
        protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BaseHolder<String> holder = new BaseHolder<String>(mContext) {
                private TextView tv;

                @Override
                protected View initView() {
                    View view = View.inflate(getContext(), R.layout.item_list_simple_search, null);
                    tv = (TextView) view.findViewById(R.id.search_tv_item);
                    return view;
                }

                @Override
                public void bindData(String data) {
                    tv.setText(data);
                }
            };
            return holder;
        }
    }
}
