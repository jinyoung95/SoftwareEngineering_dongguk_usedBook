package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinYoung on 2016-11-22.
 */

public class SellerSearchActivity extends AppCompatActivity{
    private static final String BOOK_URL = "https://openapi.naver.com/v1/search/book.xml?query=%s&display=20";
    private AQuery mAq = new AQuery(this);
    private ArrayList<BookVO> mBookList = new ArrayList<BookVO>();
    private EditText search_text;
    private BookAdaptor adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_text = (EditText) findViewById(R.id.searchText);
        listView = (ListView) findViewById(R.id.listView);

        listView.setEmptyView(findViewById(R.id.empty)); // 검색 결과 없음 표시

        adapter = new BookAdaptor(this, mBookList); //어댑터 객체 생성

        listView.setAdapter(adapter); // 리스트뷰에 어댑터 객체 설정

        // 판매등록할 책 선택 -> 판매등록 페이지로 넘어간다
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();

                BookVO curItem = (BookVO) adapter.getItem(position); // 선택한 item의 position 받기
                Intent intent = new Intent(SellerSearchActivity.this, SellerRegisterActivity.class); // intent 생성

                intent.putExtra("bookTitle", curItem.getTitle());
                intent.putExtra("bookAuthor", curItem.getAuthor());
                intent.putExtra("bookCover", curItem.getImgUrl());
                intent.putExtra("bookISBN", curItem.getIsbn());
                intent.putExtra("bookPrice", curItem.getPrice());
                intent.putExtra("bookPubdate", curItem.getPubdate());
                intent.putExtra("bookPublisher", curItem.getPublisher());

                startActivity(intent); // 액티비티 실행
            }
        });
    }

    // 검색 버튼 클릭
    public void onSearchButtonClicked(View view){
        String query = search_text.getText().toString();

        getSupportActionBar().hide(); // 타이틀이 안보이도록 함

        mBookList.clear();  // 리스트 초기화

        // 네이버 오픈 API 이용해서 책 정보를 크롤링
        mAq.ajax(String.format(BOOK_URL, query), XmlDom.class, new AjaxCallback<XmlDom>() {
            @Override
            public void callback(String url, XmlDom object, AjaxStatus status) {
                Log.d("LDK", "url: " + url);
                Log.d("LDK", "status code: " + status.getCode());

                // getElementsByTagName(tag) 와 동일, 노드리스트를 리턴받는다.
                List<XmlDom> itemList =object.tags("item");
                for(XmlDom item : itemList) {
                    //title 노드를 리턴
                    XmlDom titleNode = item.tag("title");
                    String strTitle = titleNode.text(); // 텍스트노드의 텍스트를 가져옴.
                    String strAuthor = item.tag("author").text();
                    String strImg = item.tag("image").text();
                    String strPublisher = item.tag("publisher").text();
                    String strPubdate = item.tag("pubdate").text();
                    String strISBN = item.tag("isbn").text();
                    String strPrice = item.tag("price").text();

                    BookVO book = new BookVO();
                    book.setTitle(strTitle);
                    book.setAuthor(strAuthor);
                    book.setImgUrl(strImg);
                    book.setPublisher(strPublisher);
                    book.setPubdate(strPubdate);
                    book.setIsbn(strISBN);
                    book.setPrice(strPrice);
                    mBookList.add(book);
                }
                adapter.notifyDataSetChanged(); //  변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신

            }
        }.header("X-Naver-Client-Id", "XZl1i29Qk4zL8M5ZUn6D")
                .header("X-Naver-Client-Secret", "NhKbWMdOPT"));

        Toast.makeText(getApplicationContext(), "검색이 완료되었습니다", Toast.LENGTH_LONG).show();
    }

    // 홈 버튼 클릭 -> 홈으로 이동
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}




