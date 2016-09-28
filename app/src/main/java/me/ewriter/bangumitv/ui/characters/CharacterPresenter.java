package me.ewriter.bangumitv.ui.characters;

import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.drakeet.multitype.Item;
import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.api.ApiManager;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/28.
 */

public class CharacterPresenter implements CharacterContract.Presenter{

    CompositeSubscription mSubscriptions;
    CharacterContract.View mCharacterView;

    public CharacterPresenter(CharacterContract.View mCharacterView) {
        this.mCharacterView = mCharacterView;
        mCharacterView.setPresenter(this);
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
    public void requestCharacter(String subjectId) {
        Subscription subscription = ApiManager.getWebInstance()
                .getAnimeCharacters(subjectId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Items>() {
                    @Override
                    public Items call(String s) {
                        return parseAnimeCharacters(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Items>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCharacterView.setProgressBarVisible(View.GONE);
                        mCharacterView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Items items) {
                        mCharacterView.setProgressBarVisible(View.GONE);
                        mCharacterView.refresh(items);
                    }
                });
    }

    /** 解析网页人物介绍 一页到底，不翻页*/
    private Items parseAnimeCharacters(String html) {
        Document document = Jsoup.parse(html);
        Items items = new Items();

        Elements elements = document.select("div#columnInSubjectA>div");

        for (int i = 0; i < elements.size(); i++) {

            Element element = elements.get(i);

            String avatar_url = element.select("a").attr("href");
            String avatar_image_url = element.select("a>img").attr("src");

            String role_name_cn = element.select("div.clearit>h2>span").text();
            String role_name_jp = element.select("div.clearit>h2>a").text();

            String info = element.select("div.clearit>div.crt_info").text();

            String cv_url = element.select("div.actorBadge clearit>a").attr("href");
            String cv_image_url = element.select("div.actorBadge clearit>a>img").attr("src");
            String cv_name_jp = element.select("div.actorBadge clearit>p>a").text();
            String cv_name_cn = element.select("div.actorBadge clearit>p>small").text();
        }

        return items;
    }
}
