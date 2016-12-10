package com.oblivion.redchildpuls.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.view.LazyViewPager;
import com.oblivion.redchildpuls.view.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据指尖位置对图片进行双指放大
 */
public class ImagPagerUtil {
    private List<String> mPicList;
    private Activity mActivity;
    private Dialog dialog;
    private LazyViewPager mViewPager;
    private LinearLayout mLL_progress;
    private TextView tv_loadingmsg;
    private int screenWidth;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private TextView tv_img_current_index;
    private TextView tv_img_count;
    private TextView tv_content;

    public ImagPagerUtil(Activity activity, List<String> mPicList) {
        this.mPicList = mPicList;
        this.mActivity = activity;
        imageLoader = ImageLoader.getInstance();
        setOptions();
        init();
    }

    public ImagPagerUtil(Activity activity, String[] picarr) {
        mPicList = new ArrayList<>();
        for (int i = 0; i < picarr.length; i++) {
            mPicList.add(picarr[i]);
        }
        this.mActivity = activity;
        imageLoader = ImageLoader.getInstance();
        setOptions();
        init();
    }

    /**
     * 设置图片下方的文字
     *
     * @param str
     */
    public void setContentText(String str) {
        if (!TextUtils.isEmpty(str)) {
            tv_content.setText(str);
        }
    }

    public void show() {
        dialog.show();
    }

    private void init() {
        dialog = new Dialog(mActivity, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(mActivity, R.layout.view_dialogpager_img, null);
        mViewPager = getView(contentView, R.id.view_pager);
        mLL_progress = getView(contentView, R.id.vdi_ll_progress);
        tv_loadingmsg = getView(contentView, R.id.tv_loadingmsg);
        tv_img_current_index = getView(contentView, R.id.tv_img_current_index);
        tv_img_count = getView(contentView, R.id.tv_img_count);
        tv_content = getView(contentView, R.id.tv_content);
        dialog.setContentView(contentView);

        tv_img_count.setText(mPicList.size() + "");
        tv_img_current_index.setText("1");

        int size = mPicList.size();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ZoomImageView imageView = new ZoomImageView(mActivity);
        imageView.measure(0, 0);
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(screenWidth, display.getHeight());
        imageView.setLayoutParams(marginLayoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {//如果不需要点击图片关闭的需求，可以去掉这个点击事件
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        for (int i = 0; i < size; i++) {
            imageViews.add(imageView);
        }
        initViewPager(imageViews);
    }

    private void initViewPager(ArrayList<ImageView> list) {
        mViewPager.setOnPageChangeListener(new LazyViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tv_img_current_index.setText("" + (position + 1));
            }
        });

        MyImagPagerAdapter myImagPagerAdapter = new MyImagPagerAdapter(list);
        mViewPager.setAdapter(myImagPagerAdapter);
    }

    class MyImagPagerAdapter extends PagerAdapter {
        ArrayList<ImageView> mList;

        public MyImagPagerAdapter(ArrayList<ImageView> mList) {
            this.mList = mList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mList.get(position);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            showPic(imageView, mPicList.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public int getCount() {
            if (null == mList || mList.size() <= 0) {
                return 0;
            }
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    protected void setOptions() {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    private void showPic(final ImageView imageView, String url) {
        imageView.setImageBitmap(null);
//        mLL_progress.setVisibility(View.VISIBLE);
        System.out.println(url + "99999999999");
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        MyApplication.IL.get(RBConstants.URL_SERVER + url, new com.android.volley.toolbox.ImageLoader.ImageListener() {
            @Override
            public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer imageContainer, boolean b) {
                imageView.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        dialog.show();
    }

    private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            mLL_progress.setVisibility(View.GONE);
            tv_loadingmsg.setText("");
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(loadedImage);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static final <E extends View> E getView(View parent, int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("ImagPageUtil", "Could not cast View to concrete class \n" + ex.getMessage());
            throw ex;
        }
    }
}
