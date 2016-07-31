package me.ewriter.bangumitv.event;

import android.support.annotation.Nullable;

import java.util.List;

import me.ewriter.bangumitv.api.response.Calendar;

/**
 * Created by zubin on 16/7/30.
 */
public class CalendarUpdateEvent {

    private List<Calendar> calendar;

    public CalendarUpdateEvent(@Nullable List<Calendar> calendar) {
        this.calendar = calendar;
    }

    public List<Calendar> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<Calendar> calendar) {
        this.calendar = calendar;
    }
}
