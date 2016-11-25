package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-11.
 */
public class MypageActivity extends AppCompatActivity {
    public String mRecipient = new String();
    public String mRecipientName = new String();
    private String KEY_SIMPLE_DATA = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        getSupportActionBar().hide();

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        final ArrayList<ListName> listName = get_Data();

        //create and bind to adatper
        myAdapter adapter = new myAdapter(this, listName);
        elv.setAdapter(adapter);

        //set onclick listener
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listName.get(groupPosition).info1.get(childPosition), Toast.LENGTH_LONG).show();
                if(listName.get(1).ListID == 2){
                    mRecipient = listName.get(groupPosition).info1.get(childPosition);
                    mRecipientName = listName.get(groupPosition).info2.get(childPosition);

                    Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);
                    intent.putExtra("RecipientID",mRecipient);
                    intent.putExtra("RecipientName",mRecipientName);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    //add and get data for list
    private ArrayList<ListName> get_Data() {
        ListName L_register = new ListName("판매 등록 목록");
        L_register.ListID = 1;
        L_register.info1.add("소프트웨어공학개론"); L_register.info2.add("최은만");
        L_register.info1.add("컴퓨터시스템구조론"); L_register.info2.add("최은만");

        ListName L_request = new ListName("구매 요청 목록");
        L_request.ListID = 2;
        L_request.info1.add("2014112019"); L_request.info2.add("김주현");
        L_request.info1.add("2014112041"); L_request.info2.add("김엄지");

        ArrayList<ListName> all_listName = new ArrayList<>();
        all_listName.add(L_register);
        all_listName.add(L_request);

        return all_listName;
    }
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
    public void onChattingButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);
        startActivity(intent);
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
