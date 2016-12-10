package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements HttpLoader.HttpListener{

    @Bind(R.id.register_number)
    EditText mRegisterNumber;
    @Bind(R.id.register_password)
    EditText mRegisterPassword;
    @Bind(R.id.register_passwords)
    EditText mRegisterPasswords;
    @Bind(R.id.register_register)
    Button mRegisterRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_register)
    public void onClick() {

        String username = mRegisterNumber.getText().toString().trim();
        String pwd = mRegisterPassword.getText().toString().trim();
        String pwds = mRegisterPasswords.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length()<6||pwd.length()<6){
            Toast.makeText(RegisterActivity.this, "用户名和密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(pwd, pwds)) {
            Toast.makeText(RegisterActivity.this, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("password", pwd);
        MyApplication.HL.post(RBConstants.URL_REGISTER, params, LoginResponse.class, RBConstants.REQUEST_CODE_REGISTER,this);

    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {


            LoginResponse loginResponse = (LoginResponse) response;
        System.out.println(loginResponse.response);
        if (loginResponse.response.equals("error")){
            Toast.makeText(this, "用户名已注册", Toast.LENGTH_SHORT).show();
            return;
        }

      //  LoginResponse loginResponse = (LoginResponse) response;
        /*if (loginResponse.response.equals("error")) {
            Toast.makeText(RegisterActivity.this, "注册失败或用户名重复", Toast.LENGTH_SHORT).show();
        }*/
         if (requestCode == RBConstants.REQUEST_CODE_REGISTER) {
            // LoginResponse  loginResponse = (LoginResponse) response;
            //ALog.e("----userid---" + loginResponse.userInfo.userid);
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            //登录成功获取到  userid   放到myapp中  供其他人使用
            MyApplication.userId = loginResponse.userInfo.userid;
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}

