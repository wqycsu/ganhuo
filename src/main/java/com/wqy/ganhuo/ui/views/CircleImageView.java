package com.wqy.ganhuo.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.wqy.ganhuo.R;

/**
 * 圆形ImageView
 */
public class CircleImageView extends View {

    private Bitmap mBitmapRes;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private int mBackgroundColor;

    public CircleImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleImageView, defStyle, 0);

        if (a.hasValue(R.styleable.CircleImageView_src)) {
            mBitmapRes = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.CircleImageView_src, 0));
        }

        if(a.hasValue(R.styleable.CircleImageView_mColor) && mBitmapRes == null) {
            mBackgroundColor = getResources().getColor(a.getResourceId(R.styleable.CircleImageView_mColor, 0));
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        //计算宽度
        int width = getMeasuredWidth();
        if(specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            if(mBitmapRes == null) {
                width = specSize;
            } else {
                int desireImageWidth = getPaddingLeft() + getPaddingRight() + mBitmapRes.getWidth();
                if (specMode == MeasureSpec.AT_MOST) {
                    width = Math.min(desireImageWidth, specSize);
                } else {
                    width = desireImageWidth;
                }
            }
        }

        //计算高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = getMeasuredHeight();
        if(specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            if(mBitmapRes == null) {
                width = specSize;
            } else {
                int desireImageHeight = getPaddingTop() + getPaddingBottom() + mBitmapRes.getHeight();
                if (specMode == MeasureSpec.AT_MOST) {
                    height = Math.min(desireImageHeight, specSize);
                } else {
                    height = desireImageHeight;
                }
            }
        }
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(createCircleImage(mBitmapRes, getWidth()), 0, 0, paint);
    }

    /**
     * 根据原图和宽度创建圆形图片
     *
     * @param source
     * @param min
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
