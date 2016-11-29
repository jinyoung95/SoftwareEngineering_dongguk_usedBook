package com.se_team8.dongguk_usedbook_market;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.se_team8.dongguk_usedbook_market.domain.BookVO;

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
 * Created by Jinyoung on 2016-11-26.
 */

public class SellerRegisterActivity extends AppCompatActivity{
    private String title, author, publisher, price, pubdate, ISBN, cover;
    private String sbPrice, sbCourse, sbProfessor, sbMoreDetails, sbStatus;
    private TextView tvSellerName, tvTitle, tvAuthor, tvPublisher, tvPubdate, tvPrice, tvISBN;
    private EditText etPrice, etCourse, etProfessor, etMoreDetails;
    private RadioGroup rgStatus;
    private RadioButton rb;

    private BookVO book;
    private static String strJson = "";
    private static String userName, userID, token;
    public static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);
        getSupportActionBar().hide();   // 타이틀을 안보이도록 함

        // 선택한 도서의 세부정보 보여주기
        AQuery aq = new AQuery(this);
        Intent intent = getIntent();

        if (intent != null) {
            // SellerSearchActivity로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
            userID = intent.getStringExtra("userID");
            token = intent.getStringExtra("token");
            title = intent.getStringExtra("bookTitle");
            author = intent.getStringExtra("bookAuthor");
            publisher = intent.getStringExtra("bookPublisher");
            price = intent.getStringExtra("bookPrice");
            pubdate = intent.getStringExtra("bookPubdate");
            ISBN = intent.getStringExtra("bookISBN");
            cover = intent.getStringExtra("bookCover");
        }

        tvTitle = (TextView) findViewById(R.id.tvSrBookTitle);
        tvAuthor = (TextView) findViewById(R.id.tvSrBookAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvSrBookPublisher);
        tvPrice = (TextView) findViewById(R.id.tvSrBookPrice);
        tvPubdate = (TextView) findViewById(R.id.tvSrBookPubdate);
        tvISBN = (TextView) findViewById(R.id.tvSrBookISBN);

        tvTitle.setText(Html.fromHtml(title));
        tvAuthor.setText(Html.fromHtml(author));
        tvPrice.setText(price);
        tvPubdate.setText(pubdate);
        tvPublisher.setText(Html.fromHtml(publisher));
        tvISBN.setText(ISBN);
        aq.id(R.id.ivSrBookCover).image(cover);

        // 판매자 학번
        tvSellerName = (TextView) findViewById(R.id. tvSrSellerName);
        tvSellerName.setText(userID);
    }

    // 판매등록 버튼 클릭
    public void onRegistertBtnClicked(View view) {
        // 사용자가 입력한 판매세부정보 받아오기
        etPrice = (EditText) findViewById(R.id.etSrPrice);
        etCourse = (EditText) findViewById(R.id.etSrCourse);
        etProfessor = (EditText) findViewById(R.id.etSrProfessor);
        etMoreDetails = (EditText) findViewById(R.id.etSrMoreDetails);

        sbPrice = etPrice.getText().toString();
        sbCourse = etCourse.getText().toString();
        sbProfessor = etProfessor.getText().toString();
        sbMoreDetails = etMoreDetails.getText().toString();
        // 라디오 버튼 중 선택된 상태 저장
        rgStatus = (RadioGroup) findViewById(R.id.rgSrStatusButton);
        rb = (RadioButton) findViewById(rgStatus.getCheckedRadioButtonId());

        // 공란이 없으면 DB에 판매등록 정보를 저장
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
        }
        else{
            sbStatus = rb.getText().toString();

            HttpAsyncTask task = new HttpAsyncTask(this);
            //httpTask에 로그인 data 넘겨줌
            task.execute(mainURL+"/register/book/", tvTitle.getText().toString(), tvAuthor.getText().toString(), ISBN, price, tvPublisher.getText().toString(), pubdate, cover, sbCourse, sbProfessor, sbPrice, sbMoreDetails, sbStatus);
        }
    }

    public static String POST(String url, BookVO book) {
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookTitle",book.getTitle() );
            jsonObject.put("author", book.getAuthor());
            jsonObject.put("publisher", book.getPublisher());
            jsonObject.put("isbn", book.getIsbn());
            jsonObject.put("cource", book.getCourse());
            jsonObject.put("professor", book.getProfessor());
            jsonObject.put("comment", book.getComment());
            jsonObject.put("status", book.getStatus());
            jsonObject.put("price", book.getPrice());
            jsonObject.put("sellerPrice", book.getSellerPrice());
            jsonObject.put("pubdate", book.getPubdate());
            jsonObject.put("image", book.getImgUrl());

            json = jsonObject.toString(); // convert JSONObject to JSON to String

            // Set some headers to inform server about the type of the content
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Authorization","JWT "+ token); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            httpCon.setDoOutput(true);  // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // InputStream으로 서버로부터 응답을 받겠다는 옵션.

            // checkConnection(httpCon);
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("UTF-8")); // 서버에 쓰기
            out.flush(); // 스트림 버퍼 비우기
            // checkConnection(httpCon);

            // receive response as inputStream
            try {
                //서버에서 읽어오는 객체 생성
                is = httpCon.getInputStream(); // 실질적으로 웹서버에 접속하여 요청을 보내고 응답을 수신하는 시점
                if (is != null)
                    result = convertInputStreamToString(is);    // convert inputstream to string
                else
                    result = "Did not work!";
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
        private SellerRegisterActivity mainAct;
        ProgressDialog dialog;

        HttpAsyncTask(SellerRegisterActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        protected  void onPreExecute(){
            super.onPreExecute();

            dialog = new ProgressDialog(mainAct);
            dialog.show();
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            book = new BookVO();
            // 서버에 저장할 정보 저장
            book.setTitle(urls[1]);
            book.setAuthor(urls[2]);
            book.setIsbn(urls[3]);
            book.setPrice(urls[4]);
            book.setPublisher(urls[5]);
            book.setPubdate(urls[6]);
            book.setImgUrl(urls[7]);
            book.setCourse(urls[8]);
            book.setProfessor(urls[9]);
            book.setSellerPrice(urls[10]);
            book.setComment(urls[11]);
            book.setStatus(urls[12]);

            return POST(urls[0], book);
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
                        Toast.makeText(mainAct, "등록이 완료되었습니다.", Toast.LENGTH_LONG).show();

                        // 도서검색 화면으로 이동
                        finish(); finish();
                    }
                }
            });
        }
    }

    // 공란이 있는지 확인
    private boolean validate(){
        if(sbPrice.trim().equals(""))
            return false;
        else if(rb==null)
            return false;
        else if(sbCourse.trim().equals(""))
            return false;
        else if(sbProfessor.trim().equals(""))
            return false;
        else if(sbMoreDetails.trim().equals(""))
            return false;
        else
            return true;
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

    // 연결이 제대로 이루어졌는지 확인하고 그렇지 않다면 오류메시지 출력
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

    // 목록 버튼 클릭 -> 도서검색 화면으로 이동
    public void onRegListBtnClicked(View view){
        finish();
    }

    // 홈 버튼 클릭 -> 홈 화면으로 이동
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }
}