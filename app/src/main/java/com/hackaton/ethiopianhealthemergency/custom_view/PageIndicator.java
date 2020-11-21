package com.hackaton.ethiopianhealthemergency.custom_view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.LinkedList;
import java.util.List;

public class PageIndicator extends LinearLayout {
    List<AppCompatTextView> views = new LinkedList<>();
    int totalPage = 4;

    public PageIndicator(Context context, int pageCount) {
        super(context);
        totalPage = pageCount;
        init();
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        removeAllViews();
        for (int i = 0; i < totalPage; i++) {
            AppCompatTextView view = new AppCompatTextView(getContext());
            view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setText("●");
            view.setTextColor(Color.parseColor("#883D3D3D"));
            view.setTextSize(15);
            view.setPadding(15, 0, 15, 0);
            views.add(view);
            addView(view);
        }
        setCurrentPage(0);
    }
    public void setCurrentPage(int currentPage) {
        changeColor(currentPage);
        }

    private void changeColor(int pos){
        for (int i = 0; i < views.size(); i++) {
            views.get(i).setTextColor(Color.parseColor("#883D3D3D"));
        }
        views.get(pos).setTextColor(Color.WHITE);
    }

}
