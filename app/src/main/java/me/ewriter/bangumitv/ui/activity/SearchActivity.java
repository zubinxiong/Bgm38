package me.ewriter.bangumitv.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;

/**
 * Created by Zubin on 2016/8/4.
 */
public class SearchActivity extends BaseActivity {

    Toolbar mToolbar;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initBeforeCreate() {

    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }
}
