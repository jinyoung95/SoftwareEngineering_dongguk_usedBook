package com.se_team8.dongguk_usedbook_market;

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

// bookId=책아이디  http://softwareengineeringtp.azurewebsites.net/appllication/book/
// bookName=검색할책이름  http://softwareengineeringtp.azurewebsites.net/search/book/
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
            // BuyerSearchActivity로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
            userID = intent.getStringExtra("uerID");
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

                intent.putExtra("username", userName);
                intent.putExtra("userID", userID);
                intent.putExtra("token", token);

                startActivity(intent); // 액티비티 실행
            }
        });
    }

    public void onSearchButtonClicked(View view){
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함
        String query = search_text.getText().toString();
        mBookList.clear();  // 리스트 초기화

        if(!validate())
            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
        else {
            // call AsynTask to perform network operation on separate thread
            HttpAsyncTask httpTask = new HttpAsyncTask(BuyerSearchActivity.this);
            //httpTask에 data 넘겨줌
            httpTask.execute(mainURL+"search/book/", search_text.getText().toString());
        }

        //DB에 저장된 판매등록된 목록 불러오기

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        @Override
//        public void onClick(View v) {
//            task = new BackgroundTask();
//            task.execute();
//        }
//
//        private String request(String urlStr) {
//            StringBuilder output = new StringBuilder();
//            try {
//                URL url = new URL(urlStr);
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                if (conn != null) {
//                    conn.setConnectTimeout(10000);
//                    conn.setRequestMethod("GET");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//
//                    int resCode = conn.getResponseCode();
//                    if (resCode == HttpURLConnection.HTTP_OK) {
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
//                        String line = null;
//                        while(true) {
//                            line = reader.readLine();
//                            if (line == null) {
//                                break;
//                            }
//                            output.append(line + "\n");
//                        }
//
//                        reader.close();
//                        conn.disconnect();
//                    }
//                }
//            } catch(Exception ex) {
//                Log.e("SampleHTTP", "Exception in processing response.", ex);
//                ex.printStackTrace();
//            }
//
//            return output.toString();
//        }

        //adapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신

        //Toast.makeText(getApplicationContext(), "검색이 완료되었습니다.", Toast.LENGTH_LONG).show();
    }

    public static String search(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookName", bookTitle);
            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // header설정 (Request Body 전달시 application/json로 서버에 전달.)
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Authorization","JWT "+ token); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            //checkConnection(httpCon);
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기
            checkConnection(httpCon);

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

        HttpAsyncTask(BuyerSearchActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            bookTitle=urls[1];
            return search(urls[0]);
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
                        JSONArray jsonArray = new JSONArray(strJson);
                        if(jsonArray!=null){
                            for(int i=0; i<jsonArray.length();i++){
                                BookVO book = new BookVO();
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
                                mBookList.add(book);

                                adapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                                Toast.makeText(getApplicationContext(), "검색이 완료되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "검색결과가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /** 입력텍스트를 모두 입력했는지 확인 */
    private boolean validate(){
        if(search_text.getText().toString().trim().equals(""))
            return false;
        else
            return true;
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

    /** 연결이 제대로 이루어졌는지 확인하고 그렇지 않다면 오류메시지 출력 */
    private static void checkConnection(HttpURLConnection httpCon){
        //연결 확인
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int code = 0;
        try {
            code = httpCon.getResponseCode();
            if(code>=400){
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

    // 홈 버튼 클릭 -> 홈으로 이동
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }
}