package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<RequestListItem> requestLists;

    public RequestListAdapter(Context context, ArrayList<RequestListItem> requestLists){
        this.mContext = context;
        this.requestLists = requestLists;
    }

    @Override
    public int getCount() { return requestLists.size(); }

    @Override
    public Object getItem(int position) { return requestLists.get(position);}

    @Override
    public long getItemId(int position) { return 0; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestListView requestListView;
        if(convertView == null){
            requestListView = new RequestListView(mContext, requestLists.get(position));
        } else {
            requestListView = (RequestListView) convertView;
        }
        //목록에 요청한 사람의 아이디와 이름을 출력하여 보여준다.
        requestListView.setText(0,requestLists.get(position).getRequestStudentID());
        requestListView.setText(1,requestLists.get(position).getRequestStudentName());

        return requestListView;
    }

}