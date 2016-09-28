package me.ewriter.bangumitv.ui.characters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Zubin on 2016/9/28.
 */

public class CharacterPresenter {

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
}
