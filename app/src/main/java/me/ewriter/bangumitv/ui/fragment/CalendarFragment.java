package me.ewriter.bangumitv.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.event.OpenNavgationEvent;
import me.ewriter.bangumitv.ui.activity.SearchActivity;
import me.ewriter.bangumitv.ui.adapter.CalendarPagerAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;

/**
 * Created by zubin on 16/7/30.
 */
public class CalendarFragment extends BaseFragment {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;

    public CalendarFragment() {}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isSubscribe() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_calendar;
    }


    @Override
    protected void initView(boolean resued) {
        if (resued) {
            return;
        }

        mViewPager = (ViewPager) getRootView().findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) getRootView().findViewById(R.id.tablayout);
        mToolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);

        setupToolbar();
        setupViewPager(mViewPager);
    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_drawer);
        mToolbar.setTitle("每日放送");
        mToolbar.inflateMenu(R.menu.search_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenNavgationEvent());
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

    private void setupViewPager(ViewPager mViewPager) {
        String[] tabNameArray = {getString(R.string.monday), getString(R.string.tuesday)
                , getString(R.string.wednesday), getString(R.string.thursday)
                , getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday)};

        CalendarPagerAdapter adapter = new CalendarPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < tabNameArray.length; i++) {
            adapter.addFragment(CalendarPageFragment.newInstance(i), tabNameArray[i]);
        }

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment oncreateview");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onStop");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(LogUtil.ZUBIN, "CalendarFragment onDestroy");
    }
}
