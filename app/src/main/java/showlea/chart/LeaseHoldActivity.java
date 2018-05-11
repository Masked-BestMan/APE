package showlea.chart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import UtilBean.DBUtil;
import UtilBean.DataAgent;
import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.IChartView;
import businessmonitor.com.example.newbusinessmonitor.MarketDataBean;
import businessmonitor.com.example.newbusinessmonitor.MyFragmentAdapter;
import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.UserInfoBean;
import market.list.ChooseMarketFragment;

public class LeaseHoldActivity extends AppCompatActivity implements IChartView{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息
    private DataAgent dataAgent;
    private ViewPager viewPager;
    private int market_id;   //选中的商会id
    private String market_name;   //选中的商会名
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
        market_name=((UserInfoBean)userList.get(0)).getMarket_name();

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
        Button navButton = (Button) findViewById(R.id.market_list_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        ChooseMarketFragment chooseMarketFragment = (ChooseMarketFragment) getSupportFragmentManager().findFragmentById(R.id.choose_market_fragment);
        chooseMarketFragment.setOnMarketListSelectedListener(new ChooseMarketFragment.OnMarketListSelectedListener() {
            @Override
            public void onSelected(int position) {
                drawerLayout.closeDrawer(GravityCompat.END);
                //判断用户是否有权限查看选中商会的租赁指数
                if (((UserInfoBean)userList.get(position)).getPermissions()[4]){
                    list.clear();  //将fragment列表清空防止重复
                    market_id=((UserInfoBean)userList.get(position)).getMarket_id();
                    market_name=((UserInfoBean)userList.get(position)).getMarket_name();
                    Log.d("Lease","用户Id："+market_id);
                    dataAgent.requestData(DBUtil.TABLE2,"select * from "+ DBUtil.TABLE2+" where market_id="+market_id+" order by month asc");
                }else{
                    Toast.makeText(LeaseHoldActivity.this,"无权限查看",Toast.LENGTH_SHORT).show();
                }
            }
        });
        chooseMarketFragment.loadMarket(userList);
        initFragment();
        dataAgent=new DataAgent(this,this);

        //判断用户是否有权限查看第一个商会的租赁指数
        if (((UserInfoBean)userList.get(0)).getPermissions()[4]){
            dataAgent.requestData(DBUtil.TABLE2,"select * from "+ DBUtil.TABLE2+" where market_id="+market_id+" order by month asc");
        }

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
        Log.d("Lease","更新"+info.toString());
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
        list.add(new LeaseHoldFragment(market_name,rent));
        list.add(new LeaseHoldFragment(market_name,admission_rate));
        list.add(new LeaseHoldFragment(market_name,management_fee));
        list.add(new LeaseHoldFragment(market_name,transfer_fee));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataAgent!=null)
            dataAgent.stopRequest();
    }

}