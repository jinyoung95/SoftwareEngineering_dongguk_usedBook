package com.se_team8.dongguk_usedbook_market.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.se_team8.dongguk_usedbook_market.domain.BookVO;
import com.se_team8.dongguk_usedbook_market.ListName;
import com.se_team8.dongguk_usedbook_market.R;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-20.
 */

public class myAdapter extends BaseExpandableListAdapter {
    private String mRecipientID;
    private String mRecipientName;
    private Context mContext;
    private ArrayList<ListName> listName;
    private LayoutInflater inflater;
    private ArrayList<BookVO> mBookList;

    //class Constructor
    public myAdapter(Context mContext, ArrayList<ListName> listName) {
        this.mContext = mContext;
        this.listName = listName;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getGroupCount() {
        return listName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listName.get(groupPosition).info1.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listName.get(groupPosition);
    }

    @Override
    public ListName getChild(int groupPosition, int childPosition) {
        return listName.get(groupPosition);
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_mypage_parent, null);
        }

        ListName listName = (ListName) getGroup(groupPosition);

        String list = listName.listName;
        TextView textView = (TextView) convertView.findViewById(R.id.listName);
        textView.setText(list);
        //convertView.setBackgroundColor();
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //inflate the layout
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_mypage_child, null);
        }

        //set the child name
        TextView info1 = (TextView) convertView.findViewById(R.id.info1);
        TextView info2 = (TextView) convertView.findViewById(R.id.info2);
        TextView info3 = (TextView) convertView.findViewById(R.id.info3);

        info1.setText(listName.get(groupPosition).info1.get(childPosition));
        info2.setText(listName.get(groupPosition).info2.get(childPosition));
        info3.setText(listName.get(groupPosition).info3.get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
