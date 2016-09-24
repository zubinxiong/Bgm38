package me.ewriter;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {

//    private static final String WINDOW_PATH = "E:/BangumiTV/app/src/main/java";
    private static final String MAC_PATH = "/Users/zubin/documents/AndroidWorkspace/Bangumitv/app/src/main/java";

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(5, "me.ewriter.bangumitv.dao");

        // 新建每日放送表
        addCalendar(schema);

        // 新建我的进度表
        addCollection(schema);

        new DaoGenerator().generateAll(schema, MAC_PATH);
    }

    private static void addCollection(Schema schema) {
        Entity entity = schema.addEntity("MyCollection");

        entity.addIdProperty();
        // 类型，在看，看过 等 共5 种
        entity.addStringProperty("collection_type");
        entity.addStringProperty("link_url");
        entity.addStringProperty("image_url");
        entity.addStringProperty("large_image_url");
        // 网页上的中文名
        entity.addStringProperty("normal_name");
        // 网页上的日文名,不一定有
        entity.addStringProperty("small_name");
        // 介绍信息
        entity.addStringProperty("info");
        // 分数
        entity.addStringProperty("rate_number");
        // 评分人数
        entity.addStringProperty("rate_total");

        /////// 下面两个不一定使用,放送日期网页上也没有

        // 评论
        entity.addStringProperty("comment");
        // 放送日期
        entity.addStringProperty("air_day");
    }

    private static void addCalendar(Schema schema) {
        Entity entity = schema.addEntity("BangumiCalendar");
        entity.addIdProperty();
        entity.addStringProperty("name_cn").notNull();
        entity.addIntProperty("air_weekday").notNull();
        entity.addIntProperty("bangumi_id").notNull();
        entity.addIntProperty("bangumi_total");
        entity.addFloatProperty("bangumi_average");
        entity.addStringProperty("large_image");
        entity.addStringProperty("common_image");
        entity.addStringProperty("medium_image");
        entity.addStringProperty("small_image");
        entity.addStringProperty("grid_image");
        entity.addIntProperty("rank");
        entity.addStringProperty("name_jp");
    }
}
