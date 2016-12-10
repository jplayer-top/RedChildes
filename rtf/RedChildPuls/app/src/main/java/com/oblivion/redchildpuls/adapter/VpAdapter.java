package com.oblivion.redchildpuls.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.activity.DetailActivity_;
import com.oblivion.redchildpuls.utils.ImagPagerUtil;

import java.util.List;

/**
 * Created by lxgl on 2016/12/6.
 */

public class VpAdapter extends PagerAdapter {
    private static final String TAG = "VpAdapter";

    private ImageView mIv_pic;
    private String datailname;
    private List<String> imagedata;
    private DetailActivity_ detail;

    public VpAdapter(DetailActivity_ detailActivity_, List<String> pics, String name) {
        imagedata = pics;
        datailname = name;
        detail = detailActivity_;
    }

    @Override
    public int getCount() {
        return imagedata.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.i(TAG, "instantiateItem: 成功");
        mIv_pic = new ImageView(MyApplication.mContext);
        String url = RBConstants.URL_SERVER + imagedata.get(position);
        Log.i(TAG, "instantiateItem: "+url);
        MyApplication.IL.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                mIv_pic.setImageBitmap(imageContainer.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        container.addView(mIv_pic);
        mIv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MyApplication.mContext, "这是第"+position, Toast.LENGTH_SHORT).show();
                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(detail,imagedata);
                imagPagerUtil.setContentText(datailname);
                imagPagerUtil.show();
            }
        });
        return mIv_pic;
    }
}
