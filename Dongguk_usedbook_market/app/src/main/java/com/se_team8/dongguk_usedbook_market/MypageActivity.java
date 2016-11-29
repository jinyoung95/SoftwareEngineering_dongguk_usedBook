package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.se_team8.dongguk_usedbook_market.adaptor.BookAdaptor;
import com.se_team8.dongguk_usedbook_market.domain.BookVO;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-11.
 */

public class MypageActivity extends AppCompatActivity {
    public String mRecipientID = new String();
    public String mRecipientName = new String();
    public String username;
    public String userID;
    public String token;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent=getIntent();

        if(intent != null){
            username = intent.getStringExtra("username");
            userID=intent.getStringExtra("userID");
            token=intent.getStringExtra("token");
        }

        //위에 바 숨기기
        getSupportActionBar().hide();

        //세션아이디와 이름 입력
        TextView studentName = (TextView) findViewById(R.id.studentName);
        TextView studentID = (TextView) findViewById(R.id.studentID);
        studentID.setText(userID);
        studentName.setText(username);

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);
        final ArrayList<BookVO> list = get_Data();

       // myAdapter adapter = new myAdapter(this, list);
        BookAdaptor adapter = new BookAdaptor(this, list);
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(list.get(groupPosition).getID() == "0") {
                    //내가 판매등록한 책의 세부정보로 넘어가는 버튼
                    Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);

                    intent.putExtra("bookTitle", list.get(groupPosition).getTitle());
                    intent.putExtra("bookAuthor", list.get(groupPosition).getAuthor());
                    intent.putExtra("bookCover", list.get(groupPosition).getImgUrl());
                    intent.putExtra("bookISBN", list.get(groupPosition).getIsbn());
                    intent.putExtra("bookPrice", list.get(groupPosition).getPrice());
                    intent.putExtra("bookPubdate", list.get(groupPosition).getPubdate());
                    intent.putExtra("bookPublisher", list.get(groupPosition).getPublisher());
                    intent.putExtra("course", list.get(groupPosition).getCourse());
                    intent.putExtra("professor", list.get(groupPosition).getProfessor());
                    intent.putExtra("sellerPrice", list.get(groupPosition).getSellerPrice());
                    intent.putExtra("comment", list.get(groupPosition).getComment());
                    intent.putExtra("status", list.get(groupPosition).getStatus());

                    intent.putExtra("username", username);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);

                    startActivity(intent);

                }else if(list.get(groupPosition).getID() == "1"){
                   /* Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);

                    intent.putExtra("username",username);
                    intent.putExtra("userID",userID);
                    intent.putExtra("RecipientID", list.get(groupPosition).info2.get(childPosition));
                    intent.putExtra("RecipientName",list.get(groupPosition).info3.get(childPosition));

                    String str = list.get(groupPosition).info3.get(childPosition) + "님과 채팅을 시작합니다.";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

                    startActivity(intent);*/
                    //세부정보로 넘어가는 버튼
                    Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);

                    intent.putExtra("bookTitle", list.get(groupPosition).getTitle());
                    intent.putExtra("bookAuthor", list.get(groupPosition).getAuthor());
                    intent.putExtra("bookCover", list.get(groupPosition).getImgUrl());
                    intent.putExtra("bookISBN", list.get(groupPosition).getIsbn());
                    intent.putExtra("bookPrice", list.get(groupPosition).getPrice());
                    intent.putExtra("bookPubdate", list.get(groupPosition).getPubdate());
                    intent.putExtra("bookPublisher", list.get(groupPosition).getPublisher());
                    intent.putExtra("course", list.get(groupPosition).getCourse());
                    intent.putExtra("professor", list.get(groupPosition).getProfessor());
                    intent.putExtra("sellerPrice", list.get(groupPosition).getSellerPrice());
                    intent.putExtra("comment", list.get(groupPosition).getComment());
                    intent.putExtra("status", list.get(groupPosition).getStatus());

                    intent.putExtra("username", username);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);

                    startActivity(intent);
                }

                return false;
            }
        });
    }

    //리스트 내용 설정
    private ArrayList<BookVO> get_Data() {
        ArrayList<BookVO> all_book = new ArrayList<BookVO>();
        BookVO bookVO = new BookVO();

        all_book.add(bookVO);
        /*
        ListName L_register = new ListName("판매 등록 목록");
        L_register.ListID = 1;
        // 서버데이터베이스에서 불러와 작성
        L_register.info1.add("소프트웨어공학개론");
        L_register.info2.add("최은만");
        L_register.info3.add("30000원");

        L_register.info1.add("컴퓨터시스템구조론");
        L_register.info2.add("장태무");
        L_register.info3.add("20000원");

        ListName L_request = new ListName("구매 요청 목록");
        L_request.ListID = 2;
        // 서버데이터베이스에서 불러와 작성
        L_request.info1.add("소프트웨어공학개론");
        L_request.info2.add("최은만");
        L_request.info3.add("30000원");

        L_request.info1.add("컴퓨터시스템구조론");
        L_request.info2.add("장태무");
        L_request.info3.add("20000원");

        ArrayList<ListName> all_listName = new ArrayList<>();
        all_listName.add(L_register);
        all_listName.add(L_request);
        */

        return all_book;
    }

    public void onHomeButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void onChattingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("userID",userID);
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
