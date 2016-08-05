package me.ewriter.bangumitv.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.utils.Tools;

/**
 * Created by Zubin on 2016/8/5.
 */
public class AboutActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView mVersionText;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVersionText = (TextView) findViewById(R.id.version);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setupHeader();
    }

    private void setupHeader() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle(R.string.about);
        mCollapsingToolbarLayout.setTitleEnabled(false);
        mCollapsingToolbarLayout.setExpandedTitleGravity(GravityCompat.START);

        mVersionText.setText(String.format(getString(R.string.version_info), Tools.getAppVersionName(this)));
    }

    @Override
    protected void initBeforeCreate() {

    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }
}
