package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListView extends LinearLayout {
    private TextView requestID;
    private TextView requestName;

    public RequestListView(Context context, RequestListItem requestListItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.request_list_child,this,true);

        requestID = (TextView)findViewById(R.id.requestID);
        requestName = (TextView)findViewById(R.id.requestName);
    }
    public void setText(int index, String data) {
        if (index == 0) {
            requestID.setText(data);
        } else if (index == 1) {
            requestName.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
