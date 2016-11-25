package com.example.eomji.dongguk_usedbook_marcket;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BuyerSearchActivity extends AppCompatActivity {

    ListView listView1;
    IconTextListAdapter adapter;

    private EditText search_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_search);

        // 타이틀을 안보이도록 함
        getSupportActionBar().hide();
        /*
        // 왼쪽 화살표를 보여주는 위젯 객체 참조
        PreviousButton arrowLeftBtn = (PreviousButton)findViewById(R.id.arrowLeftBtn);

        // 리소스의 이미지를 직접 가져와서 설정하는 방식
        Resources res = getResources();
        BitmapDrawable curDrawable = (BitmapDrawable) res.getDrawable(R.drawable.arrow_left);
        Bitmap iconBitmap = curDrawable.getBitmap();
        BitmapDrawable curClickedDrawable = (BitmapDrawable) res.getDrawable(R.drawable.arrow_left_clicked);
        Bitmap iconClickedBitmap = curClickedDrawable.getBitmap();
        arrowLeftBtn.setIconBitmap(iconBitmap, iconClickedBitmap);

        // 이벤트 처리
        arrowLeftBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 화면을 닫음
                finish();
            }
        });
        */
        /*
        String titleText = "도서검색";
        // 타이틀을 위한 버튼에 텍스트 설정
        BuyerSearchTitleButton titleBtn = (BuyerSearchTitleButton)findViewById(R.id.titleBtn);
        titleBtn.setTitleText(titleText);
        titleBtn.setDefaultSize(32F);
        */

        // 리스트뷰 객체 참조
        listView1 = (ListView) findViewById(R.id.listView1);

        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);

        // 아이템 데이터 만들기
        Resources res = getResources();
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "컴퓨터시스템구조론\n(정가 36,000원)", "판매자: 문주원", "10,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "컴퓨터네트워크\n(정가 30,000원)", "판매자: 김주현", "14,000 원"));

        // 리스트뷰에 어댑터 설정
        listView1.setAdapter(adapter);

        // 새로 정의한 리스너로 객체를 만들어 설정
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

                // DB 연동해서 데이터 받아온 값을 출력하게 수정해야함!!!
                Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //버튼을 누르면 책이 검색된다. (판매자가 등록한 책들 중에서)
    public void onSearchButtonClicked(View view){
        //String search_data = search_text.getText().toString();

        //search_data를 받아서 책을 검색하는 함수
        //검색한 책들을 아래 화면에 보여준다.
        //Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        //startActivity(intent);
    }
}
