package com.se_team8.dongguk_usedbook_market;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
<<<<<<< HEAD

import com.google.android.gms.common.api.GoogleApiClient;
import com.se_team8.dongguk_usedbook_market.adaptor.BookAdaptor;
import com.se_team8.dongguk_usedbook_market.domain.BookVO;
=======
>>>>>>> Eomji

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-11.
 */

public class MypageActivity extends AppCompatActivity {
<<<<<<< HEAD
    public String mRecipientID = new String();
    public String mRecipientName = new String();
    public String username;
    public String userID;
    public String token;

    private GoogleApiClient client;
=======
    public String username;
    public String userID;
    private static String token;
    private MyBookAdapter adapter;
    public static String mainURL = "http://softwareengineeringtp.azurewebsites.net/"; //서버주소
    static String strJson = "";
    ArrayList<BookVOList> all_book = new ArrayList<BookVOList>();
    private BookVOList registerBook= new BookVOList("내가 판매한 책");
    private BookVOList requestBook= new BookVOList("내가 요청한 책");
>>>>>>> Eomji

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent=getIntent();

        if(intent != null){
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
            token=intent.getStringExtra("token");
<<<<<<< HEAD
=======

>>>>>>> Eomji
        }

        //위에 바 숨기기
        getSupportActionBar().hide();

        //세션아이디와 이름 입력
        TextView studentName = (TextView) findViewById(R.id.studentName);
        TextView studentID = (TextView) findViewById(R.id.studentID);
        studentID.setText(userID);
        studentName.setText(username);
<<<<<<< HEAD

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);
        final ArrayList<BookVO> list = get_Data();

       // myAdapter adapter = new myAdapter(this, list);
        BookAdaptor adapter = new BookAdaptor(this, list);
=======

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        all_book.add(requestBook);
        all_book.add(registerBook);

        adapter = new MyBookAdapter(this, all_book);
        get_RegisterData();
        get_RequestData();
>>>>>>> Eomji
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
<<<<<<< HEAD
                if(list.get(groupPosition).getID() == "0") {
                    //내가 판매등록한 책의 세부정보로 넘어가는 버튼
                    Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);

                    intent.putExtra("bookTitle", list.get(groupPosition).getTitle());
                    intent.putExtra("bookAuthor", list.get(groupPosition).getAuthor());
                    intent.putExtra("bookCover", list.get(groupPosition).getImgUrl());
                    intent.putExtra("bookISBN", list.get(groupPosition).getIsbn());
                    intent.putExtra("bookPrice", list.get(groupPosition).getPrice());
                    intent.putExtra("bookPubdate", list.get(groupPosition).getPubdate());
                    intent.putExtra("bookPublisher", list.get(groupPosition).getPublisher());
                    intent.putExtra("course", list.get(groupPosition).getCourse());
                    intent.putExtra("professor", list.get(groupPosition).getProfessor());
                    intent.putExtra("sellerPrice", list.get(groupPosition).getSellerPrice());
                    intent.putExtra("comment", list.get(groupPosition).getComment());
                    intent.putExtra("status", list.get(groupPosition).getStatus());

                    intent.putExtra("username", username);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);

                    startActivity(intent);

                }else if(list.get(groupPosition).getID() == "1"){
                   /* Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);

                    intent.putExtra("username",username);
                    intent.putExtra("userID",userID);
                    intent.putExtra("RecipientID", list.get(groupPosition).info2.get(childPosition));
                    intent.putExtra("RecipientName",list.get(groupPosition).info3.get(childPosition));

                    String str = list.get(groupPosition).info3.get(childPosition) + "님과 채팅을 시작합니다.";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

                    startActivity(intent);*/
                    //세부정보로 넘어가는 버튼
                    Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);

                    intent.putExtra("bookTitle", list.get(groupPosition).getTitle());
                    intent.putExtra("bookAuthor", list.get(groupPosition).getAuthor());
                    intent.putExtra("bookCover", list.get(groupPosition).getImgUrl());
                    intent.putExtra("bookISBN", list.get(groupPosition).getIsbn());
                    intent.putExtra("bookPrice", list.get(groupPosition).getPrice());
                    intent.putExtra("bookPubdate", list.get(groupPosition).getPubdate());
                    intent.putExtra("bookPublisher", list.get(groupPosition).getPublisher());
                    intent.putExtra("course", list.get(groupPosition).getCourse());
                    intent.putExtra("professor", list.get(groupPosition).getProfessor());
                    intent.putExtra("sellerPrice", list.get(groupPosition).getSellerPrice());
                    intent.putExtra("comment", list.get(groupPosition).getComment());
                    intent.putExtra("status", list.get(groupPosition).getStatus());

                    intent.putExtra("username", username);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);

                    startActivity(intent);
=======
                if (all_book.get(groupPosition).getListID() == 0) {
                    //내가 판매등록한 책의 세부정보로 넘어가는 버튼
                        Intent intent = new Intent(getApplicationContext(), MypageRegisterActivity.class);

                        intent.putExtra("bookID", all_book.get(groupPosition).getBookId().get(childPosition));
                        intent.putExtra("bookTitle", all_book.get(groupPosition).getTitle().get(childPosition));
                        intent.putExtra("bookAuthor", all_book.get(groupPosition).getAuthor().get(childPosition));
                        intent.putExtra("bookCover", all_book.get(groupPosition).getImgUrl().get(childPosition));
                        intent.putExtra("bookISBN", all_book.get(groupPosition).getIsbn().get(childPosition));
                        intent.putExtra("bookPubdate", all_book.get(groupPosition).getPubdate().get(childPosition));
                        intent.putExtra("bookPublisher", all_book.get(groupPosition).getPublisher().get(childPosition));
                        intent.putExtra("cource", all_book.get(groupPosition).getCourse().get(childPosition));
                        intent.putExtra("professor", all_book.get(groupPosition).getProfessor().get(childPosition));
                        intent.putExtra("sellerPrice", all_book.get(groupPosition).getSellerPrice().get(childPosition));
                        intent.putExtra("comment", all_book.get(groupPosition).getComment().get(childPosition));
                        intent.putExtra("status", all_book.get(groupPosition).getStatus().get(childPosition));
                        intent.putExtra("bookPrice", all_book.get(groupPosition).getPrice().get(childPosition));
                        intent.putExtra("owner", all_book.get(groupPosition).getOwner().get(childPosition));

                        intent.putExtra("username", username);
                        intent.putExtra("userID", userID);
                        intent.putExtra("token", token);

                        startActivity(intent);


                } else if (all_book.get(groupPosition).getListID() == 1) {

                        //세부정보로 넘어가는 버튼
                        Intent intent = new Intent(getApplicationContext(), MypageRequestActivity.class);
                        intent.putExtra("requestId", all_book.get(groupPosition).getRequestId().get(childPosition));
                        intent.putExtra("bookTitle", all_book.get(groupPosition).getTitle().get(childPosition));
                        intent.putExtra("bookAuthor", all_book.get(groupPosition).getAuthor().get(childPosition));
                        intent.putExtra("bookCover", all_book.get(groupPosition).getImgUrl().get(childPosition));
                        intent.putExtra("bookISBN", all_book.get(groupPosition).getIsbn().get(childPosition));
                        intent.putExtra("bookPubdate", all_book.get(groupPosition).getPubdate().get(childPosition));
                        intent.putExtra("bookPublisher", all_book.get(groupPosition).getPublisher().get(childPosition));
                        intent.putExtra("cource", all_book.get(groupPosition).getCourse().get(childPosition));
                        intent.putExtra("professor", all_book.get(groupPosition).getProfessor().get(childPosition));
                        intent.putExtra("sellerPrice", all_book.get(groupPosition).getSellerPrice().get(childPosition));
                        intent.putExtra("comment", all_book.get(groupPosition).getComment().get(childPosition));
                        intent.putExtra("status", all_book.get(groupPosition).getStatus().get(childPosition));
                        intent.putExtra("bookPrice", all_book.get(groupPosition).getPrice().get(childPosition));
                        intent.putExtra("owner", all_book.get(groupPosition).getOwner().get(childPosition));

                        intent.putExtra("username", username);
                        intent.putExtra("userID", userID);
                        intent.putExtra("token", token);

                        startActivity(intent);

>>>>>>> Eomji
                }

                return false;
            }
        });

    }

    //리스트 내용 설정
<<<<<<< HEAD
    private ArrayList<BookVO> get_Data() {
        ArrayList<BookVO> all_book = new ArrayList<BookVO>();
        BookVO bookVO = new BookVO();

        all_book.add(bookVO);
        /*
        ListName L_register = new ListName("판매 등록 목록");
        L_register.ListID = 1;
        // 서버데이터베이스에서 불러와 작성
        L_register.info1.add("소프트웨어공학개론");
        L_register.info2.add("최은만");
        L_register.info3.add("30000원");

        L_register.info1.add("컴퓨터시스템구조론");
        L_register.info2.add("장태무");
        L_register.info3.add("20000원");

        ListName L_request = new ListName("구매 요청 목록");
        L_request.ListID = 2;
        // 서버데이터베이스에서 불러와 작성
        L_request.info1.add("소프트웨어공학개론");
        L_request.info2.add("최은만");
        L_request.info3.add("30000원");

        L_request.info1.add("컴퓨터시스템구조론");
        L_request.info2.add("장태무");
        L_request.info3.add("20000원");

        ArrayList<ListName> all_listName = new ArrayList<>();
        all_listName.add(L_register);
        all_listName.add(L_request);
        */

        return all_book;
    }

    public void onHomeButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
=======
    private void get_RegisterData() {
        RegisterHttpAsyncTask httpTask1 = new RegisterHttpAsyncTask(MypageActivity.this,0);
        httpTask1.execute(mainURL+"users/"+userID+"/my/register/book/");
    }
    private void get_RequestData() {
        RequestHttpAsyncTask httpTask2 = new RequestHttpAsyncTask(MypageActivity.this,1);
        httpTask2.execute(mainURL+"users/"+userID+"/my/request/book/");
    }
    public void onHomeButtonClicked(View view) {
        finish();
>>>>>>> Eomji
    }

    public void onChattingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("userID",userID);
<<<<<<< HEAD
        startActivity(intent);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
=======
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public static String getListData(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            httpCon.setRequestProperty("Authorization","JWT "+ token);
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null)
                result = convertInputStreamToString(is);
            else
                result = "Did not work!";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
>>>>>>> Eomji
    }
    private class RegisterHttpAsyncTask extends AsyncTask<String, Void, String> {
        private MypageActivity mainAct;
        int listID;
        ProgressDialog dialog;

        RegisterHttpAsyncTask(MypageActivity mainActivity, int listID) {
            this.mainAct = mainActivity;
            this.listID = listID;
        }
        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            return getListData(urls[0]);
        }

        // 메인스레드가 끝나면 호출됨
        protected  void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(mainAct);
            dialog.show();      // 로딩 화면 표시
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dialog.dismiss(); //로딩화면 끝내기
                        JSONArray jsonArray = new JSONArray(strJson); //Json을 이용한 서버 정보받아오기
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) { //스트링으로 변환한 Json 정보 분류하여 가져오기

                                //각 어레이 리스트들에 각 아이디에 해당하는 정보를 저장한다.
                                registerBook.getBookId().add(jsonArray.getJSONObject(i).getString("id"));
                                registerBook.getTitle().add(jsonArray.getJSONObject(i).getString("bookTitle"));
                                registerBook.getAuthor().add(jsonArray.getJSONObject(i).getString("author"));
                                registerBook.getCourse().add(jsonArray.getJSONObject(i).getString("cource"));
                                registerBook.getProfessor().add(jsonArray.getJSONObject(i).getString("professor"));
                                registerBook.getPublisher().add(jsonArray.getJSONObject(i).getString("publisher"));
                                registerBook.getIsbn().add(jsonArray.getJSONObject(i).getString("isbn"));
                                registerBook.getSellerPrice().add(jsonArray.getJSONObject(i).getString("sellerPrice"));
                                registerBook.getImgUrl().add(jsonArray.getJSONObject(i).getString("image"));
                                registerBook.getPubdate().add(jsonArray.getJSONObject(i).getString("pubdate"));
                                registerBook.getPrice().add(jsonArray.getJSONObject(i).getString("price"));
                                registerBook.getComment().add(jsonArray.getJSONObject(i).getString("comment"));
                                registerBook.getStatus().add(jsonArray.getJSONObject(i).getString("status"));
                                registerBook.getOwner().add(jsonArray.getJSONObject(i).getString("owner"));

                                adapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                            }
                        }
                        registerBook.setListID(listID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private class RequestHttpAsyncTask extends AsyncTask<String, Void, String> {
        private MypageActivity mainAct;
        int listID;
        ProgressDialog dialog;
        RequestHttpAsyncTask(MypageActivity mainActivity, int listID) {
            this.mainAct = mainActivity;
            this.listID = listID;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            return getListData(urls[0]);
        }
        protected  void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(mainAct);
            dialog.show();     // 로딩 화면 표시
        }
        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dialog.dismiss(); //로딩화면 끝내기
                        JSONArray jsonArray = new JSONArray(strJson);
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) { //스트링으로 변환한 Json 정보 분류하여 가져오기

                                //각 어레이 리스트들에 각 아이디에 해당하는 정보를 저장한다.
                                requestBook.getRequestId().add(jsonArray.getJSONObject(i).getString("requestId"));
                                requestBook.getTitle().add(jsonArray.getJSONObject(i).getString("bookTitle"));
                                requestBook.getAuthor().add(jsonArray.getJSONObject(i).getString("author"));
                                requestBook.getCourse().add(jsonArray.getJSONObject(i).getString("cource"));
                                requestBook.getProfessor().add(jsonArray.getJSONObject(i).getString("professor"));
                                requestBook.getPublisher().add(jsonArray.getJSONObject(i).getString("publisher"));
                                requestBook.getIsbn().add(jsonArray.getJSONObject(i).getString("isbn"));
                                requestBook.getSellerPrice().add(jsonArray.getJSONObject(i).getString("sellerPrice"));
                                requestBook.getImgUrl().add(jsonArray.getJSONObject(i).getString("image"));
                                requestBook.getPubdate().add(jsonArray.getJSONObject(i).getString("pubdate"));
                                requestBook.getPrice().add(jsonArray.getJSONObject(i).getString("price"));
                                requestBook.getComment().add(jsonArray.getJSONObject(i).getString("comment"));
                                requestBook.getStatus().add(jsonArray.getJSONObject(i).getString("status"));
                                requestBook.getOwner().add(jsonArray.getJSONObject(i).getString("owner"));

                                adapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                            }
                        }
                        requestBook.setListID(listID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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
