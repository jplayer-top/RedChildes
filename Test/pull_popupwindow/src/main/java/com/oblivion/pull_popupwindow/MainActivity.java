package com.oblivion.pull_popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_popup;
    private EditText et_text;
    private ImageButton ib_down_arrow;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_popup = (LinearLayout) findViewById(R.id.ll_popup);
        et_text = (EditText) findViewById(R.id.et_edit);
        ib_down_arrow = (ImageButton) findViewById(R.id.ib_icon);
        ib_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果不等于null,就先设定为null
                if (mPopupWindow != null) {
                    mPopupWindow = null;
                }
                View contentView = createView();
                int width = ll_popup.getWidth();
                int height = dp2px(200);
                mPopupWindow = new PopupWindow(contentView, width, height);
                //需要设定这个是否可获取焦点，不然无法获取上边的number
                mPopupWindow.setFocusable(true);
                //设定背景
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //偏移量
                int xoff = 2;
                int yoff = -2;
                mPopupWindow.showAsDropDown(ll_popup, xoff, yoff);
            }
        });
    }

    /**
     * 创建PopupWindow
     *
     * @return
     */
    private View createView() {
        //为条目中闯入数值
        List<String> list = new ArrayList<>();
        //创建ListView---其中的R.layout.popuowindow中是以listView 为节点的
        ListView view = (ListView) View.inflate(this, R.layout.popuplistview, null);
        view.setAdapter(new mypopuplistviewAdapter(list));
        view.setVerticalScrollBarEnabled(false);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_select = (TextView) view.findViewById(R.id.tv_select);
                et_text.setText(tv_select.getText().toString().trim());
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });
        return view;
    }

    private int dp2px(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) ((dp * density) + 0.5f);

    }
}
