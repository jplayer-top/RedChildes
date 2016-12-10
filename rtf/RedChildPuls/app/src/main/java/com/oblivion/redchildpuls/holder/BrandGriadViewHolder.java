package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.BrandsBean;
import com.oblivion.redchildpuls.zxq.view.SearchResultActivity;

import org.senydevpkg.base.BaseHolder;

/**
 * Created by 周乐 on 2016/12/7.
 */
public class BrandGriadViewHolder extends BaseHolder<BrandsBean.BrandBean.ValueBean> {

    private ImageView iv;

    public BrandGriadViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        iv = new ImageView(MyApplication.mContext);
        return iv;
    }

    @Override
    public void bindData(final BrandsBean.BrandBean.ValueBean data) {

        String url = RBConstants.URL_SERVER + data.pic;
        MyApplication.IL.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap bitmap = imageContainer.getBitmap();
                iv.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        //品牌的点击跳转
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MyApplication.mContext,   "hahhaa", Toast.LENGTH_SHORT).show();
                Intent starter = new Intent(MyApplication.mContext, SearchResultActivity.class);
                starter.putExtra(SearchResultActivity.KEY_REQUEST_CONTENT, data.id);
                starter.putExtra(SearchResultActivity.KEY_REQUEST_CODE, SearchResultActivity.VALUE_BRAND);
                starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.mContext.startActivity(starter);
            }
        });
    }
}
