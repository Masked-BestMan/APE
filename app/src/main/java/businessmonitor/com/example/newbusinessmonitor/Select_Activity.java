package businessmonitor.com.example.newbusinessmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import show.chart.Show_Chart_Activity;
import showevn.chart.EnvironmentActivity;
import showinv.chart.InnovationActivity;
import showlea.chart.LeaseHoldActivity;
import showpow.chart.PowerActivity;

/**
 * 主界面Activity
 */
public class Select_Activity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<AbstractDataBean> userList;    //该列表保存了该用户所能查看的商会信息

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        userList= (ArrayList<AbstractDataBean>) getIntent().getSerializableExtra("user_market_list");

        Button but_climate = (Button) findViewById(R.id.but_climate);
        Button but_leasehold = (Button) findViewById(R.id.but_leasehold);
        Button but_environmental = (Button) findViewById(R.id.but_environmental);
        Button but_human = (Button) findViewById(R.id.but_human);
        Button but_innovation = (Button) findViewById(R.id.but_innovation);

        but_climate.setOnClickListener(this);
        but_leasehold.setOnClickListener(this);
        but_environmental.setOnClickListener(this);
        but_human.setOnClickListener(this);
        but_innovation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_climate:
                Intent intent1 = new Intent(this, Show_Chart_Activity.class);
                startActivity(intent1);
                break;

            case R.id.but_leasehold:
                Intent intent2 = new Intent(this, LeaseHoldActivity.class);
                intent2.putExtra("user_market_list",userList);
                startActivity(intent2);
                break;

            case R.id.but_environmental:
                Intent intent3 = new Intent(this, EnvironmentActivity.class);
                intent3.putExtra("user_market_list",userList);
                startActivity(intent3);
                break;

            case R.id.but_human:
                Intent intent4 = new Intent(this, PowerActivity.class);
                intent4.putExtra("user_market_list",userList);
                startActivity(intent4);
                break;

            case R.id.but_innovation:
                Intent intent5 = new Intent(this, InnovationActivity.class);
                intent5.putExtra("user_market_list",userList);
                startActivity(intent5);
                break;
        }

    }
}
