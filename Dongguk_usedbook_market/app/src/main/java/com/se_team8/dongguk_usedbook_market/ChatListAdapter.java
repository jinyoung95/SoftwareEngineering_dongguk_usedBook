package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-26.
 */
/** 채팅목록을 작성하기 위한 어댑터 클래스 */
public class ChatListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<ChatListItem> chatLists; //채팅목록을 저장할 어레이 리스트

    //생성자에 변수 설정
    public ChatListAdapter(Context context, ArrayList<ChatListItem> chatLists) {
        this.mContext = context;
        this.chatLists = chatLists;
    }

    //채팅방의 개수
    @Override
    public int getCount() { return chatLists.size(); }

    //특정 목록
    @Override
    public Object getItem(int position) { return chatLists.get(position);}

    //채팅방의 아이디
    @Override
    public long getItemId(int position) { return 0; }

    //채팅방을 보여주는 View 함수
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatListView chatListView;
        if(convertView == null){ //convertView 가  NULL 이라면 CharListView 로 설정
            chatListView = new ChatListView(mContext, chatLists.get(position));
        } else {
            chatListView = (ChatListView) convertView;
        }
        chatListView.setText(0,chatLists.get(position).getmRecipientName()); //채팅방 리스트 이름 보이기
        chatListView.setText(1,chatLists.get(position).getmLast()); //채팅방 리스트 마지막 메세지 보이기

        return chatListView; //리스트뷰 반환
    }

}