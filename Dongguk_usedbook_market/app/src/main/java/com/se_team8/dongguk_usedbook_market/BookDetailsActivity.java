package com.se_team8.dongguk_usedbook_market;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
<<<<<<< HEAD
=======

import com.androidquery.AQuery;

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

/**
 *   Created by Eomji(2014112041 김엄지) on 2016-11-26.
 */
>>>>>>> Eomji

import com.androidquery.AQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *   Created by Eomji on 2016-11-26.
 */

// http://softwareengineeringtp.azurewebsites.net/application/book/bookid
public class BookDetailsActivity extends AppCompatActivity {
    private static String userName, userID, token, bookID;
    private String owner, title, author, publisher, price, pubdate, ISBN, cover, course, professor, sellerPrice, comment, status;
    private TextView tvSellerName, tvTitle, tvAuthor, tvPublisher, tvPubdate, tvPrice, tvISBN, tvCourse, tvProfessor, tvSellerPrice, tvComment, tvStatus;
    private static String strJson = "";
    private static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        getSupportActionBar().hide(); // 타이틀 숨김

<<<<<<< HEAD
        getSupportActionBar().hide(); // 타이틀을 안보이도록 함

=======
>>>>>>> Eomji
        // 선택한 도서의 세부정보 보여주기
        AQuery aq = new AQuery(this);
        Intent intent = getIntent();

        if (intent != null) {
            // BuyerSearchActivity로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
<<<<<<< HEAD
            userID = intent.getStringExtra("uerID");
=======
            userID = intent.getStringExtra("userID");
>>>>>>> Eomji
            token = intent.getStringExtra("token");

            bookID = intent.getStringExtra("bookID");
            title = intent.getStringExtra("bookTitle");
            author = intent.getStringExtra("bookAuthor");
            publisher = intent.getStringExtra("bookPublisher");
            price = intent.getStringExtra("bookPrice");
            pubdate = intent.getStringExtra("bookPubdate");
            ISBN = intent.getStringExtra("bookISBN");
            cover = intent.getStringExtra("bookCover");
<<<<<<< HEAD
            course = intent.getStringExtra("course");
=======
            course = intent.getStringExtra("cource");
>>>>>>> Eomji
            professor = intent.getStringExtra("professor");
            sellerPrice = intent.getStringExtra("sellerPrice");
            comment = intent.getStringExtra("comment");
            status = intent.getStringExtra("status");
            owner = intent.getStringExtra("owner");
        }

        tvTitle = (TextView) findViewById(R.id.tvBdBookTitle);
        tvAuthor = (TextView) findViewById(R.id.tvBdBookAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvBdBookPublisher);
        tvPrice = (TextView) findViewById(R.id.tvBdBookPrice);
        tvPubdate = (TextView) findViewById(R.id.tvBdBookPubdate);
        tvISBN = (TextView) findViewById(R.id.tvBdBookISBN);
        tvCourse = (TextView) findViewById(R.id.tvBdCourse);
        tvProfessor = (TextView) findViewById(R.id.tvBdProfessor);
        tvSellerPrice = (TextView) findViewById(R.id.tvBdSellerPrice);
        tvComment = (TextView) findViewById(R.id.tvBdMoreDetails);
        tvStatus = (TextView) findViewById(R.id.tvBdStatus);
        tvSellerName = (TextView) findViewById(R.id.tvBdSellerName);
<<<<<<< HEAD

=======
        // 받아온 정보 출력
>>>>>>> Eomji
        tvTitle.setText(Html.fromHtml(title));
        tvAuthor.setText(Html.fromHtml(author));
        tvPrice.setText(price);
        tvPubdate.setText(pubdate);
        tvPublisher.setText(Html.fromHtml(publisher));
        tvISBN.setText(ISBN);
        aq.id(R.id.ivBdBookCover).image(cover);
        tvCourse.setText(course);
        tvProfessor.setText(professor);
        tvSellerPrice.setText(sellerPrice);
        tvComment.setText(comment);
        tvStatus.setText(status);
        tvSellerName.setText(owner);
    }

<<<<<<< HEAD
    // 홈 버튼 클릭 -> 홈으로 이동
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        userName = intent.getStringExtra("username");
        userID = intent.getStringExtra("uerID");
        token = intent.getStringExtra("token");
        startActivity(intent);
    }

    // 목록 버튼 클릭 -> 구매요청목록으로 이동
    public void onListBtnClicked(View view){
        //Intent intent= new Intent(getApplicationContext(),);
        finish();
    }

    //구매요청 클릭하면 디비에 저장됨
    public void onRequestBtnClicked(View view){
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함
        // call AsynTask to perform network operation on separate thread
        HttpAsyncTask httpTask = new HttpAsyncTask(BookDetailsActivity.this);
        //httpTask에 data 넘겨줌
        httpTask.execute(mainURL+"application/book/", bookID);
    }

=======
    /**
     *  홈 버튼 클릭 -> 홈으로 이동
     *  @param view
     *  */
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /**
     * 목록 버튼 클릭 -> 구매요청목록으로 이동
     * @param view
     * */
    public void onListBtnClicked(View view){
        finish();
    }

    /**
     * 구매요청 버튼 클릭 -> 구매 요청하는 스레드 실행
     * @param view
     * */
    public void onRequestBtnClicked(View view){
        getSupportActionBar().hide(); // 타이틀 숨김
        HttpAsyncTask httpTask = new HttpAsyncTask(BookDetailsActivity.this);
        httpTask.execute(mainURL+"application/book/", bookID, userName);      //구매 요청 스레드 실행
    }

    /**
     * 구매요청 (서버에 요청)
     * @param url - 연결할 서버의 주소
     * @return - 이미 요청한 값이면 "error"
     * */
>>>>>>> Eomji
    public static String request(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

<<<<<<< HEAD
            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookId", bookID);
            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // header설정 (Request Body 전달시 application/json로 서버에 전달.)
=======
            // bookID를 JSONObject 형식으로 변환해 저장
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookId", bookID);
            jsonObject.put("ownerName", userName);
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
=======
            httpCon.setDoOutput(true);  // POST방식으로 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // 서버로부터 응답을 받겠다는 옵션

            //checkConnection(httpCon); //디버깅용 함수

            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream()); // 서버에 보낼 객체 생성
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

>>>>>>> Eomji
            //checkConnection(httpCon);

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null)
<<<<<<< HEAD
                // convert inputstream to string
                result = convertInputStreamToString(is);
=======
                result = convertInputStreamToString(is);    // 서버의 응답을 String형식으로 result에 저장
>>>>>>> Eomji
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

<<<<<<< HEAD
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private BookDetailsActivity mainAct;
=======
    /** 구매요청 서버요청을 위한 스레드 클래스 */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private BookDetailsActivity mainAct;
        ProgressDialog dialog;
>>>>>>> Eomji

        HttpAsyncTask(BookDetailsActivity mainActivity) {
            this.mainAct = mainActivity;
        }

<<<<<<< HEAD
        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            bookID=urls[1];
            return request(urls[0]);
        }

        // doInBackground(메인스레드)가 끝나면 호출됨
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
         * @return - 이미 요청한 값이면 "error"
         * */
        @Override
        protected String doInBackground(String... urls) {
            bookID=urls[1];
            userName=urls[2];
            return request(urls[0]);
        }

        /**
         *  doInBackground(메인스레드)가 끝나면 호출됨 (요청완료메시지 출력)
         *  @param result - 이미 요청한 값이면 "error"
         *  */
>>>>>>> Eomji
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
<<<<<<< HEAD
                    Toast.makeText(getApplicationContext(), "요청완료되었습니다", Toast.LENGTH_LONG).show();
                    finish();
=======
                    if(strJson.contains("error")) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"이미 요청한 책입니다", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if(strJson.contains("mybook")){
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"내가 등록한 책입니다", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "요청완료되었습니다", Toast.LENGTH_LONG).show();
                        finish();
                    }
>>>>>>> Eomji
                }
            });
        }
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
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
<<<<<<< HEAD
=======
    }

    /**
     * 연결이 제대로 이루어졌는지 확인하고 그렇지 않다면 오류메시지 출력
     * @param httpCon - 연결한 서버의 HttpURLConnection 객체체
     * */
    private static void checkConnection(HttpURLConnection httpCon){
        //연결 확인
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int code = 0;
        try {
            code = httpCon.getResponseCode();
            if(code>=400){  // 서버 내부 오류인 경우
                bos.reset();
                InputStream err = httpCon.getErrorStream();
                while(true){
                    int readlen = err.read(buf);
                    if(readlen<1)
                        break;
                    bos.write(buf,0,readlen);
                }
                String output = new String(bos.toByteArray(), "utf-8");
                System.err.println(output); //에러메시지 출력
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> Eomji
    }
}