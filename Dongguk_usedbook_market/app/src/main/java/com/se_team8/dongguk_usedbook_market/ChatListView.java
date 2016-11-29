package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class ChatListView extends LinearLayout {
    private TextView mRecipient;
    private TextView mLast;

    public ChatListView(Context context, ChatListItem chatListItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_list_child,this,true);

        mRecipient = (TextView)findViewById(R.id.mRecipient);
        mLast = (TextView)findViewById(R.id.mLast);
    }
    public void setText(int index, String data) {
        if (index == 0) {
            mRecipient.setText(data);
        } else if (index == 1) {
            mLast.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
