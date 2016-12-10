/*
package com.oblivion.redchildpuls.pager;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CatetoryResponse;
import com.oblivion.redchildpuls.factory.FragmentFactory;
import com.oblivion.redchildpuls.fragment.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * 一级分类页
 * Created by qinyin on 2016/12/4.
 *//*

public class FirstCategory extends BaseCategoryPager {
    private static final String TAG = "FirstCategory";

    public List<CatetoryResponse.CategoryBean> getmDatas() {
        return mDatas;
    }

    */
/**
     * 数据bean
     *//*

    private List<CatetoryResponse.CategoryBean> mDatas;
    private ArrayList<Integer> firstlist;


    */
/**
     * 点击的parentID
     *//*

    private int currItem;

    public int getCurrItem() {
        return currItem;
    }

    public FirstCategory(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        mDatas = categoryDataBeanList;
        firstlist = new ArrayList();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).parentId == 0) {
                firstlist.add(i);
            }
        }
        category_list_view.setAdapter(new FirstCategoryAdapter());
        */
/**
         * 点击事件
         *//*

        category_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onPageSelected: 被点击" + position);

                CategoryFragment categoryFragment = (CategoryFragment) FragmentFactory.getFragment(4);
                categoryFragment.getmCategoryViewPager().setCurrentItem(1);
                categoryFragment.getmCategoryViewPager().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        getData();
                        initData();
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                currItem = position + 1;
            }
        });
    }

    */
/**
     * 一级分类适配器
     *//*

    class FirstCategoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return firstlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHold holder;
            View view;
            if (convertView == null) {
                holder = new ViewHold();
                view = View.inflate(parent.getContext(), R.layout.item_list_category1, null);
                holder.category_icon = (ImageView) view.findViewById(R.id.category_icon);
                holder.category_title = (TextView) view.findViewById(R.id.category_title);
                holder.category_tage = (TextView) view.findViewById(R.id.category_tage);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHold) view.getTag();
            }
            holder.category_title.setText(mDatas.get(position).name);
            holder.category_tage.setText(mDatas.get(position).tag);
            String picUrl = RBConstants.URL_SERVER + mDatas.get(position).pic;
            Log.e(TAG, "图片地址：" + picUrl);
            MyApplication.IL.get(picUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    holder.category_icon.setImageBitmap(imageContainer.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(TAG, "加载错误");
                }
            });


            return view;
        }
    }

    class ViewHold {
        ImageView category_icon;
        TextView category_title;
        TextView category_tage;
    }
}
*/
