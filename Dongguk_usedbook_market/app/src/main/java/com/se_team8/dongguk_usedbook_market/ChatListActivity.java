package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
<<<<<<< HEAD
=======
import android.os.AsyncTask;
>>>>>>> Eomji
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
<<<<<<< HEAD
import com.se_team8.dongguk_usedbook_market.adaptor.ChatListAdapter;
=======

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
>>>>>>> Eomji

/**
 * Created by Juhyeon on 2016-11-26.
 */
<<<<<<< HEAD

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
=======
/** 채팅목록을 보여주는 액티비티 */
public class ChatListActivity extends AppCompatActivity{
    private static String token; //토큰
    ListView listView; //채팅 목록을 보여줄 리스트뷰
    ChatListAdapter chatListAdapter;    //목록을 위한 어댑터
    public static String mainURL = "http://softwareengineeringtp.azurewebsites.net/"; //서버주소
    private String username; //사용자 이름과 아이디
    private String userID;
    static String strJson = "";
    private String chatId;
    private ArrayList<ChatListItem> chatList = new  ArrayList<ChatListItem>(); //채팅목록중 한 목록 구성내용

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat_list); //채팅리스트 목록 뷰
        Firebase.setAndroidContext(this); //파이어베이스 연동

        setTitle("채팅 목록"); //타이틀 지정

        Intent intent=getIntent(); //이전의 인텐트를 받아온다.

        if(intent != null){

            username = intent.getStringExtra("username"); //이전의 인텐트에서 유저네임, 아이디, 토큰을 받아온다.
            userID=intent.getStringExtra("userID");
            token = intent.getStringExtra("token");
        }
        listView = (ListView) findViewById(R.id.chat_list); //chat_list 라고 지정된 xml파일을 불러들임

        chatListAdapter = new ChatListAdapter(this, chatList); //채팅목록을 보여주기 위한 어댑터 클래스 지정
        setData(); //채팅목록 데이터 set
        listView.setAdapter(chatListAdapter);//리스트뷰에 어댑터를 지정

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //목록 하나를 클릭하면

                Toast.makeText(getApplicationContext(), chatList.get(position).getmRecipientName()+"님과의 채팅",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class); //개인채팅화면이 뜸
                intent.putExtra("username",username); //유저네임 유저아이디 토큰을 보냄
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);

                intent.putExtra("RecipientID",chatList.get(position).getmRecipientID()); //채팅수신자의 아이디 이름,
                intent.putExtra("RecipientName",chatList.get(position).getmRecipientName());
                intent.putExtra("chatID",chatList.get(position).getChatID()); //채팅하기 위한 고유한 채팅방 아이디
                intent.putExtra("option", "PUT"); //서버에 적용할 옵션을 지정
                startActivity(intent); //지정된 인텐르로 이동
            }
        });
    }
    //서버에 접속하여 데이터를 가져오기 위한 함수
    public void setData(){
        HttpAsyncTask1 task1 = new HttpAsyncTask1(ChatListActivity.this);
        task1.execute(mainURL+"users/"+userID+"/my/chat/list/"); //서버에 접속할 주소 //내가 채팅방 개설자일때

        HttpAsyncTask2 task2 = new HttpAsyncTask2(ChatListActivity.this);
        task2.execute(mainURL+"users/"+userID+"/my/chat/list/partner"); //서버에 접속할 주소 //내가 채팅방 초대자 일때
    }
    //서버에 접속하여 데이터를 가져오기 위한 연동함수수
   public static String getListData(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url); //url로 서버 접속
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            httpCon.setRequestProperty("Authorization","JWT "+ token); //토큰을 넘겨주어 권한 설정
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null) //객체가 존재한다면
                result = convertInputStreamToString(is); //객체로부터의 결과값 받아오기
            else
                result = "Did not work!"; //객체가 없다면 실패
            httpCon.disconnect(); //연결끊기
        } catch (IOException e) { //io예외처리
            e.printStackTrace();
        }

        return result; //결과 반환
    }
    //서버연동을 위한 클래스
    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        private ChatListActivity mainAct;

        HttpAsyncTask1(ChatListActivity mainActivity) {
            this.mainAct = mainActivity;
        }
        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            return getListData(urls[0]); //주소를 받아옴
        }

        // 메인스레드가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result; //result의 값을 strJson에 받기
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jsonArray = new JSONArray(strJson); //JSONArray 문법으로 변환
                        if(jsonArray!=null){
                            for(int i=0; i<jsonArray.length();i++){ //받아온 데이터를 분류
                                ChatListItem chatItem = new ChatListItem(); //받아올 데이터 리스트
                                chatItem.setmRecipientID(jsonArray.getJSONObject(i).getString("partner")); //파트너 아이디
                                chatItem.setmRecipientName(jsonArray.getJSONObject(i).getString("partnerName")); //파트너 이름
                                chatItem.setmLast(jsonArray.getJSONObject(i).getString("lastMessage")); //마지막 메세지
                                chatItem.setChatID(jsonArray.getJSONObject(i).getString("id")); //채팅방 아이디

                                chatId = jsonArray.getJSONObject(i).getString("id"); //채팅방 아이디를 저장해둠
                                chatListAdapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                                chatList.add(chatItem); //생성된 아이템을 리스트에 삽입
                            }
                        }
                        else{
                            //글씨를 띄움
                            Toast.makeText(getApplicationContext(), "채팅목록이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) { //JSON 에러 처리
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    //서버연동을 위한 클래스
    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        private ChatListActivity mainAct;

        HttpAsyncTask2(ChatListActivity mainActivity) {
            this.mainAct = mainActivity;
        }
        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            return getListData(urls[0]);
        }

        // 메인스레드가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result; //결과값을 받아옴
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jsonArray = new JSONArray(strJson);//JSONArray 문법으로 변환
                        if(jsonArray!=null){
                            for(int i=0; i<jsonArray.length();i++){ //데이터를 분류
                                ChatListItem chatItem = new ChatListItem();
                                chatItem.setmRecipientID(jsonArray.getJSONObject(i).getString("studentId")); //상대방 아이디
                                chatItem.setmRecipientName(jsonArray.getJSONObject(i).getString("studentName")); //상대방 이름
                                chatItem.setmLast(jsonArray.getJSONObject(i).getString("lastMessage")); //마지막 메세지
                                chatItem.setChatID(jsonArray.getJSONObject(i).getString("id")); //채팅방 아이디
                                chatListAdapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                                chatList.add(chatItem); //생성된 아이템을 목록에 추가
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    // 서버에서 받은 INPUT값을 스트링으로 변환한다.
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream)); //버퍼로 받아온 정보
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null) //버퍼를 한 줄씩 읽으며 자료 생성
            result += line;

        inputStream.close();
        return result;//결과 반환
>>>>>>> Eomji
    }
}
