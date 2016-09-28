package me.ewriter.bangumitv.api.entity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class AnimeEpEntity {
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

    public AnimeEpEntity() {
    }

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

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


    public String getEpUrl() {
        return epUrl;
    }

    public void setEpUrl(String epUrl) {
        this.epUrl = epUrl;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
