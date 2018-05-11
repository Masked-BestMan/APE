package businessmonitor.com.example.newbusinessmonitor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> data;
    private String[] titles;
    public MyFragmentAdapter(FragmentManager fm,ArrayList<Fragment> data,String[] titles) {
        super(fm);
        this.data=data;
        this.titles=titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles==null)
            return super.getPageTitle(position);
        else
            return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public int getItemPosition(Object object) {
//必须返回该值调用notifyDataSetChanged才有效
        return POSITION_NONE;
    }

//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        Log.d("MyFragment","fragment销毁");
//    }
}

