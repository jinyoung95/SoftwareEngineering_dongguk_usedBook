package com.se_team8.searchbook.domain;

/**
 * Created by JinYoung on 2016-11-23.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.se_team8.searchbook.R;
import com.se_team8.searchbook.domain.IconTextItem;


public class IconTextView extends LinearLayout {
    private ImageView ivImg;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private TextView tvPubdate;
    private TextView tvISBN;
    private TextView tvPrice;

    // 한 아이템에 보여줄 정보 저장
    public IconTextView(Context context, IconTextItem aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listitem, this, true);

        // Set
        this.ivImg = (ImageView) findViewById(R.id. ivImg);
        this.ivImg.setImageDrawable(aItem.getIcon());
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvTitle.setText(aItem.getData(0));
        this.tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        this.tvAuthor.setText(aItem.getData(1));
        this.tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        this.tvPublisher.setText(aItem.getData(2));
        this.tvPubdate = (TextView) findViewById(R.id.tvPubdate);
        this.tvPubdate.setText(aItem.getData(3));
        this.tvISBN = (TextView) findViewById(R.id.tvISBN);
        this.tvISBN.setText(aItem.getData(4));
        this.tvPrice = (TextView) findViewById(R.id.tvPrice);
        this.tvPrice.setText(aItem.getData(5));
    }

    public void setText(int index, String data) {
        if (index == 0) {
            this.tvTitle.setText(data);
        } else if (index == 1) {
            this.tvAuthor.setText(data);
        } else if (index == 2) {
            this.tvPublisher.setText(data);
        } else if (index == 3) {
            this.tvPubdate.setText(data);
        } else if (index == 4) {
            this.tvISBN.setText(data);
        } else if (index == 5){
            this.tvPrice.setText(data);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public void setIcon(Drawable icon) {
        this.ivImg.setImageDrawable(icon);
    }
}
