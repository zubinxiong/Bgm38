package me.ewriter.bangumitv.ui.persons;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.widget.commonAdapter.TextItem;
import me.ewriter.bangumitv.widget.commonAdapter.TitleItem;
import me.ewriter.bangumitv.utils.ToastUtils;
import me.ewriter.bangumitv.utils.Tools;
import me.ewriter.bangumitv.widget.HorizonSpacingItemDecoration;

/**
 * Created by Zubin on 2016/9/28.
 * 简介及制作人员
 */

public class PersonsActivity extends BaseActivity implements PersonContract.View{

    private Toolbar mToolbar;
    private ProgressBar mProgressbar;
    private RecyclerView mRecyclerView;
    private Items list;

    private PersonContract.Presenter mPresenter;
    private String subjectId;
    private String extra;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_preson;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mPresenter = new PersonPresenter(this);
        mPresenter.subscribe();

        setUpToolbar();
        setUpRecyclerView();

        mPresenter.requestPerson(subjectId);
    }

    private void setUpRecyclerView() {
        list = new Items();
        list.add(new TitleItem(getString(R.string.bangumi_detail_content), R.drawable.ic_weekend_24dp));
        list.add(new TextItem(extra));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new HorizonSpacingItemDecoration(Tools.getPixFromDip(16)));

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
        getSupportActionBar().setTitle(getString(R.string.bangumi_detail_content));
    }

    @Override
    protected void initBefore() {
        subjectId = getIntent().getStringExtra("subjectId");
        extra = getIntent().getStringExtra("extra");
    }

    @Override
    public void setPresenter(PersonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setProgressBarVisible(int visible) {
        if (mProgressbar != null) {
            mProgressbar.setVisibility(visible);
        }
    }

    @Override
    public void refresh(Items items) {
        list.addAll(items);
        MultiTypeAdapter adapter = new MultiTypeAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }
}
