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
    public void onRegisterBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(), SellerRegisterActivity.class);
        startActivity(intent);
    }
    public void onMypageButtonClicked(View v){
        Intent mypageIntent = new Intent(getApplicationContext(),MypageActivity.class);
        startActivity(mypageIntent);
    }
    public void onLogoutButtonClicked(View v){
        Toast.makeText(getApplicationContext(),"로그아웃합니다.", Toast.LENGTH_LONG).show();
        Intent logoutIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logoutIntent);
    }
    public void onSearchButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), BuyerSearchActivity.class);
        startActivity(intent);
    }

    public void processIntent(){
        Bundle bundle = getIntent().getExtras();


    }
}
