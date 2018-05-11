package businessmonitor.com.example.newbusinessmonitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */

public class MarketAdapter extends ArrayAdapter<AbstractDataBean> {

    private int resourceId;
    public MarketAdapter(Context context,int textViewResourceId, List<AbstractDataBean> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        UserInfoBean market = (UserInfoBean) getItem(position);
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView  marketName = (TextView)view.findViewById(R.id.market_name);
        marketName.setText(market.getMarket_name());
        return view;
    }
}
