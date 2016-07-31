package com.wqy.ganhuo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by weiquanyun on 16/7/3.
 * 高宽比固定的ImageView
 */
public class ScaleImageView extends ImageView {

    private float ratio;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(ratio > 1.0f || equals(ratio, 1.0f)) {
            if(width > 0) {
                height = (int) ((float) width / ratio);
                setMeasuredDimension(width, height);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean equals(float a, float b) {
        return Math.abs(a - b) < Math.pow(10, -6);
    }
}
