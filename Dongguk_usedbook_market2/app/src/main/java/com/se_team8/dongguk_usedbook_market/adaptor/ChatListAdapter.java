package com.se_team8.dongguk_usedbook_market.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.se_team8.dongguk_usedbook_market.ChatListItem;
import com.se_team8.dongguk_usedbook_market.ChatListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class ChatListAdapter extends BaseAdapter{
    private Context mContext;
    private List<ChatListItem> chatLists = new ArrayList<ChatListItem>();

    public ChatListAdapter(Context context){ this.mContext = context; }

    @Override
    public int getCount() { return chatLists.size(); }

    @Override
    public Object getItem(int position) { return chatLists.get(position);}

    public void addItem(ChatListItem it) { chatLists.add(it); }

    @Override
    public long getItemId(int position) { return 0; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatListView chatListView;
        if(convertView == null){
            chatListView = new ChatListView(mContext, chatLists.get(position));
        } else {
            chatListView = (ChatListView) convertView;
        }
        chatListView.setText(0,chatLists.get(position).getmRecipient());
        chatListView.setText(1,chatLists.get(position).getmLast());

        return chatListView;
    }

}