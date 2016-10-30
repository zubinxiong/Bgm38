package me.ewriter.bangumitv.ui.persons;

import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.ApiManager;
import me.ewriter.bangumitv.widget.commonAdapter.TextItem;
import me.ewriter.bangumitv.ui.persons.adapter.PersonItem;
import me.ewriter.bangumitv.ui.persons.adapter.PersonItemList;
import me.ewriter.bangumitv.widget.commonAdapter.TitleItem;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/28.
 */

public class PersonPresenter implements PersonContract.Presenter {

    CompositeSubscription mSubscriptions;
    PersonContract.View mPersonView;

    public PersonPresenter(PersonContract.View mPersonView) {
        this.mPersonView = mPersonView;
        mPersonView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void requestPerson(String subjectId) {
        Subscription subscription = ApiManager.getWebInstance()
                .getAnimePersons(subjectId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Items>() {
                    @Override
                    public Items call(String s) {
                        return parseAnimePersons(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Items>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPersonView.showToast(e.getMessage());
                        mPersonView.setProgressBarVisible(View.GONE);
                    }

                    @Override
                    public void onNext(Items items) {
                        mPersonView.refresh(items);
                        mPersonView.setProgressBarVisible(View.GONE);
                    }
                });
    }


    /** 解析网页制作人员 不翻页*/
    private Items parseAnimePersons(String html) {

        Document document = Jsoup.parse(html);
        Items items = new Items();

        Elements elements = document.select("div#columnInSubjectA>div");

        items.add(new TitleItem(BangumiApp.sAppCtx.getString(R.string.bangumi_persons), R.drawable.ic_weekend_24dp));
        List<PersonItem> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {

            Element element = elements.get(i);
            String avatar_url = element.select("a").attr("href");
            String avatar_image_url = "https:" + element.select("a>img").attr("src");

            String name = element.select("div>h2>a").text().trim();
            String info = element.select("div>div.prsn_info").text().trim();

            list.add(new PersonItem(name, info, avatar_image_url));
        }
        if (list.size() == 0) {
            items.add(new TextItem("没有数据_(:з”∠)_"));
        } else {
            items.add(new PersonItemList(list));
        }

        return items;
    }
}
