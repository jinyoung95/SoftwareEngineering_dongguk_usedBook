package com.se_team8.dongguk_usedbook_market;

import java.util.Date;
/**
 * Created by Juhyeon on 2016-11-24.
 */

public class Message { //채팅 메세지 클래스
    private String mText;  //텍스트
    private String mSender; //보낸사람
    private Date mDate; //보낸날짜 --> 파이어베이스에서 채팅방 아이디 설정예정

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
