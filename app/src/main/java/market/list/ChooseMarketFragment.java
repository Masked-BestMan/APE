package market.list;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.MarketAdapter;
import businessmonitor.com.example.newbusinessmonitor.R;

/**
 * Created by Administrator on 2018/5/8.
 */

public class ChooseMarketFragment extends Fragment {

    private ListView listView;
    private MarketAdapter marketAdapter;
    private List<AbstractDataBean> marketList=new ArrayList<>();
    private OnMarketListSelectedListener onMarketListSelectedListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_market,container,false);
        listView = (ListView)view.findViewById(R.id.market_list_view);
        marketAdapter = new MarketAdapter(getContext(),R.layout.market_item,marketList);
        listView.setAdapter(marketAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onMarketListSelectedListener.onSelected(position);
            }
        });
    }

    public void loadMarket(List<AbstractDataBean> data) {
        marketList.addAll(data);
        marketAdapter.notifyDataSetChanged();
    }

    public void setOnMarketListSelectedListener(OnMarketListSelectedListener onMarketListSelectedListener) {
        this.onMarketListSelectedListener = onMarketListSelectedListener;
    }

    public interface OnMarketListSelectedListener{
        void onSelected(int position);
    }
}