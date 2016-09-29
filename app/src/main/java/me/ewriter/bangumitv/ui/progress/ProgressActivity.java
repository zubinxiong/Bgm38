package me.ewriter.bangumitv.ui.progress;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.ui.progress.adapter.BottomSheetAdapter;
import me.ewriter.bangumitv.ui.progress.adapter.MyEpAdapter;
import me.ewriter.bangumitv.utils.ToastUtils;

/**
 * Created by Zubin on 2016/9/28.
 */

public class ProgressActivity extends BaseActivity implements ProgressContract.View {

    ProgressContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ProgressBar mProgressbar;
    private List<AnimeEpEntity> list;
    MyEpAdapter adapter;

    private String subjectId;
    private ProgressDialog mProgressDialog;
    private TextView mEpsTitle, mEpsSummary;
    private BottomSheetAdapter mBottomSheetAdapter;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_progress;
    }

    @Override
    protected void initView() {
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.progress_recyclerview);

        mPresenter = new ProgressPresenter(this);
        mPresenter.subscribe();

        setUpToolbar();
        setUpRecyclerView();
        setUpBottomSheetDialog();

        mPresenter.requestProgress(subjectId);
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

    private void setUpRecyclerView() {
        list = new ArrayList<>();
        adapter = new MyEpAdapter(list, this);
        mRecyclerView.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(6, Tools.getPixFromDip(16), true));
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == MyEpAdapter.TYPE_TITLE) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });

        adapter.setOnItemClickListener(new MyEpAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position, AnimeEpEntity entity) {
                updateBottomSheet(entity, position);
                mBottomSheetDialog.show();
            }
        });

    }

    private void updateBottomSheet(final AnimeEpEntity entity, final int gridPosition) {
        mEpsTitle.setText(entity.getEpName());
        mEpsSummary.setText(entity.getInfo());

        mBottomSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetDialog.dismiss();
                showProgressDialog();
                mPresenter.updateEpStatus(entity, position, gridPosition);
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
        getSupportActionBar().setTitle(getString(R.string.watch_progress));
    }

    @Override
    protected void initBefore() {
        subjectId = getIntent().getStringExtra("subjectId");
    }

    @Override
    public void setPresenter(ProgressContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setProgressBarVisible(int visible) {
        if (mProgressbar != null) {
            mProgressbar.setVisibility(visible);
        }
    }

    @Override
    public void refresh(List<AnimeEpEntity> items) {
        list.addAll(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.progress_updating));
        mProgressDialog.show();
    }

    @Override
    public void updateEp(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
