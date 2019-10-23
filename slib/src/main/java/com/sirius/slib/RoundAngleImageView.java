package com.sirius.slib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class RoundAngleImageView extends AppCompatImageView {

    private Context mContext;
    private int height,width;

    public RoundAngleImageView(Context context) {
        this(context,null);
    }

    public RoundAngleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundAngleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    /**
     * <span>布局的高宽<span/>
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (width >= 12 && height > 12) {
            Path path = new Path();
            //四个圆角
            path.moveTo(12, 0);
            path.lineTo(width - 12, 0);
            path.quadTo(width, 0, width, 12);
            path.lineTo(width, height - 12);
            path.quadTo(width, height, width - 12, height);
            path.lineTo(12, height);
            path.quadTo(0, height, 0, height - 12);
            path.lineTo(0, 12);
            path.quadTo(0, 0, 12, 0);

            canvas.clipPath(path);
        }
    }
}
