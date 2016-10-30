package me.ewriter.bangumitv.ui.collection;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.api.ApiManager;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.dao.DaoSession;
import me.ewriter.bangumitv.dao.MyCollection;
import me.ewriter.bangumitv.dao.MyCollectionDao;
import me.ewriter.bangumitv.event.CategoryChangeEvent;
import me.ewriter.bangumitv.event.UserLoginEvent;
import me.ewriter.bangumitv.event.UserLogoutEvent;
import me.ewriter.bangumitv.ui.bangumidetail.BangumiDetailActivity;
import me.ewriter.bangumitv.ui.login.LoginActivity;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.PreferencesUtils;
import me.ewriter.bangumitv.utils.RxBus;
import me.ewriter.bangumitv.utils.RxBusSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/19.
 */
public class CollectionPresenter implements CollectionContract.Presenter {

    private CollectionContract.View mCollectionView;
    private CompositeSubscription mSubscriptions;
    private Subscription mRxLoginSub, mRxLogoutStb, mRxChangeCateStub;
    private int mPage = 1;
    private String category = "anime";

    public CollectionPresenter(CollectionContract.View mCollectionView) {
        this.mCollectionView = mCollectionView;

        mCollectionView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();

        subscribeLoginEvent();
        subscribeLogoutEvent();
        subscribeChangeCateEvent();
    }

    private void subscribeChangeCateEvent() {
        mRxChangeCateStub = RxBus.getDefault().toObservableSticky(CategoryChangeEvent.class)
                .subscribe(new RxBusSubscriber<CategoryChangeEvent>() {
                    @Override
                    protected void onEvent(CategoryChangeEvent categoryChangeEvent) {
                        mPage = 1;
                        category = categoryChangeEvent.getCategory();
                        mCollectionView.onChangeCateEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mCollectionView.showToast(e.getMessage());
                    }
                });

        mSubscriptions.add(mRxChangeCateStub);
    }

    private void subscribeLogoutEvent() {
        mRxLogoutStb = RxBus.getDefault().toObservableSticky(UserLogoutEvent.class)
                .subscribe(new RxBusSubscriber<UserLogoutEvent>() {
                    @Override
                    protected void onEvent(UserLogoutEvent userLogoutEvent) {
                        LogUtil.d(LogUtil.ZUBIN, "LogoutEvent onNext ");
                        mPage = 1;
                        mCollectionView.onLogoutEvent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.d(LogUtil.ZUBIN, "LogoutEvent onError " + e.getMessage());
                    }
                });


        mCollectionView.onLogoutEvent();
        mSubscriptions.add(mRxLogoutStb);
    }

    private void subscribeLoginEvent() {
          mRxLoginSub = RxBus.getDefault().toObservableSticky(UserLoginEvent.class)
                  .subscribe(new RxBusSubscriber<UserLoginEvent>() {
                      @Override
                      protected void onEvent(UserLoginEvent userLoginEvent) {
                          LogUtil.d(LogUtil.ZUBIN, "LoginEvent onNext ");
                          mCollectionView.onLoginEvent();
                      }

                      @Override
                      public void onError(Throwable e) {
                          super.onError(e);
                          LogUtil.d(LogUtil.ZUBIN, "LoginEvent onError " + e.getMessage());
                      }
                  });

        mSubscriptions.add(mRxLoginSub);
    }

    @Override
    public void unsubscribe() {
        if (mRxLoginSub != null) {
            mSubscriptions.remove(mRxLoginSub);
        }
        if (mRxLogoutStb != null) {
            mSubscriptions.remove(mRxLogoutStb);
        }
        mSubscriptions.clear();
    }

    @Override
    public void requestData(final String type, final String category) {

        // 检查是否登录
        if (!LoginManager.isLogin(BangumiApp.sAppCtx)) {
            mCollectionView.showLoginHint();
            mCollectionView.hideLoading();
            return;
        }

        // 数据库缓存
        Observable<List<MyCollection>> cache = Observable.create(new Observable.OnSubscribe<List<MyCollection>>() {
            @Override
            public void call(Subscriber<? super List<MyCollection>> subscriber) {
                try {
                    subscriber.onStart();

                    // 查询数据库
                    List<MyCollection> items = new ArrayList<MyCollection>();

                    List<MyCollection> queryList = BangumiApp.sAppCtx.getDaoSession()
                            .getMyCollectionDao().queryBuilder()
                            .where(MyCollectionDao.Properties.Category.eq(category)
                                    , MyCollectionDao.Properties.Collection_type.eq(type))
                            .build().list();

                    items.addAll(queryList);

                    // 超过6小时清空,使用网络, 或者非第一页则使用网络加载
                    long deta = System.currentTimeMillis() - PreferencesUtils.getLong(BangumiApp.sAppCtx,
                            MyConstants.COLLECTION_REFRESH_NAME, category + type, 0);

                    if (deta > MyConstants.COLLECTION_REFRESH_TIME || mPage > 1) {
                        items.clear();
                    }

                    subscriber.onNext(items);
                    subscriber.onCompleted();

                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        // 网络数据
        Observable<List<MyCollection>> network = ApiManager.getWebInstance()
                .listCollection(category, LoginManager.getUserName(BangumiApp.sAppCtx), type, mPage)
                .map(new Func1<String, List<MyCollection>>() {
                    @Override
                    public List<MyCollection> call(String s) {
                        return parseHtmlToDb(s, category, type);
                    }
                });

        // concat 两个数据, 取第一个数据不为空的
        // 如果数据为空会直接onerror, takeFirst 则直接调用第一个，直接onComplete 不会onnext, 所以使用 firstOrDefault
        List<MyCollection> defaultEmpty = new ArrayList<>();
        Observable<List<MyCollection>> source = Observable.concat(cache, network)
                .firstOrDefault(defaultEmpty, new Func1<List<MyCollection>, Boolean>() {
                    @Override
                    public Boolean call(List<MyCollection> myCollections) {
                        return (myCollections.size() != 0);
                    }
                });

        Subscription subscription = source.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MyCollection>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(LogUtil.ZUBIN, "collection onCompleted ");
                        mCollectionView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(LogUtil.ZUBIN, "collection onError " + e.getMessage());
                        mCollectionView.hideLoading();
                        mCollectionView.showOnError();
                    }

                    @Override
                    public void onNext(List<MyCollection> myCollections) {
                        LogUtil.d(LogUtil.ZUBIN, "collection onNext ");
                        if (mPage == 1) {
                            mCollectionView.clearData();
                        }
                        mPage++;
                        mCollectionView.refreshList(myCollections);
                    }
                });


        mSubscriptions.add(subscription);
    }

    /** 将html 解析完成 存取到数据库后并返回给 list*/
    private List<MyCollection> parseHtmlToDb(String html, String category, String type) {
        Document doc = Jsoup.parse(html);

        Elements div = doc.select("div.column");
        Elements li = div.select("ul#browserItemList>li");
//        Elements page = div.select("div#multipage>div.page_inner>a");

        List<MyCollection> mList = new ArrayList<>();

        DaoSession daoSession = BangumiApp.sAppCtx.getDaoSession();

        for (int i = 0; i < li.size(); i++) {
            MyCollection entity = new MyCollection();
            Element element = li.get(i);

            String linkUrl = "http://bgm.tv" + element.select("a").attr("href");
            String bangumiId = element.select("a").attr("href").replace("/subject/", "").trim();
            String imageUrl = "https:" + element.select("a>span>img").attr("src");
            String normalName = element.select("div>h3>a").text();
            String smallName = element.select("div>h3>small").text();
            String info = element.select("div>p.info").text();
            String comment = element.select("div>div#comment_box>div>div>div.text").text();
            // TODO: 2016/9/20 以下为未处理的字段
            String airDay = "";
            String rateNumber = "";
            String rateTotal = "";

            // 这个是自己做的，这张图不一定存在
            String coverImageUrl = imageUrl.replace("/s/", "/c/");
            String largeImageUrl = imageUrl.replace("/s/", "/l/");
//            LogUtil.d(LogUtil.ZUBIN, "coverImage = " + coverImageUrl);

            entity.setCollection_type(type);
            entity.setCategory(category);
            entity.setLink_url(linkUrl);
            entity.setBangumi_id(bangumiId);
            entity.setNormal_image_url(imageUrl);
            entity.setCover_image_url(coverImageUrl);
            entity.setLarge_image_url(largeImageUrl);
            entity.setNormal_name(normalName);
            entity.setSmall_name(smallName);
            entity.setInfo(info);
            entity.setComment(comment);
            entity.setAir_day(airDay);
            entity.setRate_number(rateNumber);
            entity.setRate_total(rateTotal);

            mList.add(entity);
        }

        // 只有第一页才做这个操作，后续的数据不加入数据库缓存
        if (mPage == 1) {
            // 删除这种category 和 type 的数据并将数据插入数据库
            MyCollectionDao collectionDao = daoSession.getMyCollectionDao();
            List<MyCollection> queryList = collectionDao.queryBuilder()
                    .where(MyCollectionDao.Properties.Category.eq(category)
                            , MyCollectionDao.Properties.Collection_type.eq(type)).list();
            collectionDao.deleteInTx(queryList);

            collectionDao.insertInTx(mList);
        }

        String key = category + type;
        PreferencesUtils.putLong(BangumiApp.sAppCtx
                , MyConstants.COLLECTION_REFRESH_NAME, key, System.currentTimeMillis());

        return mList;
    }

    @Override
    public String getType(int position) {
        String type = "do";

        switch (position) {
            case 0:
                type = "do";
                break;
            case 1:
                type = "collect";
                break;
            case 2:
                type = "wish";
                break;
            case 3:
                type = "on_hold";
                break;
            case 4:
                type = "dropped";
                break;
        }

        return type;
    }

    @Override
    public String getCategory() {
        // TODO: 2016/9/19 第一个版本 category 写死了，实际上需要用户手动选择的
        // 测试后切换标签是正常的，只需要做切换的功能即可
//        return "anime";

        return category;
    }

    @Override
    public void forceRefresh(String category, String type) {
        mPage = 1;
        PreferencesUtils.putLong(BangumiApp.sAppCtx,
                MyConstants.COLLECTION_REFRESH_NAME, category + type, 0);
    }

    @Override
    public void openLogin(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    @Override
    public void openBangumiDetail(Activity activity, View view, MyCollection collection) {
        Intent intent = new Intent(activity, BangumiDetailActivity.class);
        intent.putExtra("bangumiId", collection.getBangumi_id()+"");
        intent.putExtra("name", !TextUtils.isEmpty(collection.getNormal_name()) ? collection.getNormal_name() : collection.getSmall_name());
        intent.putExtra("common_url", collection.getCover_image_url());
        intent.putExtra("large_url", collection.getLarge_image_url());
        activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "img").toBundle());
    }
}
