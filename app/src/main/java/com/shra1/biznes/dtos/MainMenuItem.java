package com.shra1.biznes.dtos;

import android.support.annotation.Nullable;
import android.view.View;

public class MainMenuItem {
    String Title;
    int IconResource;
    View.OnClickListener OnClickListener;
    int BadgeCount;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getIconResource() {
        return IconResource;
    }

    public void setIconResource(int iconResource) {
        IconResource = iconResource;
    }

    public View.OnClickListener getOnClickListener() {
        return OnClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        OnClickListener = onClickListener;
    }

    public int getBadgeCount() {
        return BadgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        BadgeCount = badgeCount;
    }

    public MainMenuItem(String title, @Nullable int iconResource, View.OnClickListener onClickListener, int badgeCount) {

        Title = title;
        IconResource = iconResource;
        OnClickListener = onClickListener;
        BadgeCount = badgeCount;
    }

    public MainMenuItem() {

    }
}
