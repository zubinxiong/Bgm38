package me.ewriter.bangumitv.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.response.Calendar;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.dao.BangumiCalendar;
import me.ewriter.bangumitv.dao.BangumiCalendarDao;
import me.ewriter.bangumitv.dao.DaoSession;
import me.ewriter.bangumitv.event.CalendarUpdateEvent;
import me.ewriter.bangumitv.ui.activity.BangumiDetailActivity;
import me.ewriter.bangumitv.ui.adapter.CalendarItemAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.PreferencesUtils;
import me.ewriter.bangumitv.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zubin on 16/7/30.
 */
public class CalendarPageFragment extends BaseFragment {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mPosition;
    private CalendarItemAdapter calendarItemAdapter;
    List<BangumiCalendar> mCalendarList;


    public CalendarPageFragment() {
    }

    public static CalendarPageFragment newInstance(int position) {
        LogUtil.d(LogUtil.ZUBIN, "CalendarPageFragment newInstance" + position);
        CalendarPageFragment fragment = new CalendarPageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d(LogUtil.ZUBIN, "CalendarPageFragment onCreate" + mPosition);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
    }

    @Override
    protected boolean isSubscribe() {
        // CalendarFragment 发event 先于这里注册，所以无法收到
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_calender_list;
    }

    @Override
    protected void initView(boolean resued) {

        if (resued)
            return;

        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.calendar_recycleviews);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getRootView().findViewById(R.id.swipe_refresh_layout);

        setupRecyclerView();
        setupSwipeRefreshLayout();

    }


    private void setupRecyclerView() {

        mCalendarList = new ArrayList<>();

        calendarItemAdapter = new CalendarItemAdapter(BangumiApp.sAppCtx, mCalendarList);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(calendarItemAdapter);

        calendarItemAdapter.setOnCalendarItemClickListener(new CalendarItemAdapter.onCalendarItemListener() {

            @Override
            public void onCalendarClick(View view, BangumiCalendar calendar) {
                Intent intent = new Intent(getActivity(), BangumiDetailActivity.class);
                intent.putExtra("bangumiId", calendar.getBangumi_id());
                intent.putExtra("name", calendar.getName_cn() + "(" + calendar.getName_jp() + ")");
                intent.putExtra("common_url", calendar.getCommon_image());
                intent.putExtra("large_url", calendar.getLarge_image());
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "img").toBundle());
//                startActivity(intent);
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2, R.color.refresh_progress_3);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.d(LogUtil.ZUBIN, "onRefresh");
                mSwipeRefreshLayout.setRefreshing(true);
                requestDataRefresh();
            }
        });

        mSwipeRefreshLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mSwipeRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadDataFromDB();
                    }
                });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onCreateView" + mPosition);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onActivityCreated" + mPosition);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onViewCreated" +mPosition);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onStart" + mPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onResume" + mPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onPause" + mPosition);
    }


    private void loadDataFromDB() {
        DaoSession daoSession = BangumiApp.sAppCtx.getDaoSession();

        List<BangumiCalendar> queryResultList = daoSession.getBangumiCalendarDao()
                .queryBuilder().where(BangumiCalendarDao.Properties.Air_weekday.eq(mPosition + 1))
                .build().list();

        if (queryResultList.size() == 0) {
            if (mPosition == 0) {
                requestDataRefresh();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            // 虽然本地数据库有数据，但是如果超过6小时未刷新则也需要重新请求
            long deta = System.currentTimeMillis() - PreferencesUtils.getLong(BangumiApp.sAppCtx, MyConstants.CALENDAR_REFRESH_KEY, 0);
            if (deta > MyConstants.CALENDAR_REFRESH_TIME && mPosition == 0) {
                requestDataRefresh();
            } else {
                mCalendarList.clear();
                mCalendarList.addAll(queryResultList);
                calendarItemAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void requestDataRefresh() {
        BaseActivity.sBangumi.listCalendar().enqueue(new Callback<List<Calendar>>() {
            @Override
            public void onResponse(Call<List<Calendar>> call, Response<List<Calendar>> response) {
                LogUtil.d(LogUtil.ZUBIN, "CalendarUpdateEvent onResponse");
                if (response.isSuccessful() && response.body() != null) {
                    EventBus.getDefault().post(new CalendarUpdateEvent(response.body()));
                }

            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {
                LogUtil.d(LogUtil.ZUBIN, "CalendarPagerFragment onFailure");
                mSwipeRefreshLayout.setRefreshing(false);
                final Snackbar snackbar = Snackbar.make(mRecyclerView, getString(R.string.update_failed), Snackbar.LENGTH_SHORT);
                snackbar.show();
                snackbar.setAction(getString(R.string.update_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        requestDataRefresh();
                        snackbar.dismiss();
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCurrentList(CalendarUpdateEvent calendars) {
        if (calendars != null) {
            mCalendarList.clear();
            for (int i = 0; i < calendars.getCalendar().size(); i++) {
                Calendar calendar = calendars.getCalendar().get(i);

                for (int j = 0; j < calendar.getItems().size(); j++) {
                    BangumiCalendar entity = new BangumiCalendar();

                    entity.setAir_weekday(calendar.getItems().get(j).getAir_weekday());
                    entity.setName_cn(calendar.getItems().get(j).getName_cn());
                    entity.setBangumi_id(calendar.getItems().get(j).getId());
                    entity.setBangumi_total(calendar.getItems().get(j).getRating().getTotal());
                    entity.setBangumi_average(calendar.getItems().get(j).getRating().getScore());
                    if (calendar.getItems().get(j).getImages() != null) {
                        entity.setCommon_image(calendar.getItems().get(j).getImages().getCommon());
                        entity.setLarge_image(calendar.getItems().get(j).getImages().getLarge());
                    }
                    entity.setRank(calendar.getItems().get(j).getRank());
                    entity.setName_jp(calendar.getItems().get(j).getName());

                    if ((mPosition + 1) == calendar.getItems().get(i).getAir_weekday()) {
                        mCalendarList.add(entity);
                    }
                }

            }
            calendarItemAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            PreferencesUtils.putLong(BangumiApp.sAppCtx, MyConstants.CALENDAR_REFRESH_KEY, System.currentTimeMillis());
        }
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void insertToDBEvent(CalendarUpdateEvent calendars) {
        LogUtil.d(LogUtil.ZUBIN, "CalendarUpdateEvent insertToDbEvent");

        if (calendars != null && calendars.getCalendar() != null) {

            DaoSession daoSession = BangumiApp.sAppCtx.getDaoSession();

            // 存到数据库中的所有数据
            List<BangumiCalendar> mDbList = new ArrayList<>();

            for (int i = 0; i < calendars.getCalendar().size(); i++) {
                Calendar calendar = calendars.getCalendar().get(i);
                for (int j = 0; j < calendar.getItems().size(); j++) {
                    BangumiCalendar entity = new BangumiCalendar();

                    entity.setAir_weekday(calendar.getItems().get(j).getAir_weekday());
                    entity.setName_cn(calendar.getItems().get(j).getName_cn());
                    entity.setBangumi_id(calendar.getItems().get(j).getId());
                    entity.setBangumi_total(calendar.getItems().get(j).getRating().getTotal());
                    entity.setBangumi_average(calendar.getItems().get(j).getRating().getScore());
                    if (calendar.getItems().get(j).getImages() != null) {
                        entity.setCommon_image(calendar.getItems().get(j).getImages().getCommon());
                        entity.setLarge_image(calendar.getItems().get(j).getImages().getLarge());
                        entity.setMedium_image(calendar.getItems().get(j).getImages().getMedium());
                        entity.setSmall_image(calendar.getItems().get(j).getImages().getSmall());
                        entity.setGrid_image(calendar.getItems().get(j).getImages().getGrid());
                    }

                    entity.setName_jp(calendar.getItems().get(j).getName());
                    entity.setRank(calendar.getItems().get(j).getRank());

                    mDbList.add(entity);
                }
            }
            daoSession.getBangumiCalendarDao().deleteAll();
            daoSession.getBangumiCalendarDao().insertInTx(mDbList);

            PreferencesUtils.putLong(BangumiApp.sAppCtx, MyConstants.CALENDAR_REFRESH_KEY, System.currentTimeMillis());
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(LogUtil.ZUBIN, "CalendarPageFragment onStop" + mPosition);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(LogUtil.ZUBIN, "CalendarPageFragment onDestroyView" + mPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(LogUtil.ZUBIN, "CalendarPageFragment onDestroy" + mPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d(LogUtil.ZUBIN, "CalendarPageFragment onAttach" + mPosition);
    }
}
