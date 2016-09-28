package me.ewriter.bangumitv.ui.progress;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;

/**
 * Created by Zubin on 2016/9/28.
 */

public class ProgressActivity extends BaseActivity implements ProgressContract.View {

    ProgressContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ProgressBar mProgressbar;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_progress;
    }

    @Override
    protected void initView() {
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.progress_recyclerview);

        mPresenter = new ProgressPresenter(this);
        mPresenter.subscribe();

        setUpToolbar();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle(getString(R.string.watch_progress));
    }

    @Override
    protected void initBefore() {

    }

    @Override
    public void setPresenter(ProgressContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setProgressBarVisible(int visible) {
        if (mProgressbar != null) {
            mProgressbar.setVisibility(visible);
        }
    }

    @Override
    public void refresh() {

    }
}
