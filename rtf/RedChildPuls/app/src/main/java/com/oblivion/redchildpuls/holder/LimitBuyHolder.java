package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.activity.DetailActivity_;
import com.oblivion.redchildpuls.bean.LimitBuyBean;
import com.oblivion.redchildpuls.utils.PrefUtils;

import org.senydevpkg.base.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 周乐 on 2016/12/4.
 */
public class LimitBuyHolder extends BaseHolder<LimitBuyBean.ProductListBean> {

    private static final String TAG = "LimitBuyHolder";
    @Bind(R.id.ll_timeShopingItem)
    LinearLayout llTimeShopingItem;
    private ListView mlistView;

    @Bind(R.id.iv_timeShopingItem_icon)
    ImageView ivTimeShopingItemIcon;
    @Bind(R.id.tv_timeShopingItem_name)
    TextView tvTimeShopingItemName;
    @Bind(R.id.tv_timeShopingItem_price)
    TextView tvTimeShopingItemPrice;
    @Bind(R.id.tv_timeShopingItem_limitPrice)
    TextView tvTimeShopingItemLimitPrice;
    @Bind(R.id.tv_timeShopingItem_leftTime)
    TextView tvTimeShopingItemLeftTime;
    @Bind(R.id.ib_timeShopingItem_detial)
    ImageView ibTimeShopingItemDetial;
    @Bind(R.id.bt_timeShopingItem_goToBuy)
    Button btTimeShopingItemGoToBuy;

    private int curLeftTime;
    private String id;
    private String limitPrice;

    public LimitBuyHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(getContext(), R.layout.item_timeshoping, null);
        ButterKnife.bind(this, view);

//        llTimeShopingItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.mContext, "hehe"+id, Toast.LENGTH_SHORT).show();
//            }
//        });

        //立即抢购点击事件
        btTimeShopingItemGoToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MyApplication.mContext, "购买界面", Toast.LENGTH_SHORT).show();
                DetailActivity_.activitystart(MyApplication.mContext, id, limitPrice);
            }
        });
        return view;
    }

    @Override
    public void bindData(LimitBuyBean.ProductListBean data) {
        id = data.id;
        limitPrice = data.limitPrice;
        tvTimeShopingItemName.setText(data.name);
        tvTimeShopingItemPrice.setText("￥" + data.price);
        tvTimeShopingItemLimitPrice.setText("￥" + limitPrice);

        //不断刷新的剩余时间
        String leftTime = leftTime = data.leftTime;
        String startTimeMillis = PrefUtils.getString(MyApplication.mContext, "startTimeMillis", "");
        int timeGon = (int) ((System.currentTimeMillis() - Long.parseLong(startTimeMillis)) / 1000 + 0.5f);
        curLeftTime = Integer.parseInt(leftTime) - timeGon;
        if (curLeftTime == 0) {
            tvTimeShopingItemLeftTime.setText("该商品的促销活动结束");
            btTimeShopingItemGoToBuy.setVisibility(View.INVISIBLE);
        }
        //设置剩余时间控件
        Date d = new Date(Long.parseLong(curLeftTime + "000"));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(d);
        tvTimeShopingItemLeftTime.setText(time);

        //设置商品图片控件
        String url = RBConstants.URL_SERVER + data.pic;
        MyApplication.IL.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap bitmap = imageContainer.getBitmap();
                ivTimeShopingItemIcon.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }


}
