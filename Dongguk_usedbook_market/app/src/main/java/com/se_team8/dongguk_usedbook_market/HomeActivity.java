package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
/**
 * Created by Juhyeon on 2016-11-10.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();
    }

    // 도서 검색 클릭
    public void onBuyerSearchButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), BuyerSearchActivity.class);
        startActivity(intent);
    }

    // 판매 등록 클릭
    public void onSellerSearchBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(), SellerSearchActivity.class);
        startActivity(intent);
    }

    // 마이페이지 클릭
    public void onMypageButtonClicked(View v){
        Intent mypageIntent = new Intent(getApplicationContext(),MypageActivity.class);
        startActivity(mypageIntent);
    }

    // 로그아웃 클릭
    public void onLogoutButtonClicked(View v){
        Toast.makeText(getApplicationContext(),"로그아웃합니다.", Toast.LENGTH_LONG).show();
        Intent logoutIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logoutIntent);
    }

}
