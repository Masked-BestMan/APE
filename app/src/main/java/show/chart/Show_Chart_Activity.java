package show.chart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import UtilBean.DBUtil;
import UtilBean.DataAgent;
import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.IChartView;
import businessmonitor.com.example.newbusinessmonitor.MarketIndexBean;
import businessmonitor.com.example.newbusinessmonitor.MyFragmentAdapter;
import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.SyntheticIndexBean;

/**
 * ---------------------------------------------
 * 展示景气指数的Activity
 * ---------------------------------------------
 */
public class Show_Chart_Activity extends AppCompatActivity implements IChartView{

    private ViewPager viewPager;
    private MyFragmentAdapter myAdapter;
    private DataAgent dataAgent;
    private ArrayList<Fragment> list;
    private TabLayout tabLayout;
    private final String[] indexTitle={"景气指数","合成指数"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__chart);
        tabLayout = (TabLayout) findViewById(R.id.boom_tab_layout);
        viewPager = (ViewPager)findViewById(R.id.viewpager_boom);
        Button backButton = (Button) findViewById(R.id.backbutton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iniViewPager();
        dataAgent=new DataAgent(this,this);
        dataAgent.requestData(DBUtil.TABLE1,"select * from "+ DBUtil.TABLE1+" order by month_index asc");
    }

    private void iniViewPager() {
        list = new ArrayList<>();
        //Fragment 列表
        myAdapter = new MyFragmentAdapter(getSupportFragmentManager(), list,indexTitle);
        viewPager.setAdapter(myAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onInfoUpdate(ArrayList<AbstractDataBean> info) {
        Log.d("BoomFragment","指数表bean数："+info.size());
        if (info.get(0) instanceof MarketIndexBean){
            float[][] index_data=new float[12][6];
            int month=0;
            for (AbstractDataBean abstractDataBean:info){
                MarketIndexBean md= (MarketIndexBean) abstractDataBean;
                index_data[month][0]=md.getInnovation_index();
                index_data[month][1]=md.getEnvironmental_index();
                index_data[month][2]=md.getFastBatch_index();
                index_data[month][3]=md.getLogistics_index();
                index_data[month][4]=md.getLeasehold_index();
                index_data[month][5]=md.getHuman_index();
                month++;
            }
            list.add(new BoomFragment(index_data));
            dataAgent.requestData(DBUtil.TABLE3,"select * from "+ DBUtil.TABLE3+" order by month_index asc");
        }else if (info.get(0) instanceof SyntheticIndexBean){
            float[][] index_data=new float[12][3];
            int month=0;
            for (AbstractDataBean abstractDataBean:info){
                SyntheticIndexBean s= (SyntheticIndexBean) abstractDataBean;
                index_data[month][0]=s.getAntecedent_index();
                index_data[month][1]=s.getUnanimous_index();
                index_data[month][2]=s.getLag_index();
                month++;
            }
            list.add(new CompositeFragment(index_data));
        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataAgent.stopRequest();
    }
}
