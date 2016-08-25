package me.ewriter.bangumitv.ui.fragment;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.api.response.MyCollection;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.event.LogoutEvent;
import me.ewriter.bangumitv.event.OpenNavgationEvent;
import me.ewriter.bangumitv.event.UserLoginEvent;
import me.ewriter.bangumitv.ui.activity.BangumiDetailActivity;
import me.ewriter.bangumitv.ui.activity.LoginActivity;
import me.ewriter.bangumitv.ui.activity.SearchActivity;
import me.ewriter.bangumitv.ui.adapter.CollectionAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import me.ewriter.bangumitv.utils.Tools;
import me.ewriter.bangumitv.widget.VertialSpacingItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zubin on 16/7/30.
 */
public class MyCollectionFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView mEmptyText;
    private Button mEmptyButton;
    private ProgressBar mProgreebar;

    private List<MyCollection> mList = new ArrayList<>();
    private CollectionAdapter adapter;

    @Override
    protected boolean isSubscribe() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initView(boolean resued) {
        if (resued)
            return;

        mToolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.collection_recyclerview);
        mEmptyText = (TextView) getRootView().findViewById(R.id.empty_text);
        mEmptyButton = (Button) getRootView().findViewById(R.id.empty_button);
        mProgreebar = (ProgressBar) getRootView().findViewById(R.id.progressbar);

        mEmptyButton.setOnClickListener(this);
        LogUtil.d(LogUtil.ZUBIN, "cf initview");
        setupToolbar();
        setupRecyclerview();
    }

    private void requestDataRefresh() {
        if (LoginManager.isLogin(getActivity())) {
            mEmptyButton.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.GONE);
            BaseActivity.sBangumi.getUserCollection(LoginManager.getUserId(getActivity())).enqueue(new Callback<List<MyCollection>>() {
                @Override
                public void onResponse(Call<List<MyCollection>> call, Response<List<MyCollection>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        mList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                    mProgreebar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<MyCollection>> call, Throwable t) {
                    LogUtil.d(LogUtil.ZUBIN, t.toString());
                    ToastUtils.showShortToast(getActivity(), R.string.collection_fail);
                    mEmptyButton.setText(getString(R.string.get_retry));
                    mEmptyButton.setVisibility(View.VISIBLE);
                    mEmptyText.setVisibility(View.VISIBLE);
                    mProgreebar.setVisibility(View.GONE);
                }
            });
        } else {
            mEmptyButton.setText(getString(R.string.login));
            mProgreebar.setVisibility(View.GONE);
            mEmptyButton.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerview() {
        adapter = new CollectionAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(BangumiApp.sAppCtx,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new VertialSpacingItemDecoration(Tools.getPixFromDip(8)));

        adapter.setonCollectionClcik(new CollectionAdapter.onCollectionItemListener() {
            @Override
            public void onCollectionClick(View view, MyCollection collection) {
                Intent intent = new Intent(getActivity(), BangumiDetailActivity.class);
                intent.putExtra("bangumiId", collection.getSubject().getId());
                intent.putExtra("name", collection.getSubject().getName_cn() + "(" + collection.getName() + ")");
                intent.putExtra("common_url", collection.getSubject().getImages().getCommon());
                intent.putExtra("large_url", collection.getSubject().getImages().getLarge());
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "img").toBundle());
            }
        });
    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_drawer);
        mToolbar.setTitle(getString(R.string.nav_my_collection));
        mToolbar.inflateMenu(R.menu.search_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenNavgationEvent());
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.toolbar_search) {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_button:
                if (LoginManager.isLogin(getActivity())) {
                    mList.clear();
                    adapter.notifyDataSetChanged();
                    mProgreebar.setVisibility(View.VISIBLE);
                    requestDataRefresh();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;

            default:
                break;
        }
    }

    @Subscribe
    public void onLougout(LogoutEvent event) {
        // 退出登录的 event
        mList.clear();
        adapter.notifyDataSetChanged();
        mProgreebar.setVisibility(View.VISIBLE);
        requestDataRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLoginEvent();
        LogUtil.d(LogUtil.ZUBIN, "cf onresume");
    }

    private void checkLoginEvent() {
        mProgreebar.setVisibility(View.VISIBLE);
        requestDataRefresh();
    }
}
