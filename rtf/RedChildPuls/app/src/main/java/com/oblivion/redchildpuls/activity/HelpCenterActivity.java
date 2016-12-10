package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oblivion.redchildpuls.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/7.
 */
public class HelpCenterActivity extends AppCompatActivity implements View.OnClickListener {
	@Bind(R.id.return_more)
	Button mReturnMore;
	@Bind(R.id.more_help_shopping)
	TextView mMoreHelpShopping;//售后指南
	@Bind(R.id.more_help_after)
	TextView mMoreHelpAfter;//售后服务
	@Bind(R.id.more_help_dist)
	TextView mMoreHelpDist;//配送方式
	@Bind(R.id.ly_help)
	LinearLayout mLyHelp;
	@Bind(R.id.more_help_phone)
	TextView mMoreHelpPhone;//客服电话

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		ButterKnife.bind(this);
		mMoreHelpShopping.setOnClickListener(this);
		mMoreHelpAfter.setOnClickListener(this);
		mMoreHelpDist.setOnClickListener(this);
		mMoreHelpPhone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.more_help_shopping:
				Toast.makeText(HelpCenterActivity.this, "杰哥教你买东西", Toast.LENGTH_SHORT).show();
				break;
			case R.id.more_help_after:
				Toast.makeText(HelpCenterActivity.this, "杰哥给你做售后", Toast.LENGTH_SHORT).show();
				break;
			case R.id.more_help_dist:
				Toast.makeText(HelpCenterActivity.this, "将由杰哥为您配送", Toast.LENGTH_SHORT).show();
				break;
			case R.id.more_help_phone://跳转到拨打电话界面
				//客服电话
				Uri uri = Uri.parse("tel:120");
				//跳转到拨号界面
				Intent intent = new Intent(Intent.ACTION_DIAL,uri);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;
		}
	}
}
