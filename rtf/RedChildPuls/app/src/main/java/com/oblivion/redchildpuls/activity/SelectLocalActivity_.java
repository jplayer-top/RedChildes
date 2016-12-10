package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.adapter.SelectLocalAdapter;
import com.oblivion.redchildpuls.bean.AddressResponse;
import com.oblivion.redchildpuls.event.EventSelectLocal;

import org.greenrobot.eventbus.EventBus;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class SelectLocalActivity_ extends AppCompatActivity implements HttpLoader.HttpListener {
    @Bind(R.id.lv_selectlocal)
    ListView lvSelectlocal;
    @Bind(R.id.ib_change_local)
    ImageButton ibChangeLocal;
    private AddressResponse addressResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlocal);
        ButterKnife.bind(this);
        com.oblivion.redchildpuls.activity.oblivapi.LocalApi.getCheckoutData(this, "");
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        addressResponse = (AddressResponse) response;
        lvSelectlocal.setAdapter(new SelectLocalAdapter(addressResponse.addressList));
        lvSelectlocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressResponse.AddressListBean bean = addressResponse.addressList.get(position);
                EventBus.getDefault().postSticky(new EventSelectLocal(bean.name, bean.phoneNumber, bean.addressArea, bean.addressDetail));
                finish();
            }
        });
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    @OnClick(R.id.ib_change_local)
    public void onClick() {
        startActivity(new Intent(this, ChangeLocalActivity.class));
    }
}
