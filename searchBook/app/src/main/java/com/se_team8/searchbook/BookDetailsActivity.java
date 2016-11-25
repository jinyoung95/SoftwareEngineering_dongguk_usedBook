package com.se_team8.searchbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // 타이틀을 안보이도록 함
        getSupportActionBar().hide();

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

    public void onHomeButtonClicked(View view){
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
    }
    //목록 버튼 클릭하면 목록 보는 페이지로 넘어가는 함수 추가하기
    //구매요청 클릭하면 채팅하는 페이지로 넘어가는 함수 추가하기
}
