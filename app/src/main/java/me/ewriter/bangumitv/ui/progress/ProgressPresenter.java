package me.ewriter.bangumitv.ui.progress;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.ewriter.bangumitv.base.BasePresenter;
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
    public void requestProgress() {

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
}
