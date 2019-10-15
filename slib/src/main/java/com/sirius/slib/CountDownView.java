package com.sirius.slib;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public final class CountDownView extends View implements View.OnClickListener {

    public CountDownView(Context context) {
        this(context,null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        /* 启用倒计时 */
        simpleTimer();

    }

    private void init() {
        /* 点击事件注册 */
        setOnClickListener(this);

        /* mPathMeasure */
        mPathMeasure = new PathMeasure();

        /* 初始化中间的数字 */
        mPaintText = new Paint();
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setDither(true);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(mTextSize);
        mPaintText.setColor(Color.argb(255,82,78,78));
        mPaintText.setTextAlign(Paint.Align.CENTER);

        /* 初始化背景 */
        mPaintBg = new Paint();
        mPaintBg.setStyle(Paint.Style.STROKE);
        mPaintBg.setDither(true);
        mPaintBg.setAntiAlias(true);
        mPaintBg.setColor(Color.argb(255,173,216,230));
        mPaintBg.setStrokeWidth(mWidth);

        /* 初始化进度 */
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.argb(255,255,20,147));
        mPaint.setStrokeWidth(mWidth);

        /* Path */
        mPath = new Path();
        mDrawPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = getMyMeasureSpec(widthMeasureSpec, defaultWH);
        int h = getMyMeasureSpec(heightMeasureSpec, defaultWH);
        setMeasuredDimension(w,h);
    }
    /* 默认高宽 */
    private int defaultWH = 120;
    /* 内外圈圆的半径 */
    private float defaultC = 100f;
    private int w,h;
    /* 倒计时的秒数 */
    private long mTime = 10000;
    /* 间隔时间 */
    private long mInterval = 25;
    /* 剩余时间百分比 */
    private float mRemainingTime;
    /* 加载时间百分比 */
    private float mLoadingTime;
    /* 剩余时间 */
    private int mOverTime;
    /* 初始化 */
    private Paint mPaintText,mPaint,mPaintBg;
    /* path */
    private Path mPath,mDrawPath;
    /* pathMeasure管理器 */
    private PathMeasure mPathMeasure;
    /* 内圈、外圈的宽度 */
    private float mWidth = 7f;
    /* 内圈汉字大小 */
    private float mTextSize = 32f;
    /* 倒计时 */
    private CountDownTimer timer;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* 转换坐标0,0 */
        canvas.translate(w/2,h/2);

        /* 汉字 */
        canvas.drawText(mOverTime+"s",0,14,mPaintText);

        /* 内圈 */
        canvas.drawCircle(0,0,defaultC/2,mPaintBg);

        /* 外圈圆形 */
        mPath.addCircle(0,0,defaultC/2, Path.Direction.CCW);
        mPathMeasure.setPath(mPath,false);
        float length = mPathMeasure.getLength();
        float v = length * mLoadingTime; //将要画图多少
        Log.e("TAG","增量 "+v+" 总长度"+length);

        mPathMeasure.getSegment(0,length - v,mDrawPath,true);
        canvas.drawPath(mDrawPath, mPaint);
    }

    public void setStartDraw(float mLoadingTimes,float mRemainingTimes,int mOverTimes){
        this.mLoadingTime = mLoadingTimes;
        this.mRemainingTime = mRemainingTimes;
        this.mOverTime = mOverTimes;

        Log.e("TAG","mLoadingTime="+mLoadingTime+" mRemainingTime="+mRemainingTime);

        invalidate();
    }

    private void simpleTimer(){

        timer=new CountDownTimer(mTime,mInterval) {
            @Override
            public void onTick(long m) {
                /* 剩余的百分比 */
                float f = (float)m/mTime;
                float mLoadingTime = f;
                float mRemainingTime = 1f - mLoadingTime;
                int mOverTime = (int) m / 1000;
                Log.e("TAG","调用一次+"+mOverTime+" 画的百分比 "+mLoadingTime);
                setStartDraw(mLoadingTime,mRemainingTime,mOverTime);
            }

            @Override
            public void onFinish() {
                mStopTimer.over();
            }
        };
        timer.start();
    }

    /* 测量阶段 */
    public int getMyMeasureSpec(int measure,int defaultSize){
        //拆分高/低位
        int size = MeasureSpec.getSize(measure);
        int mode = MeasureSpec.getMode(measure);
        //返回的大小
        int resultSize = 0;
        switch (mode){
            /**
             * <p>相当于warp_parent的时候</p>
             * <p>相当于没有给定大小，设置自定义大小</p>
             */
            case MeasureSpec.AT_MOST:
                resultSize = defaultSize;
                break;
            /**
             * <p>给定了精确值</p>
             * 相当于100dp
             */
            case MeasureSpec.EXACTLY:
                resultSize = Math.max(defaultSize,size);
                break;
            /**
             * <p>未确定状态下使用默认值</p>
             *
             */
            case MeasureSpec.UNSPECIFIED:
                resultSize = defaultSize+0;
                break;
        }
        return resultSize;
    }

    @Override
    public void onClick(View v) {
        timer.cancel();
        mStopTimer.stop();
    }

    /* 回调此事件 */
    private OnClinkStopTimer mStopTimer;

    public void setOnStopTimer(OnClinkStopTimer mStopTimer) {
        this.mStopTimer = mStopTimer;
    }

    public interface OnClinkStopTimer{
        //主动结束
        void stop();
        //自动结束
        void over();
    }
}
