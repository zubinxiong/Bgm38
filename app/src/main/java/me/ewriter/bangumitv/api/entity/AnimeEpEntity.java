package me.ewriter.bangumitv.api.entity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class AnimeEpEntity {
    // Grid 类型的数据
    // 显示的名字，即 1-2-3 之类的
    String displayName;
    // 包含中文和日文
    String epName;
    // 单集链接地址
    String epUrl;
    /** 单集的id */
    String epId;
    /** 状态，对应Air WISH, WATCHED,DROP 和默认*/
    String epStatusName;
    /** 状态 code 值 */
    int epStatusCode;
    String subjectId;
    String info;

    /** item 的 类别*/
    int type;

    /** title 类型的数据*/
    String title;
    int iconRes;

    /** title 用的构造函数 */
    public AnimeEpEntity(int iconRes, String title, int type) {
        this.iconRes = iconRes;
        this.title = title;
        this.type = type;
    }

    /**Grid 用的构造函数*/
    public AnimeEpEntity(String displayName, String epName, String epId, String epStatusName, String info, int type) {
        this.displayName = displayName;
        this.epName = epName;
        this.epId = epId;
        this.epStatusName = epStatusName;
        this.info = info;
        this.type = type;
    }

    /** 详情页显示的构造函数 */
    public AnimeEpEntity(String displayName, String subjectId) {
        this.displayName = displayName;
        this.subjectId = subjectId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public String getEpUrl() {
        return epUrl;
    }

    public void setEpUrl(String epUrl) {
        this.epUrl = epUrl;
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId;
    }

    public String getEpStatusName() {
        return epStatusName;
    }

    public void setEpStatusName(String epStatusName) {
        this.epStatusName = epStatusName;
    }

    public int getEpStatusCode() {
        return epStatusCode;
    }

    public void setEpStatusCode(int epStatusCode) {
        this.epStatusCode = epStatusCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
