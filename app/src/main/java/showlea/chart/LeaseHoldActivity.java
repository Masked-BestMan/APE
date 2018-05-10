package showlea.chart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import businessmonitor.com.example.newbusinessmonitor.MyFragmentAdapter;
import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.UserInfoBean;

public class LeaseHoldActivity extends AppCompatActivity implements IChartView{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息
    private DataAgent dataAgent;
    private ViewPager viewPager;
    private Button navButton;
    private int market_id;   //选中的商会id
    private MyFragmentAdapter adapter;
    private ArrayList<Fragment> list;
    private final String[] indexTitle={"租金","入驻率","商铺管理费","商铺转手费"};
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_hold);
        userList= (ArrayList<AbstractDataBean>) getIntent().getSerializableExtra("user_market_list");
        market_id=((UserInfoBean)userList.get(0)).getMarket_id();  //默认选中第一个商会
        viewPager = (ViewPager)findViewById(R.id.viewpager_leasthold);
        tabLayout = (TabLayout) findViewById(R.id.lease_tab_layout);
        Button backButton = (Button) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navButton = (Button)findViewById(R.id.market_list_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
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
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onInfoUpdate(ArrayList<AbstractDataBean> info) {
        float[] rent=new float[info.size()];
        float[] admission_rate=new float[info.size()];
        float[] management_fee=new float[info.size()];
        float[] transfer_fee=new float[info.size()];
        for (int i=0;i<info.size();i++){
            rent[i]=((MarketDataBean)info.get(i)).getRent();
            admission_rate[i]=((MarketDataBean)info.get(i)).getAdmission_rate();
            management_fee[i]=((MarketDataBean)info.get(i)).getManagement_fee();
            transfer_fee[i]=((MarketDataBean)info.get(i)).getTransfer_fee();
        }

        list.clear();
        list.add(new LeaseHoldFragment(rent));
        list.add(new LeaseHoldFragment(admission_rate));
        list.add(new LeaseHoldFragment(management_fee));
        list.add(new LeaseHoldFragment(transfer_fee));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataAgent!=null)
            dataAgent.stopRequest();
    }

}