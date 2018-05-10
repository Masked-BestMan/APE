package show.chart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;

import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.MyMarkerView;

/**
 * Created by Administrator on 2018/4/21.
 */

public class BoomFragment extends Fragment{
    private RadarChart mRadarChart;
    private float[][] index_data;
    public BoomFragment(){}

    @SuppressLint("ValidFragment")
    public BoomFragment(float[][] index_data){
        this.index_data=index_data;
    }
    private final String[] xVals = {"创新指数","环境指数","快批指数",
            "物流指数","租赁指数","人力指数"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_boom,container,false);
        mRadarChart = (RadarChart) view.findViewById(R.id.boom_RadarChart);
        //设置图表的一些属性
        initChart();
        return view;
    }

    //设置基本属性
    private void initChart() {
        //描述信息
        Description description = new Description();
        description.setText(" ");
        //设置描述信息
        mRadarChart.setDescription(description);
        //设置没有数据时显示的文本
        mRadarChart.setNoDataText("没有数据喔~~");
        //设置是否绘制chart边框的线
        mRadarChart.setDrawWeb(true);
        //设置网线颜色
        mRadarChart.setWebColor(Color.WHITE);
        //设置chart网线的宽度
        mRadarChart.setWebLineWidth(2f);
        //设置chart是否可以触摸
        mRadarChart.setTouchEnabled(false);
        //设置chart动画
        mRadarChart.animateXY(1000,1000);

        //=========================设置图例=========================
        Legend legend = mRadarChart.getLegend();
        //设置垂直方向上还是下或中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        //设置水平方向是左边还是右边或中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //设置所有图例位置排序方向
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        //设置图例的形状 有圆形、正方形、线
        legend.setForm(Legend.LegendForm.SQUARE);
        //是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        legend.setWordWrapEnabled(true);
        //设置图例文字的颜色
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(10f);

        //=======================设置X轴显示效果==================
        XAxis xAxis = mRadarChart.getXAxis();
        //是否启用X轴
        xAxis.setEnabled(true);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(true);
        //设置X轴上每个竖线是否显示
        xAxis.setDrawGridLines(true);
        //设置x轴每个竖线的宽度
        xAxis.setGridLineWidth(10f);
        //设置是否绘制X轴上的对应值(标签)
        xAxis.setDrawLabels(true);
        //设置X轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置竖线为虚线样式
        // xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置x轴标签数
        xAxis.setLabelCount(6, true);
        //设置x轴颜色
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineWidth(10f);
        //设置x轴字体大小
        xAxis.setTextSize(10f);
        //自定义x轴的标签
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(xVals[((int) value)%6]);
            }
        });

        //=================设置Y轴===============
        YAxis yAxis = mRadarChart.getYAxis();
        // Y坐标值标签个数
        yAxis.setLabelCount(12, false);
        // Y坐标值字体大小
        yAxis.setTextSize(0f);

        // Y坐标值是否从0开始
        //yAxis.setStartAtZero(true);
        yAxis.setTextColor(Color.WHITE);

        //==================设置Marker==============
        MyMarkerView myMarkerView = new MyMarkerView(getActivity());
        myMarkerView.setChartView(mRadarChart);
        mRadarChart.setMarker(myMarkerView);
        setRadarChartData(index_data);
    }

    /**
     *
     * @param index_data  二维数组表示某月某个指数量
     */
    private void setRadarChartData(float[][] index_data) {
        int cnt = 6; // 不同的维度指数总个数

        // Y的值，数据填充
        ArrayList<RadarEntry> y1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y2 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y3 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y4 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y5 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y6 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y7 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y8 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y9 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y10 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y11 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> y12 = new ArrayList<RadarEntry>();

        for(int i = 0;i<cnt;i++){
            y1.add(new RadarEntry(index_data[0][i], i));
            y2.add(new RadarEntry(index_data[1][i], i));
            y3.add(new RadarEntry(index_data[2][i], i));
            y4.add(new RadarEntry(index_data[3][i], i));
            y5.add(new RadarEntry(index_data[4][i], i));
            y6.add(new RadarEntry(index_data[5][i], i));
            y7.add(new RadarEntry(index_data[6][i], i));
            y8.add(new RadarEntry(index_data[7][i], i));
            y9.add(new RadarEntry(index_data[8][i], i));
            y10.add(new RadarEntry(index_data[9][i], i));
            y11.add(new RadarEntry(index_data[10][i], i));
            y12.add(new RadarEntry(index_data[11][i], i));
        }

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(SetRadarDatas(y1,"1月",Color.rgb(0xdb,0x66,0xae)));
        sets.add(SetRadarDatas(y2,"2月",Color.rgb(0x68,0x6f,0xba)));
        sets.add(SetRadarDatas(y3,"3月",Color.rgb(0xa6,0x63,0xa6)));
        sets.add(SetRadarDatas(y4,"4月",Color.rgb(0x86,0xd9,0xe0)));
        sets.add(SetRadarDatas(y5,"5月",Color.rgb(0xe0,0x88,0x86)));
        sets.add(SetRadarDatas(y6,"6月",Color.rgb(0xd7,0xef,0x7e)));
        sets.add(SetRadarDatas(y7,"7月",Color.rgb(0xdb,0x66,0xae)));
        sets.add(SetRadarDatas(y8,"8月",Color.rgb(0x68,0x6f,0xba)));
        sets.add(SetRadarDatas(y9,"9月",Color.rgb(0xa6,0x63,0xa6)));
        sets.add(SetRadarDatas(y10,"10月",Color.rgb(0x86,0xd9,0xe0)));
        sets.add(SetRadarDatas(y11,"11月",Color.rgb(0xe0,0x88,0x86)));
        sets.add(SetRadarDatas(y12,"12月",Color.rgb(0xd7,0xef,0x7e)));

        RadarData data = new RadarData(sets);
        //data.setValueTextSize(5f);
        data.setDrawValues(false);
        mRadarChart.setData(data);

    }

    public RadarDataSet SetRadarDatas(ArrayList<RadarEntry> pvalues, String lineName, int color){
        RadarDataSet radarDataSet = new RadarDataSet(pvalues,lineName);
        radarDataSet.setColor(color,0x90FFFFFF);
        radarDataSet.setDrawFilled(true);
        radarDataSet.setFillColor(color&0xFFFFFFFF);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setDrawHighlightCircleEnabled(true);
        radarDataSet.setHighlightCircleOuterRadius(2f);
        radarDataSet.setHighlightCircleFillColor(Color.WHITE);
        return radarDataSet;
    }

}
