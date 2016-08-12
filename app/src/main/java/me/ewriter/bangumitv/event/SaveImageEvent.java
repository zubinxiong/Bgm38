package me.ewriter.bangumitv.event;

/**
 * Created by Zubin on 2016/8/12.
 */
public class SaveImageEvent {

    private String mImageUrl;
    private String mName;

    public SaveImageEvent(String mImageUrl, String mName) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mImageText) {
        this.mName = mImageText;
    }
}
