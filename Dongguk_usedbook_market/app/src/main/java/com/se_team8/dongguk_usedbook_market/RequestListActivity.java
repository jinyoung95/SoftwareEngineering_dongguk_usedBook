package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-26.
 */

//요청목록을 위한 클래스
public class RequestListActivity extends AppCompatActivity{
    ListView listView;
    RequestListAdapter requestListAdapter;
    private String username;
    private String userID;
    private static String bookID;
    private static String token;
    public static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";
    static String strJson = "";
    private ArrayList<RequestListItem> requestList = new  ArrayList<RequestListItem>();

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat_list);
        Firebase.setAndroidContext(this);

        setTitle("요청 목록"); //타이틀 이름 지정
        listView = (ListView) findViewById(R.id.chat_list);

        Intent intent=getIntent();

        if(intent != null){ //이전 인텐트에서 유저의 정보와 책의 아이디를 받아온다.
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
            token = intent.getStringExtra("token");
            bookID = intent.getStringExtra("bookID");
        }
        //어댑터를 설정한다.
        requestListAdapter = new RequestListAdapter(this,requestList);
        setData(); //리스트뷰의 데이터를 설정
        listView.setAdapter(requestListAdapter); //리스트뷰에 어댑터를 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //요청목록을 클릭한다.
                RequestListItem curItem = (RequestListItem)requestListAdapter.getItem(position);
                String[] curData = curItem.getData();

                Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);

                intent.putExtra("username",username); //데이터를 보낸다.
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("option","POST");

                intent.putExtra("RecipientID", curData[0]);
                intent.putExtra("RecipientName",curData[1]);

                String str = curData[1] + "님과 채팅을 시작합니다."; //채팅하는 사람의 이름을 보여준다.
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

                startActivity(intent);
            }
        });
    }
    public void setData(){
        HttpAsyncTask httpTask = new HttpAsyncTask(RequestListActivity.this);
        httpTask.execute(mainURL+"users/"+userID+"/my/buy/book/"+bookID+"/");
    }
    public static String search(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookId", bookID);

            httpCon.setRequestProperty("Authorization","JWT "+ token);
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null)
                result = convertInputStreamToString(is); //읽어온 객체를 스트링으로 변환한다.
            else
                result = "Did not work!"; //객체를 읽어오지 못한다.
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private RequestListActivity mainAct;

        HttpAsyncTask(RequestListActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            return search(urls[0]);
        }

        // 메인스레드가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            if (strJson.compareTo("[]") != 0) {
                mainAct.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(strJson);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                RequestListItem requestListItem = new RequestListItem(); //Json 자료를 변환하여 저장한다.

                                requestListItem.setRequestStudentID(jsonArray.getJSONObject(i).getString("owner"));
                                requestListItem.setRequestStudentName(jsonArray.getJSONObject(i).getString("ownerName"));

                                requestList.add(requestListItem);
                                requestListAdapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "요청한 사람이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
    /** InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환) */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}