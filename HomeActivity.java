package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void onMypageButtonClicked(View v){
        Intent mypageIntent = new Intent(getApplicationContext(),MypageActivity.class);
        startActivity(mypageIntent);
    }
    public void onLogoutButtonClicked(View v){
        Toast.makeText(getApplicationContext(),"로그아웃합니다.",Toast.LENGTH_LONG).show();
        Intent logoutIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logoutIntent);
    }
}
