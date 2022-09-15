package com.example.brainymerchandising.Utils.Calendar.Abstract;

import com.example.brainymerchandising.Utils.Calendar.controller.VerticalWeekAdapter;

public interface DateWatcher {
    int getStateForDate(int year, int month, int day, VerticalWeekAdapter.DayViewHolder view);
}
