package me.ewriter.bangumitv.ui.progress;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.ApiManager;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;
import me.ewriter.bangumitv.api.response.BaseResponse;
import me.ewriter.bangumitv.api.response.SubjectProgress;
import me.ewriter.bangumitv.ui.progress.adapter.MyEpAdapter;
import me.ewriter.bangumitv.utils.LogUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/28.
 */

public class ProgressPresenter implements ProgressContract.Presenter {

    CompositeSubscription mSubscriptions;
    ProgressContract.View mProgressView;

    public ProgressPresenter(ProgressContract.View mProgressView) {
        this.mProgressView = mProgressView;
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();

        mProgressView.setPresenter(this);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void requestProgress(String subjectId) {
        Subscription subscription = Observable.zip(ApiManager.getWebInstance().getAnimeEp(subjectId)
                , ApiManager.getBangumiInstance().getSubjectProgress(LoginManager.getUserId(BangumiApp.sAppCtx),
                        LoginManager.getAuthString(BangumiApp.sAppCtx), subjectId), new Func2<String, SubjectProgress, List<AnimeEpEntity>>() {
                    @Override
                    public List<AnimeEpEntity> call(String s, SubjectProgress subjectProgress) {
                        LogUtil.d(LogUtil.ZUBIN, "requestProgress " + Thread.currentThread());
                        return funsionData(s, subjectProgress);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AnimeEpEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressView.setProgressBarVisible(View.GONE);
                        mProgressView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AnimeEpEntity> items) {
                        mProgressView.refresh(items);
                        mProgressView.setProgressBarVisible(View.GONE);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void updateEpStatus(final AnimeEpEntity entity, final int position, final int gridPosition) {

        // 先检查是否已经登录
        if (!LoginManager.isLogin(BangumiApp.sAppCtx)) {
            mProgressView.dismissProgressDialog();
            mProgressView.showToast(BangumiApp.sAppCtx.getString(R.string.not_login_hint));
            return;
        }

        final String[] valueArray = BangumiApp.sAppCtx.getResources().getStringArray(R.array.bottom_sheet_value);
        final String[] type = {"Queue", "Watched", "Drop", ""};

        int epsId = Integer.parseInt(entity.getEpId());
        String status = valueArray[position];

        Subscription subscription = ApiManager.getBangumiInstance()
                .updateEp(epsId, status, LoginManager.getAuthString(BangumiApp.sAppCtx))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressView.dismissProgressDialog();
                        mProgressView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mProgressView.dismissProgressDialog();
                        mProgressView.showToast(BangumiApp.sAppCtx.getString(R.string.progress_updated));
                        entity.setEpStatusName(type[position]);
                        // 这里的position 是 bottomsheet 的position
                        mProgressView.updateEp(gridPosition);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void openDiscuss(AnimeEpEntity entity) {
        String epid = entity.getEpId();
        String url = "http://bangumi.tv/m/topic/ep/" + epid;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(BangumiApp.sAppCtx.getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(BangumiApp.sAppCtx, Uri.parse(url));
    }

    /** 处理列表和进度的数据，混合起来后返回*/
    private List<AnimeEpEntity> funsionData(String html, SubjectProgress subjectProgress) {


        List<AnimeEpEntity> returnList = new ArrayList<>();

        HashMap<String, List<AnimeEpEntity>> map = parseAnimeEP(html);

        for (Map.Entry<String, List<AnimeEpEntity>> entry : map.entrySet()) {
            String key = entry.getKey();
            returnList.add(new AnimeEpEntity(R.drawable.ic_live_tv_24dp, key, MyEpAdapter.TYPE_TITLE));
            List<AnimeEpEntity> value = entry.getValue();

            if (subjectProgress != null && subjectProgress.getEps() != null) {
                List<SubjectProgress.EpsBean> eps = subjectProgress.getEps();

                for (int i = 0; i < eps.size(); i++) {
                    // 循环返回的状态
                    SubjectProgress.EpsBean bean = eps.get(i);
                    String epsId = bean.getId() + "";
                    String epsStatusName = bean.getStatus().getCssName();
                    int epsStatusCode = bean.getStatus().getId();

                    for (int j = 0; j < value.size(); j++) {
                        AnimeEpEntity entity = value.get(j);
                        if (entity.getEpId().equals(epsId)) {
                            entity.setEpStatusName(epsStatusName);
                            entity.setEpStatusCode(epsStatusCode);
                            break;
                        }
                    }
                }
            }
            returnList.addAll(value);
        }
        return returnList;
    }

    /** 解析网页章节 */
    private HashMap<String, List<AnimeEpEntity>> parseAnimeEP(String html) {
        Document document = Jsoup.parse(html);

        Elements line_list = document.select("ul.line_list>li");

        int catNumber = document.select("li.cat").size();

        // key 是 cat， value 是 list，list 里面是 item，这里先以 string 代替
        HashMap<String, List<AnimeEpEntity>> hashMap = new HashMap<>();
        List<AnimeEpEntity> itemList = new ArrayList<>();

        String key = "";

        int displayNumber = 1;
        for (int i = 0; i < line_list.size(); i++) {
            Element element = line_list.get(i);

            String cat = element.attr("class");

            if (cat.startsWith("line")) {
                // 已放送
                String epAirStatusStr = element.select("h6>span.epAirStatus").attr("title");
                //Air
                String epAirStatus = element.select("h6>span.epAirStatus>span").attr("class");

                String epUrl = element.select("h6>a").attr("href");
                String epId = epUrl.replace("/ep/", "").trim();
                String name_jp = element.select("h6>a").text();
                String name_cn = element.select("h6>span.tip").text();
                String info = element.select("small").text();

//                entity.setEpName(name_jp + name_cn);
//                entity.setEpId(epId);
//                entity.setEpUrl(epUrl);
//                entity.setInfo(info);
//                entity.setEpStatusName(epAirStatus);
//
//                entity.setDisplayName(displayNumber + "");
                AnimeEpEntity entity = new AnimeEpEntity(displayNumber+"", name_jp + name_cn, epId, epAirStatus, info, MyEpAdapter.TYPE_GRID);
                displayNumber++;

                itemList.add(entity);
            } else {
                key = element.text();
                // 换了一个类别，显示的计数从0开始
                displayNumber = 1;
            }

            // 最后一个
            if (i == line_list.size() -1) {
                hashMap.put(key, itemList);
            } else {
                // 中间
                String nextCat = line_list.get(i + 1).attr("class");
                if (!nextCat.startsWith("line")) {
                    List<AnimeEpEntity> copyList = new ArrayList<>();
                    copyList.addAll(itemList);
                    hashMap.put(key, copyList);
                    itemList.clear();
                }
            }

        }

        return hashMap;
    }
}
