package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        getSupportActionBar().hide(); // 타이틀을 안보이도록 함

        // 선택한 도서의 세부정보 보여주기
        AQuery aq = new AQuery(this);
        Intent intent = getIntent();

        if (intent != null) {
            // BuyerSearchActivity로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
            userID = intent.getStringExtra("uerID");
            token = intent.getStringExtra("token");

            bookID = intent.getStringExtra("bookID");
            title = intent.getStringExtra("bookTitle");
            author = intent.getStringExtra("bookAuthor");
            publisher = intent.getStringExtra("bookPublisher");
            price = intent.getStringExtra("bookPrice");
            pubdate = intent.getStringExtra("bookPubdate");
            ISBN = intent.getStringExtra("bookISBN");
            cover = intent.getStringExtra("bookCover");
            course = intent.getStringExtra("course");
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

    public static String request(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bookId", bookID);
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
        private BookDetailsActivity mainAct;

        HttpAsyncTask(BookDetailsActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {
            bookID=urls[1];
            return request(urls[0]);
        }

        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "요청완료되었습니다", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    /** InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환) */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}