package showinv.chart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.MyMarkerView;

/**
 * Created by Administrator on 2018/4/21.
 */

public class InnovationFragment extends Fragment{
    private LineChart mLineChart;
    private float[] datas;
    private String label;
    public InnovationFragment(){}

    @SuppressLint("ValidFragment")
    public InnovationFragment(String label,float[] datas){
        this.datas=datas;
        this.label=label;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_innovation,container,false);
        mLineChart= (LineChart) view.findViewById(R.id.innovation_lineChart);
        //设置图表的一些属性
        initChart();
        initData();
        return view;
    }

    //设置基础属性
    private void initChart() {

        // 描述信息
        Description description = new Description();
        description.setText(" ");
        // 设置描述信息
        mLineChart.setDescription(description);
        //设置没有数据时显示的文本
        mLineChart.setNoDataText("没有数据喔~~");
        //设置是否绘制chart边框的线
        mLineChart.setDrawBorders(false);
        //设置chart边框线颜色
        mLineChart.setBorderColor(Color.WHITE);
        //设置chart边框线宽度
        mLineChart.setBorderWidth(1f);
        //设置chart是否可以触摸
        mLineChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mLineChart.setDragEnabled(true);
        //设置是否可以缩放 x和y，默认true
        mLineChart.setScaleEnabled(true);
        //设置是否可以通过双击屏幕放大图表。默认是true
        mLineChart.setDoubleTapToZoomEnabled(true);
        //设置chart动画
        mLineChart.animateXY(1000, 1000);

        //=========================设置图例=========================
        Legend legend=mLineChart.getLegend();  //显示标签
        legend.setTextSize(18.0f);
        legend.setTextColor(Color.WHITE);
        //设置图例显示在chart那个位置 setPosition建议放弃使用了
        //设置垂直方向上还是下或中
        //legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //设置水平方向是左边还是右边或中
        //legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //设置所有图例位置排序方向
        //legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置图例的形状 有圆形、正方形、线
        //legend.setForm(Legend.LegendForm.SQUARE);
        //是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        //legend.setWordWrapEnabled(true);

        //=======================设置X轴显示效果==================
        XAxis xAxis = mLineChart.getXAxis();
        //是否启用X轴
        xAxis.setEnabled(true);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);
        //设置X轴上每个竖线是否显示
        xAxis.setDrawGridLines(false);
        //设置是否绘制X轴上的对应值(标签)
        xAxis.setDrawLabels(true);
        //设置X轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置竖线为虚线样式
        // xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置x轴标签数
        xAxis.setLabelCount(12, true);
        //设置x轴颜色
        xAxis.setTextColor(Color.WHITE);
        //自定义X轴上的值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value + "月");
            }
        });
        MyMarkerView myMarkerView = new MyMarkerView(getActivity());
        myMarkerView.setChartView(mLineChart);
        mLineChart.setMarker(myMarkerView);

        //=================设置左边Y轴===============
        YAxis axisLeft = mLineChart.getAxisLeft();
        //是否启用左边Y轴
        axisLeft.setEnabled(true);
        //设置最小值（这里就按demo里固死的写）
        axisLeft.setAxisMinimum(0);
        axisLeft.setDrawGridLines(true);
        //设置横向的线为虚线
        axisLeft.enableGridDashedLine(10f, 10f, 0f);
        //设置y轴颜色
        axisLeft.setTextColor(Color.WHITE);
        //axisLeft.setDrawLimitLinesBehindData(true);
        //左边Y轴添加限制线
        //axisLeft.addLimitLine(limitLine);

        //====================设置右边的Y轴===============
        YAxis axisRight = mLineChart.getAxisRight();
        //是否启用右边Y轴
        axisRight.setEnabled(false);
        //设置最小值（这里按demo里的数据固死写了）
        axisRight.setAxisMinimum(0);
        //设置y轴颜色
        axisRight.setTextColor(Color.WHITE);
        //设置横向的线为虚线
        axisRight.enableGridDashedLine(10f, 10f, 0f);
    }

    private void initData() {

        ArrayList<Entry> pointValues;

        pointValues = new ArrayList<>();

        for(int i = 0; i <12; i++){
            pointValues.add(new Entry(i+1,datas[i]));
            //pointValues2.add(new Entry(i,(float)(Math.random() * 20)));
            //pointValues3.add(new Entry(i,(float)(Math.random() * 20)));
        }

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(SetLineDatas(pointValues,label,Color.rgb(0xdb,0x66, 0xae),Color.rgb(0xdb,0x66, 0xae)));
        //dataSets.add(SetLineDatas(pointValues2,"海珠区商业中心",Color.rgb(0x68,0x6f, 0xba),Color.rgb(0x68,0x6f, 0xba)));
        //dataSets.add(SetLineDatas(pointValues3,"天河购物中心",Color.rgb(0xe0,0x88, 0x86),Color.rgb(0xe0,0x88, 0x86)));
        //把要画的所有线(线的集合)添加到LineData里
        LineData lineData = new LineData(dataSets);
        //把最终的数据setData
        mLineChart.setData(lineData);

    }

    public LineDataSet SetLineDatas(ArrayList<Entry> pvalues,String lineName,int color,int circleColor){
        //点构成的某条线
        LineDataSet lineDataSet = new LineDataSet(pvalues, lineName);
        //设置该线的颜色
        lineDataSet.setColor(color);
        //设置每个点的颜色
        lineDataSet.setCircleColor(circleColor);
        //设置该线的宽度
        lineDataSet.setLineWidth(2f);
        //设置每个坐标点的圆大小
        lineDataSet.setCircleRadius(2f);
        //设置是否画圆
        lineDataSet.setDrawCircles(true);
        // 设置平滑曲线模式
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //设置线一面部分是否填充颜色
        lineDataSet.setDrawFilled(true);
        //设置填充的颜色
        lineDataSet.setFillColor(color);
        //设置是否显示点的坐标值
        lineDataSet.setDrawValues(false);
        return lineDataSet;
    }

}