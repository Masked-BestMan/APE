package showevn.chart;

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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import businessmonitor.com.example.newbusinessmonitor.MyMarkerView;
import businessmonitor.com.example.newbusinessmonitor.R;

/**
 * Created by Administrator on 2018/4/21.
 */

public class EnvironmentFragment extends Fragment{
    private LineChart lineChart;
    private float[] datas;
    public EnvironmentFragment(){ }

    @SuppressLint("ValidFragment")
    public EnvironmentFragment(float[] datas){
        this.datas=datas;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_environment,container,false);
        lineChart= (LineChart) view.findViewById(R.id.environment_lineChart);
        //设置图表的一些属性
        initChart();

        return view;
    }

    private void initChart() {
        lineChart.setBorderColor(Color.WHITE);
        lineChart.getLegend().setEnabled(false);  //不显示标签
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);    //右边的Y轴不显示
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setAxisMinimum(0);   //保证Y轴从0开始，不然会上移一点
        lineChart.getAxisLeft().setDrawGridLines(true);  //左边绘制网格

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisMaximum(11);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签
        xAxis.setDrawGridLines(false);   //X轴不绘制网格
        //自定义X轴上的值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value + 1 + "月");
            }
        });
        xAxis.setTextColor(Color.WHITE);
        //xAxis.setCenterAxisLabels(true);  //设置为true会影响AxisMinimum，AxisMaximum
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12, false);


        MyMarkerView myMarkerView = new MyMarkerView(getActivity());
        myMarkerView.setChartView(lineChart);
        lineChart.setMarker(myMarkerView);

        setLineChartData(datas);
    }

    //X轴标签数，Y轴范围
    public void setLineChartData(float[] datas) {
        int lineColor=getResources().getColor(R.color.light_blue);

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            float val = datas[i];
            values.add(new Entry(i, val));
        }


        LineDataSet set;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set.setValues(values);

            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, "");   //代表一条线


            set.setColor(lineColor);
            set.setCircleColor(lineColor);
            set.setFillColor(lineColor);
            set.setDrawFilled(true);
            set.setLineWidth(2.0f);
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);  //曲线平滑

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            LineData data = new LineData(dataSets);
            data.setValueTextSize(25.0f);
            data.setDrawValues(false);
            lineChart.setData(data);
        }
    }

}
