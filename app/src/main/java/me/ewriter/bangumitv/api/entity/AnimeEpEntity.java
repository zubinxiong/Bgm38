package me.ewriter.bangumitv.api.entity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class AnimeEpEntity {
    String displayName;
    String epName;
    String epId;
    String epStatus;

    public AnimeEpEntity(String displayName) {
        this.displayName = displayName;
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

    public String getEpStatus() {
        return epStatus;
    }

    public void setEpStatus(String epStatus) {
        this.epStatus = epStatus;
    }
}
