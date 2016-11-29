package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.se_team8.dongguk_usedbook_market.adaptor.RequestListAdapter;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListActivity extends AppCompatActivity{
    ListView listView;
    RequestListAdapter requestListAdapter = new RequestListAdapter(this);
    private MessageDataSource.MessageListener mListener;
   // private static final String mSender="2014110000";
    //private static final String mSenderName = "홍길동";
    private String username;
    private String userID;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat_list);
        Firebase.setAndroidContext(this);

        setTitle("요청 목록");
        listView = (ListView) findViewById(R.id.chat_list);

        Intent intent=getIntent();

        if(intent != null){
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
        }

        setData();
        listView.setAdapter(requestListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestListItem curItem = (RequestListItem)requestListAdapter.getItem(position);
                String[] curData = curItem.getData();

                Intent chatintent = new Intent(getApplicationContext(), ChatMainActivity.class);

                chatintent.putExtra("username",username);
                chatintent.putExtra("userID",userID);

                chatintent.putExtra("RecipientID", curData[0]);
                chatintent.putExtra("RecipientName",curData[1]);

                String str = curData[1] + "님과 채팅을 시작합니다.";
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

                startActivity(chatintent);
            }
        });
    }
    public void setData(){
        Firebase sRef;
        MessageDataSource messageDataSource = new MessageDataSource();
        sRef = messageDataSource.getsRef();

        //sRef.child(userID).
        if(sRef.child(userID).getKey() == userID){
            requestListAdapter.addItem(new RequestListItem("2014112019","김주현"));
            requestListAdapter.addItem(new RequestListItem("2014112041","김엄지"));

        }else{
            listView.setEmptyView(findViewById(R.id.empty));
        }
    }
}
