package com.se_team8.dongguk_usedbook_market;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    public String listName;
    public ArrayList<String> users = new ArrayList<String>();

    public ListViewActivity(String listName){
        this.listName=listName;
    }
    public String toString(){
        return listName;
    }

}