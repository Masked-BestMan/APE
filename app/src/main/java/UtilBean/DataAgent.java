package UtilBean;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.IChartView;

public class DataAgent {
    /**
     * 数据代理类，用于视图层(Fragment)发起更新数据请求，由该类负责网络请求并将服务器返回的数据解析好返回给视图层
     */
    private IChartView iChartView;
    private String key;   //fragment的名字作为缓存的key
    private Context context;
    public DataAgent(Context context,IChartView iChartView) {
        this.context=context;
        this.iChartView = iChartView;
    }

    /**
     * 指数模块请求数据如果在缓存中有就不请求网络
     *
     * @param table 要查询的表名
     * @param sql   查询语句
     */
    public void requestData(String table, String sql) {
        if (sql!=null)
            key = ((Activity) iChartView).getClass().getSimpleName().toLowerCase()+"_"+sql.hashCode();
        Log.d("DataAgent",key);
        ArrayList<AbstractDataBean> as=new ArrayList<>();
        for (int i=1;i<=12;i++){
            AbstractDataBean cache=CacheUtil.getCacheUtilInstance(context).getStringFromCache(key+"_"+i);
            if (cache==null)break;
            as.add(cache);
        }

        //如果取不到12个月的数据，则重新请求网络
        if (as.size()==12)
            iChartView.onInfoUpdate(as);
        else
            DBUtil.getHttpUtilInstance().getChartData(table, sql, new DBUtil.OnReceiveResultListener() {
                @Override
                public void onReceived(ArrayList<AbstractDataBean> response) {
                    Log.d("data_agent", response.toString());
                    int month=1;
                    for (AbstractDataBean ab:response) {
                        Log.d("data_agent","多少月："+response.size());
                        CacheUtil.getCacheUtilInstance(context).addStringToCache(key+"_"+month,ab);  //以每次一个月来保存
                        month++;
                    }
                    iChartView.onInfoUpdate(response);
                }

                @Override
                public void onError() {

                }
            });
    }

    /*
      如果数据还没请求完成的情况下用户退出界面，则停止请求，释放系统资源
     */
    public void stopRequest() {
        DBUtil.getHttpUtilInstance().removeTask();
    }


}
