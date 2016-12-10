package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.AddressResponse;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class SelectLocalHolder extends BaseHolder<AddressResponse.AddressListBean>  {
    @Bind(R.id.tv_local_name)
    TextView tvLocalName;
    @Bind(R.id.tv_local_call)
    TextView tvLocalCall;
    @Bind(R.id.tv_local_local)
    TextView tvLocalLocal;
    @Bind(R.id.tv_local_home)
    TextView tvLocalHome;
    private View view;

    public SelectLocalHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        view = View.inflate(MyApplication.mContext, R.layout.itemselectlocal, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(AddressResponse.AddressListBean data) {
        super.bindData(data);
        tvLocalName.setText(data.name);
        tvLocalCall.setText(data.phoneNumber);
        tvLocalLocal.setText(data.addressArea);
        tvLocalHome.setText(data.addressDetail);
    }

}
