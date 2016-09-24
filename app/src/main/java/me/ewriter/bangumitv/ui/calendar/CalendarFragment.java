package me.ewriter.bangumitv.ui.calendar;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.event.OpenNavigationEvent;
import me.ewriter.bangumitv.ui.search.SearchActivity;
import me.ewriter.bangumitv.utils.RxBus;

/**
 * Created by Zubin on 2016/9/6.
 */
public class CalendarFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initView(boolean reused) {
        if (reused)
            return;

        mViewPager = (ViewPager) getRootView().findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) getRootView().findViewById(R.id.tablayout);
        mToolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);

        setupToolbar();
        setupViewPager();
    }

    @Override
    protected void loadData() {

    }

    private void setupToolbar() {
        mToolbar.setTitle(getString(R.string.nav_daily_calendar));
        mToolbar.inflateMenu(R.menu.search_menu);
        mToolbar.setNavigationIcon(R.drawable.ic_action_drawer);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new OpenNavigationEvent());
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.toolbar_search) {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
                return false;
            }
        });
    }

    private void setupViewPager() {
        String[] tabNameArray = {getString(R.string.monday), getString(R.string.tuesday)
                , getString(R.string.wednesday), getString(R.string.thursday)
                , getString(R.string.friday), getString(R.string.saturday)
                , getString(R.string.sunday)};

        final CalendarAdapter adapter = new CalendarAdapter(getChildFragmentManager());
        for (int i = 0; i < tabNameArray.length; i++) {
            adapter.addFragment(CalendarChildFragment.newInstance(i), tabNameArray[i]);
        }
        mViewPager.post(new Runnable() {
            @Override
            public void run() {

                mViewPager.setAdapter(adapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
