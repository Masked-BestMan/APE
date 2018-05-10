package showevn.chart;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import UtilBean.DBUtil;
import UtilBean.DataAgent;
import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.IChartView;
import businessmonitor.com.example.newbusinessmonitor.MarketDataBean;
import businessmonitor.com.example.newbusinessmonitor.MyFragmentAdapter;
import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.UserInfoBean;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class EnvironmentActivity extends AppCompatActivity implements IChartView{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息
    private DataAgent dataAgent;
    private ViewPager viewPager;
    private int market_id;   //选中的商会id
    private TabLayout tabLayout;
    private final String[] indexTitle={"保安人数","人流量","餐厅翻台率"};
    private MyFragmentAdapter adapter;
    private ArrayList<Fragment> list;
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        userList= (ArrayList<AbstractDataBean>) getIntent().getSerializableExtra("user_market_list");
        market_id=((UserInfoBean)userList.get(0)).getMarket_id();  //默认选中第一个商会
        tabLayout = (TabLayout) findViewById(R.id.enviroment_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_environment);
        Button backButton = (Button) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initFragment();
        dataAgent=new DataAgent(this,this);
        dataAgent.requestData(DBUtil.TABLE2,"select * from "+ DBUtil.TABLE2+" where market_id="+market_id+" order by month asc");
    }

    private void initFragment(){
        list = new ArrayList<>();
        adapter=new MyFragmentAdapter(getSupportFragmentManager(), list,indexTitle);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onInfoUpdate(ArrayList<AbstractDataBean> info) {
        float[] security_num=new float[info.size()];
        float[] visitors_num=new float[info.size()];
        float[] table_rate=new float[info.size()];
        for (int i=0;i<info.size();i++){
            security_num[i]=((MarketDataBean)info.get(i)).getSecurity_num();
            visitors_num[i]=((MarketDataBean)info.get(i)).getVisitors_flowrate();
            table_rate[i]=((MarketDataBean)info.get(i)).getTable_rate();
        }
        list.clear();
        EnvironmentFragment a=new EnvironmentFragment(security_num);
        EnvironmentFragment b=new EnvironmentFragment(visitors_num);
        EnvironmentFragment c=new EnvironmentFragment(table_rate);
        list.add(a);
        list.add(b);
        list.add(c);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dataAgent.stopRequest();
    }
}

