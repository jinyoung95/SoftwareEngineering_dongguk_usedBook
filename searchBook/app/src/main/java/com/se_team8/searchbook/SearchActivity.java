package com.se_team8.searchbook;

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
import com.se_team8.searchbook.adaptor.BookAdaptor;
import com.se_team8.searchbook.domain.BookVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinYoung on 2016-11-22.
 */

public class SearchActivity extends AppCompatActivity{
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

        listView = (ListView) findViewById(R.id.listView);    // 객체 참조
        search_text = (EditText) findViewById(R.id.searchText); // 객체 참조

        adapter = new BookAdaptor(this, mBookList); //어댑터 객체 생성

        Button btnSearch = (Button) findViewById(R.id.searchButton);
        // 검색 버튼을 눌렀을 때
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View v){
                getSupportActionBar().hide(); // 타이틀이 안보이도록 함

                Toast.makeText(getApplicationContext(), "검색이 완료되었습니다.", Toast.LENGTH_LONG).show();

                String query = search_text.getText().toString();
            /*HashMap<String, String> params = new HashMap<String, String>();
            params.put("query", query);*/

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

                        //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신
                        adapter.notifyDataSetChanged();
                    }
                }.header("X-Naver-Client-Id", "XZl1i29Qk4zL8M5ZUn6D")
                        .header("X-Naver-Client-Secret", "NhKbWMdOPT"));
            }
        });

        listView.setAdapter(adapter); // 리스트뷰에 어댑터 객체 설정

        // 리스트 뷰의 한 아이템 선택했을 때
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BookVO curItem = (BookVO) adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onHomeButtonClicked(View view){
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
    }

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

}




