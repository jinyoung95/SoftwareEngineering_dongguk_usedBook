package com.example.eomji.dongguk_usedbook_marcket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Eomji on 2016-11-20.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegisterBtnClicked(View view){
        Intent intent = new Intent(getApplicationContext(), SellerRegisterActivity.class);
        startActivity(intent);
    }
}
