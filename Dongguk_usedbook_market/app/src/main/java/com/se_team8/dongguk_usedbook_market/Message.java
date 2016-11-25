package com.se_team8.dongguk_usedbook_market;

import java.util.Date;
/**
 * Created by Juhyeon on 2016-11-24.
 */

public class Message {
    private String mText;
    private String mSender;
    private Date mDate;

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
