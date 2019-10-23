package com.sirius.slib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SimpleNumberView extends LinearLayout {
    /* 初始化状态 */
    private boolean isDefault = true;
    /* 初始化数据值 */
    private int num = 0;
    /* 累加的基数 */
    private int indexNum = 1;

    public static final String TAG = "SimpleNumberView";
    private Context mContext;

    public SimpleNumberView(Context context) {
        this(context,null);
    }

    public SimpleNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init();

        /* 点击事件 */
        mAddTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 设置默认布局为隐藏状态 */
                mDefault.setVisibility(GONE);
                /* 设置第二布局为显示状态 */
                mDefaultAdd.setVisibility(VISIBLE);
                num = indexNum;
                /* 设置数字 */
                mStateTxt.setText(num+"");
                /* 回调 */
                listener.number(num);
            }
        });

        mStateLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num == indexNum){
                    /* 设置默认布局为隐藏状态 */
                    mDefault.setVisibility(VISIBLE);
                    /* 设置第二布局为显示状态 */
                    mDefaultAdd.setVisibility(GONE);
                    num = 0;
                }else {
                    num -=indexNum;
                    mStateTxt.setText(num+"");
                }
                listener.number(num);
            }
        });

        mStateRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num +=indexNum;
                mStateTxt.setText(num+"");
                listener.number(num);
            }
        });
    }

    private TextView mAddTxt,mStateTxt;
    private ImageView mStateLeft,mStateRight;
    private LinearLayout mDefault,mDefaultAdd;

    /* 布局设置 */
    private void init() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.simple_number_view, this);
        /* 获取布局 */
        mDefault = (LinearLayout) view.findViewById(R.id.mSimpleNumberViewLayoutA);
        mDefaultAdd = (LinearLayout) view.findViewById(R.id.mSimpleNumberViewLayoutB);

        /* 查找布局控件 */
        mAddTxt = (TextView) view.findViewById(R.id.mSimpleNumberAddTxt);
        mStateTxt = (TextView) view.findViewById(R.id.mSimpleNumberImageCenterTxt);
        mStateLeft = (ImageView) view.findViewById(R.id.mSimpleNumberImageLeft);
        mStateRight = (ImageView) view.findViewById(R.id.mSimpleNumberImageRight);

        /* 获取当前状态 */
        int mA = mDefault.getVisibility();
        int mB = mDefaultAdd.getVisibility();
        /* 假如当前A布局处于非隐藏状态，则默认状态为true */
        if(mA != GONE){
            isDefault=true;
        }
        if(mB != GONE){
            isDefault=false;
        }




    }

    /* 回调事件的数值 */
    private OnChangeNumberListener listener;
    public void SetOnChangeNumberListener(OnChangeNumberListener listener) {
        this.listener = listener;
    }
    public interface OnChangeNumberListener{
        /**
         * <span>回调剩余的数值</span>
         */
        void number(int indexNum);
    }
}
