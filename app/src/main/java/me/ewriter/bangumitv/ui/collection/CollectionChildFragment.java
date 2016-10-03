package me.ewriter.bangumitv.ui.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.dao.MyCollection;
import me.ewriter.bangumitv.ui.collection.adapter.CollectionItemAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.Tools;
import me.ewriter.bangumitv.widget.VertialSpacingItemDecoration;
import me.ewriter.bangumitv.widget.headerfooter.EndlessRecyclerOnScrollListener;
import me.ewriter.bangumitv.widget.headerfooter.HeaderAndFooterAdapter;
import me.ewriter.bangumitv.widget.headerfooter.LoadingFooter;
import me.ewriter.bangumitv.widget.headerfooter.RecyclerViewStateUtils;

/**
 * Created by Zubin on 2016/9/8.
 */
public class CollectionChildFragment extends BaseFragment implements CollectionContract.View{

    private int mPosition;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<MyCollection> mList;
    private ProgressBar mProgressbar;
    //emptytext not use
    private TextView mEmptyText;
    private Button mEmptyButton;
    private int MAX_ONEPAGE = 24;

    HeaderAndFooterAdapter mHeaderFooterAdapter;
    CollectionItemAdapter mDataAdapter;

    private CollectionContract.Presenter mPresenter;
    private boolean isNoMoreData = false;

    public static CollectionChildFragment newInstance(int position) {
        CollectionChildFragment fragment = new CollectionChildFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_collection_child;
    }

    @Override
    protected void initView(boolean reused) {
        if (reused)
            return;

        mPresenter = new CollectionPresenter(this);
        mPresenter.subscribe();
        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.collection_recycleviews);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getRootView().findViewById(R.id.swipe_refresh_layout);
        mProgressbar = (ProgressBar) getRootView().findViewById(R.id.loadingProgress);
        mEmptyText = (TextView) getRootView().findViewById(R.id.empty_text);
        mEmptyButton = (Button) getRootView().findViewById(R.id.empty_button);
        mList = new ArrayList<>();

        setupRecyclerView();
        setupRefreshLayout();

    }

    private void setupRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String categoty = mPresenter.getCategory();
                String type = mPresenter.getType(mPosition);
                mPresenter.forceRefresh(categoty, type);
                mPresenter.requestData(type, categoty);
            }
        });
    }

    private void setupRecyclerView() {
        mDataAdapter = new CollectionItemAdapter(getActivity(), mList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mHeaderFooterAdapter = new HeaderAndFooterAdapter(mDataAdapter);

        mRecyclerView.addItemDecoration(new VertialSpacingItemDecoration(Tools.getPixFromDip(8)));
        mRecyclerView.setAdapter(mHeaderFooterAdapter);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);

                if (!isNoMoreData) {
                    RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, MAX_ONEPAGE,
                            LoadingFooter.State.Loading, null);
                    // 当前非刷新状态下加载下一页，避免出现重复请求的情况
                    String type = mPresenter.getType(mPosition);
                    String category = mPresenter.getCategory();
                    mPresenter.requestData(type, category);
                }
            }
        });

        mDataAdapter.setOnItemClickListener(new CollectionItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, MyCollection collection) {
                mPresenter.openBangumiDetail(getActivity(), view, collection);
            }
        });
    }

    @Override
    protected void loadData() {
        LogUtil.d(LogUtil.ZUBIN, "collection loadData + "  + mPosition);

        if (mList != null && mList.size() == 0) {
            showLoading();
            String type = mPresenter.getType(mPosition);
            String category = mPresenter.getCategory();
            mPresenter.requestData(type, category);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            LogUtil.d(LogUtil.ZUBIN, "CollectionChildFragment onDestroy");
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(CollectionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshList(List<MyCollection> list) {
        if (list.size() == 0) {
            // 返回的数据为空，说明数据已经没有更多的数据了
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, MAX_ONEPAGE,
                    LoadingFooter.State.TheEnd, null);
            isNoMoreData = true;
        } else {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, MAX_ONEPAGE,
                    LoadingFooter.State.Normal, null);
        }

        LogUtil.d(LogUtil.ZUBIN, "return list = " + list.size());
        mList.addAll(list);
        // 这里需要调用数据的adapter 通知更新而不是调用包裹的adapter
        mDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showLoginHint() {
        mEmptyButton.setText(getString(R.string.login));
        mEmptyButton.setVisibility(View.VISIBLE);
        mEmptyText.setVisibility(View.VISIBLE);
        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openLogin(getActivity());
            }
        });
    }

    @Override
    public void clearData() {
        mList.clear();
        isNoMoreData = false;
    }

    @Override
    public void showOnError() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, MAX_ONEPAGE,
                LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, MAX_ONEPAGE,
                                LoadingFooter.State.Loading, null);
                        String type = mPresenter.getType(mPosition);
                        String category = mPresenter.getCategory();
                        mPresenter.requestData(type, category);
                    }
                });
    }

    @Override
    public void onLogoutEvent() {
        LogUtil.d(LogUtil.ZUBIN, "onLogoutEvent + " + mPosition);
        if (mList != null) {
            mList.clear();
            mDataAdapter.notifyDataSetChanged();
        }
        isNoMoreData = false;
        loadData();
    }

    @Override
    public void onLoginEvent() {
        LogUtil.d(LogUtil.ZUBIN, "onLoginEvent + " + mPosition);
        mEmptyButton.setVisibility(View.GONE);
        mEmptyText.setVisibility(View.GONE);
        loadData();
    }
}
