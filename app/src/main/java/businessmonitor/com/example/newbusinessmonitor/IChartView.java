package businessmonitor.com.example.newbusinessmonitor;

import java.util.ArrayList;

public interface IChartView {
    /**
     * 每个视图层都要声明该接口,该方法用于通知视图层更新显示的数据
     */
    void onInfoUpdate(ArrayList<AbstractDataBean> info);
}
