package me.ewriter.bangumitv.ui.bangumidetail;

import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import me.ewriter.bangumitv.utils.Tools;
import me.ewriter.bangumitv.widget.HorizonSpacingItemDecoration;

/**
 * Created by zubin on 2016/9/24.
 */

public class BangumiDetailActivity extends BaseActivity implements BangumiDetailContract.View, View.OnClickListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mCoverImg;
    private ViewGroup mCoverGroup;
    private TextView mCoverName, mCoverSummary, mCoverTag, mCoverScore;
    private ProgressBar mProgressbar;
    private FloatingActionButton mFab;

    private BangumiDetailContract.Presenter mPresenter;
    private String mBangumiId;
    private String mCommonImageUrl;
    private String mLargeImageUrl;
    private String mBangumiName;

    // 插入的数据
    int evRating, evStatus;
    String evComment;

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
        mCoverTag = (TextView) findViewById(R.id.cover_tag);
        mCoverGroup = (ViewGroup) findViewById(R.id.cover_group);
        mCoverScore = (TextView) findViewById(R.id.cover_score);
        mProgressbar = (ProgressBar) findViewById(R.id.loading_progreeebar);
        mFab = (FloatingActionButton) findViewById(R.id.edit_fab);

        setUpCover();
        setUpToolbar();
        setUpRecyclerView();
        mFab.setOnClickListener(this);

        mPresenter.requestWebDetail(mBangumiId);
    }

    private void setUpRecyclerView() {
        mRecyclerView.addItemDecoration(new HorizonSpacingItemDecoration(Tools.getPixFromDip(16)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /** 初始化封面模糊效果*/
    private void setUpCover() {
        mCoverName.setText(mBangumiName);
        Picasso.with(this).load(mLargeImageUrl)
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .into(mCoverImg);
        mPresenter.setUpCover(this, mCoverGroup, mCommonImageUrl);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle(getString(R.string.bangumi_detail_title));
        mCollapsingToolbarLayout.setTitleEnabled(false);
        mCollapsingToolbarLayout.setExpandedTitleGravity(GravityCompat.START);
    }

    @Override
    protected void initBefore() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
        }

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
    public void refresh(Items items) {
        MultiTypeAdapter adapter = new MultiTypeAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setFabVisible(int visible) {
        mFab.setVisibility(visible);
    }

    @Override
    public void showEvaluationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View itemView = LayoutInflater.from(this).inflate(R.layout.view_evaluation, null);
        // spinner
        final AppCompatSpinner spinner = (AppCompatSpinner) itemView.findViewById(R.id.spanner);
        String[] mItems = getResources().getStringArray(R.array.spinner_name);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item ,mItems);
        spinner.setAdapter(spinnerAdapter);

        // 评分
        final TextView mSeekBarTitle = (TextView) itemView.findViewById(R.id.rating_title);
        final DiscreteSeekBar mSeekBar = (DiscreteSeekBar) itemView.findViewById(R.id.rating_seekbar);
        mSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                String text = getString(R.string.rate_number_hint) + ": " + value;
                mSeekBarTitle.setText(text);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        // 详情
        final TextInputLayout mRatingDetail = (TextInputLayout) itemView.findViewById(R.id.rate_detail);

        if (evRating >= 0) {
            mSeekBar.setProgress(evRating);
        }
        mRatingDetail.getEditText().setText(evComment);
        if (evStatus > 0) {
            spinner.setSelection(evStatus - 1);
        }

        builder.setView(itemView);
        builder.setTitle(R.string.my_comment);
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                String[] status = getResources().getStringArray(R.array.spinner_value);
                int index = spinner.getSelectedItemPosition();
                int rating = mSeekBar.getProgress();
                LogUtil.d(LogUtil.ZUBIN, "rating = " + rating);
                String comment = mRatingDetail.getEditText().getText().toString().trim();

                mPresenter.updateComment(mBangumiId, status[index], rating, comment);
                dialog.dismiss();
            }
        });

        builder.show();

    }

    @Override
    public void insertComment(String comment, int status, int rating) {
        evComment = comment;
        evRating = rating;
        evStatus = status;
    }

    @Override
    public void hideProgress() {
        if (mProgressbar != null) {
            mProgressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }

    @Override
    public void updateHeader(String summary, String tag, String score) {
        mCoverSummary.setText(summary);
        mCoverTag.setText(String.format(getString(R.string.tags), tag));
        mCoverScore.setText(String.format(getString(R.string.score), score));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_fab:
                mPresenter.clickFab(BangumiDetailActivity.this, mBangumiId);
                break;
        }
    }
}
