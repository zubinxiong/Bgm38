package me.ewriter.bangumitv.dailyOnAir;

import android.support.v4.app.Fragment;

/**
 * Created by Zubin on 2016/9/5.
 */
public class DailyOnAirFragment extends Fragment implements DailyOnAirContract.View {

    DailyOnAirContract.Presenter mPresenter;

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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        mPresenter.unsubscribe();
        super.onPause();
    }
}
