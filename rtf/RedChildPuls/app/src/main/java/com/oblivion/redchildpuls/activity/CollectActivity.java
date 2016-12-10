package com.oblivion.redchildpuls.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CollectBean;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxgl on 2016/12/7.
 */

public class CollectActivity extends Activity implements HttpLoader.HttpListener {
    @Bind(R.id.lv)
    ListView mLv;
    private static final String TAG = "CollectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        HttpParams p = new HttpParams().put("page", "0").put("pageNum", "10").addHeader("userid", "" + MyApplication.userId);
        Log.i(TAG, "onCreate: __________________________________________________" + MyApplication.userId);
        MyApplication.HL.get(RBConstants.URL_COLLECT, p, CollectBean.class, 98, this);

    }


    /**
     * 当网络访问成功
     *
     * @param requestCode response对应的requestCode
     * @param response    返回的response
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

        final CollectBean bean = (CollectBean) response;
        if (bean.productList.size() == 0) {
            Toast.makeText(this, "您未在收藏夹内添加任何内容,页面不能被打开", Toast.LENGTH_SHORT).show();
            SystemClock.sleep(20);
            finish();
        }
        Log.i(TAG, "onGetResponseSuccess: " + bean.toString());
        Log.i(TAG, "onGetResponseSuccess: " + bean.productList.size());
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                Intent intent=new Intent(CollectActivity.this,DetailActivity_.class);
                intent.putExtra("pId",bean.productList.get(position).id+"");
                startActivity(intent);*/
                DetailActivity_.activitystart(CollectActivity.this, bean.productList.get(position).id + "");

            }
        });
        mLv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return bean.productList.size();
            }

            @Override
            public Object getItem(int position) {
                return new CollectBean().productList;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                viewholder v = new viewholder();
                View view;
                if (convertView == null) {
                    convertView = View.inflate(MyApplication.mContext, R.layout.item_collect, null);
                    v.iv = (ImageView) convertView.findViewById(R.id.iv);
                    v.tv_feihuiyuan = (TextView) convertView.findViewById(R.id.feihuiyuan);
                    v.tv_huiyuan = (TextView) convertView.findViewById(R.id.tv_huiyuan);
                    v.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                    v.tv_youhui = (TextView) convertView.findViewById(R.id.tv_cuxiaoxinxi);
                    convertView.setTag(v);
                } else {
                    //view=convertView;
                    v = (viewholder) convertView.getTag();
                }
                String picUrl = RBConstants.URL_SERVER + bean.productList.get(position).pic;
                Log.e(TAG, "图片地址：" + picUrl);
                final viewholder finalV = v;
                MyApplication.IL.get(picUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        finalV.iv.setImageBitmap(imageContainer.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, "加载错误");
                    }
                });
                System.out.println("markprice" + bean.productList.get(position).marketPrice);
                v.tv_feihuiyuan.setText("￥" + bean.productList.get(position).marketPrice + ".00");
                v.tv_feihuiyuan.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
                v.tv_huiyuan.setText("￥" + bean.productList.get(position).price + ".00");
                v.tv_name.setText(bean.productList.get(position).name + "");


                return convertView;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * 当网络访问失败
     *
     * @param requestCode 请求码
     * @param error       异常详情
     */
    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    class viewholder {
        TextView tv_name;
        TextView tv_huiyuan;
        TextView tv_feihuiyuan;
        TextView tv_youhui;
        ImageView iv;
    }
}
