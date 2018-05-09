package market.list;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import businessmonitor.com.example.newbusinessmonitor.MarketAdapter;
import businessmonitor.com.example.newbusinessmonitor.MarketBean;
import businessmonitor.com.example.newbusinessmonitor.R;

/**
 * Created by Administrator on 2018/5/8.
 */

public class ChooseMarketFragment extends Fragment {

    private ListView listView;
    private MarketAdapter marketAdapter;
    private TextView titlename;
    private List<MarketBean> marketlist=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_market,container,false);
        listView = (ListView)view.findViewById(R.id.market_list_view);
        titlename = (TextView)view.findViewById(R.id.title_market_text) ;
        marketAdapter = new MarketAdapter(getContext(),R.layout.market_item,marketlist);
        listView.setAdapter(marketAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //添加Item响应事件
                titlename.setText(marketlist.get(position).getMarket_name());
            }
        });

        queryMarket();
    }

    private void queryMarket() {
        titlename.setText("请选择您要查看的商会");
        marketlist.add(new MarketBean(1,"白云皮具城"));
        marketlist.add(new MarketBean(2,"广东芳村茶叶城"));
        marketlist.add(new MarketBean(3,"广州国际轻纺城"));
        marketlist.add(new MarketBean(4,"广州眼镜城"));
        marketlist.add(new MarketBean(5,"国际皮革五金城"));
        marketlist.add(new MarketBean(6,"红棉国际时装城"));
        marketlist.add(new MarketBean(7,"华林国际"));
        marketlist.add(new MarketBean(8,"清平医药中心"));
        marketlist.add(new MarketBean(9,"万菱广场"));
        marketlist.add(new MarketBean(10,"五洲城"));
        marketAdapter.notifyDataSetChanged();
        listView.setSelection(0);
    }


}