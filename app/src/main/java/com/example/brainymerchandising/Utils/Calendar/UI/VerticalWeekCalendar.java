package com.example.brainymerchandising.Utils.Calendar.UI;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brainymerchandising.R;
import com.example.brainymerchandising.Utils.Calendar.Abstract.DateWatcher;
import com.example.brainymerchandising.Utils.Calendar.Abstract.OnDateClickListener;
import com.example.brainymerchandising.Utils.Calendar.Abstract.ResProvider;
import com.example.brainymerchandising.Utils.Calendar.controller.VerticalWeekAdapter;

import java.util.GregorianCalendar;

public class VerticalWeekCalendar extends LinearLayoutCompat implements ResProvider {

    private Context context;
    private String customFont;
    private int defaultDayTextColor;
    private int defaultWeekDayTextColor;
    private int defaultBackground;
    private int selectedTextColor;
    private int selectedBackground;
    public   static int m  ;
    public   static int today  ;



    public  VerticalWeekAdapter adapter;

    public VerticalWeekCalendar(Context context) {
        super(context);

        this.context = context;
        initLayout(context);
    }

    public VerticalWeekCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
        loadStyle(context,attrs);
    }

    public VerticalWeekCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
        loadStyle(context,attrs);
    }

    private void init() {
        setupRecyclerView();
    }

    private void initLayout(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
        inflate(context, R.layout.verticalweekcalendar_week,this);
    }

    private void loadStyle(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VerticalWeekCalendar, R.attr.verticalWeekCalendarStyleAttr, R.style.VerticalWeekCalendarStyle);
        customFont = ta.getString(R.styleable.VerticalWeekCalendar_customFont);

        defaultBackground = ta.getResourceId(R.styleable.VerticalWeekCalendar_dayBackground, 0);
        defaultDayTextColor = ta.getColor(R.styleable.VerticalWeekCalendar_dayTextColor, 0);
        defaultWeekDayTextColor = ta.getColor(R.styleable.VerticalWeekCalendar_weekDayTextColor, 0);

        selectedBackground = ta.getResourceId(R.styleable.VerticalWeekCalendar_selectedBackground, 0);
        selectedTextColor = ta.getColor(R.styleable.VerticalWeekCalendar_selectedDayTextColor, 0);

        ta.recycle();
    }

    public void setupRecyclerView() {
        RecyclerView recyclerView  = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager
                        (getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(getAdapter());
        adapter.days.clear();


        if(this.today==1){
            adapter.initCalendar();
        }else if (this.today==0)      {

        adapter.initCalendar(this.m);
        adapter.notifyDataSetChanged();         }

      //  Log.d("adapterMaher 1", "FirstVisibleItem: " + adapter);



        recyclerView.scrollToPosition(15);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            private int mLastFirstVisibleItem;
            private boolean mIsScrollingUp;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

                final int currentFirstVisibleItem = lm.findFirstVisibleItemPosition();

                if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                    mIsScrollingUp = false;
                } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                    mIsScrollingUp = true;
                }

                mLastFirstVisibleItem = currentFirstVisibleItem;

                if (lm.findFirstVisibleItemPosition() < 10 && mIsScrollingUp) {
                    getAdapter().addCalendarDays(false);
                    Log.d("adapterMaher 0", "FirstVisibleItem: " + getAdapter());

                    Log.i("onScrollChange", "FirstVisibleItem: " + lm.findFirstVisibleItemPosition());
                    Log.i("onScrollChange", "new Size: " + getAdapter().days.size());
                } else if ((getAdapter().days.size() - 1 - lm.findLastVisibleItemPosition()) < 10 && !mIsScrollingUp) {
                    getAdapter().addCalendarDays(true);
                    Log.i("onScrollChange", "LastVisibleItem: " + lm.findLastVisibleItemPosition());
                    Log.i("onScrollChange", "new Size: " + getAdapter().days.size());}
            }
        });
    }

    public VerticalWeekAdapter getAdapter() {

        return adapter == null ? createAdapter() : adapter;
    }

    public VerticalWeekAdapter createAdapter() {

        adapter = new VerticalWeekAdapter(this, VerticalWeekCalendar.m);


        return adapter;
    }

    public void setOnDateClickListener(final OnDateClickListener callback){
        getAdapter().setOnDateClickListener(callback);
    }

    public void setDateWatcher(DateWatcher dateWatcher) {
        getAdapter().setDateWatcher(dateWatcher);
    }

    public void setCustomFont(String customFont) {
        this.customFont = customFont;
    }

    public void setDefaultDayTextColor(int defaultDayTextColor) {
        this.defaultDayTextColor = defaultDayTextColor;
    }

    public void setDefaultBackground(int defaultBackground) {
        this.defaultBackground = defaultBackground;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public void setSelectedBackground(int selectedBackground) {
        this.selectedBackground = selectedBackground;
    }

    @Override
    public int getSelectedDayBackground() {
        return selectedBackground;
    }

    @Override
    public int getSelectedDayTextColor() {
        return selectedTextColor;
    }

    @Override
    public int getDayTextColor() {
        return defaultDayTextColor;
    }

    @Override
    public int getWeekDayTextColor() {
        return defaultWeekDayTextColor;
    }

    @Override
    public int getDayBackground() {
        return defaultBackground;
    }

    @Override
    public Typeface getCustomFont() {
        if (customFont == null) {
            return null;
        }
        try {
            return ResourcesCompat.getFont(context, getResources().getIdentifier(customFont.split("\\.")[0],
                    "font", context.getPackageName()));
        } catch (Exception e) {
            return null;
        }
    }

    public static class Builder {

        private int view;

        public Builder() {
        }

        public Builder setView(int view) {
            this.view = view;
            return this;
        }

        public VerticalWeekCalendar init(View appCompatActivity){

            VerticalWeekCalendar calendar = appCompatActivity.findViewById(view);
            calendar.init();
            return calendar;
        }
    }
}
