package com.se_team8.dongguk_usedbook_market;

/**
 * Created by JinYoung (2014112057 최진영) on 2016-11-20.
 */

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

/** 도서검색을 위한 BookAdaptor */
public class BookAdaptor extends BaseAdapter {
    private Context mContext;
    private ArrayList<BookVO> mBookList;

    BookAdaptor(Context context, ArrayList<BookVO> booklist) {
        this.mContext = context;
        this.mBookList = booklist;
    }

    /** 어댑터에서 관리하는 아이템의 개수 리턴 */
    @Override
    public int getCount() { return mBookList.size(); }

    /** 리스트에서 클릭한 아이템 리턴
     * @param position: 리스트에서 클릭한 아이템의 위치
     * */
    @Override
    public Object getItem(int position) { return mBookList.get(position); }

    /** 리스트에서 클릭한 아이템의 위치 리턴
     * @param position : 리스트에서 클릭한 아이템의 위치
     * */
    @Override
    public long getItemId(int position) { return position; }

    /** 아이템에 표시할 뷰를 리턴. n번째 해당하는 View를 리턴하는 함수 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성
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
            holder.tvSellerPrice = (TextView) convertView.findViewById(R.id.tvSellerPrice);
            convertView.setTag(holder);
        }
        else {   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용
            holder = (ViewHolder) convertView.getTag();
        }

        // findviewById 매번 하지 않고 ViewHolder 기법으로 저장했다가 재사용 => ViewHolder 패턴
        // TextView의 html 처리: Html.fromHtml 사용
        holder.tvTitle.setText(Html.fromHtml(mBookList.get(position).getTitle()));
        holder.tvAuthor.setText(Html.fromHtml(mBookList.get(position).getAuthor()));
        holder.tvPublisher.setText(Html.fromHtml(mBookList.get(position).getPublisher()));
        holder.tvPubdate.setText(mBookList.get(position).getPubdate());
        holder.tvISBN.setText(mBookList.get(position).getIsbn());
        holder.tvPrice.setText(mBookList.get(position).getPrice());
        holder.tvSellerPrice.setText(mBookList.get(position).getSellerPrice());

        // 이미지 보여주기 : 스크롤 중일때는 이미지 처리를 하지 않고, 스크롤이 멈추면 그 때 이미지를 가져온다.
        AQuery aq = new AQuery(convertView);
        if(aq.shouldDelay(position, convertView, parent, mBookList.get(position).getImgUrl())){
            // 현재 리스트가 스크롤일때는 이미처 처리를 하지 않는다.
        } else {
            // 두번째파라미터: 메모리캐시, 세번째파라미터: 파일캐시 사용
            aq.id(holder.ivImg).image(mBookList.get(position).getImgUrl(), true, true, 200, 0);
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
        public TextView tvSellerPrice;
    }
}