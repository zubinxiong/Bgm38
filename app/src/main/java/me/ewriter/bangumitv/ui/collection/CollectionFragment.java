package me.ewriter.bangumitv.ui.collection;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.event.CategoryChangeEvent;
import me.ewriter.bangumitv.event.OpenNavigationEvent;
import me.ewriter.bangumitv.ui.collection.adapter.CollectionAdapter;
import me.ewriter.bangumitv.ui.search.SearchActivity;
import me.ewriter.bangumitv.utils.RxBus;

/**
 * Created by Zubin on 2016/9/6.
 */
public class CollectionFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private PopupMenu mPopupMenu;
    View showView;

    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void initView(boolean reused) {
        if (reused)
            return;

        mViewPager = (ViewPager) getRootView().findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) getRootView().findViewById(R.id.tablayout);
        mToolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);

        setupToolbar();
        setupViewPager();
    }

    @Override
    protected void loadData() {
    }

    private void setupToolbar() {
        mToolbar.setTitle(getString(R.string.nav_my_collection));
        mToolbar.inflateMenu(R.menu.filter_menu);
        mToolbar.setNavigationIcon(R.drawable.ic_action_drawer);

        showView = mToolbar.findViewById(R.id.toolbar_filter);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new OpenNavigationEvent());
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.toolbar_filter) {
//                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    showMenu();
                }
                return false;
            }
        });
    }

    private void showMenu() {
        mPopupMenu = new PopupMenu(getActivity(), showView);
        mPopupMenu.inflate(R.menu.menu_popupmenu);
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_anime:
                        RxBus.getDefault().postSticky(new CategoryChangeEvent("anime"));
                        return true;

                    case R.id.item_book:
                        RxBus.getDefault().postSticky(new CategoryChangeEvent("book"));
                        return true;

                    case R.id.item_game:
                        RxBus.getDefault().postSticky(new CategoryChangeEvent("game"));
                        return true;

                    case R.id.item_music:
                        RxBus.getDefault().postSticky(new CategoryChangeEvent("music"));
                        return true;

                    case R.id.item_real:
                        RxBus.getDefault().postSticky(new CategoryChangeEvent("real"));
                        return true;
                }

                return false;
            }
        });

        mPopupMenu.show();
    }

    private void setupViewPager() {
        // 因为 setAdapter 是
        String[] tabNameArray = {"在看", "看过", "想看", "搁置", "抛弃"};

        final CollectionAdapter adapter = new CollectionAdapter(getChildFragmentManager());
        for (int i = 0; i < tabNameArray.length; i++) {
            adapter.addFragment(CollectionChildFragment.newInstance(i), tabNameArray[i]);
        }
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.setAdapter(adapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });
    }

}
