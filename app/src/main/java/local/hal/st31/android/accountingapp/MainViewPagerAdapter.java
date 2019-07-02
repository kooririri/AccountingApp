package local.hal.st31.android.accountingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    LinkedList<MainFragment> fragments = new LinkedList<>();
    LinkedList<String> dates = new LinkedList<>();

    public MainViewPagerAdapter(FragmentManager fm){
        super(fm);
        initFragment();
    }

    private void initFragment(){
        dates.add("2019-06-28");
        dates.add("2019-06-29");
        dates.add("2019-06-30");
        for(String date : dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }

    public int getLastIndex(){
        return fragments.size()-1;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
