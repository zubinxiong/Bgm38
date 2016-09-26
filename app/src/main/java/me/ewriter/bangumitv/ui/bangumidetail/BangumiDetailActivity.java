package me.ewriter.bangumitv.ui.bangumidetail;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.AnimeDetailEntity;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.utils.ToastUtils;

/**
 * Created by zubin on 2016/9/24.
 */

public class BangumiDetailActivity extends BaseActivity implements BangumiDetailContract.View {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mCoverImg;
    private ViewGroup mCoverGroup;
    private TextView mCoverName, mCoverSummary, mCoverAirDay, mCoverUrl;
    private ProgressBar mProgressbar;
    private FloatingActionButton mFab;

    private BangumiDetailContract.Presenter mPresenter;
    private String mBangumiId;
    private String mCommonImageUrl;
    private String mLargeImageUrl;
    private String mBangumiName;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initView() {

        mPresenter = new BangumiDetailPresenter(this);
        mPresenter.subscribe();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.bangumi_detail_recyclerview);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCoverImg = (ImageView) findViewById(R.id.cover_img);
        mCoverName = (TextView) findViewById(R.id.cover_name);
        mCoverSummary = (TextView) findViewById(R.id.cover_summary);
        mCoverAirDay = (TextView) findViewById(R.id.cover_air_day);
        mCoverGroup = (ViewGroup) findViewById(R.id.cover_group);
        mCoverUrl = (TextView) findViewById(R.id.cover_url);
        mProgressbar = (ProgressBar) findViewById(R.id.loading_progreeebar);
        mFab = (FloatingActionButton) findViewById(R.id.edit_fab);

        setUpCover();
        setUpToolbar();
        setUpRecyclerView();

        mPresenter.requestWebDetail(mBangumiId);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /** 初始化封面模糊效果*/
    private void setUpCover() {
        mCoverName.setText(mBangumiName);
        Picasso.with(this).load(mLargeImageUrl).into(mCoverImg);
        mPresenter.setUpCover(this, mCoverGroup, mCommonImageUrl);
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

        getSupportActionBar().setTitle(getString(R.string.bangumi_detail_title));
        mCollapsingToolbarLayout.setTitleEnabled(false);
        mCollapsingToolbarLayout.setExpandedTitleGravity(GravityCompat.START);
    }

    @Override
    protected void initBefore() {
        mBangumiId = getIntent().getStringExtra("bangumiId");
        mCommonImageUrl = getIntent().getStringExtra("common_url");
        mBangumiName = getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("large_url"))) {
            mLargeImageUrl = getIntent().getStringExtra("large_url");
        } else {
            mLargeImageUrl = mCommonImageUrl.replace("/c/", "/l/");
        }
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

    @Override
    public void refresh(AnimeDetailEntity animeDetailEntity) {
        mCoverSummary.setText(animeDetailEntity.getSummary());
        Items items = new Items();

        items.add(new TextItem(animeDetailEntity.getSummary()));

        int infoSize = animeDetailEntity.getInfoList().size();
        for (int i = 0; i < infoSize; i++) {
            TextItem textItem = new TextItem(animeDetailEntity.getInfoList().get(i));
            items.add(textItem);
        }

        MultiTypeAdapter adapter = new MultiTypeAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void hideProgress() {
        if (mProgressbar != null) {
            mProgressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        ToastUtils.showShortToast(this, error);
    }
}
