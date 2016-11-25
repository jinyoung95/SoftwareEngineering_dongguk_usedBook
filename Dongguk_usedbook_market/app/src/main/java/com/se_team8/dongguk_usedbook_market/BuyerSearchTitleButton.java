package com.se_team8.dongguk_usedbook_market;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

public class BuyerSearchTitleButton extends Button {
    Context context;                //Base Context
    Paint paint;                    //Pain instance
    int defaultColor = 0xffffffff;  //Default Color
    float defaultSize = 20F;        //Default Size
    float defaultScaleX = 1.0F;     //Default ScaleX
    Typeface defaultTypeface = Typeface.DEFAULT_BOLD;//Default Typeface
    String titleText = "";          //Title Text
    boolean paintChanged = false;   //Flag for paint changed

    public BuyerSearchTitleButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BuyerSearchTitleButton(Context context, AttributeSet atts) {
        super(context, atts);
        this.context = context;
        init();
    }

    //Initialize
    public void init() {
        setBackgroundResource(R.drawable.title_button);
        paint = new Paint();
        paint.setColor(defaultColor);
        paint.setAntiAlias(true);
        paint.setTextScaleX(defaultScaleX);
        paint.setTextSize(defaultSize);
        paint.setTypeface(defaultTypeface);

    }

    //Handles touch event, move to main screen
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(context, titleText, Toast.LENGTH_LONG).show();
                break;
        }
        // repaint the screen
        invalidate();
        return true;
    }

    //Draw the text
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int curWidth = getWidth();
        int curHeight = getHeight();
        // apply paint attributes
        if (paintChanged) {
            paint.setColor(defaultColor);
            paint.setTextScaleX(defaultScaleX);
            paint.setTextSize(defaultSize);
            paint.setTypeface(defaultTypeface);
        }
        // calculate bounds
        Rect bounds = new Rect();
        paint.getTextBounds(titleText, 0, titleText.length(), bounds);
        float textWidth = ((float)curWidth - bounds.width())/2.0F;
        float textHeight = ((float)(curHeight-4) + bounds.height())/2.0F-1.0F;
        // draw title text
        canvas.drawText(titleText, textWidth, textHeight, paint);

    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
        paintChanged = true;
    }

    public float getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(float defaultSize) {
        this.defaultSize = defaultSize;
        paintChanged = true;
    }

    public float getDefaultScaleX() {
        return defaultScaleX;
    }

    public void setDefaultScaleX(float defaultScaleX) {
        this.defaultScaleX = defaultScaleX;
        paintChanged = true;
    }

    public Typeface getDefaultTypeface() {
        return defaultTypeface;
    }

    public void setDefaultTypeface(Typeface defaultTypeface) {
        this.defaultTypeface = defaultTypeface;
        paintChanged = true;
    }


}
