package me.ewriter.bangumitv.event;

/**
 * Created by Zubin on 2016/8/9.
 */
public class ProgressUpdateEvent {

    boolean isUpdate;

    public ProgressUpdateEvent(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
