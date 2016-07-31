package com.wqy.ganhuo.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.wqy.ganhuo.R;

/**
 * Created by weiquanyun on 16/2/28.
 */
public class MaterialCircleProgressBar extends ImageView {

    private static final int KEY_SHADOW_COLOR = 0x1E000000;
    private static final int FILL_SHADOW_COLOR = 0x3D000000;
    // Default background for the progress spinner
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int LARGE_CIRCLE_DIAMETER = 80;
    // PX
    private static final float X_OFFSET = 0f;
    private static final float Y_OFFSET = 1.75f;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final int SHADOW_ELEVATION = 4;

    private static final int SIZE_TYPE_NORMAL = 0;
    private static final int SIZE_TYPE_LARGE = 1;

    private Animation.AnimationListener mListener;
    private int mShadowRadius;

    MaterialProgressDrawable mProgress;

    private boolean mRefreshing;
    private Animation mScaleAnimation;
    private boolean showShapeBg;
    private int sizeType;
    private int circleColor;

    private Animation.AnimationListener mRefreshListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mRefreshing) {
                // Make sure the progress view is fully visible
                mProgress.setAlpha(255);
                mProgress.start();
            } else {
                mProgress.stop();
                setVisibility(View.GONE);
            }
        }
    };

    public MaterialCircleProgressBar(Context context) {
        this(context, null);
    }

    public MaterialCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public MaterialCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialCircleProgressBar, defStyleAttr, defStyleRes);
        showShapeBg = typedArray.getBoolean(R.styleable.MaterialCircleProgressBar_needShapeBg, false);
        sizeType = typedArray.getInt(R.styleable.MaterialCircleProgressBar_shapeSize, 0);
        circleColor = typedArray.getColor(R.styleable.MaterialCircleProgressBar_circleColor, CIRCLE_BG_LIGHT);
        typedArray.recycle();
        if(sizeType == SIZE_TYPE_LARGE) {
            init(context, CIRCLE_BG_LIGHT, LARGE_CIRCLE_DIAMETER / 2);
        } else if(sizeType == SIZE_TYPE_NORMAL) {
            init(context, CIRCLE_BG_LIGHT, CIRCLE_DIAMETER / 2);
        }
    }

    private void init(Context context, int color, final float radius) {
        final float density = getContext().getResources().getDisplayMetrics().density;
        final int diameter = (int) (radius * density * 2);
        final int shadowYOffset = (int) (density * Y_OFFSET);
        final int shadowXOffset = (int) (density * X_OFFSET);

        mShadowRadius = (int) (density * SHADOW_RADIUS);

        ShapeDrawable circle;
        if (elevationSupported()) {
            circle = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, SHADOW_ELEVATION * density);
        } else {
            OvalShape oval = new OvalShadow(mShadowRadius, diameter);
            circle = new ShapeDrawable(oval);
            ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());
            circle.getPaint().setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset,
                    KEY_SHADOW_COLOR);
            final int padding = mShadowRadius;
            // set padding so the inner image sits correctly within the shadow.
            setPadding(padding, padding, padding, padding);
        }
        circle.getPaint().setColor(color);
        if(showShapeBg) {
            setBackgroundDrawable(circle);
        }
        mProgress = new MaterialProgressDrawable(getContext(), this);
        mProgress.setBackgroundColor(circleColor);
        if(sizeType == SIZE_TYPE_LARGE) {
            mProgress.updateSizes(MaterialProgressDrawable.LARGE);
        }
        setImageDrawable(mProgress);
    }

    private boolean elevationSupported() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

    public void startProgress() {
        mRefreshing = true;
        startScaleUpAnimation(mRefreshListener);
    }

    public void stopProgress() {
        mRefreshing = false;
        clearAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!elevationSupported()) {
            setMeasuredDimension(getMeasuredWidth() + mShadowRadius*2, getMeasuredHeight()
                    + mShadowRadius*2);
        }
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }

    /**
     * Update the background color of the circle image view.
     *
     * @param colorRes Id of a color resource.
     */
    public void setBackgroundColorRes(int colorRes) {
        setBackgroundColor(getContext().getResources().getColor(colorRes));
    }

    @Override
    public void setBackgroundColor(int color) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(color);
        }
    }

    private class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint;
        private int mCircleDiameter;

        public OvalShadow(int shadowRadius, int circleDiameter) {
            super();
            mShadowPaint = new Paint();
            mShadowRadius = shadowRadius;
            mCircleDiameter = circleDiameter;
            mRadialGradient = new RadialGradient(mCircleDiameter / 2, mCircleDiameter / 2,
                    mShadowRadius, new int[] {
                    FILL_SHADOW_COLOR, Color.TRANSPARENT
            }, null, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mRadialGradient);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            final int viewWidth = MaterialCircleProgressBar.this.getWidth();
            final int viewHeight = MaterialCircleProgressBar.this.getHeight();
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, (mCircleDiameter / 2 + mShadowRadius),
                    mShadowPaint);
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, (mCircleDiameter / 2), paint);
        }
    }

    private void startScaleUpAnimation(Animation.AnimationListener listener) {
        setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // Pre API 11, alpha is used in place of scale up to show the
            // progress circle appearing.
            // Don't adjust the alpha during appearance otherwise.
            mProgress.setAlpha(255);
        }
        mScaleAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(interpolatedTime);
            }
        };
        mScaleAnimation.setDuration(400);
        if (listener != null) {
            setAnimationListener(listener);
        }
        clearAnimation();
        startAnimation(mScaleAnimation);
    }

    private void setAnimationProgress(float progress) {
        if (isAlphaUsedForScale()) {
            setColorViewAlpha((int) (progress * 255));
        } else {
            ViewCompat.setScaleX(this, progress);
            ViewCompat.setScaleY(this, progress);
        }
    }

    private void setColorViewAlpha(int targetAlpha) {
        getBackground().setAlpha(targetAlpha);
        mProgress.setAlpha(targetAlpha);
    }

    private boolean isAlphaUsedForScale() {
        return android.os.Build.VERSION.SDK_INT < 11;
    }

    public void setColorSchemeColors(int ...colors) {
        mProgress.setColorSchemeColors(colors);
    }

    public void show() {
        startProgress();
        setVisibility(VISIBLE);
    }

    public void hide() {
        stopProgress();
        setVisibility(GONE);
    }
}
