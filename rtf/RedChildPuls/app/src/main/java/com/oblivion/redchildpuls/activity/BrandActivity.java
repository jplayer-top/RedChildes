package com.oblivion.redchildpuls.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.base.MyAdapter;
import com.oblivion.redchildpuls.bean.BrandsBean;
import com.oblivion.redchildpuls.holder.BrandHolder;
import com.oblivion.redchildpuls.view.MyTitleBar;

import org.senydevpkg.base.BaseHolder;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrandActivity extends AppCompatActivity {

    private static final String TAG = "BrandActivity";
    @Bind(R.id.mtb_brand)
    MyTitleBar mtbBrand;
    @Bind(R.id.lv_brand)
    ListView lvBrand;

    private ArrayList<BrandsBean.BrandBean> brandBeanbeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        ButterKnife.bind(this);
        mtbBrand.addOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String url = RBConstants.URL_SERVER + "brand";
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "15");
        Class<? extends IResponse> clazz = BrandsBean.class;
        int requestCode = 10;
        MyApplication.HL.get(url, params, clazz, requestCode, new HttpLoader.HttpListener() {

            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {

                Log.e(TAG, "onGetResponseSuccess: "+"jiazaichenggong1111" );

                BrandsBean brandsBean = (BrandsBean) response;

                brandBeanbeanList = (ArrayList<BrandsBean.BrandBean>) brandsBean.brand;
                lvBrand.setAdapter(new BrandAdapter(brandBeanbeanList));
//                objectArrayList = new ArrayList<>();
//
//                for (BrandsBean.BrandBean brandBean : brandBeanbeanList) {
//                    myBrandbeenList = new ArrayList<>();
//
////                    MyBrandbean myBrandbean1 = new MyBrandbean();
////                    myBrandbean1.isTitel=true;
////                    myBrandbean1.species = key;
//
//                    for (BrandsBean.BrandBean.ValueBean valueBean : brandBean.value) {
//                        MyBrandbean myBrandbean = new MyBrandbean();
//                        myBrandbean.species = brandBean.key;
//                        myBrandbean.id = valueBean.id;
//                        myBrandbean.name = valueBean.name;
//                        myBrandbean.pic = valueBean.pic;
//                        myBrandbeenList.add(myBrandbean);
//                    }
//
//                    objectArrayList.add(myBrandbeenList);

//                }

            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                 Log.e(TAG, "onGetResponseError: "+"jiazaishibai" );
            }
        });


    }

    class BrandAdapter<T> extends MyAdapter {

        /**
         * 接收AbsListView要显示的数据
         *
         * @param data 要显示的数据
         */
        public BrandAdapter(List data) {
            super(data);
        }

        @Override
        public void loadMoreAndRefeshUi() {

        }

        @Override
        protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandHolder(MyApplication.mContext);
        }

        @Override
        public boolean hasLoadMore() {
            return false;
        }
    }
}
