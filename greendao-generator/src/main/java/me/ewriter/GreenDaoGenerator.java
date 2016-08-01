package me.ewriter;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {

    private static final String WINDOW_PATH = "E:/BangumiTV/app/src/main/java";
//    private static final String MAC_PATH = "/Users/zubin/documents/AndroidWorkspace/Bangumitv/app/src/main/java";

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(2, "me.ewriter.bangumitv.dao");

        // 新建每日放送表
        addCalendar(schema);

        new DaoGenerator().generateAll(schema, WINDOW_PATH);
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
    }
}
