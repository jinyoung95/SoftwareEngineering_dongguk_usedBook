package com.example.juhyeon.elephantusedbookmarket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClicked(View v){
        Toast.makeText(getApplicationContext(), "로그인합니다.", Toast.LENGTH_LONG).show();

    }

}
