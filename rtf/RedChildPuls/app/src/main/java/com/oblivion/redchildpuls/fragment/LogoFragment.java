package com.oblivion.redchildpuls.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.base.BaseFragment;
import com.oblivion.redchildpuls.bean.BrandResponse;
import com.oblivion.redchildpuls.zxq.view.SearchResultActivity;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class LogoFragment extends BaseFragment implements HttpLoader.HttpListener {

    private static final String TAG = "LogoFragment";
    /** brand的bean**/
    private BrandResponse brandBean;
    /**
     * 品牌展示的gridView
     */
    private GridView glv;
    /**
     * 品牌valuebean
     */
    private List<BrandResponse.BrandBean.ValueBean>  valueBeen;

    @Override
    public View initView() {
       View view =View.inflate(MyApplication.mContext, R.layout.brand_fragment,null);
        glv = (GridView) view.findViewById(R.id.gridview_brand);
        getData();
        return view;
    }

    public void  getData(){
        //请求数据
        String url = RBConstants.URL_Brand;
        HttpParams params = new HttpParams();
        MyApplication.HL.get(url, params, BrandResponse.class,
                RBConstants.REQUEST_CODE_Brand, this);
    }


    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode==RBConstants.REQUEST_CODE_Brand&&response instanceof BrandResponse){
            if (brandBean==null){
                brandBean=new BrandResponse();
                brandBean = (BrandResponse) response;
                Log.i(TAG, "品牌bean: " + brandBean.brand.size());
                Log.i(TAG, "品牌bean: " + brandBean.brand.get(0).value.size());
            }
        }
        if (valueBeen==null){
            valueBeen=new ArrayList<>();
            for (int i=0;i<brandBean.brand.size();i++){
                for ( int j=0;j<brandBean.brand.get(i).value.size();j++){
                    valueBeen.add(brandBean.brand.get(i).value.get(j));
                    Log.i(TAG,"valueBean======"+brandBean.brand.get(i).value.get(j).toString());
                }
            }
            Log.i(TAG,"valueBean======"+valueBeen.size());

        }

        BrandGridViewAdapter brandGridViewAdapter=new BrandGridViewAdapter();
        glv.setAdapter(brandGridViewAdapter);
        glv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SearchResultActivity.startBrandPage(parent.getContext(),valueBeen.get(position).id);

            }
        });
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Log.i(TAG, "品牌数据请求失败");
    }
    class  BrandGridViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return valueBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder ;
            View view;
            if (convertView==null){
                holder=new ViewHolder();
                 view=View.inflate(parent.getContext(),R.layout.item_brand_gridview,null);
               holder.iv_brand= (ImageView) view.findViewById(R.id.iv_brand);
                view.setTag(holder);
            }else {
                view=convertView;
                holder= (ViewHolder) view.getTag();
            }
            String picUrl = RBConstants.URL_SERVER + valueBeen.get(position).pic;
            Log.e(TAG, "图片地址：" + picUrl);
            MyApplication.IL.get(picUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    holder.iv_brand.setImageBitmap(imageContainer.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i(TAG, "品牌图片---------请求失败");
                }
            });


            return view;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
        class  ViewHolder{
            ImageView iv_brand;
        }
    }

}
