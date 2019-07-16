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

        dates = GlobalUtil.getInstance().databaseHelper.getAvailableDate();

        if(!dates.contains(DateUtil.getFormattedDate())){
            dates.addLast(DateUtil.getFormattedDate());
        }
        for(String date : dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }

    /**
     *
     */
    public void reload(){
        for(MainFragment fragment:fragments){
            fragment.reload();
        }
    }

    public int getLastIndex(){
        return fragments.size()-1;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    public String getDateStr(int index){
        return dates.get(index);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int getTotalCost(int index){
        return fragments.get(index).getTotalCost();
    }
    public int getTotalIncome(int index){
        return fragments.get(index).getTotalIncome();
    }
}
