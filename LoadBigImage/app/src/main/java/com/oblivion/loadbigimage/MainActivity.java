package com.oblivion.loadbigimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView iv_loadbigimage;
    private Button bt_loadbigimage;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_loadbigimage = (ImageView) findViewById(R.id.iv_loagbigimage);
        bt_loadbigimage = (Button) findViewById(R.id.bt_loadbigimage);
        bt_loadbigimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "点击加载");
                new Thread() {
                    @Override
                    public void run() {
                        //获取 选择项Options
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //仅获取所选择图片的边框
                        options.inJustDecodeBounds = true;
                        //作为参数传入到要加载的图片中
                        BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/IMG_0248.JPG", options);
                        //设置适配值
                        options.inSampleSize = getSize(options);
                        //将仅选择边框设定为false
                        options.inJustDecodeBounds = false;
                        //定义显示的图片
                        final Bitmap newBiemap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/IMG_0248.JPG", options);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //子线程更新UI
                                iv_loadbigimage.setImageBitmap(newBiemap);

                            }
                        });
                    }
                }.start();

            }
        });

    }

    /**
     * 获取放置OOM溢出的边框适配值
     *
     * @param options 选择项
     * @return 需要设定的缩放值
     */
    public int getSize(BitmapFactory.Options options) {
        WindowManager mwm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int wmHeight = mwm.getDefaultDisplay().getHeight();
        int wmWidth = mwm.getDefaultDisplay().getWidth();
        int relHeight = options.outHeight;
        int relWidth = options.outWidth;
        return (relHeight / wmHeight) > (relWidth / wmWidth) ? (relHeight / wmHeight) : (relWidth / wmWidth);
    }

}

