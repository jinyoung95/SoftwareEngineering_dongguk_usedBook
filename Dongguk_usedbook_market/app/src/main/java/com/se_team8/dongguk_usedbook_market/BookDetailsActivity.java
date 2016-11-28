package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 *   Created by Eomji on 2016-11-26.
 */

public class BookDetailsActivity extends AppCompatActivity {
    private String userName, userID, token;
    private String title, author, publisher, price, pubdate, ISBN, cover, course, professor, sellerPrice, comment, status;
    private TextView tvTitle, tvAuthor, tvPublisher, tvPubdate, tvPrice, tvISBN, tvCourse, tvProfessor, tvSellerPrice, tvComment, tvStatus;


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
        finish();
    }

    //구매요청 클릭하면 채팅하는 페이지로 넘어가는 함수 추가하기

}
