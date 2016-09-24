package me.ewriter.bangumitv.ui.bangumidetail;

import android.support.v7.widget.Toolbar;
import android.view.View;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;

/**
 * Created by zubin on 2016/9/24.
 */

public class BangumiDetailActivity extends BaseActivity implements BangumiDetailContract.View {

    private Toolbar mToolbar;
    private BangumiDetailContract.Presenter mPresenter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setUpToolbar();

        mPresenter = new BangumiDetailPresenter(this);
        mPresenter.subscribe();

        mPresenter.requestWebDetail("158316");
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initBefore() {

    }

    @Override
    public void setPresenter(BangumiDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
