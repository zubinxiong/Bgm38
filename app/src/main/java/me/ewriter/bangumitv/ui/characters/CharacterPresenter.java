package me.ewriter.bangumitv.ui.characters;

import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.drakeet.multitype.Item;
import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.api.ApiManager;
import me.ewriter.bangumitv.ui.characters.adapter.CharacterItem;
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

        mSubscriptions.add(subscription);
    }

    /** 解析网页人物介绍 一页到底，不翻页*/
    private Items parseAnimeCharacters(String html) {
        Document document = Jsoup.parse(html);
        Items items = new Items();

        Elements elements = document.select("div#columnInSubjectA>div");

        for (int i = 0; i < elements.size(); i++) {

            Element element = elements.get(i);

            String avatar_url = element.select("a").attr("href");
            String avatar_image_url = "https:" + element.select("a>img").attr("src");
            // 脑补大图，不一定存在
            String large_image_url = avatar_image_url.replace("/g/", "/l/");

            String role_name_cn = element.select("div.clearit>h2>span").text();
            String role_name_jp = element.select("div.clearit>h2>a").text();

            String info = element.select("div.clearit>div.crt_info").text();

            String cv_url = element.select("div.clearit>div.actorBadge>a").attr("href");
            String cv_image_url = "https:" + element.select("div.clearit>div.actorBadge>a>img").attr("src");
            String cv_name_jp = element.select("div.clearit>div.actorBadge>p>a").text();
            String cv_name_cn = element.select("div.clearit>div.actorBadge>p>small").text();

            CharacterItem characterItem = new CharacterItem(role_name_jp + role_name_cn, large_image_url,
                    info, cv_name_jp);
            items.add(characterItem);
        }

        return items;
    }
}
