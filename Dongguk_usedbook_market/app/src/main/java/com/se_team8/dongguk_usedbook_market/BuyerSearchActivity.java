package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.androidquery.AQuery;
import java.util.ArrayList;

/**
 * Created by JinYoung on 2016-11-26.
 */

public class BuyerSearchActivity extends AppCompatActivity{
    private AQuery aq = new AQuery(this);
    private ArrayList<BookVO> mBookList = new ArrayList<BookVO>();
    private EditText search_text;
    private BookAdaptor adapter;
    private ListView listView;
    private BookVO book;

    public static String urlCon = "http://softwareengineeringtp.azurewebsites.net/application/book//";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_text = (EditText) findViewById(R.id.searchText);
        listView = (ListView) findViewById(R.id.listView);

        listView.setEmptyView(findViewById(R.id.empty)); // 검색 결과 없음 표시

        adapter = new BookAdaptor(this, mBookList); //어댑터 객체 생성

        listView.setAdapter(adapter); // 리스트뷰에 어댑터 객체 설정

        // 구매요청할 책 선택 -> 해당 도서의 세부정보 페이지로 넘어간다
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BookVO curItem = (BookVO) adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();
                BookVO Item = (BookVO) adapter.getItem(position); // 선택한 item의 position 받기
                Intent intent = new Intent(BuyerSearchActivity.this, BookDetailsActivity.class); // intent 생성

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

    public void onSearchButtonClicked(View view){
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함

        String query = search_text.getText().toString();

        mBookList.clear();  // 리스트 초기화

        //DB에 저장된 판매등록된 목록 불러오기

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        @Override
//        public void onClick(View v) {
//            task = new BackgroundTask();
//            task.execute();
//        }
//
//        private String request(String urlStr) {
//            StringBuilder output = new StringBuilder();
//            try {
//                URL url = new URL(urlStr);
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                if (conn != null) {
//                    conn.setConnectTimeout(10000);
//                    conn.setRequestMethod("GET");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//
//                    int resCode = conn.getResponseCode();
//                    if (resCode == HttpURLConnection.HTTP_OK) {
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
//                        String line = null;
//                        while(true) {
//                            line = reader.readLine();
//                            if (line == null) {
//                                break;
//                            }
//                            output.append(line + "\n");
//                        }
//
//                        reader.close();
//                        conn.disconnect();
//                    }
//                }
//            } catch(Exception ex) {
//                Log.e("SampleHTTP", "Exception in processing response.", ex);
//                ex.printStackTrace();
//            }
//
//            return output.toString();
//        }


        adapter.notifyDataSetChanged(); //변경된 모델 데이터를 리스트 뷰에게 알려줘서 뷰를 갱신

        Toast.makeText(getApplicationContext(), "검색이 완료되었습니다.", Toast.LENGTH_LONG).show();
    }

    // 홈 버튼 클릭 -> 홈으로 이동
    public void onHomeButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}




