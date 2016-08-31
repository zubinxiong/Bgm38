package me.ewriter.bangumitv.dailyOnAir;

import android.support.v4.app.Fragment;

/**
 * Created by Zubin on 2016/8/31.
 */
public class TestFragment extends Fragment implements DailyOnAirContract.View{

    private DailyOnAirContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showRefreshView() {

    }

    @Override
    public void setTitle() {

    }

    @Override
    public void stopRefreshView() {

    }

    @Override
    public void setPresenter(DailyOnAirContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
