package com.oblivion.redchildpuls.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.adapter.VpAdapter;
import com.oblivion.redchildpuls.bean.DatailInfo;
import com.oblivion.redchildpuls.bean.DetailCollect;
import com.oblivion.redchildpuls.bean.DetailResponse;
import com.oblivion.redchildpuls.event.EventToCart;
import com.oblivion.redchildpuls.utils.FontDisplayUtil;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;

import org.greenrobot.eventbus.EventBus;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品详情页
 * by:李
 */

public class DetailActivity_ extends Activity implements View.OnClickListener, HttpLoader.HttpListener {
    private static final String TAG = "DetailActivity_";
    /**
     * 商品展示的ViewPager
     */
    @Bind(R.id.vp)
    ViewPager mVp;
    /**
     * 商品名称
     */
    @Bind(R.id.tv_goodsname)
    TextView mTvGoodsname;
    /**
     * 非会员的价格
     */
    @Bind(R.id.tv_feihuiyuanjiage)
    TextView mTvFeihuiyuanjiage;
    /**
     * 星级
     */
    @Bind(R.id.rb_score)
    RatingBar mRbScore;
    /**
     * 会员价格
     */
    @Bind(R.id.tv_huiyuanjiage)
    TextView mTvHuiyuanjiage;
    /**
     * 商品数量输入
     */
    @Bind(R.id.et_num)
    EditText mEtnum;
    /**
     * 商品属性选择
     */
    @Bind(R.id.sp1)
    Spinner mSp1;
    /**
     * 商品属性选择
     */
    @Bind(R.id.sp2)
    Spinner mSp2;
    /**
     * 商品属性标签1
     */
    @Bind(R.id.tv_pro1)
    TextView mTvPro1;
    /**
     * 商品属性标签2
     */
    @Bind(R.id.tv_pro2)
    TextView mTvPro2;
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.button2)
    Button mButton2;
    /**
     * 商品促销信息
     */
    @Bind(R.id.tv_cuxiaoxinxi)
    TextView mTvCuxiaoxinxi;
    @Bind(R.id.peisong)
    TextView mPeisong;
    @Bind(R.id.pinglun)
    TextView mPinglun;
    @Bind(R.id.miaoshutitle)
    TextView mMiaoshutitle;
    @Bind(R.id.miaoshu)
    TextView mMiaoshu;
    @Bind(R.id.ll_cont)
    LinearLayout mLlCont;
    /**
     * 通过网络获取到的轮播图
     */
    private int[] mImage;
    /**
     * 通过请求网络获取到的下拉列表数据
     */
    private List mData_list;
    private ArrayAdapter<String> sp_color_adapter;
    private List<String> mPro1;
    private List<String> mPro2;
    private List<String> mPics;
    public String x = "";
    private String mPid;
    private String mDatailcolor;
    private String mDatailsize;
    private int xiaoyuandian = 0;
    private DetailResponse mDetailResponse;
    private List<String> mSp_shuxing2;
    private List<String> mSp_shuxing1;
    private String mId1;
    //    private String mId2;
    private String mId2;
    private String mMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        //获取商品的id值用于网络请求
        mPid = getIntent().getStringExtra("pId");
        mMoney = getIntent().getStringExtra("money");
        //根据传入的pId打开对应的链接,并获取数据
        HttpParams p = new HttpParams().put("pId", mPid);
        initdata();
        initview();
        MyApplication.HL.get(RBConstants.URL_DATAIL, p, DetailResponse.class, RBConstants.REQUEST_CODE_DATAIL, this);
        mLlCont.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mPid != null) {
                    SystemClock.sleep(30);
                }
                mLlCont.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        HttpParams par = new HttpParams().put("pId", mPid);
        MyApplication.HL.get(RBConstants.URL_DATAIL_PRO, par, DatailInfo.class, RBConstants.REQUEST_CODE_DATAILINFO, this);
    }

    /**
     * 初始化布局
     */
    private void initview() {
        //以下为初始化Viewpager
        mVp.setPageMargin(10);
        mVp.setOffscreenPageLimit(3);
        mVp.setPageTransformer(true, new RotateDownPageTransformer());
        //下拉菜单的选择事件
        mSp1.setOnItemSelectedListener(new spitemselect(mSp1));
        mSp2.setOnItemSelectedListener(new spitemselect(mSp2));
        mButton.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }

    /**
     * @param context 调用者上下文
     * @param pid     商品的id值
     */
    public static void activitystart(Context context, String pid) {
        Intent i = new Intent(context, DetailActivity_.class);
        i.putExtra("pId", pid);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * @param context 调用者上下文
     * @param pid     商品的id值
     * @param money   折后价
     */
    public static void activitystart(Context context, String pid, String money) {
        Intent i = new Intent(context, DetailActivity_.class);
        i.putExtra("pId", pid);
        i.putExtra("money", money);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 初始化相关数据
     */
    private void initdata() {
//        mLlCont.getChildAt(1).setSelected(true);
        xiaoyuandian = 0;
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mPics.size(); i++) {
                    mLlCont.getChildAt(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 加入购物车和收藏按钮的单击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //发送购物车数据
                String y = "|" + mDetailResponse.product.id + ":" + mEtnum.getText() + ":" + mId1 + "," + mId2;
                MyApplication.stringBuffer.append(y);
                Log.i(TAG, "onClick: ++++-----" + y);
                EventBus.getDefault().postSticky(new EventToCart(y));
                Toast.makeText(this, "商品添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                HttpParams p = new HttpParams().put("pId", mPid).addHeader("userid", "" + MyApplication.userId);
                MyApplication.HL.get(RBConstants.URL_DETAIL_COLLECT, p, DetailCollect.class, RBConstants.REQUEST_CODE_DETAIL_COLLECT, this);
                break;
        }
    }


    /**
     * 网络请求成功
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

        switch (requestCode) {
            //收藏
            case RBConstants.REQUEST_CODE_DETAIL_COLLECT:
                Toast.makeText(this, "商品收藏成功", Toast.LENGTH_SHORT).show();
                break;
            //商品详情
            case RBConstants.REQUEST_CODE_DATAIL:
                mDetailResponse = (DetailResponse) response;
                //商品名
                mTvGoodsname.setText(mDetailResponse.product.name);
                //非会员
                mTvFeihuiyuanjiage.setText(mDetailResponse.product.marketPrice);
                mTvFeihuiyuanjiage.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                //星级
                mRbScore.setMax(5);
                mRbScore.setRating((int) mDetailResponse.product.score);
                //会员价格
                if (mMoney == null) {
                    Log.i(TAG, "onGetResponseSuccess: 未使用限时抢购");
                    mTvHuiyuanjiage.setText(mDetailResponse.product.price);
                } else {
                    Log.i(TAG, "onGetResponseSuccess: 使用限时抢购");
                    mTvHuiyuanjiage.setText(mMoney);
                }
                //设置商品的属性值
                setspinnerinfo(mDetailResponse);
                setSpinneradapter(mSp1, mPro1);
                setSpinneradapter(mSp2, mPro2);
                //配送说明
                mPeisong.setText(mDetailResponse.product.inventoryArea + "仓(有货)");
                //用户说明
                mPinglun.setText(mDetailResponse.product.commentCount + "条评价");
                //促销信息
                //小图
                mPics = mDetailResponse.product.pics;
                Log.i(TAG, "onGetResponseSuccess: " + mPics.size());
                mVp.setAdapter(new VpAdapter(this, mPics, mDetailResponse.product.name));
                //详情
                mMiaoshutitle.setText(mDetailResponse.product.name);
                //初始化ViewPager下的小圆点
                xiaodiandian();
                break;
            case RBConstants.REQUEST_CODE_DATAILINFO:
                DatailInfo infox = (DatailInfo) response;
                if (infox.productdesc == null) {
                    x = "该商品在服务器中不存在详细信息";
                } else {
                    x = infox.productdesc;
                }
                mMiaoshu.setText(x);
                Log.i(TAG, "onGetResponseSuccess: " + infox.productdesc);
                Log.i(TAG, "onGetResponseSuccess:商品详情访问网络成功");
                break;
            default:
                break;
        }
    }

    /**
     * 根据页面图片的数量动态添加小圆点
     */
    private void xiaodiandian() {
        Log.i(TAG, "xiaodiandian: !@#$%^&*(");
        //初始化ViewPager底部的小圆点
        if (xiaoyuandian == 0) {

            for (int i = 0; i < mPics.size(); i++) {
                //mLlCont.getChildAt(0).setSelected(true);
                ImageView iv = new ImageView(this);
                //iv.setImageResource(R.drawable.diandian);
                iv.setBackgroundResource(R.drawable.diandian);
                if (i != 0) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FontDisplayUtil.dip2px(this, 10), FontDisplayUtil.dip2px(this, 10));
                    params.leftMargin = FontDisplayUtil.dip2px(this, 5);
                    iv.setPadding(FontDisplayUtil.dip2px(this, 10), 0, 0, 0);
                    iv.setLayoutParams(params);
                } else {
                    iv.setSelected(true);
                }
                mLlCont.addView(iv);
            }
            xiaoyuandian++;
        } else {
            return;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    /**
     * 设置下拉列表的信息分类
     *
     * @param detailResponse
     */
    private void setspinnerinfo(DetailResponse detailResponse) {
        mPro1 = new ArrayList<>();
        mPro2 = new ArrayList<>();
        String x;
        if (detailResponse.product.productProperty.size() != 0) {
            x = detailResponse.product.productProperty.get(0).k;
            Log.i(TAG, "onGetResponseSuccess: ---" + x);
            mSp_shuxing1 = new ArrayList();
            mSp_shuxing2 = new ArrayList();
            for (int i = 0; i < detailResponse.product.productProperty.size(); i++) {
                Log.i(TAG, "onGetResponseSuccess: " + detailResponse.product.productProperty.get(i).k);
                if (detailResponse.product.productProperty.get(i).k.equals(detailResponse.product.productProperty.get(0).k)) {
                    mPro1.add(detailResponse.product.productProperty.get(i).v);
                    mSp_shuxing1.add(detailResponse.product.productProperty.get(i).id);
                    Log.i(TAG, "setspinnerinfo: mpro1" + mPro1.size() + "__________" + mSp_shuxing1.size());
                    //设置对应标签的文本
                    mTvPro1.setText(detailResponse.product.productProperty.get(i).k);
                } else {
                    mPro2.add(detailResponse.product.productProperty.get(i).v);
                    mSp_shuxing2.add(detailResponse.product.productProperty.get(i).id);
                    //设置对应标签的文本
                    mTvPro2.setText(detailResponse.product.productProperty.get(i).k);
                }
            }
            //当只有一种属性的时候隐藏第二个属性的下拉列表
            if (mPro2.size() == 0) {
                mSp2.setVisibility(View.GONE);
                //隐藏对应的属性的标签
                mTvPro2.setVisibility(View.GONE);
            }
        } else {
            //当没有属性的时候隐藏所有的属性的下拉列表
            mSp1.setVisibility(View.GONE);
            mSp2.setVisibility(View.GONE);
            //隐藏对应的属性的标签
            mTvPro1.setVisibility(View.GONE);
            mTvPro2.setVisibility(View.GONE);
        }
    }

    /**
     * 下拉列表的适配器
     *
     * @param sp1
     * @param pro1
     */
    private void setSpinneradapter(Spinner sp1, List<String> pro1) {
        sp_color_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pro1);

        //设置样式
        sp_color_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp1.setAdapter(sp_color_adapter);
    }

    /**
     * 网络访问失败
     *
     * @param requestCode 请求码
     * @param error       异常详情
     */
    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        switch (requestCode) {
            case RBConstants.REQUEST_CODE_DATAIL:
                Toast.makeText(this, "网络访问失败,请刷新重试", Toast.LENGTH_SHORT).show();
                break;
            case RBConstants.REQUEST_CODE_DETAIL_COLLECT:
                Toast.makeText(this, "添加到收藏夹失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    /**
     * 下拉菜单选择器
     */
    class spitemselect implements AdapterView.OnItemSelectedListener {
        //用于区分当前使用的是哪一个下拉菜单
        View v;

        public spitemselect(Spinner sp) {
            v = sp;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (v.getId()) {
                case R.id.sp1:
                    Log.i(TAG, "onItemSelected: " + mPro1.get(position));
                    mDatailcolor = mPro1.get(position);
                    mId1 = mSp_shuxing1.get(position);
                    break;
                case R.id.sp2:
                    Log.i(TAG, "onItemSelected: " + mPro2.get(position));
                    mDatailsize = mPro2.get(position);
                    mId2 = mSp_shuxing2.get(position);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(DetailActivity_.this, "网络请求失败,请刷新重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 当页面不可见直接销毁
     * 防止出现商品简介错乱的BUG
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
