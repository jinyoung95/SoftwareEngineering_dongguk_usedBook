package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Juhyeon on 2016-11-26.
 */
<<<<<<< HEAD

public class ChatListView extends LinearLayout {
    private TextView mRecipient;
    private TextView mLast;
=======
/** 채팅리스트뷰를 설정하는 클래스  */
public class ChatListView extends LinearLayout {
    private TextView mRecipientName; //이상대방 이름
    private TextView mLast;//마지막 메세지
>>>>>>> Eomji

    public ChatListView(Context context, ChatListItem chatListItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
<<<<<<< HEAD
        inflater.inflate(R.layout.chat_list_child,this,true);

        mRecipient = (TextView)findViewById(R.id.mRecipient);
        mLast = (TextView)findViewById(R.id.mLast);
    }
    public void setText(int index, String data) {
        if (index == 0) {
            mRecipient.setText(data);
=======
        inflater.inflate(R.layout.chat_list_child,this,true); //채팅목록 XML 파일 지정

        mRecipientName = (TextView)findViewById(R.id.mRecipientName); //상대방 이름 텍스트뷰
        mLast = (TextView)findViewById(R.id.mLast); //마지막 메세지 텍스트 뷰
    }
    public void setText(int index, String data) { //텍스트 설정
        if (index == 0) {
            mRecipientName.setText(data);
>>>>>>> Eomji
        } else if (index == 1) {
            mLast.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
