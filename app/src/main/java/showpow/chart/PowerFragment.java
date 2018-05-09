package showpow.chart;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.MyMarkerView;

/**
 * Created by Administrator on 2018/4/23.
 */
public class PowerFragment extends Fragment{
    private BarChart barChart;
    private float[] datas;
    public PowerFragment() {
    }

    @SuppressLint("ValidFragment")
    public PowerFragment(float[] datas) {
        this.datas=datas;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_power, container, false);
        barChart = (BarChart) view.findViewById(R.id.barChart);
        initChart();
        setBarChartData();
        return view;
    }

    private void initChart() {
        barChart.setDrawBorders(false);
        barChart.setBorderColor(Color.WHITE);
        barChart.getLegend().setEnabled(false);  //不显示标签
        barChart.getDescription().setEnabled(false);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisLeft().setDrawGridLines(false);
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签
        xAxis.setLabelCount(12, false);//设置标签显示的个数
        xAxis.setTextColor(Color.WHITE);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //value的值为AxisMinimum到AxisMaximum-1
                return String.valueOf(((int) value) + 1 + "月");  //自定义X轴标签
            }
        });
        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴
        MyMarkerView myMarkerView = new MyMarkerView(getActivity());
        myMarkerView.setChartView(barChart);
        barChart.setMarker(myMarkerView);
    }

    private void setBarChartData() {
        int barColor = getResources().getColor(R.color.light_blue);
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            entries.add(new BarEntry(i + 0.5f, datas[i]));
        }

        BarDataSet set;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(entries, "");
            set.setColor(barColor);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(25.0f);
            data.setBarWidth(0.5f);
            data.setDrawValues(false);
            barChart.setData(data);
        }
    }

}
