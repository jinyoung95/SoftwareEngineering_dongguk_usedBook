package com.se_team8.dongguk_usedbook_market;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        final ArrayList<ListName> listName = getData();

        //create and bind to adatper
        myAdapter adapter = new myAdapter(this, listName);
        elv.setAdapter(adapter);

        //set onclick listener
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listName.get(groupPosition).users.get(childPosition), Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
    //add and get data for list
    private ArrayList<ListName> getData() {

        ListName L1 = new ListName("구매 요청 목록");
        L1.users.add("2014112019");
        L1.users.add("2014112041");

        ListName L2 = new ListName("판매 등록 목록");
        L2.users.add("2014112057");
        L2.users.add("2014112022");

        ArrayList<ListName> alllistName = new ArrayList<>();
        alllistName.add(L1);
        alllistName.add(L2);

        return alllistName;
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
