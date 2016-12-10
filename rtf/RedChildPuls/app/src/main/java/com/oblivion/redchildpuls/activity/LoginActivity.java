package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.LoginResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements HttpLoader.HttpListener {

    private static final String TAG = "LoginActivity";
    private SharedPreferences mSharedPreferences;
    @Bind(R.id.login_username)
    EditText mUsername;
    @Bind(R.id.login_password)
    EditText mPassword;

    @Bind(R.id.login_checkbox)
    CheckBox mCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


//        //测试按钮
//        Button cs = (Button) findViewById(R.id.login_ce);
//        cs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MyApplication.mContext,LimitbuyActivity.class));
//            }
//        });




//        startActivity(new Intent(this, DetailActivity_.class));
        saveusername();
        //监听当前勾选状态
        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG,"当前状态:"+isChecked);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.commit();

            }
        });
    }

    //保存账号密码到SharedPreferences
    public void saveusername() {
        mSharedPreferences = this.getSharedPreferences("config", 0);
        //回显控件状态
        boolean isChecked = mSharedPreferences.getBoolean("isChecked", true);
        mCheckbox.setChecked(isChecked);
        if (isChecked) {
            String username = mSharedPreferences.getString("username", "");
            String pwd = mSharedPreferences.getString("pwd", "");
            mUsername.setText(username);
            mPassword.setText(pwd);
        } else {
            String username = mSharedPreferences.getString("username", "");
            String pwd = mSharedPreferences.getString("pwd", "");
            mUsername.setText("");
            mPassword.setText("");
        }
    }
    //跳转到主界面
    @OnClick(R.id.login_register)
    public void onClick() {
        String username = mUsername.getText().toString().trim();
        String pwd = mPassword.getText().toString().trim();
        //判断用户名和密码不能为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 记住密码
        if (mCheckbox.isChecked()) {
            Log.i(TAG, "记住密码");
            //得到sp文件的编辑器
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString("username", username);
            editor.putString("pwd", pwd);
            editor.commit();
        } else {
            Log.i(TAG, "不需要记住密码");
        }

        HttpParams params = new HttpParams();
        params.put("username",username);
        params.put("password",pwd);
        MyApplication.HL.post(RBConstants.URL_LOGIN, params, LoginResponse.class, RBConstants.REQUEST_CODE_LOGIN, this);

    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == RBConstants.REQUEST_CODE_LOGIN) {
            //登录成功获取到  userid   放到myapp中  供其他人使用
            LoginResponse loginResponse = (LoginResponse) response;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            MyApplication.userId = loginResponse.userInfo.userid;
            finish();
             ALog.e("----userid---" + loginResponse.userInfo.userid);
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();

    }

    //跳转到注册页面
    public void newuesrname(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

}
