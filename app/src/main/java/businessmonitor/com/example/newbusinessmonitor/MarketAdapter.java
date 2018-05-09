package businessmonitor.com.example.newbusinessmonitor;

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

public class MarketAdapter extends ArrayAdapter<MarketBean> {

    private int resourceId;
    public MarketAdapter(Context context,int textviewResourceId, List<MarketBean> objects){
        super(context,textviewResourceId,objects);
        resourceId = textviewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MarketBean market = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView  marketname = (TextView)view.findViewById(R.id.market_name);
        marketname.setText(market.getMarket_name());
        return view;
    }
}
