package me.ewriter.bangumitv.ui.bangumidetail;

import android.app.ProgressDialog;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.api.response.BangumiDetail;
import me.ewriter.bangumitv.api.response.BaseResponse;
import me.ewriter.bangumitv.api.response.SubjectProgress;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.event.ProgressUpdateEvent;
import me.ewriter.bangumitv.ui.adapter.BottomSheetAdapter;
import me.ewriter.bangumitv.ui.adapter.MyProgressAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ProgressDialog mProgressDialog;
    private BangumiDetail mDetail;
    private boolean isUpdate = false;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_my_progress;
    }

    @Override
    protected void initViews() {
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        setUpToolbar();
        setUpBottomSheetDialog();
        requestProgressData();
    }

    private void requestProgressData() {
        sBangumi.getSubjectProgress(LoginManager.getUserId(this), LoginManager.getAuthString(this),
                mDetail.getId()).enqueue(new Callback<SubjectProgress>() {
            @Override
            public void onResponse(Call<SubjectProgress> call, Response<SubjectProgress> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SubjectProgress mSubjectProgress = response.body();
                    for (int i = 0; i < mSubjectProgress.getEps().size(); i++) {
                        int id = mSubjectProgress.getEps().get(i).getId();
                        int status_id = mSubjectProgress.getEps().get(i).getStatus().getId();
                        for (int j = 0; j < mList.size(); j++) {
                            if (id == mList.get(j).getId()) {
                                mList.get(j).setType(status_id);
                                if (status_id == 1 || status_id == 2 || status_id == 3) {
                                    mList.get(j).setStatus("Air");
                                }
                                break;
                            }
                        }
                    }
                }
                setUpRecyclerView();
                mProgressAdapter.notifyDataSetChanged();
                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SubjectProgress> call, Throwable t) {
                LogUtil.e(LogUtil.ZUBIN, t.toString());
                ToastUtils.showShortToast(MyProgressActivity.this, t.toString());
                setUpRecyclerView();
                mProgressbar.setVisibility(View.GONE);
            }
        });
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

        mProgressAdapter.setOnGridClickListener(new MyProgressAdapter.onGridClick() {
            @Override
            public void onClick(View view, int position) {
                updateBottomSheet(mList.get(position), position);
                mBottomSheetDialog.show();
            }
        });
    }

    private void updateBottomSheet(final BangumiDetail.EpsBean epsBean, final int detailPostion) {
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

        final String[] valueArray = getResources().getStringArray(R.array.bottom_sheet_value);
        final int[] type = {1, 2, 3, 0};
        mBottomSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                mBottomSheetDialog.dismiss();
                if (epsBean.getStatus().equals("NA") || epsBean.getStatus().equals("TODAY")) {
                    ToastUtils.showShortToast(MyProgressActivity.this, R.string.not_display_yet);
                } else {
                    showProgressDialog();
                    sBangumi.updateEp(epsBean.getId(), valueArray[position],
                            LoginManager.getAuthString(MyProgressActivity.this)).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getCode() == 200) {
                                    mList.get(detailPostion).setType(type[position]);
                                    mProgressAdapter.notifyItemChanged(detailPostion);
                                    dismissProgressDialog();
                                    ToastUtils.showShortToast(MyProgressActivity.this, R.string.progress_updated);
                                    isUpdate = true;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            LogUtil.e(LogUtil.ZUBIN, t.toString());
                            ToastUtils.showShortToast(MyProgressActivity.this, t.toString());
                            dismissProgressDialog();
                        }
                    });
                }

            }
        });
    }

    private void setUpBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View mContentView = LayoutInflater.from(this).inflate(R.layout.view_bottom_sheet, null, false);
        RecyclerView mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.bottom_recyclerView);
        String[] nameArray = getResources().getStringArray(R.array.bottom_sheet_name);
        List<String> mNameList = new ArrayList<>();
        for (int i = 0; i < nameArray.length; i++) {
            mNameList.add(nameArray[i]);
        }
        mBottomSheetAdapter = new BottomSheetAdapter(this, mNameList);
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
            mDetail = (BangumiDetail) getIntent().getSerializableExtra("detail");
            mList.addAll(mDetail.getEps());
            LogUtil.d(LogUtil.ZUBIN, "not null");
        } else {
            ToastUtils.showShortToast(this, R.string.init_failed);
        }
    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.progress_updating));
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().postSticky(new ProgressUpdateEvent(isUpdate));
        super.onPause();
    }
}
