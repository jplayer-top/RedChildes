package com.oblivion.zhbj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by oblivion on 2016/11/5.
 */
public class DetialActivity extends AppCompatActivity {

    private String url;
    private WebView webView;
    private TextView tTitle;
    private ImageView size;
    private ImageView back;
    private int checkedItem;
    private ImageView share;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_title);
        //获取到URL
        url = getIntent().getStringExtra("url");
        System.out.println(url);
        initView();
    }

    public void initView() {
        tTitle = (TextView) findViewById(R.id.tv_wb_title);
        size = (ImageView) findViewById(R.id.iv_wb_textsize);
        back = (ImageView) findViewById(R.id.iv_wb_back);
        share = (ImageView) findViewById(R.id.iv_wb_share);
        webView = (WebView) findViewById(R.id.wv_detial);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {

        });
        //设置谷歌内核获取到数据
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                System.out.println("加载进度" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //tTitle.setText(title+"网页标题");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkedItem = 2;
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleDialog();
            }
        });
    }

    /**
     * 这里我居然重写了他，，所以导致一致报错，，，没事看看是不是手贱
     */
    private void showSingleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("字体型号");
        String[] items = new String[]{"大号字体，中号字体，正常字体，小号字体"};
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final WebSettings settings = webView.getSettings();
                switch (which) {
                    case 0:
                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                        dialog.dismiss();
                        break;
                    case 1:
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                        dialog.dismiss();
                        break;
                    case 2:
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                        dialog.dismiss();
                        break;
                    case 3:
                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }
}
