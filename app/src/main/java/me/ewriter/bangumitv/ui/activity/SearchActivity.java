package me.ewriter.bangumitv.ui.activity;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.response.SearchResponse;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.ui.adapter.SearchAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import me.ewriter.bangumitv.utils.Tools;
import me.ewriter.bangumitv.widget.GridSpacingItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zubin on 2016/8/4.
 */
public class SearchActivity extends BaseActivity {

    Toolbar mToolbar;
    EditText mSearchEdit;
    ImageView mBackImg, mStatusImg;
    RecyclerView mRecyclerView;
    ProgressBar mProgressbar;
    SearchAdapter adapter;
    List<SearchResponse.ListBean> mList = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchEdit = (EditText) findViewById(R.id.search_editText);
        mBackImg = (ImageView) findViewById(R.id.search_arrow_back);
        mStatusImg = (ImageView) findViewById(R.id.status_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);

        setupToolbar();
        setupSearchEdit();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }

    private void setupSearchEdit() {
        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = v.getText().toString().trim();
                    if (TextUtils.isEmpty(key)) {
                        ToastUtils.showShortToast(SearchActivity.this, R.string.search_null_hint);
                    } else {
                        Tools.hideInputMethod(SearchActivity.this);
                        mList.clear();
                        adapter.notifyDataSetChanged();
                        mProgressbar.setVisibility(View.VISIBLE);
                        requestSearch(v.getText().toString().trim());
                    }
                }
                return false;
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new SearchAdapter(this, mList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));

        adapter.setOnCardListener(new SearchAdapter.onCardClickListener() {
            @Override
            public void onCardClick(View view, SearchResponse.ListBean listBean) {
                Intent intent = new Intent(SearchActivity.this, BangumiDetailActivity.class);
                intent.putExtra("bangumiId", listBean.getId());
                intent.putExtra("name", listBean.getName_cn() + "(" + listBean.getName() + ")");
                if (listBean.getImages() != null && listBean.getImages().getCommon() != null) {
                    intent.putExtra("common_url", listBean.getImages().getCommon());
                }
                if (listBean.getImages() != null && listBean.getImages().getLarge() != null) {
                    intent.putExtra("large_url", listBean.getImages().getLarge());
                }

                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this, view, "img").toBundle());
            }
        });
    }

    private void requestSearch(String keyWord) {
        sBangumi.search(keyWord).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SearchResponse data = response.body();
                    if (data.getResults() == 0) {
                        ToastUtils.showShortToast(SearchActivity.this, R.string.search_nothing);
                    } else {
                        mList.addAll(data.getList());
                        adapter.notifyDataSetChanged();
                        if (data.getResults() > 8) {
                            ToastUtils.showShortToast(SearchActivity.this,
                                    String.format(getString(R.string.search_api_limit),
                                            data.getResults(), data.getList().size()));
                        }
                    }

                    mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                ToastUtils.showShortToast(SearchActivity.this, R.string.search_error);
                LogUtil.d(LogUtil.ZUBIN, t.toString());
            }
        });
    }

    @Override
    protected void initBeforeCreate() {

    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }
}
