package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
/**
 * Created by Jinyoung(2014112057 최진영) on 2016-11-10.
 */
public class HomeActivity extends AppCompatActivity {
    private String userName, userID, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide(); // 타이틀을 안보이도록 함

        Intent intent = getIntent();
        if (intent != null) {
            // LoginActivity로부터 넘어온 데이터를 꺼낸다
            userName = intent.getStringExtra("username");
            userID = intent.getStringExtra("userID");
            token = intent.getStringExtra("token");
        }
    }

    /** 도서 검색 버튼 클릭 */
    public void onBuyerSearchButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), BuyerSearchActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /** 판매 등록 버튼 클릭 */
    public void onSellerSearchBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(), SellerSearchActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /** 마이페이지 버튼 클릭 */
    public void onMypageButtonClicked(View v){
        Intent intent = new Intent(getApplicationContext(),MypageActivity.class);
        intent.putExtra("username", userName);
        intent.putExtra("userID", userID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /** 로그아웃 버튼 클릭 */
    public void onLogoutButtonClicked(View v){
        Toast.makeText(getApplicationContext(),"로그아웃합니다.", Toast.LENGTH_LONG).show();
        Intent logoutIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logoutIntent);
    }
}
