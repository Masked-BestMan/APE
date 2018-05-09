package showlea.chart;

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

public class LeaseHoldFragment extends Fragment{
    private BarChart leaseHold_barChart;
    private float[] datas;
    public LeaseHoldFragment(){ }

    @SuppressLint("ValidFragment")
    public LeaseHoldFragment(float[] datas){
        this.datas=datas;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_leasehold,container,false);
        leaseHold_barChart=(BarChart) view.findViewById(R.id.leasehold_barChart);

        //设置基础属性
        initChart();

        //设置数据
        setBarChartData();
        return view;
    }
    private void initChart() {
        leaseHold_barChart.setDrawBorders(true);
        leaseHold_barChart.setBorderColor(Color.WHITE);
        leaseHold_barChart.getLegend().setEnabled(false);  //不显示标签
        leaseHold_barChart.getDescription().setEnabled(false);
        leaseHold_barChart.getAxisLeft().setTextColor(Color.WHITE);
        leaseHold_barChart.getAxisLeft().setAxisMinimum(0);
        leaseHold_barChart.getAxisLeft().setDrawGridLines(false);
        XAxis xAxis = leaseHold_barChart.getXAxis();//获取x轴
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
        leaseHold_barChart.getAxisRight().setEnabled(false);//禁用右侧y轴
        MyMarkerView myMarkerView = new MyMarkerView(getActivity());
        myMarkerView.setChartView(leaseHold_barChart);
        leaseHold_barChart.setMarker(myMarkerView);
    }

    private void setBarChartData() {
        int barColor = getResources().getColor(R.color.depth_blue);
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            entries.add(new BarEntry(i+0.5f, datas[i]));
        }

        BarDataSet set;
        if (leaseHold_barChart.getData() != null && leaseHold_barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) leaseHold_barChart.getData().getDataSetByIndex(0);
            set.setValues(entries);
            leaseHold_barChart.getData().notifyDataChanged();
            leaseHold_barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(entries, "");
            set.setColor(barColor);
            set.setColors(new int[]{Color.rgb(255,241,226),Color.rgb(155,241,226),Color.rgb(255,211,226),
                    Color.rgb(255,24,226),Color.rgb(55,241,226),Color.rgb(25,211,226),
                    Color.rgb(55,221,226),Color.rgb(155,21,226),Color.rgb(215,11,226),
                    Color.rgb(255,111,226),Color.rgb(155,241,156),Color.rgb(255,211,126)});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(25.0f);
            data.setBarWidth(0.5f);
            data.setDrawValues(false);
            leaseHold_barChart.setData(data);
        }
    }

}