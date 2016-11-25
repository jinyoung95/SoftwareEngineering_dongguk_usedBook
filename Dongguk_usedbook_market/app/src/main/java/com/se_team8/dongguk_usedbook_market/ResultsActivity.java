package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class ResultsActivity extends AppCompatActivity {
    ListView listView1;
    IconTextListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 타이틀을 안보이도록 함
        getSupportActionBar().hide();

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
}
