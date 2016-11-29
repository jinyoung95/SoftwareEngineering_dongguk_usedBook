package com.se_team8.dongguk_usedbook_market.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.se_team8.dongguk_usedbook_market.RequestListItem;
import com.se_team8.dongguk_usedbook_market.RequestListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListAdapter extends BaseAdapter{
    private Context mContext;
    private List<RequestListItem> requestLists = new ArrayList<RequestListItem>();

    public RequestListAdapter(Context context){ this.mContext = context; }

    @Override
    public int getCount() { return requestLists.size(); }

    @Override
    public Object getItem(int position) { return requestLists.get(position);}

    public void addItem(RequestListItem it) { requestLists.add(it); }

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
        requestListView.setText(0,requestLists.get(position).getRequestID());
        requestListView.setText(1,requestLists.get(position).getRequestName());

        return requestListView;
    }

}