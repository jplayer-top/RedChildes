package com.oblivion.rxjavatest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLStreamHandler;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv1.setText(getImgSpanString("哈哈,看我头像[笑脸]", R.drawable.emo_im_tongue_sticking_out));
        tv2.setText(getUrlSpanString("我的blog,点我点我<a href='http://blog.csdn.net/qq_16666847'>blog</a>"));
        tv2.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以点击啊
        tv3.setText(getTextSpanString("我是[蓝色]", Color.BLUE));
        tv4.setText(getMyUrlSpanString("点我,别点我"));
        tv4.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以点击啊

    }

    public SpannableString getImgSpanString(String string, int resImg) {
        SpannableString sString = new SpannableString(string);
        Drawable drawable = getResources().getDrawable(resImg);
        drawable.setBounds(0, 0, 20, 20);
        ImageSpan imageSpan = new ImageSpan(drawable);
        sString.setSpan(imageSpan, string.indexOf("["), string.indexOf("]") + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return sString;
    }

    public Spanned getUrlSpanString(String string) {
        Spanned html = Html.fromHtml(string);
        return html;
    }

    public SpannableString getTextSpanString(String string, int color) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(colorSpan, string.indexOf("["), string.indexOf("]") + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public SpannableString getMyUrlSpanString(String string) {
        MySpanUrl spanUrl = new MySpanUrl(string.substring(0, 2));
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(spanUrl, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 自定义的MySpanUrl,实现自定义操作
     */
    private class MySpanUrl extends URLSpan {
        public MySpanUrl(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(MainActivity.this, getURL(), Toast.LENGTH_SHORT).show();
            widget.setBackgroundColor(Color.TRANSPARENT);
            widget.clearFocus();
        }
    }
}

