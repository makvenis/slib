package com.sirius.slib.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class SimpleCircleImageView extends AppCompatImageView {

    private Context mContext;

    public SimpleCircleImageView(Context context) {
        this(context,null);
    }

    public SimpleCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }
}
