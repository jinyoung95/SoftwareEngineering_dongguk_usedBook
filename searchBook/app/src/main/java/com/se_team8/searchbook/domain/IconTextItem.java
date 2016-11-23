package com.se_team8.searchbook.domain;

/**
 * Created by JinYoung on 2016-11-23.
 */

import android.graphics.drawable.Drawable;

// 데이터를 담고 있을 아이템 정의
public class IconTextItem {
    private Drawable mIcon; // Icon
    private String[] mData; // Data array

    private boolean mSelectable = true; // True if this item is selectable

    // Initialize with icon and data array
    public IconTextItem(Drawable icon, String[] obj) {
        mIcon = icon;
        mData = obj;
    }

    // Initialize with icon and strings
    public IconTextItem(Drawable icon, String obj01, String obj02, String obj03, String obj04, String obj05, String obj06) {
        mIcon = icon;

        mData = new String[6];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
        mData[3] = obj04;
        mData[4] = obj05;
        mData[5] = obj06;
    }

    // True if this item is selectable
    public boolean isSelectable() {
        return mSelectable;
    }

    // Set selectable flag
    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    // Get data array
    public String[] getData() {
        return mData;
    }

    // Get data
    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    // Set data array
    public void setData(String[] obj) {
        mData = obj;
    }

    // Set icon
    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    // Get icon
    public Drawable getIcon() {
        return mIcon;
    }

    // Compare with the input object
    public int compareTo(IconTextItem other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }
        return 0;
    }
}
