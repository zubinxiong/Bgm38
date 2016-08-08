package me.ewriter.bangumitv.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.response.BangumiDetail;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.ui.adapter.BottomSheetAdapter;
import me.ewriter.bangumitv.ui.adapter.MyProgressAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;

/**
 * Created by Zubin on 2016/8/8.
 * 当前用户观看进度，点击后便跳转至此页面
 */
public class MyProgressActivity extends BaseActivity {

    private TextView mEpsTitle, mEpsSummary;
    private BottomSheetDialog mBottomSheetDialog;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private MyProgressAdapter mProgressAdapter;
    private BottomSheetAdapter mBottomSheetAdapter;
    private List<BangumiDetail.EpsBean> mList = new ArrayList<>();
    private ProgressBar mProgressbar;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_my_progress;
    }

    @Override
    protected void initViews() {
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        setUpToolbar();
        setUpBottomSheetDialog();
        setUpRecyclerView();
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.progress_recyclerview);
        mProgressAdapter = new MyProgressAdapter(this, mList);
        mRecyclerView.setAdapter(mProgressAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));

        mProgressAdapter.notifyDataSetChanged();
        mProgressbar.setVisibility(View.GONE);

        mProgressAdapter.setOnGridClickListener(new MyProgressAdapter.onGridClick() {
            @Override
            public void onClick(View view, int position) {
                updateBottomSheet(mList.get(position));
                mBottomSheetDialog.show();
            }
        });
    }

    private void updateBottomSheet(BangumiDetail.EpsBean epsBean) {
        if (!TextUtils.isEmpty(epsBean.getName_cn())) {
            mEpsTitle.setText(epsBean.getName_cn() + "/" +  epsBean.getAirdate());
        } else {
            mEpsTitle.setText(epsBean.getName() + "/" + epsBean.getAirdate());
        }
        if (!TextUtils.isEmpty(epsBean.getDesc())) {
            mEpsSummary.setVisibility(View.VISIBLE);
            mEpsSummary.setText(epsBean.getDesc());
        } else {
            mEpsSummary.setVisibility(View.GONE);
        }

        mBottomSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetDialog.dismiss();
            }
        });
    }

    private void setUpBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View mContentView = LayoutInflater.from(this).inflate(R.layout.view_bottom_sheet, null, false);
        RecyclerView mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.bottom_recyclerView);
        mBottomSheetAdapter = new BottomSheetAdapter(this);
        mRecyclerView.setAdapter(mBottomSheetAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mBottomSheetDialog.setContentView(mContentView);

        mEpsTitle = (TextView) mContentView.findViewById(R.id.eps_title);
        mEpsSummary = (TextView) mContentView.findViewById(R.id.eps_summary);

        // 解决BottomSheetDialog 滑动后无法再次显示
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void initBeforeCreate() {
        if (getIntent().getSerializableExtra("detail") != null) {
            BangumiDetail mDetail = (BangumiDetail) getIntent().getSerializableExtra("detail");
            mList.addAll(mDetail.getEps());
            LogUtil.d(LogUtil.ZUBIN, "not null");
        } else {
            ToastUtils.showShortToast(this, "初始化失败，请稍后重试");
        }
    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }
}
