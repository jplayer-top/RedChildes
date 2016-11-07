package com.oblivion.zhbj.base.impl.newsdetial;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.oblivion.zhbj.R;
import com.oblivion.zhbj.base.BaseNewsCenterParger;
import com.oblivion.zhbj.domain.NewsPhotos;
import com.oblivion.zhbj.goble.Golbe;
import com.oblivion.zhbj.utils.bitmap.MyBitmapUtils;
import com.oblivion.zhbj.utils.PrefUtils;
import com.oblivion.zhbj.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by oblivion on 2016/11/1.
 */
public class MenuPicture extends BaseNewsCenterParger {

    private RecyclerView rv_photo;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    private NewsPhotos newsPhotos;
    private ArrayList<NewsPhotos.NewsData.NewsDetial> news;
    private GridLayoutManager gridLayoutManager;
    private boolean change = true;
    private OnItemClickListener onItemClickListener;

    public boolean getChange() {
        return change;
    }

    public MenuPicture(Activity activity) {
        super(activity);
        //initData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.photo_recycleview, null);
        //在哪导入数据是重要的
        initData();
        rv_photo = (RecyclerView) view.findViewById(R.id.rv_photo);
        //创建线性布局管理器
        linearLayoutManager = new LinearLayoutManager(mActivity);
        gridLayoutManager = new GridLayoutManager(mActivity, 2);
        //动态绑定布局
        changeState();
        return view;
    }

    /**
     * 动态的获取储存的boolean值，来决定他的布局
     */
    private void changeState() {
        if (PrefUtils.getBoolean(mActivity, "state", false)) {
            rv_photo.setLayoutManager(linearLayoutManager);
        } else {
            rv_photo.setLayoutManager(gridLayoutManager);
        }
    }

    /**
     * 点击事件监听接口
     */
    public interface OnItemClickListener {
        void OnItemClick();
    }

    /**
     * 点击事件监听方法
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * 调用实现布局状态发生改变
     */
    public void setChangeLayout() {
        changeState();
    }

    /**
     * 适配 类
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_recycle, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            view.setOnClickListener(this);//好像点击这里没有position
            viewHolder.getLayoutPosition();//这不是也可以获取到点击事件啊
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            View itemView = holder.itemView;//可以从这里获取到点击的是那个View,这里好啊，，有position
            int layoutPosition = holder.getLayoutPosition();//这是获取到真实的position
            //BitmapUtils bitmapUtils = new BitmapUtils(mActivity);
            // bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
            holder.tv_photo.setText(news.get(position).title);
            //----------自定义BitmapUtils是三级缓存-----------
            MyBitmapUtils bitmapUtils = new MyBitmapUtils(mActivity);
            bitmapUtils.display(holder.ivPhoto, news.get(position).listimage);
        }


        @Override
        public int getItemCount() {
            return news.size();
        }

        /**
         * 每个条目的点击事件,作用在view
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClick();
        }

        /**
         * 适配器内部ViewHolder
         */
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivPhoto;
            TextView tv_photo;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_photo = (TextView) itemView.findViewById(R.id.tv_photo_title);
                ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            }
        }

    }

    /**
     * 初始化数据，获取json
     */
    @Override
    public void initData() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, Golbe.PURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                progressData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 获取解析的数据
     *
     * @param result
     */
    private void progressData(String result) {
        Gson gson = new Gson();
        //获取到json数据,并将数据存到对象中
        newsPhotos = gson.fromJson(result, NewsPhotos.class);
        news = newsPhotos.data.news;
        System.out.println(news.get(0).title + "title");
        //创建适配器
        myAdapter = new MyAdapter();
        rv_photo.setAdapter(myAdapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick() {
                ToastUtils.setToast(mActivity, "条目被点击");
            }
        });
    }
}
