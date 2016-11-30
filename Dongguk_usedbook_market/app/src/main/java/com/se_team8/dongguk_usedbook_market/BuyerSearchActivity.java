package com.se_team8.dongguk_usedbook_market;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.se_team8.dongguk_usedbook_market.adaptor.BookAdaptor;
import com.se_team8.dongguk_usedbook_market.domain.BookVO;

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Eomji on 2016-11-27.
 */

// bookName=검색할책이름  http://softwareengineeringtp.azurewebsites.net/search/book/
=======
import com.androidquery.AQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Eomji(2014112041 김엄지) on 2016-11-27.
 */

>>>>>>> Eomji
public class BuyerSearchActivity extends AppCompatActivity{
    private AQuery aq = new AQuery(this);
    private ArrayList<BookVO> mBookList = new ArrayList<BookVO>();
    private static String userName, userID, token;
    private EditText search_text;
    private BookAdaptor adapter;
    private ListView listView;
    private static String bookTitle;
    public static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";
    static String strJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함

        Intent intent = getIntent();
        if (intent != null) {
            // HomeActivity로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
<<<<<<< HEAD
            userID = intent.getStringExtra("uerID");
=======
            userID = intent.getStringExtra("userID");
>>>>>>> Eomji
            token = intent.getStringExtra("token");
        }

        search_text = (EditText) findViewById(R.id.searchText);
        listView = (ListView) findViewById(R.id.listView);

        listView.setEmptyView(findViewById(R.id.empty)); // 검색 결과 없음 표시
        adapter = new BookAdaptor(this, mBookList); //어댑터 객체 생성
        listView.setAdapter(adapter); // 리스트뷰에 어댑터 객체 설정

        // 구매요청할 책 선택 -> 해당 도서의 세부정보 페이지로 넘어간다
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();
                BookVO curItem = (BookVO) adapter.getItem(position); // 선택한 item의 position 받기
                Intent intent = new Intent(BuyerSearchActivity.this, BookDetailsActivity.class); // intent 생성
<<<<<<< HEAD

                intent.putExtra("bookTitle", curItem.getTitle());
                intent.putExtra("bookAuthor", curItem.getAuthor());
                intent.putExtra("bookCover", curItem.getImgUrl());
                intent.putExtra("bookISBN", curItem.getIsbn());
                intent.putExtra("bookPrice", curItem.getPrice());
                intent.putExtra("bookPubdate", curItem.getPubdate());
                intent.putExtra("bookPublisher", curItem.getPublisher());
                intent.putExtra("course", curItem.getCourse());
                intent.putExtra("professor", curItem.getProfessor());
                intent.putExtra("sellerPrice", curItem.getSellerPrice());
                intent.putExtra("comment", curItem.getComment());
                intent.putExtra("status", curItem.getStatus());
                intent.putExtra("bookID", curItem.getID());
                intent.putExtra("owner", curItem.getOwner());

                intent.putExtra("username", userName);
                intent.putExtra("userID", userID);
                intent.putExtra("token", token);

=======

                intent.putExtra("bookTitle", curItem.getTitle());
                intent.putExtra("bookAuthor", curItem.getAuthor());
                intent.putExtra("bookCover", curItem.getImgUrl());
                intent.putExtra("bookISBN", curItem.getIsbn());
                intent.putExtra("bookPrice", curItem.getPrice());
                intent.putExtra("bookPubdate", curItem.getPubdate());
                intent.putExtra("bookPublisher", curItem.getPublisher());
                intent.putExtra("cource", curItem.getCourse());
                intent.putExtra("professor", curItem.getProfessor());
                intent.putExtra("sellerPrice", curItem.getSellerPrice());
                intent.putExtra("comment", curItem.getComment());
                intent.putExtra("status", curItem.getStatus());
                intent.putExtra("bookID", curItem.getID());
                intent.putExtra("owner", curItem.getOwner());

                intent.putExtra("username", userName);
                intent.putExtra("userID", userID);
                intent.putExtra("token", token);

>>>>>>> Eomji
                startActivity(intent); // 액티비티 실행
            }
        });
    }

<<<<<<< HEAD
    public void onSearchButtonClicked(View view){
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함
        String query = search_text.getText().toString();
        mBookList.clear();  // 리스트 초기화

        if(!validate())
            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
        else {
            // call AsynTask to perform network operation on separate thread
            HttpAsyncTask httpTask = new HttpAsyncTask(BuyerSearchActivity.this);
            // httpTask에 data 넘겨줌
            httpTask.execute(mainURL+"search/book/", search_text.getText().toString());
        }
    }

=======
    /**
     * 검색 버튼 클릭 -> 판매 등록 도서들이 검색되는 스레드 실행
     * @param view
     * */
    public void onSearchButtonClicked(View view){
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함
        mBookList.clear();  // 리스트 초기화

        if(!validate())     // 검색어를 입력하지 않고 검색 버튼을 누른 경우
            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
        else {
            HttpAsyncTask httpTask = new HttpAsyncTask(BuyerSearchActivity.this);
            httpTask.execute(mainURL+"search/book/", search_text.getText().toString()); // 검색 스레드 실행
        }
    }

    /**
     * 검색어에 따른 판매 등록 도서 검색 (서버에 요청)
     * @param url - 연결할 서버의 주소소
     * @return - 검색된 책 리스트를 JSONObject String형식으로 반환
     * */
>>>>>>> Eomji
    public static String search(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

<<<<<<< HEAD
            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookName", bookTitle);
            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // header설정 (Request Body 전달시 application/json로 서버에 전달.)
=======
            // 책제목을 JSONObject 형식으로 변환해 저장
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookName", bookTitle);
            json = jsonObject.toString();

            // header설정
>>>>>>> Eomji
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Authorization","JWT "+ token); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
<<<<<<< HEAD
            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            //checkConnection(httpCon);
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기
            //checkConnection(httpCon);

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null)
                // convert inputstream to string
                result = convertInputStreamToString(is);
            else
                result = "Did not work!";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private BuyerSearchActivity mainAct;
        ProgressDialog dialog;

=======
            httpCon.setDoOutput(true);  // POST방식으로 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // 서버로부터 응답을 받겠다는 옵션

            //checkConnection(httpCon); // 디버깅 위한 임시 함수

            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream()); // 서버에 보낼 객체 생성
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            is = httpCon.getInputStream(); //서버에서 읽어오는 객체 생성
            if(is != null)
                result = convertInputStreamToString(is);    // 서버의 응답을 String으로 변환해 result에 저장
            else
                result = "Did not work!";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /** 판매등록도서 검색 서버요청을 위한 스레드 클래스 */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private BuyerSearchActivity mainAct;
        ProgressDialog dialog;
>>>>>>> Eomji
        HttpAsyncTask(BuyerSearchActivity mainActivity) {
            this.mainAct = mainActivity;
        }

<<<<<<< HEAD
        protected  void onPreExecute(){
            super.onPreExecute();

            dialog = new ProgressDialog(mainAct);
            dialog.show();
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
=======
        /** 메인스레드 실행하기 전에 수행됨 (로딩 화면 표시) */
        protected  void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(mainAct);
            dialog.show(); // 로딩 화면 표시
        }

        /**
         * 스레드의 메인부분 (검색 요청 수행)
         * @param urls - 연결할 서버 주소
         * @return - 검색된 책 리스트를 JSONObject String형식으로 반환
         * */
>>>>>>> Eomji
        @Override
        protected String doInBackground(String... urls) {
            bookTitle=urls[1];
            return search(urls[0]);
        }

<<<<<<< HEAD
        // doInBackground(메인스레드)가 끝나면 호출됨
=======
        /**
         *  doInBackground(메인스레드)가 끝나면 호출됨 (검색완료메시지 출력)
         *  @param result - JSONObject String형식의 검색된 책 리스트
         *  */
>>>>>>> Eomji
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
<<<<<<< HEAD
            if (strJson.compareTo("[]") != 0) {
=======
            if (strJson.compareTo("[]") != 0) { // 검색 결과 존재하는 경우
>>>>>>> Eomji
                mainAct.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(strJson);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BookVO book = new BookVO();
                                book.setID(jsonArray.getJSONObject(i).getString("id"));
                                book.setTitle(jsonArray.getJSONObject(i).getString("bookTitle"));
                                book.setAuthor(jsonArray.getJSONObject(i).getString("author"));
                                book.setImgUrl(jsonArray.getJSONObject(i).getString("image"));
                                book.setPublisher(jsonArray.getJSONObject(i).getString("publisher"));
                                book.setPubdate(jsonArray.getJSONObject(i).getString("pubdate"));
                                book.setIsbn(jsonArray.getJSONObject(i).getString("isbn"));
                                book.setPrice(jsonArray.getJSONObject(i).getString("price"));
                                book.setSellerPrice(jsonArray.getJSONObject(i).getString("sellerPrice"));
                                book.setComment(jsonArray.getJSONObject(i).getString("comment"));
                                book.setStatus(jsonArray.getJSONObject(i).getString("status"));
                                book.setCourse(jsonArray.getJSONObject(i).getString("cource"));
                                book.setProfessor(jsonArray.getJSONObject(i).getString("professor"));
                                book.setOwner(jsonArray.getJSONObject(i).getString("owner"));
                                mBookList.add(book);

                                adapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                            }
<<<<<<< HEAD
                            dialog.dismiss();
=======
                            dialog.dismiss();   // 로딩화면 끝
>>>>>>> Eomji
                            Toast.makeText(getApplicationContext(), "검색이 완료되었습니다.", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
<<<<<<< HEAD
=======
                dialog.dismiss();
>>>>>>> Eomji
                Toast.makeText(getApplicationContext(), "검색결과가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

<<<<<<< HEAD
    /** 입력텍스트를 모두 입력했는지 확인 */
=======
    /**
     * 입력텍스트를 모두 입력했는지 확인
     * @return - 모두 입력했으면 true, 입력하지 않았으면 false를 반환
     * */
>>>>>>> Eomji
    private boolean validate(){
        if(search_text.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

<<<<<<< HEAD
    /** InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환) */
=======
    /**
     * InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환)
     * @param inputStream - 서버에서 받은 값을 저장한 InputStream 객체
     * @return - 서버에서 받은 값을 String으로 변환한 결과
     * */
>>>>>>> Eomji
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

<<<<<<< HEAD
    /** 연결이 제대로 이루어졌는지 확인하고 그렇지 않다면 오류메시지 출력 */
=======
    /**
     * 연결이 제대로 이루어졌는지 확인하고 그렇지 않다면 오류메시지 출력
     * @param httpCon - 연결한 서버의 HttpURLConnection 객체체
     * */
>>>>>>> Eomji
    private static void checkConnection(HttpURLConnection httpCon){
        //연결 확인
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int code = 0;
        try {
            code = httpCon.getResponseCode();
<<<<<<< HEAD
            if(code>=400){
=======
            if(code>=400){          // 서버 내부 오류이면
>>>>>>> Eomji
                bos.reset();
                InputStream err = httpCon.getErrorStream();
                while(true){
                    int readlen = err.read(buf);
                    if(readlen<1)
                        break;
                    bos.write(buf,0,readlen);
                }
                String output = new String(bos.toByteArray(), "utf-8");
                System.err.println(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    // 홈 버튼 클릭 -> 홈 화면으로 이동
=======
    /**
     * 홈 버튼 클릭 -> 홈으로 이동
     * @param view
     * */
>>>>>>> Eomji
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }
}