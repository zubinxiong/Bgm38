package me.ewriter.bangumitv.ui.about;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.utils.Tools;

/**
 * Created by Zubin on 2016/9/18.
 * 这里没有什么逻辑处理，懒得改成mvp 了
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mVersionText;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mWeibo;
    private TextView mGithub;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVersionText = (TextView) findViewById(R.id.version);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mWeibo = (TextView) findViewById(R.id.weibo_link);
        mGithub = (TextView) findViewById(R.id.github_link);

        mWeibo.setOnClickListener(this);
        mGithub.setOnClickListener(this);

        setupHeader();
    }

    @Override
    protected void initBefore() {

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
    public void onClick(View v) {
        CustomTabsIntent customTabsIntent = null;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);

        switch (v.getId()) {
            case R.id.weibo_link:
                customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(getString(R.string.weibo_url)));
                break;
            case R.id.github_link:
                customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(getString(R.string.github_url)));
                break;

            default:
                break;
        }
    }
}
