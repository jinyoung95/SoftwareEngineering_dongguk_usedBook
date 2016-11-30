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

import com.androidquery.AQuery;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *   Created by Eomji(2014112041김엄지) on 2016-11-28.
 */

public class MypageRequestActivity extends AppCompatActivity {
    private static String userName, userID, token, bookID;
    private String owner, title, author, publisher, price, pubdate, ISBN, cover, course, professor, sellerPrice, comment, status;
    private static String requestID;
    private TextView tvSellerName, tvTitle, tvAuthor, tvPublisher, tvPubdate, tvPrice, tvISBN, tvCourse, tvProfessor, tvSellerPrice, tvComment, tvStatus;
    private static String strJson = "";
    private static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_request);
        getSupportActionBar().hide(); // 타이틀을 안보이도록 함

        // 선택한 도서의 세부정보 보여주기
        AQuery aq = new AQuery(this);
        Intent intent = getIntent();

        if (intent != null) {
            // My Page로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
            userID = intent.getStringExtra("userID");
            token = intent.getStringExtra("token");

            requestID = intent.getStringExtra("requestId");
            bookID = intent.getStringExtra("bookID");
            title = intent.getStringExtra("bookTitle");
            author = intent.getStringExtra("bookAuthor");
            publisher = intent.getStringExtra("bookPublisher");
            price = intent.getStringExtra("bookPrice");
            pubdate = intent.getStringExtra("bookPubdate");
            ISBN = intent.getStringExtra("bookISBN");
            cover = intent.getStringExtra("bookCover");
            course = intent.getStringExtra("cource");
            professor = intent.getStringExtra("professor");
            sellerPrice = intent.getStringExtra("sellerPrice");
            comment = intent.getStringExtra("comment");
            status = intent.getStringExtra("status");
            owner = intent.getStringExtra("owner");
        }

        tvTitle = (TextView) findViewById(R.id.tvMpBookTitle);
        tvAuthor = (TextView) findViewById(R.id.tvMpBookAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvMpBookPublisher);
        tvPrice = (TextView) findViewById(R.id.tvMpBookPrice);
        tvPubdate = (TextView) findViewById(R.id.tvMpBookPubdate);
        tvISBN = (TextView) findViewById(R.id.tvMpBookISBN);
        tvCourse = (TextView) findViewById(R.id.tvMpCourse);
        tvProfessor = (TextView) findViewById(R.id.tvMpProfessor);
        tvSellerPrice = (TextView) findViewById(R.id.tvMpSellerPrice);
        tvComment = (TextView) findViewById(R.id.tvMpMoreDetails);
        tvStatus = (TextView) findViewById(R.id.tvMpStatus);
        tvSellerName = (TextView) findViewById(R.id.tvMpSellerName);
        // 꺼내온 데이터 출력
        tvTitle.setText(Html.fromHtml(title));
        tvAuthor.setText(Html.fromHtml(author));
        tvPrice.setText(price);
        tvPubdate.setText(pubdate);
        tvPublisher.setText(Html.fromHtml(publisher));
        tvISBN.setText(ISBN);
        aq.id(R.id.ivMpBookCover).image(cover);
        tvCourse.setText(course);
        tvProfessor.setText(professor);
        tvSellerPrice.setText(sellerPrice);
        tvComment.setText(comment);
        tvStatus.setText(status);
        tvSellerName.setText(owner);
    }

    /**
     * 홈 버튼 클릭 -> 홈으로 이동
     * @param view
     * */
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /**
     * 목록 버튼 클릭 -> 이전화면(마이페이지)로 이동
     * @param view
     * */
    public void onListBtnClicked(View view){
        finish();
    }

    /**
     * 삭제 버튼 클릭 -> 구매 요청 도서 삭제 스레드 실행
     * @param view
     * */
    public void onReqDelBtnClicked(View view){
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함
        HttpAsyncTask httpTask = new HttpAsyncTask(MypageRequestActivity.this);
        httpTask.execute(mainURL+"application/del/book/"+requestID+"/");    // 삭제 스레드 실행
    }

    /**
     * 구매 요청 도서 삭제 (서버에 요청)
     * @param url - 연결할 서버 주소
     * @return - 삭제 완료되면 "" 반환
     * */
    public static String deleteRequest(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

            // header설정
            httpCon.setRequestMethod("DELETE"); // DELETE방식으로 보냄 (삭제)
            httpCon.setRequestProperty("Authorization","JWT "+ token); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            httpCon.setDoInput(true); // 서버로부터 응답을 받겠다는 옵션.

            //checkConnection(httpCon); //연결 확인(디버깅 위한 함수)

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null)
                result = convertInputStreamToString(is);    // 서버의 응답을 String형식으로 result에 저장
            else
                result = "Did not work!";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 구매요청도서 삭제 서버요청을 위한 스레드 클래스 */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private MypageRequestActivity mainAct;
        ProgressDialog dialog;

        HttpAsyncTask(MypageRequestActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        /** 메인스레드 실행하기 전에 수행됨 (로딩 화면 표시) */
        protected  void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(mainAct);
            dialog.show();      // 로딩 화면 표시
        }

        /**
         * 스레드의 메인부분 (삭제 요청 수행)
         * @param urls - 연결할 서버 주소
         * @return -
         * */
        @Override
        protected String doInBackground(String... urls) {
            return deleteRequest(urls[0]);
        }

        /**
         *  doInBackground(메인스레드)가 끝나면 호출됨 (삭제완료메시지 출력)
         *  @param result -
         *  */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();       // 로딩화면 끝
                    Toast.makeText(getApplicationContext(), "삭제완료되었습니다", Toast.LENGTH_LONG).show();
                    finish();  finish();
                }
            });
        }
    }

    /**
     * InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환)
     * @param inputStream - 서버에서 받은 값을 저장한 InputStream 객체
     * @return - 서버에서 받은 값을 String으로 변환한 결과
     * */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
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
    }
}