package com.example.juhyeon.elephantusedbookmarket;

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
    public void onLogoutButtonClicked(View v){
        Toast.makeText(getApplicationContext(), "로그아웃합니다.", Toast.LENGTH_LONG).show();
        finish();
    }

}
