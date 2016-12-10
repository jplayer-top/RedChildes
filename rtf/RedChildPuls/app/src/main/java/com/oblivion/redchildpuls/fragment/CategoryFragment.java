package com.oblivion.redchildpuls.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.base.BaseFragment;
import com.oblivion.redchildpuls.bean.CatetoryResponse;
import com.oblivion.redchildpuls.zxq.view.SearchResultActivity;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * by qinyin
 * date 2016/12/6
 */
public class CategoryFragment extends BaseFragment implements HttpLoader.HttpListener {


    private static final String TAG = "CategoryFragment";
    /**
     * 一级分类
     */
    private ListView lv1;
    /**
     * 二级分类
     */
    private ListView lv2;
    /*
    三级分类
     */
    private ListView lv3;
    /**
     * 数据bean
     */
    private List<CatetoryResponse.CategoryBean> categoryDataBeanList;
    /**
     * 一级分类页
     */
    private List<CatetoryResponse.CategoryBean> firstData;
    /**
     * 二级分类页
     */
    private List<CatetoryResponse.CategoryBean> secondData;
    /**
     * 三级分类页
     */
    private List<CatetoryResponse.CategoryBean> threeData = new ArrayList<>();
    private IResponse x;
    private FirstCategoryAdapter firstCategoryAdapter;
    private int currItem;
    private int currItem2;
    private Button btn_back;
    private SecondCategoryAdapter secondCategoryAdapter;
    private RelativeLayout kong;

    @Override
    public View initView() {
        final View view = View.inflate(MyApplication.mContext, R.layout.category_froment, null);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        kong = (RelativeLayout) view.findViewById(R.id.kong);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MyApplication.mContext,"dianle",Toast.LENGTH_SHORT).show();
                if (lv3.getVisibility() == View.VISIBLE) {
                    lv3.setVisibility(View.GONE);
                    lv1.setVisibility(View.GONE);
                    lv2.setVisibility(View.VISIBLE);
                } else if (lv2.getVisibility() == View.VISIBLE) {
                    lv1.setVisibility(View.VISIBLE);
                    lv2.setVisibility(View.GONE);
                    lv3.setVisibility(View.GONE);
                    goneBack();
                } else if (lv1.getVisibility() == View.VISIBLE) {
                    lv3.setVisibility(View.GONE);
                    lv2.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MyApplication.mContext, "错了", Toast.LENGTH_SHORT).show();
                }
                if (threeData.size()!=0&&kong.getVisibility()==View.VISIBLE){
                    kong.setVisibility(View.GONE);
                }else if (secondData.size()!=0&&kong.getVisibility()==View.VISIBLE){

                    kong.setVisibility(View.GONE);
                }else {
                    kong.setVisibility(View.GONE);

                }
                secondCategoryAdapter.notifyDataSetChanged();

            }
        });
        lv1 = (ListView) view.findViewById(R.id.lv1);
        lv2 = (ListView) view.findViewById(R.id.lv2);
        lv3 = (ListView) view.findViewById(R.id.lv3);
        getData();
        return view;
    }


    public void getData() {
        if (x != null) {
            System.out.println("缓存复用，防止空指针");
            categoryDataBeanList = ((CatetoryResponse) x).category;

        }
        //请求数据
        String url = RBConstants.URL_CATEGORY_LIST;
        HttpParams params = new HttpParams();
        MyApplication.HL.get(url, params, CatetoryResponse.class,
                RBConstants.REQUEST_CODE_CATEGORY_LIST, this);

    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == RBConstants.REQUEST_CODE_CATEGORY_LIST &&
                response instanceof CatetoryResponse) {
            if (categoryDataBeanList == null) {
                categoryDataBeanList = new ArrayList<>();

                categoryDataBeanList = ((CatetoryResponse) response).category;
            }
            x = response;
            System.out.println("----请求成功-----");
            firstData = new ArrayList<>();
            secondData = new ArrayList<>();
            for (int i = 0; i < categoryDataBeanList.size(); i++) {
                if (categoryDataBeanList.get(i).parentId == 0) {
                    firstData.add(categoryDataBeanList.get(i));
                }
            }

            firstCategoryAdapter = new FirstCategoryAdapter();
            lv1.setAdapter(firstCategoryAdapter);

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lv1.setVisibility(View.GONE);
                    lv2.setVisibility(View.VISIBLE);
                    lv3.setVisibility(View.GONE);
                    goneBack();


//                currItem=(position+1);
                    currItem = firstData.get(position).id;
                    secondData.removeAll(secondData);
                    System.out.println("点击位置=======" + position);
                    for (int i = 0; i < categoryDataBeanList.size(); i++) {
                        if (categoryDataBeanList.get(i).parentId == currItem) {
                            secondData.add(categoryDataBeanList.get(i));
                        }
                    }
                    if (secondData.size() == 0) {
                        kong.setVisibility(View.VISIBLE);
                        Toast.makeText(MyApplication.mContext, "服务器数据为空！", Toast.LENGTH_SHORT).show();
                    } else {
                        kong.setVisibility(View.GONE);
                    }
                }
            });

            secondCategoryAdapter = new SecondCategoryAdapter();
            lv2.setAdapter(secondCategoryAdapter);
            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    lv2.setVisibility(View.GONE);
                    lv3.setVisibility(View.VISIBLE);
                    lv1.setVisibility(View.GONE);
                    threeData.removeAll(threeData);
                    currItem2 = secondData.get(position).id;
                    Log.i(TAG, "onItemClick: =++++++++++++++++++" + categoryDataBeanList.size());
                    if (currItem2 != 0) {
                        threeData = new ArrayList<CatetoryResponse.CategoryBean>();
                        for (int i = 0; i < categoryDataBeanList.size(); i++) {
                            if (categoryDataBeanList.get(i).isLeafNode) {
                                if (categoryDataBeanList.get(i).parentId == currItem2) {
                                    threeData.add(categoryDataBeanList.get(i));
                                } else {
                                    Log.i(TAG, "onItemClick: 没有相关数据");
                                }
                            }
                        }
                        if (threeData.size() == 0) {
                            kong.setVisibility(View.VISIBLE);
                            Toast.makeText(MyApplication.mContext, "kong===", Toast.LENGTH_SHORT).show();
                        }else {
                            kong.setVisibility(View.GONE);
                        }
                    } else {
                        Log.i(TAG, "onItemClick: hehehehehehehehehehehe");
                    }
                }
            });
            ThreeCategoryAdapter threeCategoryAdapter = new ThreeCategoryAdapter();
            lv3.setAdapter(threeCategoryAdapter);
            lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lv1.setVisibility(View.GONE);
                    lv2.setVisibility(View.GONE);
                    lv3.setVisibility(View.VISIBLE);
// 跳转到搜索结果界面
                    Intent starter = new Intent(getActivity(), SearchResultActivity.class);
                    starter.putExtra(SearchResultActivity.KEY_REQUEST_CONTENT, threeData.get(position).id + "");
                    starter.putExtra(SearchResultActivity.KEY_REQUEST_CODE, SearchResultActivity.VALUE_PRODUCT);
                    getActivity().startActivity(starter);


                }
            });
        }
    }

    private void goneBack() {
        if (btn_back.getVisibility() == View.VISIBLE) {
            btn_back.setVisibility(View.GONE);
        } else {
            btn_back.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        System.out.println("错了   ");
    }


    /**
     * 一级分类适配器
     */
    class FirstCategoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return firstData.size();
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
            holder.category_title.setText(firstData.get(position).name);
            holder.category_tage.setText(firstData.get(position).tag);
            String picUrl = RBConstants.URL_SERVER + firstData.get(position).pic;
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

    /**
     * 二级分类适配器
     */

    class SecondCategoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return secondData.size();
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
            View view = View.inflate(parent.getContext(), R.layout.item_list_category2, null);
            TextView category_title = (TextView) view.findViewById(R.id.category_title);
            category_title.setText(secondData.get(position).name + "");
            return view;
        }
    }

    class ViewHold {
        ImageView category_icon;
        TextView category_title;
        TextView category_tage;
    }

    /**
     * 三级分类适配器
     */

    class ThreeCategoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return threeData.size();
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
            View view = View.inflate(parent.getContext(), R.layout.item_list_category2, null);
            TextView category_title = (TextView) view.findViewById(R.id.category_title);
            category_title.setText(threeData.get(position).name + "");
            return view;
        }
    }
}
