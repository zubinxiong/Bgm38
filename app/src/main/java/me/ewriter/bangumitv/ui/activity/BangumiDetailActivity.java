package me.ewriter.bangumitv.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.response.BangumiDetail;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.ui.adapter.BangumiDetailAdapter;
import me.ewriter.bangumitv.utils.BlurUtil;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zubin on 2016/8/1.
 * 番剧详情页
 */
public class BangumiDetailActivity extends BaseActivity implements View.OnClickListener {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    AppBarLayout mAppbarLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ImageView mCoverImg;
    ViewGroup mCoverGroup;
    TextView mCoverName, mCoverSummary, mCoverAirDay, mCoverMore;
    ProgressBar mProgressbar;

    BangumiDetailAdapter adapter;
    GridLayoutManager manager;
    List<String> mList;

    int mBangumiId = -1;
    String mCommonImageUrl;
    String mLargeImageUrl;
    String mBangumiName;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initViews() {

        if (mBangumiId == -1) {
            ToastUtils.showShortToast(this, R.string.bangumi_detail_init_error);
            return;
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.bangumi_detail_recyclerview);
        mAppbarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCoverImg = (ImageView) findViewById(R.id.cover_img);
        mCoverName = (TextView) findViewById(R.id.cover_name);
        mCoverSummary = (TextView) findViewById(R.id.cover_summary);
        mCoverAirDay = (TextView) findViewById(R.id.cover_air_day);
        mCoverGroup = (ViewGroup) findViewById(R.id.cover_group);
        mCoverMore = (TextView) findViewById(R.id.cover_more);
        mProgressbar = (ProgressBar) findViewById(R.id.loading_progreeebar);

        setUpCover();
        setUpToolbar();
        setUpRecyclerView();

        mCoverGroup.setOnClickListener(this);

        sBangumi.getBangumiDetail(mBangumiId).enqueue(new Callback<BangumiDetail>() {
            @Override
            public void onResponse(Call<BangumiDetail> call, Response<BangumiDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BangumiDetail detail = response.body();
                    updateCover(detail);
                    updateList(detail);
                    mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BangumiDetail> call, Throwable t) {
                LogUtil.e(LogUtil.ZUBIN, t.toString());
                ToastUtils.showShortToast(BangumiDetailActivity.this, t.toString());
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void updateCover(BangumiDetail detail) {
        mCoverSummary.setText(String.format(getString(R.string.bangumi_detail_summary), detail.getSummary()));
        mCoverAirDay.setText(String.format(getString(R.string.bangumi_detail_air_day),detail.getAir_date()));
        mCoverMore.setVisibility(View.VISIBLE);
    }

    private void updateList(BangumiDetail detail) {
        for (int i = 0; i < 25; i++) {
            mList.add(i+"");
        }
        adapter.notifyDataSetChanged();
    }

    private void setUpCover() {
        mCoverName.setText(mBangumiName);
        Picasso.with(this).load(mLargeImageUrl).into(mCoverImg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BlurUtil.blur(BangumiDetailActivity.this, Picasso.with(BangumiDetailActivity.this).load(mCommonImageUrl).get());

                    BangumiDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCoverGroup.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setUpRecyclerView() {
        mList = new ArrayList<>();
        adapter = new BangumiDetailAdapter(this, mList);
        mRecyclerView.setAdapter(adapter);
        manager = new GridLayoutManager(this, 6);
        mRecyclerView.setLayoutManager(manager);

        // spanCount 为 6，每个item 为 1，因此需要占一行则需要下面返回 6
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == BangumiDetailAdapter.TYPE_TITLE) {
                    return manager.getSpanCount();
                } else if (type == BangumiDetailAdapter.TYPE_CARD){
                    return 2;
                } else {
                    return 1;
                }
            }
        });
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
            mBangumiName = getIntent().getStringExtra("name");
        }
    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_group:
                ToastUtils.showShortToast(BangumiDetailActivity.this, "detail");
                break;

            default:
                break;
        }
    }
}
