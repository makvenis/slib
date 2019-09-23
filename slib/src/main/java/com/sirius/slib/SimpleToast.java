package com.sirius.slib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义Toast
 */

public class SimpleToast {

    private Toast mToast;

    public SimpleToast(Context context, CharSequence text, int duration) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_layout_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.mMsg);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(view);

    }

    //返回静态方法本身
    public static SimpleToast makeText(Context context, CharSequence text, int duration) {
        return new SimpleToast(context, text, duration);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
