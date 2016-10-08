package me.ewriter.bangumitv.event;

/**
 * Created by Zubin on 2016/10/8.
 */

public class CategoryChangeEvent {
    private String category;

    public CategoryChangeEvent(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
