package com.sirius.aar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SimpleNumberView extends LinearLayout {
    /* 初始化状态 */
    private boolean isDefault = true;
    /* 初始化数据值 */
    private int num = 0;

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
    }

    /* 布局设置 */
    private void init() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.simple_number_view, this);
        /* 获取布局 */
        final LinearLayout mDefault = (LinearLayout) view.findViewById(com.sirius.slib.R.id.mSimpleNumberViewLayoutA);
        final LinearLayout mDefaultAdd = (LinearLayout) view.findViewById(com.sirius.slib.R.id.mSimpleNumberViewLayoutB);

        /* 查找布局控件 */
        TextView mAddTxt = (TextView) view.findViewById(com.sirius.slib.R.id.mSimpleNumberAddTxt);
        final TextView mStateTxt = (TextView) view.findViewById(com.sirius.slib.R.id.mSimpleNumberImageCenterTxt);
        ImageView mStateLeft = (ImageView) view.findViewById(com.sirius.slib.R.id.mSimpleNumberImageLeft);
        ImageView mStateRight = (ImageView) view.findViewById(com.sirius.slib.R.id.mSimpleNumberImageRight);

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

        /* 点击事件 */
        if(mA != GONE && mAddTxt != null){
            mAddTxt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 设置默认布局为隐藏状态 */
                    mDefault.setVisibility(GONE);
                    /* 设置第二布局为显示状态 */
                    mDefaultAdd.setVisibility(VISIBLE);
                    num = 1;
                }
            });
        }

        if(mB != GONE && mStateLeft != null && mStateRight != null){
            mStateLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(num == 0){
                        /* 设置默认布局为隐藏状态 */
                        mDefault.setVisibility(VISIBLE);
                        /* 设置第二布局为显示状态 */
                        mDefaultAdd.setVisibility(GONE);
                        num = 0;
                    }else {
                        num -=1;
                        mStateTxt.setText(num+"");
                    }
                }
            });

            mStateRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    num +=1;
                    mStateTxt.setText(num+"");
                }
            });
        }
    }
}
