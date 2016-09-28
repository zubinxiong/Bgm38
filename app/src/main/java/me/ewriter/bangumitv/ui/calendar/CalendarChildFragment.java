package me.ewriter.bangumitv.ui.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.dao.BangumiCalendar;
import me.ewriter.bangumitv.ui.calendar.adapter.CalendarItemAdapter;
import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by Zubin on 2016/9/8.
 */
public class CalendarChildFragment extends BaseFragment implements CalendarContract.View{

    private int mPosition;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressbar;
    private CalendarItemAdapter mAdapter;
    private CalendarContract.Presenter mPresenter;

    List<BangumiCalendar> mCalendarList;

    public static CalendarChildFragment newInstance(int position) {
        CalendarChildFragment fragment = new CalendarChildFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_calender_child;
    }

    @Override
    protected void initView(boolean reused) {
        if (reused)
            return;

        mPresenter = new CalendarPresenter(this);
        mPresenter.subscribe();
        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.calendar_recycleviews);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getRootView().findViewById(R.id.swipe_refresh_layout);
        mProgressbar = (ProgressBar) getRootView().findViewById(R.id.loadingProgress);
        mCalendarList = new ArrayList<>();

        setupRecyclerView();
        setupRefreshLayout();

    }

    private void setupRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.forceRefresh();
                mPresenter.loadData(mPosition);
            }
        });
    }

    private void setupRecyclerView() {
        mAdapter = new CalendarItemAdapter(mCalendarList, getActivity());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnCalendarItemClickListener(new CalendarItemAdapter.onCalendarItemListener() {
            @Override
            public void onCalendarClick(View view, BangumiCalendar calendar) {
                mPresenter.openBangumiDetail(getActivity(), view, calendar);
            }
        });
    }

    @Override
    protected void loadData() {
        // 默认只会在fragment第一次运行的时候调用，这里手动修改了懒加载的方法每次都会调用
        // 如果要自己调用，需要手动调用prepareFetchData(true)
        if (mCalendarList.size() == 0) {
            LogUtil.d(LogUtil.ZUBIN, "loadData "+ mPosition);
            showLoading();
            mPresenter.loadData(mPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(LogUtil.ZUBIN, "CalendarChildFragment onDestroyView " + mPosition);
        hideLoading();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(CalendarContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refresh(List<BangumiCalendar> calendarList) {
        mCalendarList.clear();
        mCalendarList.addAll(calendarList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        if (mRecyclerView != null) {
            Snackbar snackbar = Snackbar.make(mRecyclerView, getString(R.string.update_failed), Snackbar.LENGTH_SHORT);
            snackbar.show();
            snackbar.setAction(getString(R.string.update_retry), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    mPresenter.loadData(mPosition);
                }
            });
        }

    }
}
