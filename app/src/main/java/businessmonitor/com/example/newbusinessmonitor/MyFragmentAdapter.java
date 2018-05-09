package businessmonitor.com.example.newbusinessmonitor;

/**
 * Created by Administrator on 2018/4/21.
 */
import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;

        import java.util.ArrayList;

/**
 *  FragmentPagerAdapter
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

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
}

