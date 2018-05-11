package showevn.chart;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import market.list.ChooseMarketFragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class EnvironmentActivity extends AppCompatActivity implements IChartView{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息
    private DataAgent dataAgent;
    private ViewPager viewPager;
    private int market_id;   //选中的商会id
    private String market_name;   //选中的商会名
    private TabLayout tabLayout;
    private final String[] indexTitle={"保安人数","人流量","餐厅翻台率"};
    private DrawerLayout drawerLayout;
    private MyFragmentAdapter adapter;
    private ArrayList<Fragment> list;
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_environment);

        userList= (ArrayList<AbstractDataBean>) getIntent().getSerializableExtra("user_market_list");

        market_id=((UserInfoBean)userList.get(0)).getMarket_id();  //默认选中第一个商会
        market_name=((UserInfoBean)userList.get(0)).getMarket_name();

        tabLayout = (TabLayout) findViewById(R.id.enviroment_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_environment);
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
                if (((UserInfoBean)userList.get(position)).getPermissions()[1]){
                    list.clear();  //将fragment列表清空防止重复
                    market_id=((UserInfoBean)userList.get(position)).getMarket_id();
                    market_name=((UserInfoBean)userList.get(position)).getMarket_name();
                    Log.d("Lease","用户Id："+market_id);
                    dataAgent.requestData(DBUtil.TABLE2,"select * from "+ DBUtil.TABLE2+" where market_id="+market_id+" order by month asc");
                }else{
                    Toast.makeText(EnvironmentActivity.this,"无权限查看",Toast.LENGTH_SHORT).show();
                }
            }
        });
        chooseMarketFragment.loadMarket(userList);
        initFragment();
        dataAgent=new DataAgent(this,this);

        //判断用户是否有权限查看第一个商会的租赁指数
        if (((UserInfoBean)userList.get(0)).getPermissions()[1]){
            dataAgent.requestData(DBUtil.TABLE2,"select * from "+ DBUtil.TABLE2+" where market_id="+market_id+" order by month asc");
        }
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
        list.add(new EnvironmentFragment(market_name,security_num));
        list.add(new EnvironmentFragment(market_name,visitors_num));
        list.add(new EnvironmentFragment(market_name,table_rate));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataAgent!=null)
            dataAgent.stopRequest();
    }
}

