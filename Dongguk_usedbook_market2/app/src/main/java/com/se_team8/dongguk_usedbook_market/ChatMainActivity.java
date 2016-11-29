package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juhyeon on 2016-11-24.
 */

public class ChatMainActivity extends AppCompatActivity implements View.OnClickListener, MessageDataSource.MessagesCallbacks {

    public static final String USER_EXTRA = "USER";
    public static final String TAG = "ChatMainActivity";
    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private String mRecipientName;
  //  private String mSender;
    private ListView mListView;
    private String mConvoId;
    private MessageDataSource.MessageListener mListener;
    public String username;
    public String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        Firebase.setAndroidContext(this);

        Intent intent = getIntent();

        if(intent != null){
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
            mRecipient = intent.getStringExtra("RecipientID");
            mRecipientName=intent.getStringExtra("RecipientName");
        }

        mListView = (ListView) findViewById(R.id.message_list);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        setTitle(mRecipientName);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Button sendMessage = (Button) findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        String[] ids = {userID,"-", mRecipient};
        //Arrays.sort(ids);
        mConvoId = userID;
        mListener = MessageDataSource.addMessagesListener(mConvoId,mRecipient, this);
    }

    @Override
    public void onClick(View v) {
        EditText newMessageView = (EditText) findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setmDate(new Date());
        msg.setmText(newMessage);
        msg.setmSender(mRecipient);
        MessageDataSource.saveMessage(msg, mConvoId,mRecipient);
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }

    private class MessagesAdapter extends ArrayAdapter<Message> {

        MessagesAdapter(ArrayList<Message> messages) {
            super(ChatMainActivity.this, R.layout.message_item, R.id.message, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            TextView nameView = (TextView) convertView.findViewById(R.id.message);
            nameView.setText(message.getmText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getmSender().equals(userID)) {
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_right_green));
                } else {
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                }
                layoutParams.gravity = Gravity.RIGHT;
            } else {
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_left_gray));
                } else {
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                }
                layoutParams.gravity = Gravity.LEFT;
            }

            nameView.setLayoutParams(layoutParams);

            return convertView;
        }
    }
}
