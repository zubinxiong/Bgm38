package me.ewriter.bangumitv.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.api.entity.BangumiDetailEntity;
import me.ewriter.bangumitv.api.response.BangumiDetail;
import me.ewriter.bangumitv.api.response.BaseResponse;
import me.ewriter.bangumitv.api.response.SubjectComment;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.ui.adapter.BangumiDetailAdapter;
import me.ewriter.bangumitv.utils.BlurUtil;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import me.ewriter.bangumitv.widget.GridSpacingItemDecoration;
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
    TextView mCoverName, mCoverSummary, mCoverAirDay, mCoverUrl;
    ProgressBar mProgressbar;
    FloatingActionButton mFab;

    BangumiDetailAdapter adapter;
    GridLayoutManager manager;
    List<BangumiDetailEntity> mList;
    SubjectComment mSubjectComment;

    int mBangumiId = -1;
    String mCommonImageUrl;
    String mLargeImageUrl;
    String mBangumiName;
    String mDetailUrl;

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
        mCoverUrl = (TextView) findViewById(R.id.cover_url);
        mProgressbar = (ProgressBar) findViewById(R.id.loading_progreeebar);
        mFab = (FloatingActionButton) findViewById(R.id.edit_fab);

        setUpCover();
        setUpToolbar();
        setUpRecyclerView();

        mCoverGroup.setOnClickListener(this);
        mFab.setOnClickListener(this);

        requestDetailData();
        if (LoginManager.isLogin(this)) {
            requestCommentData();
        }

    }

    private void requestDetailData() {
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

    private void requestCommentData() {
        sBangumi.getSubjectComment(mBangumiId, LoginManager.getAuthString(this)).enqueue(new Callback<SubjectComment>() {
            @Override
            public void onResponse(Call<SubjectComment> call, Response<SubjectComment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mSubjectComment = response.body();
                }
            }

            @Override
            public void onFailure(Call<SubjectComment> call, Throwable t) {
                LogUtil.e(LogUtil.ZUBIN, t.toString());
                ToastUtils.showShortToast(BangumiDetailActivity.this, t.toString());
            }
        });
    }

    private void updateCover(BangumiDetail detail) {
        mCoverSummary.setText(String.format(getString(R.string.bangumi_detail_summary), detail.getSummary()));
        mCoverAirDay.setText(String.format(getString(R.string.bangumi_detail_air_day),detail.getAir_date()));
        mCoverUrl.setVisibility(View.VISIBLE);
        mDetailUrl = detail.getUrl();
    }

    private void updateList(BangumiDetail detail) {
        mFab.setVisibility(View.VISIBLE);
        // 作品类型
        mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, "类型"));
        int category_type = detail.getType();
        String type = "_(:з”∠)_";
        if (category_type == 1) {
            type = "漫画/小说";
        } else if (category_type == 2) {
            type = "动画/二次元番";
        } else if (category_type == 3) {
            type = "音乐";
        } else if (category_type == 4) {
            type = "游戏";
        } else if (category_type == 6) {
            type = "三次元番";
        }
        mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_CONTENT, type));
        mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, ""));

        // 作品简介
        mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, "作品简介"));
        if (!TextUtils.isEmpty(detail.getSummary())) {
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_CONTENT, detail.getSummary()));
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, ""));
        } else {
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_CONTENT, "没有啦_(:з”∠)_"));
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, ""));
        }



        // 角色介绍
        if (detail.getCrt() != null && detail.getCrt().size() != 0) {
            // CV title
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, "角色"));

            // CV 信息
            for (int i = 0; i < detail.getCrt().size(); i++) {
                String role_cv = "";
                String role_img = null;
                String role_name = detail.getCrt().get(i).getName_cn();
                if (TextUtils.isEmpty(role_name)) {
                    role_name = detail.getCrt().get(i).getName();
                }
                if (detail.getCrt().get(i).getActors() != null) {
                    role_cv = detail.getCrt().get(i).getActors().get(0).getName();
                }
                if (detail.getCrt().get(i).getImages()!= null && detail.getCrt().get(i).getImages().getMedium() != null) {
                    role_img = detail.getCrt().get(i).getImages().getMedium();
                }
                mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_CARD, role_name, role_cv, role_img));
            }
        }

        // Staff 介绍
        if (detail.getStaff() != null && detail.getStaff().size() != 0) {
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, ""));
            mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_TITLE, "Staff信息 "));

            for (int i = 0; i < detail.getStaff().size(); i++) {
                String staff_name = detail.getStaff().get(i).getName_cn();
                String staff_job = "";
                String staff_img = null;
                if (TextUtils.isEmpty(staff_name)) {
                    staff_name = detail.getStaff().get(i).getName();
                }

                if (detail.getStaff().get(i).getJobs() != null) {
                    List list = detail.getStaff().get(i).getJobs();
                    staff_job = list.toString().substring(1, list.toString().length() - 1);
                }

                if (detail.getStaff().get(i).getImages()!= null && detail.getStaff().get(i).getImages().getMedium() != null) {
                    staff_img = detail.getStaff().get(i).getImages().getMedium();
                }
                mList.add(new BangumiDetailEntity(BangumiDetailAdapter.TYPE_CARD, staff_name, staff_job, staff_img));
            }
        }

        // 放映eps



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
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));

        // spanCount 为 6，每个item 为 1，因此需要占一行则需要下面返回 6
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == BangumiDetailAdapter.TYPE_TITLE || type == BangumiDetailAdapter.TYPE_CONTENT) {
                    return manager.getSpanCount();
                } else if (type == BangumiDetailAdapter.TYPE_CARD){
                    return 3;
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
//                finish();
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle(getString(R.string.bangumi_detail_title));
        mCollapsingToolbarLayout.setTitleEnabled(false);
        mCollapsingToolbarLayout.setExpandedTitleGravity(GravityCompat.START);
    }

    @Override
    protected void initBeforeCreate() {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Fade());
        }

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
                // 启动url
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(mDetailUrl));
                break;

            case R.id.edit_fab:
                Intent intent = null;
                if (LoginManager.isLogin(this)) {
                    showEvaluationDialog();
                } else {
                    ToastUtils.showShortToast(this, R.string.not_login_hint);
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void showEvaluationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View itemView = LayoutInflater.from(this).inflate(R.layout.view_evaluation, null);
        // spinner
        final AppCompatSpinner spinner = (AppCompatSpinner) itemView.findViewById(R.id.spanner);
        String[] mItems = getResources().getStringArray(R.array.spinner_name);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item ,mItems);
        spinner.setAdapter(spinnerAdapter);

        // 评分
        final TextInputLayout mRatingNumber = (TextInputLayout) itemView.findViewById(R.id.rate_number);

        // 详情
        final TextInputLayout mRatingDetail = (TextInputLayout) itemView.findViewById(R.id.rate_detail);

        if (mSubjectComment != null) {
            if (mSubjectComment.getRating() != 0) {
                mRatingNumber.getEditText().setText(mSubjectComment.getRating() + "");
            }
            mRatingDetail.getEditText().setText(mSubjectComment.getComment());

            if (mSubjectComment.getStatus() != null) {
                spinner.setSelection(mSubjectComment.getStatus().getId() - 1);
            }
        }

        builder.setView(itemView);
        builder.setTitle("我的评价");
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
                int rating = 0;
                if(!TextUtils.isEmpty(mRatingNumber.getEditText().getText().toString().trim())) {
                   rating = Integer.parseInt(mRatingNumber.getEditText().getText().toString().trim());
                }
                String comment = mRatingDetail.getEditText().getText().toString().trim();

                sBangumi.updateComment(mBangumiId, status[index], rating, comment,
                        LoginManager.getAuthString(BangumiDetailActivity.this)).enqueue(new Callback<SubjectComment>() {
                    @Override
                    public void onResponse(Call<SubjectComment> call, Response<SubjectComment> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mSubjectComment = response.body();
                            ToastUtils.showShortToast(BangumiDetailActivity.this, R.string.update_comment);
                        }
                    }

                    @Override
                    public void onFailure(Call<SubjectComment> call, Throwable t) {
                        LogUtil.e(LogUtil.ZUBIN, t.toString());
                        ToastUtils.showShortToast(BangumiDetailActivity.this, t.toString());
                    }
                });

                dialog.dismiss();
            }
        });

        builder.show();
    }
}
