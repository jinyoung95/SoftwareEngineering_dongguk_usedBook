package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-20.
 */

public class myAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<ListName> listName;
    private LayoutInflater inflater;

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

    //get position
    @Override
    public Object getGroup(int groupPosition) {
        return listName.get(groupPosition);
    }

    //this is where we get the information of player
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listName.get(groupPosition).info1.get(childPosition);
    }

    //position ID
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    //where to get user's id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //get parent row
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_mypage_parent, null);
        }
        //get position
        ListName listName = (ListName) getGroup(groupPosition);
        //set positionName
        String list = listName.listName;
        TextView textView = (TextView) convertView.findViewById(R.id.listName);
        textView.setText(list);
        //convertView.setBackgroundColor();
        return convertView;
    }

    //get child_list.xml (View)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        //inflate the layout
        if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_mypage_child, null);
        }

        String child = (String) getChild(groupPosition, childPosition);

        //set the child name
        TextView info1 = (TextView) convertView.findViewById(R.id.info1);
        TextView info2 = (TextView) convertView.findViewById(R.id.info2);
        //get the imageView

        info1.setText(child);
        info2.setText(child);

        //get position name
        String positionName = (String) getGroup(groupPosition).toString();
       /* if (positionName == "pitcher") {
            if (child == "고원준") {
                img.setImageResource(R.drawable.wonjun_ko);
            } else if (child == "Brooks Raley") {
                img.setImageResource(R.drawable.books_raley);
            } else if (child == "박세웅") {
                img.setImageResource(R.drawable.sewoong_park);
            }
        } else if (positionName == "infield") {
            if (child == "문규현") {
                img.setImageResource(R.drawable.kyuhyun_moon);
            } else if (child == "박종윤") {
                img.setImageResource(R.drawable.jongyun_park);
            }
        } else if (positionName == "catcher") {
            if (child == "강민호") {
                img.setImageResource(R.drawable.minho_kang);
            } else if (child == "안중열") {
                img.setImageResource(R.drawable.jungyul_ahn);
            }
        } else if (positionName == "outfield") {
            if (child == "Jim Adduci") {
                img.setImageResource(R.drawable.jim_adduci);
            } else if (child == "손아섭") {
                img.setImageResource(R.drawable.ahsup_son);
            }
        }*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}