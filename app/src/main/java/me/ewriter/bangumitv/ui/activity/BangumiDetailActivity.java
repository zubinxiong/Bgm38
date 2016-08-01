package me.ewriter.bangumitv.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.ui.adapter.BangumiDetailAdapter;
import me.ewriter.bangumitv.utils.BlurUtil;

/**
 * Created by Zubin on 2016/8/1.
 * 番剧详情页
 */
public class BangumiDetailActivity extends BaseActivity{

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    AppBarLayout mAppbarLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    int mBangumiId;
    String mCommonImageUrl;
    String mLargeImageUrl;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.bangumi_detail_recyclerview);
        mAppbarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setUpToolbar();
        setUpRecyclerView();

        ImageView test = (ImageView) findViewById(R.id.cover_img);
        final ViewGroup testGroup = (ViewGroup) findViewById(R.id.cover_group);

        Picasso.with(this).load(mLargeImageUrl).into(test);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BlurUtil.blur(BangumiDetailActivity.this, Picasso.with(BangumiDetailActivity.this).load(mCommonImageUrl).get());

                    BangumiDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testGroup.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void setUpRecyclerView() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mList.add(i + "");
        }
        mRecyclerView.setAdapter(new BangumiDetailAdapter(this, mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

        getSupportActionBar().setTitle(getString(R.string.bangumi_detail_title));
        mCollapsingToolbarLayout.setTitleEnabled(false);
        mCollapsingToolbarLayout.setExpandedTitleGravity(GravityCompat.START);
    }

    @Override
    protected void initIntent() {
        if (getIntent() != null) {
            mBangumiId = getIntent().getIntExtra("bangumiId", 0);
            mCommonImageUrl = getIntent().getStringExtra("common_url");
            mLargeImageUrl = getIntent().getStringExtra("large_url");
        }
    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }
}
