package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-28.
 */

public class MyBookAdapter extends BaseExpandableListAdapter {
    private ArrayList<BookVOList> bookVOLists;
    private LayoutInflater inflater;

    //class Constructor
    public MyBookAdapter(Context mContext, ArrayList<BookVOList> bookVOLists) {
        this.bookVOLists = bookVOLists;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /** Getter and Setter 및 기본 메소드들  */
    @Override
    public int getGroupCount() {
        return bookVOLists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bookVOLists.get(groupPosition).getTitle().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return bookVOLists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bookVOLists.get(groupPosition).getTitle().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //확장리스트의 부모를 보여주는 메소드
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_mypage_parent, null);
        }
        //확장리스트의 명명을 한다.
        String list = bookVOLists.get(groupPosition).getListName();
        TextView textView = (TextView) convertView.findViewById(R.id.listName);
        textView.setText(list);

        return convertView;
    }

    //자식을 보여주는 메소드
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem, null); //listItem.xml 파일에서 발췌
        }

        //listItem 의 뷰들을 정의함
        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
        TextView tvPublisher = (TextView) convertView.findViewById(R.id.tvPublisher);
        TextView tvPubdate = (TextView) convertView.findViewById(R.id.tvPubdate);
        TextView tvISBN = (TextView) convertView.findViewById(R.id.tvISBN);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvsellerPrice = (TextView) convertView.findViewById(R.id.tvSellerPrice);

        //뷰들에 책들의 정보를 입력하여 자식 리스트 생성
        tvTitle.setText(bookVOLists.get(groupPosition).getTitle().get(childPosition));
        tvAuthor.setText(bookVOLists.get(groupPosition).getAuthor().get(childPosition));
        tvPublisher.setText(bookVOLists.get(groupPosition).getPublisher().get(childPosition));
        tvPubdate.setText(bookVOLists.get(groupPosition).getPubdate().get(childPosition));
        tvISBN.setText(bookVOLists.get(groupPosition).getIsbn().get(childPosition));
        tvPrice.setText(bookVOLists.get(groupPosition).getPrice().get(childPosition));
        tvsellerPrice.setText(bookVOLists.get(groupPosition).getSellerPrice().get(childPosition));

        AQuery aq = new AQuery(convertView); //쿼리문을 이용한 이미지를 url형태로 불러와 보여준다.
        aq.id(ivImg).image(bookVOLists.get(groupPosition).getImgUrl().get(childPosition), true, true, 200, 0);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}