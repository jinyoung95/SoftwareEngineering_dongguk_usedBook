package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juhyeon on 2016-11-24.
 */

public class ChatMainActivity extends AppCompatActivity implements View.OnClickListener, MessageDataSource.MessagesCallbacks {

    public static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";
    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private String mRecipientName;
    private ListView mListView;
    private String mConvoId;
    private MessageDataSource.MessageListener mListener;
    public static String username;
    public static String userID;
    private static String token;
    private String chatID;
    static String strJson = "";
    private ChatListItem chatListItem;
    private static String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        Firebase.setAndroidContext(this);

        Intent intent = getIntent();

        if(intent != null){ //이전 인텐트에서 정보들을 받아온다.
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
            token = intent.getStringExtra("token");
            mRecipient = intent.getStringExtra("RecipientID");
            mRecipientName=intent.getStringExtra("RecipientName");
            chatID =  intent.getStringExtra("chatID");
            option = intent.getStringExtra("option");
        }

        mListView = (ListView) findViewById(R.id.message_list);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        setTitle(mRecipientName);
        if (getSupportActionBar() != null) { //액션바를 지원한다.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button sendMessage = (Button) findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        mConvoId = userID; //유저아이디 설정
        mListener = MessageDataSource.addMessagesListener(mConvoId,mRecipient, this); //메세지를 추가한다.
    }

    @Override
    public void onClick(View v) { //SEND 버튼을 클릭하면
        EditText newMessageView = (EditText) findViewById(R.id.new_message); //적혀있는 메세지
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setmDate(new Date()); //메세지에 각정보를 담아
        msg.setmText(newMessage);
        msg.setmSender(mRecipient);
        MessageDataSource.saveMessage(msg, mConvoId,mRecipient); //메세지를 파이어베이스에 저장한다.

        HttpAsyncTask httpTask1 = new HttpAsyncTask(ChatMainActivity.this);
        HttpAsyncTask httpTask2 = new HttpAsyncTask(ChatMainActivity.this);

        if(option.equals("POST")) {
            httpTask1.execute(mainURL + "users/" + userID + "/my/chat/list/", mRecipient, mRecipientName, msg.getmText(),username);
        }
        else {
            httpTask2.execute(mainURL + "users/" + chatID + "/my/chat/list/update/", msg.getmText());
            option = "PUT";
        }
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
            if (message.getmSender().equals(userID)) { //메세지를 보낸 사람이 유저 아이디와 같다면
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_right_green)); //오른쪽 말풍선 삽입
                } else {
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                }
                layoutParams.gravity = Gravity.RIGHT; //오른쪽 정렬
            } else { //메세지를 보낸사람이 유저아이디가 아니라면
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_left_gray)); //왼쪽 회색 말풍선삽입
                } else {
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                }
                layoutParams.gravity = Gravity.LEFT; //왼쪽으로 정렬
            }

            nameView.setLayoutParams(layoutParams);

            return convertView;
        }
    }

    public static String POST(String url, ChatListItem chatListItem) {
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            if(option.equals("POST")) { //옵션형식이 POST인 경우
                jsonObject.put("partner", chatListItem.getmRecipientID());
                jsonObject.put("partnerName", chatListItem.getmRecipientName());
                jsonObject.put("studentName", username);
            }
            //PUT인경우  마지막 메세지만 저장한다.
            jsonObject.put("lastMessage", chatListItem.getmLast());

            json = jsonObject.toString(); // convert JSONObject to JSON to String

            httpCon.setRequestMethod(option);
            httpCon.setRequestProperty("Authorization","JWT "+ token); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            httpCon.setDoOutput(true);  // POST 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // 서버로부터 응답을 받겠다는 옵션.

            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("UTF-8")); // 서버에 쓰기
            out.flush(); // 스트림 버퍼 비우기

            try {
                is = httpCon.getInputStream(); // 실질적으로 웹서버에 접속하여 요청을 보내고 응답을 수신하는 시점
                if (is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!"; //객체를 받아오지 못함
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpCon.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    // 판매등록 저장을 위한 스레드
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private ChatMainActivity mainAct;
        HttpAsyncTask(ChatMainActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        protected  void onPreExecute(){
            super.onPreExecute();
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            chatListItem = new ChatListItem();
            if(option.equals("POST")) {
                chatListItem.setmRecipientID(urls[1]);
                chatListItem.setmRecipientName(urls[2]);
                chatListItem.setmLast(urls[3]);
            }
            else{
                chatListItem.setmLast(urls[1]);
            }

            return POST(urls[0], chatListItem);
        }

        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (strJson.contains("Success")) {
                        option = "PUT";
                    }
                }
            });
        }
    }
    // InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환)
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
