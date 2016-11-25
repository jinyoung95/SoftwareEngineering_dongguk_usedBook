package com.se_team8.dongguk_usedbook_market;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Eomji on 2016-11-20.
 */

public class SellerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        // 타이틀을 안보이도록 함
        getSupportActionBar().hide();
    }

}
