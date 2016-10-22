package com.oblivion.canvas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainAcyivity";
    private SeekBar sb;
    private ImageView iv;
    private Bitmap bitmap;
    private Bitmap newBitmap;
    private Canvas canvas;
    private Paint paint;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        sb = (SeekBar) findViewById(R.id.sb);
        new Thread() {
            @Override
            public void run() {

                //获取选择器
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/IMG_0248.JPG", opts);
                opts.inSampleSize = 2;
                opts.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/IMG_0248.JPG", opts);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bitmap);

                    }
                });
                //获取画布的宽高
                newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                //创建画布
                canvas = new Canvas(newBitmap);
                //创建画笔
                paint = new Paint();
                //设置画笔的颜色
                paint.setColor(Color.BLACK);
                sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int result = sb.getProgress();
                        float v = result / 100.0f;
                        ColorMatrix cm = new ColorMatrix();
                        Log.i(TAG, "onProgressChanged: " + v);
                        cm.set(
                                new float[]{
                                        1 * v, 0, 0, 0, 0,//红
                                        0, 1, 0, 0, 0,//绿
                                        0, 0, 1, 0, 0,//蓝
                                        0, 0, 0, 1, 0,//透明度

                                }
                        );
                        //设置画笔的颜色过滤器
                        paint.setColorFilter(new ColorMatrixColorFilter(cm));
                        //以原图作画，不是以拷贝来的新图去作画
                        canvas.drawBitmap(bitmap, new Matrix(), paint);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //设定图片到控件上
                                iv.setImageBitmap(newBitmap);
                            }
                        });
                    }
                });

            }
        }.start();
    }
}
