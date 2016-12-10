package com.oblivion.redchildpuls.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.utils.FontDisplayUtil;

/**
 * Created by nick on 2016/12/3.
 */
public class MyTitleBar extends RelativeLayout {

    private ImageButton backButton;
    private Button menuButton;
    private TextView tv;
    private Button filterButton;

    public MyTitleBar(Context context) {
        this(context,null,0);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public MyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        setBackgroundColor(Color.parseColor("#d90000"));
        initView(context, ta);
//        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = FontDisplayUtil.dip2px(context,60);
//        setLayoutParams(layoutParams);
    }

    private void initView(Context context, TypedArray ta) {
            String title = ta.getString(R.styleable.MyTitleBar_titleText);             //标题文字
            Boolean isBackup = ta.getBoolean(R.styleable.MyTitleBar_isBackup,true);   //返回默认显示
            Boolean isMenu = ta.getBoolean(R.styleable.MyTitleBar_isMenu,false);      //菜单默认不显示
            Boolean isFilter = ta.getBoolean(R.styleable.MyTitleBar_isFilter,false);

        tv = new TextView(context);
            /*
            <item name="android:gravity">center_horizontal</item>
            <item name="android:textSize">30sp</item>
            * */
            //设置标题
            //设置按钮
           // tv.setBackgroundColor(Color.RED);
            tv.setText(title);    //设定标题
            int pading =  FontDisplayUtil.dip2px(context,3);
            tv.setPadding(pading,pading,pading,pading);
            tv.setShadowLayer(5,5,5,Color.GRAY);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            LayoutParams TVparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TVparams.addRule(CENTER_VERTICAL,TRUE);
          //  TVparams.addRule(CENTER_IN_PARENT,TRUE);
            TVparams.addRule(CENTER_HORIZONTAL,TRUE);
            addView(tv,TVparams);

            backButton = new ImageButton(context);
            //初始化 backButton
            // backButton.setImageResource(R.mipmap.back);
            backButton.setBackgroundResource(R.drawable.select_back);   //回退按钮的背景
            backButton.setVisibility(VISIBLE);
            LayoutParams backButtonParams = new LayoutParams(FontDisplayUtil.dip2px(context,35),FontDisplayUtil.dip2px(context,35));
            backButtonParams.addRule(ALIGN_PARENT_LEFT,TRUE);
            backButtonParams.addRule(CENTER_VERTICAL,TRUE);
            backButtonParams.leftMargin = FontDisplayUtil.dip2px(context,10);
            addView(backButton,backButtonParams);

            //初始化menu Button
            menuButton = new Button(context);
            menuButton.setBackgroundResource(R.drawable.select_menu);   //菜单按钮的背景
            menuButton.setVisibility(GONE);                    //默认关闭

            menuButton.setTextColor(Color.WHITE);
            LayoutParams menuButtonParames = new LayoutParams(FontDisplayUtil.dip2px(context,35),FontDisplayUtil.dip2px(context,35));
            menuButtonParames.addRule(ALIGN_PARENT_LEFT,TRUE);
            menuButtonParames.addRule(CENTER_VERTICAL,TRUE);
            menuButtonParames.leftMargin = FontDisplayUtil.dip2px(context,10);
            addView(menuButton,menuButtonParames);


            //初始化 filterButton
            // backButton.setImageResource(R.mipmap.back);
            filterButton = new Button(context);
              filterButton.setBackgroundResource(R.drawable.select_filter);   //回退按钮的背景
               filterButton.setVisibility(GONE);
            LayoutParams filterButtonParams = new LayoutParams(FontDisplayUtil.dip2px(context,35),FontDisplayUtil.dip2px(context,35));
            filterButtonParams.addRule(ALIGN_PARENT_RIGHT,TRUE);
            filterButtonParams.addRule(CENTER_VERTICAL,TRUE);
            filterButtonParams.rightMargin = FontDisplayUtil.dip2px(context,10);
            addView(filterButton,filterButtonParams);




            if (isFilter){
                filterButton.setVisibility(VISIBLE);
            }else {
                filterButton.setVisibility(GONE);
            }

            if (isBackup){
                backButton.setVisibility(VISIBLE);
            }else {
                backButton.setVisibility(GONE);
            }

            if (isMenu) {
                menuButton.setVisibility(VISIBLE);
            }else{
                menuButton.setVisibility(GONE);
            }

    }


    /**
     * 返回按钮的点击事件
     * @param onClickListener   点击监听
     */
    public void addOnBackClickListener(OnClickListener onClickListener) {
        backButton.setOnClickListener(onClickListener);
    }

    /**
     * 菜单按钮的点击事件
     * @param onClickListener   点击监听
     */
    public void addOnMenuClickListener(OnClickListener onClickListener){
        menuButton.setOnClickListener(onClickListener);
    }

    /**
     * 筛选按钮的点击事件
     * @param onClickListener   点击监听
     */
    public void addOnFilterClickListener(OnClickListener onClickListener){
        filterButton.setOnClickListener(onClickListener);
    }

    /**
     * 菜单按钮的
     * @param title
     */
    public void setTitle(String title){
        tv.setText(title);
        tv.measure(0,0);
    }

    public void setFilterButtonVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        filterButton.setVisibility(visibility);
    }

}
