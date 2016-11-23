package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Juhyeon on 2016-11-10.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }
    public void onLoginClicked(View v){
        Toast.makeText(getApplicationContext(),"로그인합니다.", Toast.LENGTH_LONG).show();
        Intent loginIntent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(loginIntent);
    }
}
