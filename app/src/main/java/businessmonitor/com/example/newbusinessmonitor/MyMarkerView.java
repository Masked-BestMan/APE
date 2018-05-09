package businessmonitor.com.example.newbusinessmonitor;

/**
 * Created by Administrator on 2018/4/21.
 */



import android.annotation.SuppressLint;
        import android.content.Context;
import android.util.Log;
import android.widget.TextView;

        import com.github.mikephil.charting.components.MarkerView;
        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.highlight.Highlight;
        import com.github.mikephil.charting.utils.MPPointF;
        import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;

import businessmonitor.com.example.newbusinessmonitor.R;

public class MyMarkerView extends MarkerView {
    private TextView tvContent;
    public MyMarkerView(Context context) {
        super(context, R.layout.custom_marker_view);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface) 每次 MarkerView 重绘此方法都会被调用，并为您提供更新它显示的内容的机会
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(e.getY()+"");
        super.refreshContent(e, highlight);
    }


    /*
     * offset 是以點到的那個點作為 (0,0) 中心然後往右下角畫出來 该方法是让markerview现实到坐标的上方
     * 所以如果要顯示在點的上方
     * X=寬度的一半，負數
     * Y=高度的負數
     */
    @Override
    public MPPointF getOffset() {
        // Log.e("ddd", "width:" + (-(getWidth() / 2)) + "height:" + (-getHeight()));
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
