package com.oblivion.parallax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.oblivion.domain.Cheeses;
import com.oblivion.ui.ParallaxListView;

public class MainActivity extends AppCompatActivity {
    private com.oblivion.ui.ParallaxListView plv_parallax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plv_parallax = (ParallaxListView) findViewById(R.id.plv_parallax);
        View view = View.inflate(this, R.layout.item_image, null);
        final ImageView iv_parallax;
        iv_parallax = (ImageView) view.findViewById(R.id.iv_parallax);
        plv_parallax.addHeaderView(view);
        //添加渲染监听，当渲染完成后传出ImageView,难道只能放这儿吗
        iv_parallax.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                plv_parallax.init(iv_parallax);
                iv_parallax.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        plv_parallax.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.NAMES));
    }
}
