package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.se_team8.dongguk_usedbook_market.adaptor.ChatListAdapter;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class ChatListActivity extends AppCompatActivity{
    ListView listView;
    ChatListAdapter chatListAdapter = new ChatListAdapter(this);
    private MessageDataSource.MessageListener mListener;
   // private static final String mSender="2014110000";
    //private static final String mSenderName = "홍길동";
    private String username;
    private String userID;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat_list);
        Firebase.setAndroidContext(this);

        setTitle("채팅 목록");
        listView = (ListView) findViewById(R.id.chat_list);

        Intent intent=getIntent();

        if(intent != null){
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
        }

        setData();
        listView.setAdapter(chatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatListItem curItem = (ChatListItem)chatListAdapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getApplicationContext(), curData[0]+"님과의 채팅",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);
                intent.putExtra("RecipientID","2014112019");
                intent.putExtra("RecipientName","김주현");
                startActivity(intent);
            }
        });
    }
    public void setData(){
        Firebase sRef;
        MessageDataSource messageDataSource = new MessageDataSource();
        sRef = messageDataSource.getsRef();

        //sRef.child(userID).
        if(sRef.child(userID).getKey() == userID){
            chatListAdapter.addItem(new ChatListItem("김주현",""));
            chatListAdapter.addItem(new ChatListItem("김엄지",""));

        }else{
            listView.setEmptyView(findViewById(R.id.empty));
        }
    }
}
