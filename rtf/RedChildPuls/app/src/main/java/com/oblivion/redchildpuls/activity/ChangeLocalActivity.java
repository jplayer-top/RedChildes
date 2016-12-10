package com.oblivion.redchildpuls.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.oblivapi.AddressApi;
import com.oblivion.redchildpuls.activity.oblivapi.AreaApi;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class ChangeLocalActivity extends AppCompatActivity implements HttpLoader.HttpListener {

    @Bind(R.id.ib_local)
    ImageButton ibLocal;
    @Bind(R.id.et_update_ReceiveName)
    EditText etUpdateReceiveName;
    @Bind(R.id.et_update_MobliePhoneNumber)
    EditText etUpdateMobliePhoneNumber;
    @Bind(R.id.sp1)
    Spinner sp1;
    @Bind(R.id.local_sp2)
    Spinner sp2;
    @Bind(R.id.sp3)
    Spinner sp3;
    @Bind(R.id.et_update_local)
    EditText etUpdateLocal;
    @Bind(R.id.bt_update_default_address)
    Button btUpdateDefaultAddress;
    private ArrayAdapter<String> adapterSp1;
    private List<AddressListResponse.AreaListBean> areaList;
    private ArrayAdapter adapterSp2;
    private AddressListResponse listResponse2;
    private List<AddressListResponse.AreaListBean> arealist2;
    private List<AddressListResponse.AreaListBean> areaList3;
    private List<String> list3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changelocal);
        ButterKnife.bind(this);
        AreaApi.getCheckoutData(this, "0");
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        AddressListResponse listResponse = (AddressListResponse) response;
        // listResponse.
        areaList = listResponse.areaList;
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < areaList.size(); i++) {
            list.add(areaList.get(i).value);
        }
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapterSp1 = new ArrayAdapter<>(this, R.layout.item_change_address, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterSp1.setDropDownViewResource(R.layout.item_drap);
        //绑定到spinner上
        sp1.setAdapter(adapterSp1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   /* 将所选mySpinner 的值带入myTextView 中*/
                sp1.setSelection(position);
                //选择之后进行络访问
                AreaApi.getCheckoutData(new HttpLoader.HttpListener() {
                    @Override
                    public void onGetResponseSuccess(int requestCode, IResponse response) {
                        listResponse2 = (AddressListResponse) response;
                        arealist2 = listResponse2.areaList;
                        List<String> list2 = new ArrayList<>();
                        for (int i = 0; i < arealist2.size(); i++) {
                            list2.add(arealist2.get(i).value);
                        }
                        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
                        adapterSp2 = new ArrayAdapter<>(MyApplication.mContext, R.layout.item_change_address, list2);
                        //第三步：为适配器设置下拉列表下拉时的菜单样式。
                        adapterSp2.setDropDownViewResource(R.layout.item_drap);
                        //绑定到spinner上
                        sp2.setAdapter(adapterSp2);
                        //刷新布局
                        adapterSp2.notifyDataSetChanged();
                        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                sp2.setSelection(position);
                                AreaApi.getCheckoutData(new HttpLoader.HttpListener() {
                                    @Override
                                    public void onGetResponseSuccess(int requestCode, IResponse response) {
                                        AddressListResponse listResponse3 = (AddressListResponse) response;
                                        areaList3 = listResponse3.areaList;
                                        list3 = new ArrayList<>();
                                        for (int i = 0; i < areaList3.size(); i++) {
                                            list3.add(areaList3.get(i).value);
                                        }
                                        ArrayAdapter<String> adapterSp3 = new ArrayAdapter<>(MyApplication.mContext, R.layout.item_change_address, list3);
                                        adapterSp2.setDropDownViewResource(R.layout.item_drap);
                                        sp3.setAdapter(adapterSp3);
                                        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                sp3.setSelection(position);
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onGetResponseError(int requestCode, VolleyError error) {
                                        sp3.setSelected(false);
                                    }
                                }, arealist2.get(position).id + "");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onGetResponseError(int requestCode, VolleyError error) {

                    }
                }, areaList.get(position).id + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //  System.out.println(listResponse.areaList.get(0).value);
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    @OnClick({R.id.bt_update_default_address, R.id.ib_local})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_update_default_address:
                System.out.println(sp1.getSelectedItem().toString());
                String name = etUpdateReceiveName.getText().toString().trim();
                String call = etUpdateMobliePhoneNumber.getText().toString().trim();
                String detail = etUpdateLocal.getText().toString().trim();
                String province = sp1.getSelectedItem().toString();
                String city = "";
                if (sp2.getSelectedItem() == null) {
                } else {
                    city = sp2.getSelectedItem().toString();
                }
                String addressAreay = sp3.getSelectedItem().toString();
                AddressApi.getCheckoutData(this, name, call, province, city, addressAreay, detail, 1 + "");
                Toast.makeText(ChangeLocalActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.ib_local:
                finish();
                break;
        }
    }
}
