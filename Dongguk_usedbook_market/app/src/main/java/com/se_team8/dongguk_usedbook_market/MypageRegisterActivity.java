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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *   Created by Eomji(2014112041김엄지) on 2016-11-28.
 */

public class MypageRegisterActivity extends AppCompatActivity {
    private static String userName, userID, token, bookID;
    private String owner, title, author, publisher, price, pubdate, ISBN, cover, course, professor, sellerPrice, comment, status;
    private TextView tvSellerName, tvTitle, tvAuthor, tvPublisher, tvPubdate, tvPrice, tvISBN, tvCourse, tvProfessor, tvSellerPrice, tvComment, tvStatus;
    private static String strJson = "";
    private static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_register);
        getSupportActionBar().hide(); // 타이틀 숨김

        // 선택한 도서의 세부정보 보여주기
        AQuery aq = new AQuery(this);
        Intent intent = getIntent();

        if (intent != null) {
            // My Page로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
            userID = intent.getStringExtra("userID");
            token = intent.getStringExtra("token");

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

        tvTitle = (TextView) findViewById(R.id.tvMprBookTitle);
        tvAuthor = (TextView) findViewById(R.id.tvMprBookAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvMprBookPublisher);
        tvPrice = (TextView) findViewById(R.id.tvMprBookPrice);
        tvPubdate = (TextView) findViewById(R.id.tvMprBookPubdate);
        tvISBN = (TextView) findViewById(R.id.tvMprBookISBN);
        tvCourse = (TextView) findViewById(R.id.tvMprCourse);
        tvProfessor = (TextView) findViewById(R.id.tvMprProfessor);
        tvSellerPrice = (TextView) findViewById(R.id.tvMprSellerPrice);
        tvComment = (TextView) findViewById(R.id.tvMprMoreDetails);
        tvStatus = (TextView) findViewById(R.id.tvMprStatus);
        tvSellerName = (TextView) findViewById(R.id.tvMprSellerName);
        // 꺼내온 데이터 출력
        tvTitle.setText(Html.fromHtml(title));
        tvAuthor.setText(Html.fromHtml(author));
        tvPrice.setText(price);
        tvPubdate.setText(pubdate);
        tvPublisher.setText(Html.fromHtml(publisher));
        tvISBN.setText(ISBN);
        aq.id(R.id.ivMprBookCover).image(cover);
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
        finish();
    }

    /**
     * 목록 버튼 클릭 -> 이전화면(마이페이지)로 이동
     * @param view
     * */
    public void onListBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    /**
     * 요청목록 클릭 -> 이 책을 구매요청한 사람들의 목록 보여주는 페이지(RequestListActivity)로 이동
     * @param view
     * */
    public void RequestListBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(),RequestListActivity.class );
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        intent.putExtra("bookID",bookID);
        startActivity(intent);
    }

    /**
     * 삭제 버튼 클릭 -> 판매 등록 도서 삭제 스레드 실행
     * @param view
     * */
    public void onRegisterDelBtnClicked(View view){
        getSupportActionBar().hide(); // 타이틀 숨김
        HttpAsyncTask httpTask = new HttpAsyncTask(MypageRegisterActivity.this);
        httpTask.execute(mainURL+"register/book/"+bookID+"/");  //판매 등록 도서 삭제 스레드 실행
        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /**
     * 수정 버튼 클릭 -> 판매 등록 도서 수정하는 페이지(SellerRegisterActivity)로 넘어감
     * @param view
     * */
    public void onRegisterMdfBtnClicked(View view){
        Intent intent = new Intent(MypageRegisterActivity.this, SellerRegisterActivity.class); // intent 생성
        // 필요한 정보 전달
        intent.putExtra("option", "PUT");
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        intent.putExtra("bookID",bookID);
        intent.putExtra("bookTitle", title);
        intent.putExtra("bookAuthor", author);
        intent.putExtra("bookCover", cover);
        intent.putExtra("bookISBN", ISBN);
        intent.putExtra("bookPrice", price);
        intent.putExtra("bookPubdate", pubdate);
        intent.putExtra("bookPublisher", publisher);
        intent.putExtra("mypage", "mypage");
        startActivity(intent); // 액티비티 실행
        finish();
    }

    /**
     * 판매 등록 도서 삭제 (서버에 요청)
     * @param url - 연결할 서버의 주소
     * @return - 삭제 완료되면 ""반환
     * */
    public static String deleteRegister(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

            // header설정
            httpCon.setRequestMethod("DELETE"); // DELETE 형식으로 보냄(삭제)
            httpCon.setRequestProperty("Authorization","JWT "+ token); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            httpCon.setDoInput(true);           // InputStream으로 서버로 부터 응답을 받겠다는 옵션.

            is = httpCon.getInputStream();      // 서버에서 읽어오는 객체 생성
            if(is != null)
                result = convertInputStreamToString(is);    // 서버의 응답을 String형식으로 바꾸어 result에 저장
            else
                result = "Did not work!";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 판매등록도서 삭제 서버요청을 위한 스레드 클래스 */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private MypageRegisterActivity mainAct;
        ProgressDialog dialog;

        HttpAsyncTask(MypageRegisterActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        /** 메인스레드 실행하기 전에 수행됨 (로딩 화면 표시) */
        protected  void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(mainAct);
            dialog.show();       // 로딩 화면 표시
        }

        /**
         * 스레드의 메인부분 (삭제 요청 수행)
         * @param urls - 연결할 서버 주소
         * @return
         * */
        @Override
        protected String doInBackground(String... urls) {
            return deleteRegister(urls[0]);
        }

        /**
         *  doInBackground(메인스레드)가 끝나면 호출됨 (삭제완료메시지 출력)
         *  @param result
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
     * */    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}