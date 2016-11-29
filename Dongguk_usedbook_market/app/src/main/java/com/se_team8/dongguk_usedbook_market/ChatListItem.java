package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class ChatListItem {
    private String[] data = new String[2];
    private String mRecipient;
    private String mLast;

    public ChatListItem(String mRecipient, String mLast){
        this.mRecipient = mRecipient;
        this.mLast = mLast;
        this.data[0] = mRecipient;
        this.data[1] = mLast;
    }

    public String getmRecipient() {
        return mRecipient;
    }

    public void setmRecipient(String mRecipient) {
        this.mRecipient = mRecipient;
    }

    public String getmLast() {
        return mLast;
    }

    public void setmLast(String mLast) {
        this.mLast = mLast;
    }

    public String[] getData(){ return data; }
}
