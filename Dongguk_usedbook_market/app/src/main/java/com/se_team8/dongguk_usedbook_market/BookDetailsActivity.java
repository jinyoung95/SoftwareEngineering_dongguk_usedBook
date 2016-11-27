package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 *   Created by JinYoung on 2016-11-26.
 */

public class BookDetailsActivity extends AppCompatActivity {
    private String title, author, publisher, price, pubdate, ISBN, cover;
    private TextView tvTitle, tvAuthor, tvPublisher, tvPubdate, tvPrice, tvISBN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        getSupportActionBar().hide(); // 타이틀을 안보이도록 함

        // 선택한 도서의 세부정보 보여주기
        AQuery aq = new AQuery(this);
        Intent intent = getIntent();

        if (intent != null) {
            // SellerSearchActivity로부터 넘어온 데이터를 꺼낸다
            title = intent.getStringExtra("bookTitle");
            author = intent.getStringExtra("bookAuthor");
            publisher = intent.getStringExtra("bookPublisher");
            price = intent.getStringExtra("bookPrice");
            pubdate = intent.getStringExtra("bookPubdate");
            ISBN = intent.getStringExtra("bookISBN");
            cover = intent.getStringExtra("bookCover");
        }

        tvTitle = (TextView) findViewById(R.id.tvBdBookTitle);
        tvAuthor = (TextView) findViewById(R.id.tvBdBookAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvBdBookPublisher);
        tvPrice = (TextView) findViewById(R.id.tvBdBookPrice);
        tvPubdate = (TextView) findViewById(R.id.tvBdBookPubdate);
        tvISBN = (TextView) findViewById(R.id.tvBdBookISBN);

        tvTitle.setText(Html.fromHtml(title));
        tvAuthor.setText(Html.fromHtml(author));
        tvPrice.setText(price);
        tvPubdate.setText(pubdate);
        tvPublisher.setText(Html.fromHtml(publisher));
        tvISBN.setText(ISBN);
        aq.id(R.id.ivBdBookCover).image(cover);

        // 서버에서 sellerPrice, owner,

    }

    // 홈 버튼 클릭 -> 홈으로 이동
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    // 목록 버튼 클릭 -> 구매요청목록으로 이동
    public void onListBtnClicked(View view){
 //       Intent intent = new Intent(getApplicationContext(), Activity.class);
 //       startActivity(intent);
    }

    //구매요청 클릭하면 채팅하는 페이지로 넘어가는 함수 추가하기

        /*
        // 왼쪽 화살표를 보여주는 위젯 객체 참조
        PreviousButton arrowLeftBtn = (PreviousButton)findViewById(R.id.arrowLeftBtn);
        // 리소스의 이미지를 직접 가져와서 설정하는 방식
        Resources res = getResources();
        BitmapDrawable curDrawable = (BitmapDrawable) res.getDrawable(R.drawable.arrow_left);
        Bitmap iconBitmap = curDrawable.getBitmap();
        BitmapDrawable curClickedDrawable = (BitmapDrawable) res.getDrawable(R.drawable.arrow_left_clicked);
        Bitmap iconClickedBitmap = curClickedDrawable.getBitmap();
        arrowLeftBtn.setIconBitmap(iconBitmap, iconClickedBitmap);

        // 이벤트 처리
        arrowLeftBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 화면을 닫음
                finish();
            }
        });
        */
}
