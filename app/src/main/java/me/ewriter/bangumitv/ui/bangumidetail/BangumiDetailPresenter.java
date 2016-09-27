package me.ewriter.bangumitv.ui.bangumidetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.ApiManager;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.api.entity.AnimeCharacterEntity;
import me.ewriter.bangumitv.api.entity.AnimeDetailEntity;
import me.ewriter.bangumitv.api.entity.CommentEntity;
import me.ewriter.bangumitv.api.entity.TagEntity;
import me.ewriter.bangumitv.api.response.SubjectComment;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.CharacterList;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.TextItem;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.TitleItem;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.TitleMoreItem;
import me.ewriter.bangumitv.ui.login.LoginActivity;
import me.ewriter.bangumitv.utils.BlurUtil;
import me.ewriter.bangumitv.utils.LogUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zubin on 2016/9/24.
 */

public class BangumiDetailPresenter implements BangumiDetailContract.Presenter {

    private CompositeSubscription mSubscriptions;
    private BangumiDetailContract.View mDetailView;
    // cover信息
    String summaryStr = "", tagStr = "", scoreStr = "";
    // 评价信息
    int evRating = 0;
    String evComment = "";
    int evStatus = -1;


    public BangumiDetailPresenter(BangumiDetailContract.View mDetailView) {
        this.mDetailView = mDetailView;

        mDetailView.setPresenter(this);
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
    public void requestWebDetail(final String subjectId) {
//        Subscription subscription = ApiManager.getWebInstance()
//                .getAnimeDetail(subjectId)
//                .subscribeOn(Schedulers.io())
//                .map(new Func1<String, Items>() {
//                    @Override
//                    public Items call(String s) {
//                        return parseAnimeDetail(s);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Items>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.d(LogUtil.ZUBIN, "requestWebDetail onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.d(LogUtil.ZUBIN, "requestWebDetail onError ;" + e.getMessage());
//                        mDetailView.hideProgress();
//                        mDetailView.showToast(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Items items) {
//                        mDetailView.updateHeader(summaryStr, tagStr, scoreStr);
//                        mDetailView.setFabVisible(View.VISIBLE);
//                        mDetailView.refresh(items);
//                        mDetailView.hideProgress();
//                        LogUtil.d(LogUtil.ZUBIN, "requestWebDetail onNext");
//                    }
//                });

        Subscription subscription = Observable.zip(ApiManager.getWebInstance().getAnimeDetail(subjectId)
                , ApiManager.getBangumiInstance().
                        getSubjectComment(subjectId, LoginManager.getAuthString(BangumiApp.sAppCtx))
                , new Func2<String, SubjectComment, Items>() {
                    @Override
                    public Items call(String s, SubjectComment subjectComment) {
                        if (subjectComment != null) {
                            evComment = subjectComment.getComment();
                            evRating = subjectComment.getRating();
                            evStatus = subjectComment.getStatus().getId();
                        }
                        return parseAnimeDetail(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Items>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(LogUtil.ZUBIN, "requestWebDetail onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(LogUtil.ZUBIN, "requestWebDetail onError ;" + e.getMessage());
                        mDetailView.hideProgress();
                        mDetailView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Items items) {
                        mDetailView.updateHeader(summaryStr, tagStr, scoreStr);
                        mDetailView.insertComment(evComment, evStatus, evRating);
                        mDetailView.setFabVisible(View.VISIBLE);
                        mDetailView.refresh(items);
                        mDetailView.hideProgress();
                        LogUtil.d(LogUtil.ZUBIN, "requestWebDetail onNext");
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void setUpCover(final Activity activity, final ViewGroup viewGroup, final String imageUrl) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    subscriber.onStart();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        Bitmap cachedBitmap = Picasso.with(activity).load(imageUrl).get();
                        Bitmap bitmap = BlurUtil.blur(activity, cachedBitmap);

                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        viewGroup.setBackground(new BitmapDrawable(activity.getResources(), bitmap));
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void clickFab(Activity activity, String bangumiId) {
        if (!LoginManager.isLogin(BangumiApp.sAppCtx)) {
            mDetailView.showToast(BangumiApp.sAppCtx.getString(R.string.not_login_hint));
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        } else {
            mDetailView.showEvaluationDialog();
        }
    }

    @Override
    public void updateComment(String mBangumiID, String status, int rating, String comment) {
        Subscription subscription = ApiManager.getBangumiInstance()
                .updateComment(mBangumiID, status, rating, comment, LoginManager.getAuthString(BangumiApp.sAppCtx))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubjectComment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mDetailView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(SubjectComment subjectComment) {
                        mDetailView.showToast(BangumiApp.sAppCtx.getString(R.string.update_comment));
                    }
                });

        mSubscriptions.add(subscription);
    }

    /** 解析网页动画概览 */
    private Items parseAnimeDetail(String html) {
        LogUtil.d(LogUtil.ZUBIN, "parseDetail thread = " + Thread.currentThread());
        Items items = new Items();

        AnimeDetailEntity animeDetailEntity = new AnimeDetailEntity();
        Document document = Jsoup.parse(html);

        // 剧情简介
        items.add(new TitleItem(BangumiApp.sAppCtx.getString(R.string.bangumi_detail_summary), R.mipmap.ic_launcher));
        String summary = document.select("div#subject_summary").text().trim();
        summaryStr = summary;
        items.add(new TextItem(summary));

        // 左侧列表, 外面只有简介，点击进入后还有制作人员
        Elements li = document.select("div.infobox>ul#infobox>li");
        List<String> introList = new ArrayList<>();
        String showInfo = "";
        for (int i = 0; i < li.size(); i++) {
            Element element = li.get(i);

            String left_intro = element.text();

            if (i < 6) {
                showInfo += left_intro + "\n";
            }

            introList.add(left_intro);

            // 暂时不做简介里面的跳转，放到 ep/characters 中去做跳转,放在这里跳转太重了
            // 左侧的标题，比如 话数：，中文名:
//            String left_tip = element.select("span").text();
//
//            // 没有链接的标签 , 有可能是各种 分隔号比如 / 或者 、 还有其它未处理的，如果同时有多个的话要如何显示？
//            String left_normal_value = element.ownText();
//
//            // 左侧的的链接地址,不是每个标签都可点,可能有多个
//            Elements a = element.select("a");
//            for (int j = 0; j < a.size(); j++) {
//                Element href = a.get(j);
//                String href_name = href.text();
//                String href_url = href.attr("href");
//            }
        }
        items.add(new TitleMoreItem(BangumiApp.sAppCtx.getString(R.string.bangumi_detail_content), R.mipmap.ic_launcher));
        items.add(new TextItem(showInfo));


        // 角色介绍, 可能不全
        items.add(new TitleMoreItem(BangumiApp.sAppCtx.getString(R.string.bangumi_detail_character), R.mipmap.ic_launcher));
        Elements subject_clearit = document.select("ul#browserItemList>li");
        List<AnimeCharacterEntity> characterList = new ArrayList<>();
        for (int clearItIndex = 0; clearItIndex < subject_clearit.size(); clearItIndex++) {
            Element element = subject_clearit.get(clearItIndex);

            AnimeCharacterEntity entity = new AnimeCharacterEntity();

            // 角色小头像图片
            String role_image_url = "https:" + element.select("div>strong>a>span>img").attr("src");
            entity.setRoleImageUrl(role_image_url);
            // 下列链接为脑补，不一定有
            String large_mage_url = role_image_url.replace("/s/", "/l/");
            entity.setRoleLargeImageUrl(large_mage_url);

            // 日文名字
            String role_name_jp = element.select("div>strong>a").text();
            entity.setRoleNameJp(role_name_jp);
            // 角色链接
            String role_url = element.select("div>strong>a").attr("href");
            entity.setRoleUrl(role_url);

            // 角色类型， 主角
            String role_type = element.select("div>div>span>small").text();
            entity.setRoleType(role_type);
            // 角色中文名
            String role_name_cn = element.select("div>div>span>span.tip").text();
            entity.setRoleNameCn(role_name_cn);
            // 声优
            String cv_name = element.select("div>div>span>a").text();
            entity.setCvName(cv_name);
            // 声优链接
            String cv_url = element.select("div>div>span>a").attr("href");
            entity.setCvUrl(cv_url);

            characterList.add(entity);
        }
        items.add(new CharacterList(characterList));

        // 右侧收藏盒
        String global_score = document.select("div.global_score").text();
        scoreStr = global_score;

        // 标签栏
        Elements tag = document.select("div.subject_tag_section>div.inner>a");
        for (int tagIndex = 0; tagIndex < tag.size(); tagIndex++) {
            Element element = tag.get(tagIndex);

            String tag_name = element.text();
            String tag_src = element.attr("href");

            if (tagIndex < tag.size() - 1) {
                tagStr += tag_name + "、";
            } else {
                tagStr += tag_name;
            }
        }

        // 观看进度
        Elements prg_list = document.select("ul.prg_list>li");
        for (int i = 0; i < prg_list.size(); i++) {
            Element element = prg_list.get(i);
            // /ep/638065
            String url = element.select("a").attr("href");
            // load-epinfo epBtnWatched
            String state = element.select("a").attr("class");
            //  ep.5 OCHIMUSHA ～超能力と僕～
            String name = element.select("a").attr("title");
        }



        // 最顶上的名称， 一般是日文名
        String title = document.select("h1.nameSingle").text();
        animeDetailEntity.setNameJp(title);

        // 顶上名字旁边的灰色小字，一般是类别
        String small_type = document.select("h1.nameSingle>small").text();
        animeDetailEntity.setSmallType(small_type);

        // 图片large 地址, 可能为空
        String large_image_url = "https:" + document.select("div.infobox>div>a").attr("href");
        animeDetailEntity.setLargeImageUrl(large_image_url);

        // 图片cover 地址, 可能为空
        String cover_image_url = "https:" + document.select("div.infobox>div>a>img").attr("src");
        animeDetailEntity.setCoverImageUrl(cover_image_url);

        // 吐槽箱
        Elements comments = document.select("div#comment_box>div");
        List<CommentEntity> commentList = new ArrayList<>();
        for (int commentIndex = 0; commentIndex < comments.size(); commentIndex++) {
            Element element = comments.get(commentIndex);
            CommentEntity entity = new CommentEntity();

            String userLink = element.select("a").attr("href");
            //background-image:url('//lain.bgm.tv/pic/user/s/000/30/28/302862.jpg?r=1472368595')
            String originImage = element.select("a>span").attr("style").replace("\'", "");
            String userAvatar = originImage.substring(originImage.indexOf("(") + 1, originImage.indexOf(")"));
            String userName = element.select("div>a").text();
            String userComment = element.select("div>p").text();
            String commentDate = element.select("div>small").text();

            entity.setUserLink(userLink);
            entity.setUserAvatar(userAvatar);
            entity.setUserName(userName);
            entity.setUserComment(userComment);
            entity.setCommentDate(commentDate);

            commentList.add(entity);
        }
        animeDetailEntity.setCommentList(commentList);

        // TODO: 2016/9/24   关联条目 ,有好多，暂时没想好怎么放，先不做

        return items;
    }


    /** 解析网页章节 */
    private String parseAnimeEP(String html) {
        Document document = Jsoup.parse(html);

        Elements line_list = document.select("ul.line_list>li");

        int catNumber = document.select("li.cat").size();

        // key 是 cat， value 是 list，list 里面是 item，这里先以 string 代替
        HashMap<String, List<String>> hashMap = new HashMap<>();
        List<String> itemList = new ArrayList<>();

        String key = "";

        for (int i = 0; i < line_list.size(); i++) {
            Element element = line_list.get(i);

            String cat = element.attr("class");

            if (cat.startsWith("line")) {
                // 已放送
                String epAirStatusStr = element.select("h6>span.epAirStatus").attr("title");
                //Air
                String epAirStatus = element.select("h6>span.epAirStatus>span").attr("class");

                String epUrl = element.select("h6>a").attr("href");
                String name_jp = element.select("h6>a").text();
                String name_cn = element.select("h6>span.tip").text();
                String info = element.select("small").text();

                itemList.add(name_cn);
            } else {
                key = element.text();
            }

            // 最后一个
            if (i == line_list.size() -1) {
                hashMap.put(key, itemList);
            } else {
                // 中间
                String nextCat = line_list.get(i + 1).attr("class");
                if (!nextCat.startsWith("line")) {
                    List<String> copyList = new ArrayList<>();
                    copyList.addAll(itemList);
                    hashMap.put(key, copyList);
                    itemList.clear();
                }
            }

        }

        return "parseEP";
    }


    /** 解析网页人物介绍 一页到底，不翻页*/
    private String parseAnimeCharacters(String html) {
        Document document = Jsoup.parse(html);

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

        return "parseAnimeCharacters";
    }


    /** 解析网页制作人员 不翻页*/
    private String parseAnimePersons(String html) {

        Document document = Jsoup.parse(html);

        Elements elements = document.select("div#columnInSubjectA>div");

        for (int i = 0; i < elements.size(); i++) {

            Element element = elements.get(i);

            String avatar_url = element.select("a").attr("href");
            String avatar_image_url = element.select("a>img").attr("src");

            String name = element.select("div>h2>a").text();
            String info = element.select("div>div.prsn_info").text();

        }


        return "parseAnimePersons";
    }


    /** 解析网页评论 会翻页*/
    private String parseAnimeComments(String html) {

        Document document = Jsoup.parse(html);

        Elements comments = document.select("div#comment_box>div");

        for (int i = 0; i < comments.size(); i++) {
            Element element = comments.get(i);

            String userLink = element.select("a").attr("href");
            // TODO: 2016/9/24  style 还需要再处理
            //background-image:url('//lain.bgm.tv/pic/user/s/000/30/28/302862.jpg?r=1472368595')
            String originImage = element.select("a>span").attr("style").replace("\'", "");
            String userAvatar = originImage.substring(originImage.indexOf("(") + 1, originImage.indexOf(")"));

            String userName = element.select("div>div>a").text();
            String userComment = element.select("div>div>p").text();
            String commentDate = element.select("div>div>small").text();
        }

        return "parseAnimeComments";
    }

}
