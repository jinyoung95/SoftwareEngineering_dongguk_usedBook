package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class ChatListItem {
    private String[] data = new String[2];
<<<<<<< HEAD
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
=======
    private String mRecipientID; //상대방아이디
    private String mRecipientName; //상대방 이름
    private String mLast; //마지막 메세지
    private String chatID; //채팅방아이디

    public  ChatListItem(){}

    /** Getter and Setter */
    public String getChatID(){
        return chatID;
    }

    public void setChatID(String chatID)
    {
        this.chatID =chatID;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getmRecipientID() {
        return mRecipientID;
    }

    public void setmRecipientID(String mRecipientID) {
        this.mRecipientID = mRecipientID;
    }

    public String getmRecipientName() {
        return mRecipientName;
    }

    public void setmRecipientName(String mRecipientName) {
        this.mRecipientName = mRecipientName;
>>>>>>> Eomji
    }

    public String getmLast() {
        return mLast;
    }

    public void setmLast(String mLast) {
        this.mLast = mLast;
    }
<<<<<<< HEAD

    public String[] getData(){ return data; }
=======
>>>>>>> Eomji
}
