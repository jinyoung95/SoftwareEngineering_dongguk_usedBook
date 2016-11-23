package com.se_team8.searchbook.adaptor;

/*
 * Created by JinYoung on 2016-11-20.
 * */

import android.content.Context;
// import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.se_team8.searchbook.R;
import com.se_team8.searchbook.domain.BookVO;

import java.util.ArrayList;

/**
 * 어댑터의 함수는 리스트뷰가 호출
 */
public class BookAdaptor extends BaseAdapter {
    private Context mContext;
    private ArrayList<BookVO> mBookList;

    public BookAdaptor(Context context, ArrayList<BookVO> booklist) {
        this.mContext = context;
        this.mBookList = booklist;
    }

    // 어댑터에서 관리하는 아이템의 개수 리턴
    @Override
    public int getCount() { return mBookList.size(); }

    @Override
    public Object getItem(int position) { return mBookList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    // 아이템에 표시할 뷰를 리턴. n번째 해당하는 View를 리턴하는 함수
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.listitem, null);

            holder = new ViewHolder();
            holder.ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            holder.tvPublisher = (TextView) convertView.findViewById(R.id.tvPublisher);
            holder.tvPubdate = (TextView) convertView.findViewById(R.id.tvPubdate);
            holder.tvISBN = (TextView) convertView.findViewById(R.id.tvISBN);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);

            convertView.setTag(holder);
        }
        else {   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다.
            holder = (ViewHolder) convertView.getTag();
        }

        // findviewById 매번 하지 않고 ViewHolder 기법으로 저장했다가 재사용 => ViewHolder 패턴
        // TextView의 html 처리: Html.fromHtml 사용
        // TextView의 글자가 길때 자르기 -> 찾아보고 다시 수정
        // holder.tvTitle.setText(Html.fromHtml(mBookList.get(position).getTitle()));
        holder.tvTitle.setText(mBookList.get(position).getTitle());
        holder.tvAuthor.setText(mBookList.get(position).getAuthor());
        holder.tvPublisher.setText(mBookList.get(position).getPublisher());
        holder.tvPubdate.setText(mBookList.get(position).getPubdate());
        holder.tvISBN.setText(mBookList.get(position).getIsbn());
        holder.tvPrice.setText(mBookList.get(position).getPrice());

        // 이미지 보여주기
        // 딜레이 로딩 이미지 : 스크롤 중일때는 이미지처리를 하지 않고,
        // 스크롤이 멈추면 그 때 이미지를 가져온다.
        AQuery aq = new AQuery(convertView);
        if(aq.shouldDelay(position, convertView, parent, mBookList.get(position).getImgUrl())){
            // 현재 리스트가 스크롤일때는 이미처 처리를 하지 않는다.
        } else {
            // 두번째파라미터: 메모리캐시, 세번째파라미터: 파일캐시 사용하기
            aq.id(holder.ivImg).image(mBookList.get(position).getImgUrl(), true, true);
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView ivImg;
        public TextView tvTitle;
        public TextView tvAuthor;
        public TextView tvPublisher;
        public TextView tvPubdate;
        public TextView tvISBN;
        public TextView tvPrice;
    }
}