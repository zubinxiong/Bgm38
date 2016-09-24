package me.ewriter.bangumitv.ui.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.ui.calendar.CalendarChildFragment;

/**
 * Created by Zubin on 2016/9/8.
 */
public class CalendarAdapter extends FragmentPagerAdapter {

    List<CalendarChildFragment> mList = new ArrayList<>();
    List<String> name = new ArrayList<>();

    public CalendarAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(CalendarChildFragment fragment, String title) {
        mList.add(fragment);
        name.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return name.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
