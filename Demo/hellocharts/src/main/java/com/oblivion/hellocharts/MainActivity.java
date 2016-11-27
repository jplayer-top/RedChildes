package com.oblivion.hellocharts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

    private LineChartView chartView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = (LineChartView) findViewById(R.id.chart);
        textView = (TextView) findViewById(R.id.button);
        initData2Chart();
        textView.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // chartView.resetViewports();
           initData2Chart();
        }
    };

    /**
     * 初始化数据到chart中
     */
    private void initData2Chart() {
        chartView.setOnValueTouchListener(listener);
        /**
         * 简单模拟的数据
         */
        List<PointValue> values = new ArrayList<>();
        values.add(new PointValue(0, 3));
        values.add(new PointValue(1, 1));
        values.add(new PointValue(2, 4));
        values.add(new PointValue(3, 0));
        //setCubic(true),true是曲线型，false是直线连接
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axisX = new Axis();
        //setHasLines(true),设定是否有网格线
        Axis axisY = new Axis().setHasLines(false);
        //为两个坐标系设定名称
        axisX.setName("Axis X");
        axisY.setName("Axis Y");
        //设置图标所在位置
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        //将数据添加到View中
        chartView.setLineChartData(data);
    }

    /**
     * 为每个点设置监听
     */
    private LineChartOnValueSelectListener listener = new LineChartOnValueSelectListener() {
        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(MainActivity.this, "value" + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
        }
    };
}
