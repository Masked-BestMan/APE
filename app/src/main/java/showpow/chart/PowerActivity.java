package showpow.chart;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import UtilBean.DBUtil;
import UtilBean.DataAgent;
import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.IChartView;
import businessmonitor.com.example.newbusinessmonitor.MarketDataBean;
import businessmonitor.com.example.newbusinessmonitor.R;
import businessmonitor.com.example.newbusinessmonitor.MyFragmentAdapter;
import businessmonitor.com.example.newbusinessmonitor.UserInfoBean;
import showlea.chart.LeaseHoldFragment;

public class PowerActivity extends AppCompatActivity implements IChartView{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息
    private DataAgent dataAgent;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int market_id;   //选中的商会id
    private MyFragmentAdapter adapter;
    private ArrayList<Fragment> list;
    private final String[] indexTitle = {"薪酬平均数", "雇员教育水平占比(高中以上)", "雇员平均数"};

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
        setContentView(R.layout.activity_power);
        userList= (ArrayList<AbstractDataBean>) getIntent().getSerializableExtra("user_market_list");
        market_id=((UserInfoBean)userList.get(0)).getMarket_id();  //默认选中第一个商会
        viewPager = (ViewPager) findViewById(R.id.power_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.power_tab_layout);

        initFragment();
        dataAgent=new DataAgent(this,this);
        dataAgent.requestData(DBUtil.TABLE2,"select * from "+ DBUtil.TABLE2+" where market_id="+market_id+" order by month asc");

    }

    private void initFragment() {
        list = new ArrayList<>();
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), list, indexTitle);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onInfoUpdate(ArrayList<AbstractDataBean> info) {
        float[] salary_avg=new float[info.size()];
        float[] education_level=new float[info.size()];
        float[] employee_average=new float[info.size()];
        for (int i=0;i<info.size();i++){
            salary_avg[i]=((MarketDataBean)info.get(i)).getSalary_avg();
            education_level[i]=((MarketDataBean)info.get(i)).getEducation_level();
            employee_average[i]=((MarketDataBean)info.get(i)).getEmployee_average();
        }

        list.clear();
        list.add(new PowerFragment(salary_avg));
        list.add(new PowerFragment(education_level));
        list.add(new PowerFragment(employee_average));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataAgent!=null)
            dataAgent.stopRequest();
    }
}

