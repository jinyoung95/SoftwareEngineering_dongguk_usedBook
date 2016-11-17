package com.example.eomji.dongguk_usedbook_marcket;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class BuyerSearchActivity extends AppCompatActivity {

    private EditText search_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_search);

        // 타이틀을 안보이도록 함
        getSupportActionBar().hide();

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
        /*
        String titleText = "도서검색";
        // 타이틀을 위한 버튼에 텍스트 설정
        BuyerSearchTitleButton titleBtn = (BuyerSearchTitleButton)findViewById(R.id.titleBtn);
        titleBtn.setTitleText(titleText);
        titleBtn.setDefaultSize(32F);
        */
    }

    //버튼을 누르면 책이 검색된다. (판매자가 등록한 책들 중에서)
    public void onSearchButtonClicked(View view){
        //String search_data = search_text.getText().toString();

        //search_data를 받아서 책을 검색하는 함수
        //검색한 책들을 아래 화면에 보여준다.
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        startActivity(intent);
    }
}
