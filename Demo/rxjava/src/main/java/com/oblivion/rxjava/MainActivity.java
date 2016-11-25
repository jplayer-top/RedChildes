package com.oblivion.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oblivion.rxjava.model.RetrofitModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_data)
    TextView tvData;
    @Bind(R.id.tv_clothes)
    TextView tvClothes;
    @Bind(R.id.tv_car)
    TextView tvCar;
    @Bind(R.id.tv_goto)
    TextView tvGoto;
    @Bind(R.id.tv_seek)
    TextView tvSeek;
    @Bind(R.id.tv_play)
    TextView tvPlay;
    @Bind(R.id.tv_sun)
    TextView tvSun;
    @Bind(R.id.bt_check)
    Button btCheck;
    @Bind(R.id.et_check)
    EditText etCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_check)
    public void onClick() {
        String etCity = etCheck.getText().toString().trim();
        RetrofitModel retrofitModel = new RetrofitModel();
        retrofitModel.getResString(etCity);
    }
}
