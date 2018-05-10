package showinv.chart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import UtilBean.DBUtil;
import UtilBean.DataAgent;
import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.IChartView;
import businessmonitor.com.example.newbusinessmonitor.MarketDataBean;
import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.MyFragmentAdapter;
import businessmonitor.com.example.newbusinessmonitor.UserInfoBean;

public class InnovationActivity extends AppCompatActivity implements IChartView{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息
    private DataAgent dataAgent;
    private ViewPager viewPager;
    private int market_id;   //选中的商会id
    private TabLayout tabLayout;
    private final String[] indexTitle={"商铺注册商标的数量","电商数量变化趋势","各行业定制化商品平均比率"};
    private MyFragmentAdapter adapter;
    private ArrayList<Fragment> list;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innovation);
        userList= (ArrayList<AbstractDataBean>) getIntent().getSerializableExtra("user_market_list");
        market_id=((UserInfoBean)userList.get(0)).getMarket_id();  //默认选中第一个商会
        tabLayout = (TabLayout) findViewById(R.id.innovation_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_innovation);
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

    private void initFragment() {

        list = new ArrayList<>();

        adapter=new MyFragmentAdapter(getSupportFragmentManager(), list,indexTitle);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onInfoUpdate(ArrayList<AbstractDataBean> info) {
        float[] registered_num=new float[info.size()];
        float[] ecommerce_change=new float[info.size()];
        float[] customized_avg=new float[info.size()];
        for (int i=0;i<info.size();i++){
            registered_num[i]=((MarketDataBean)info.get(i)).getRegistered_num();
            ecommerce_change[i]=((MarketDataBean)info.get(i)).getEcommerce_change();
            customized_avg[i]=((MarketDataBean)info.get(i)).getCustomized_avg();
        }

        list.clear();
        list.add(new InnovationFragment(registered_num));
        list.add(new InnovationFragment(ecommerce_change));
        list.add(new InnovationFragment(customized_avg));
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataAgent.stopRequest();
    }
}
